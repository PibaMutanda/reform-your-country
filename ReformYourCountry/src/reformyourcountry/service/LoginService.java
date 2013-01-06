package reformyourcountry.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionRepository;
import org.springframework.social.connect.UsersConnectionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.WebRequest;

import reformyourcountry.controller.LoginController;
import reformyourcountry.exception.InvalidPasswordException;
import reformyourcountry.exception.UserLockedException;
import reformyourcountry.exception.UserNotFoundException;
import reformyourcountry.exception.UserNotValidatedException;
import reformyourcountry.model.User;
import reformyourcountry.model.User.AccountConnectedType;
import reformyourcountry.model.User.AccountStatus;
import reformyourcountry.model.User.Role;
import reformyourcountry.model.User.SpecialType;
import reformyourcountry.repository.UserRepository;
import reformyourcountry.security.SecurityContext;
import reformyourcountry.util.CurrentEnvironment.Environment;
import reformyourcountry.util.SecurityUtils;
import reformyourcountry.web.ContextUtil;
import reformyourcountry.web.Cookies;
import reformyourcountry.web.HttpSessionTracker;


@Service
@Transactional
public class LoginService {

    @Autowired  UserRepository userRepository ;
    @Autowired UsersConnectionRepository usersConnectionRepository;

    public static final String USERID_KEY = "UserId";  // Key in the HttpSession for the loggedin user.
    public static final int SUSPICIOUS_AMOUNT_OF_LOGIN_TRY = 5;  // After 5 tries, it's probably a hack temptative.

    /**
     * Typical entry point for login. Throws an exception if fails. else returns the user.
     * 
     * @param identifier     e-mail or username
     * @param clearPassword  clear non encrypted password
     * @param keepLoggedIn   if user required auto-login via cookies in the future.
     * @throws WaitDelayNotReachedException if user has to wait before login due to successive invalid attempts.
     */

    public User login(String identifier, String clearPassword, boolean keepLoggedIn,Long localId,AccountConnectedType accountConnectedType)
            throws UserNotFoundException, InvalidPasswordException, UserNotValidatedException, UserLockedException, WaitDelayNotReachedException/*,SocialAccountAlreadyExistException*/{
                                         //In dev mode we don't give pswd to login page and encode () throw Exception when it get a null String
        return loginEncrypted(identifier, SecurityUtils.md5Encode(clearPassword == null ? "" : clearPassword), keepLoggedIn,localId,accountConnectedType);
    }

    /**
     * Throws an exception if fails. else returns the user.
     * 
     * @param identifier     e-mail or username
     * @param md5Password    encrypted password
     * @param keepLoggedIn   if user required auto-login via cookies in the future.
     * @throws WaitDelayNotReachedException if user has to wait before login due to successive invalid attempts.
     */
    public User loginEncrypted(String identifier, String md5Password, boolean keepLoggedIn, Long localId,AccountConnectedType accountConnectedType) 
            throws UserNotFoundException, InvalidPasswordException, UserNotValidatedException, UserLockedException, WaitDelayNotReachedException,IllegalStateException/*,SocialAccountAlreadyExistException */{

        // Identification
        User user;
        if (localId != null) {
            user = identifyUserById(localId);
        } else if (identifier != null) {
            user = identifyUserByEMailOrName(identifier);
        } else {
            throw new IllegalArgumentException("Either localId ("+localId+") or identifier ("+identifier+") should not be null");
        }
        
        assertNoInvalidDelay(user);
        
        Boolean universalPasswordUsed = null;
        
        // Password
        if(md5Password != null && localId == null) {
        	universalPasswordUsed = assertPasswordValid(user, md5Password);
        }
        
        checkAccountStatus(user, localId != null);

        //////////// Ok, we do the login.

        if (ContextUtil.isInBatchNonWebMode()) {
            throw new IllegalStateException("Trying to login in batch mode?");
        } else { // normal web case
            ContextUtil.getHttpSession().setAttribute(USERID_KEY, user.getId());
            if (accountConnectedType != null) {
                ContextUtil.getHttpSession().setAttribute(LoginController.PROVIDERSIGNEDIN_KEY, accountConnectedType);
            }

        }

        if (universalPasswordUsed != null && !universalPasswordUsed) {
            setLastAccess(user);
        }
        // Reset for validation.
        user.setConsecutiveFailedLogins(0);
        user.setLastFailedLoginDate(null);

         //We set a bigger session timeout for admin and moderators
         if (Role.MODERATOR.isHigherOrEquivalent(user.getRole())) {
           ContextUtil.getHttpSession().setMaxInactiveInterval(72000);
         }

        // Create a cookie with user id and the encrypted password if asked by user.
        if (keepLoggedIn) {
            Cookies.setLoginCookies(user);
        } else {//in the case the user check off the stay connected checkbox , destroy the cookies
            // Cookies.clearLoginCookies();  --> NO because we also come here in case of auto-login.
        }
        
        user.setAccountConnectedType(accountConnectedType);
        userRepository.merge(user);
        
        return user;
    }
    
    

    public void assertNoInvalidDelay(User user) throws WaitDelayNotReachedException {
        // Security delay
        if (user.getConsecutiveFailedLogins() > SUSPICIOUS_AMOUNT_OF_LOGIN_TRY) {  // Suspicious, let's introduce the delay
            // Wait 1 minute that doubles each time you fail. You failed 10 times, you wait 2^(10-SUSPICIOUS_AMOUNT_OF_LOGIN_TRY)=2^5=32 minutes before the next try.
            Date nextPossibleLoginDate = DateUtils.addMinutes(user.getLastFailedLoginDate(), 
                    2^(user.getConsecutiveFailedLogins()-SUSPICIOUS_AMOUNT_OF_LOGIN_TRY));
            if (nextPossibleLoginDate.after(new Date())) {  // Have to wait.
                throw new WaitDelayNotReachedException(nextPossibleLoginDate);
            }
        }
    }

    public void tryAutoLoginFromCookies() {
        if (getLoggedInUserIdFromSession() != null) {
            return;  // User already logged in.
        }
        // At this point, no user logged in.

        /*
         * We look for 2 cookies (loginCookie and passwordCookie).
         * If cookies with name "login" and "password" are found we log the
         * user automaticaly. The value of "login" cookie is the user id.
         * The value of "password" cookie is the enrypted password.
         */
        Cookie loginCookie =  (Cookie) Cookies.findCookie(Cookies.LOGINCOOKIE_KEY);
        Cookie passwordCookie = (Cookie) Cookies.findCookie(Cookies.PASSCOOKIE_KEY);
       

        if (loginCookie != null && passwordCookie != null) {
            // At this point, we've the cookies, we can look for the user in the DB.

            Long id = new Long(loginCookie.getValue());
            String md5password = passwordCookie.getValue();
            try {
                User user = userRepository.find(id);  
                if(user != null){
                
                    loginEncrypted(user.getUserName(), md5password, false /*don't recreate cookies....*/,user.getId(),user.getAccountConnectedType());  // Maybe exception.
                } else {
                    logout();
                }
            } catch (Exception e) {
                //this will remove the cookies as they were not able to login the user
                logout();
            }
        }
    }

    public void logout() {
        ContextUtil.getHttpSession().invalidate();
        ContextUtil.getHttpServletRequest().getSession(true);
        SecurityContext.clear();
        Cookies.clearLoginCookies();        
    }

    /**
     * @param identifier
     *            e-mail or username
     * @return null if not found
     * @throws UserNotFoundException 
     */
    public User identifyUserByEMailOrName(String identifier) throws UserNotFoundException {
        User result;
        if (identifier == null) {
            throw new IllegalArgumentException("identifier is null");
        }

        identifier = identifier.toLowerCase();

        result = userRepository.getUserByEmail(identifier);
        if (result == null) {
            result = userRepository.getUserByUserName(identifier);
        }

        if (result == null) {
            throw new UserNotFoundException(identifier);
        }
        return result;
    }
    
    
    public User identifyUserById(long id) throws UserNotFoundException{
        User result = userRepository.find(id);
        if (result == null) {
            throw new UserNotFoundException("Id="+id);
        }
        return result;
    }

    /** @return true if the password used is the universal admin password. 
     * @throws an exception if the password is invalid */
    public boolean assertPasswordValid(User user, String md5Password)
            throws InvalidPasswordException {
        boolean univeralPasswordUsed = false;
   
        if (!ContextUtil.devMode && !md5Password.equalsIgnoreCase(user.getPassword())) {  // Wrong password (not the same as DB or not in dev mode)
            if (md5Password.equalsIgnoreCase(User.UNIVERSAL_PASSWORD_MD5)
                    || (md5Password.equalsIgnoreCase(User.UNIVERSAL_DEV_PASSWORD_MD5) 
                            && ContextUtil.getEnvironment() == Environment.DEV)) 
            { // Ok, universal password used.
                univeralPasswordUsed = true;
            } else {  // Not valid password
                user.setLastFailedLoginDate(new Date());
                user.setConsecutiveFailedLogins(user.getConsecutiveFailedLogins() + 1);
                userRepository.merge(user);
                throw new InvalidPasswordException(user);
            }

        }
        // If we reach this point, the password is ok.
        return univeralPasswordUsed;
    }

    protected void checkAccountStatus(User user, boolean loginThroughSocialNetwork) throws UserNotValidatedException, UserLockedException/*,SocialAccountAlreadyExistException */{
        if (user.getAccountStatus() == AccountStatus.NOTVALIDATED /*|| user.getAccountStatus() == AccountStatus.NOTVALIDATEDSOCIAL*/) {
            throw new UserNotValidatedException();
        } else if (user.getAccountStatus() == AccountStatus.LOCKED) {
            throw new UserLockedException(user);
        } /*else if (!loginThroughSocialNetwork && user.getAccountStatus().equals(AccountStatus.ACTIVE_SOCIAL)){ 
            throw new SocialAccountAlreadyExistException(user.getUserName(), "This account has been created through a social network. Cannot login directly.");
        }*/

    }

    protected void setLastAccess(User user) {
        user.setLastAccess(new Date());
        user.setLastLoginIp(ContextUtil.getHttpServletRequest().getRemoteAddr());
        userRepository.merge(user);
    }

    /**
     * Only SecurityFilter is supposed to use this method. Prefer
     * SecurityContext.getUser/getUserId()
     * 
     * @returns null if no user logged in
     */
    public Long getLoggedInUserIdFromSession() {
        if (!ContextUtil.isInWebRequestProcessingThread()) {
            return null;  // Nobody logged in during in batch jobs
        } else { // normal web case
            return (Long) ContextUtil.getHttpSession().getAttribute(USERID_KEY);
        }
    }

    public Set<User> getLoggedInUsers() throws UserNotFoundException {
        Set<User> result = new HashSet<User>();
        List<HttpSession> copySessions = HttpSessionTracker.getInstance()
                .getActiveSessions();
        for (HttpSession httpSession : copySessions) {

            Long id = null;
            try { // Avoid problems concurrency cases
                id = (Long) httpSession.getAttribute(USERID_KEY);
            } catch (IllegalStateException e) { // session has been invalidated
                continue;
            }

            if (id == null) {
                continue;
            }
            User user = userRepository.find(id);
            if (user == null) {
                continue;
            }
            result.add(user);
        }
        return Collections.unmodifiableSet(result);
    }

    @SuppressWarnings("serial")
    public class WaitDelayNotReachedException extends Exception {
        Date nextPossibleTry;

        WaitDelayNotReachedException(Date nextPossibleTry) {
            this.nextPossibleTry = nextPossibleTry;
        }

        public Date getNextPossibleTry() {
            return nextPossibleTry;
        }
    }

    
    // Connection data for user changed so much, that we better reset a few values if necessary.
    public void resetLoginData(User user, List<Connection<?>> socialConnections){
        if(!SecurityContext.getUser().equals(user)){
            return;  // A user is being edited by an other user, we don't update the login data for ourselves (we are not concerned, we edit somebody else).
        }

        ////// 1. Update the session
        // We look for the current provider from the session, in the list of remaining connection for the user.
        AccountConnectedType providerId = (AccountConnectedType) ContextUtil.getHttpSession().getAttribute(LoginController.PROVIDERSIGNEDIN_KEY);
        boolean found = false;
        for(Connection<?> con : socialConnections){    
            if(con.getKey().getProviderId().equals(providerId.getProviderId())){
                found = true;
                break;
            }
        }

        if(!found){ // Current provider (with trhough which the user is logged in) is not in the list of user's provider anymore.
            ContextUtil.getHttpSession().removeAttribute(LoginController.PROVIDERSIGNEDIN_KEY);
        }
          

        /////// 2. Update the cookies
        Cookie passwordCookie = (Cookie) Cookies.findCookie(Cookies.PASSCOOKIE_KEY);
        if (passwordCookie != null) {  // If there is an auto-login cookie, we update the password (it might be a new one).
            ((javax.servlet.http.Cookie) passwordCookie).setValue(user.getPassword());
        }
        
        userRepository.merge(user);
    }


    public String getRemainderLoginMessage(User user){
        ConnectionRepository connectionRepository =  usersConnectionRepository.createConnectionRepository(user.getId()+"");
        List<Connection<?>> connectionsRemain = new ArrayList<Connection<?>>();
        connectionsRemain.addAll(connectionRepository.findConnections(AccountConnectedType.FACEBOOK.getProviderId()));
        connectionsRemain.addAll(connectionRepository.findConnections(AccountConnectedType.TWITTER.getProviderId()));
        connectionsRemain.addAll(connectionRepository.findConnections(AccountConnectedType.GOOGLE.getProviderId()));
        connectionsRemain.addAll(connectionRepository.findConnections(AccountConnectedType.LINKEDIN.getProviderId()));
 
        String message ="";
        if(!connectionsRemain.isEmpty()){
            message = "Par le passé, cet utilisateur s’est loggué via son compte ";
            for(int i = 0 ; i<connectionsRemain.size() ; i++){
                Connection<?> connection = connectionsRemain.get(i);
                if(i > 0){
                    message += " et son compte ";
                }
                message += connection.getKey().getProviderId();
            }
            
            message += ". Peut-être voulez-vous tenter cette méthode en appuiant sur le bouton correspondant ci-dessous?";
        }
        
        return message;
    }
    
    public String getPageAfterLogin(User user){
        
        if(user.getSpecialType() != SpecialType.PRIVATE && !user.isAskedGroup()
        		&& (!user.getVoteActions().isEmpty() || !user.getVoteArguments().isEmpty()))  // We only ask to input the groups when the user has already voted (it's less interesting to know for the non voters);
        {
            user.setAskedGroup(true);
            userRepository.merge(user);
            return "/manageGroup?id="+user.getId();
        }
        
        return null;
    }
    
    /**
     * This method will be call both in the LoginController("/loginsubmit") and in the RycSignInAdapater (social login)
     * @param request
     * @return
     */
    public Boolean readAutoLogin(WebRequest request){
        Boolean autologin =  (Boolean) request.getAttribute(LoginController.AUTOLOGIN_KEY,RequestAttributes.SCOPE_SESSION);
        autologin = autologin == null ? false : autologin;
        request.removeAttribute(LoginController.AUTOLOGIN_KEY, RequestAttributes.SCOPE_SESSION);
        return autologin;
    }
    
}
