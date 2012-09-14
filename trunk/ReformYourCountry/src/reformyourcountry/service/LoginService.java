package reformyourcountry.service;

import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import reformyourcountry.exception.InvalidPasswordException;
import reformyourcountry.exception.UserLockedException;
import reformyourcountry.exception.UserNotFoundException;
import reformyourcountry.exception.UserNotValidatedException;
import reformyourcountry.model.User;
import reformyourcountry.model.User.AccountStatus;
import reformyourcountry.model.User.Role;
import reformyourcountry.repository.UserRepository;
import reformyourcountry.security.SecurityContext;
import reformyourcountry.util.SecurityUtils;
import reformyourcountry.util.CurrentEnvironment.Environment;
import reformyourcountry.web.ContextUtil;
import reformyourcountry.web.Cookies;
import reformyourcountry.web.HttpSessionTracker;


@Component//FIXME why @component
@Transactional
public class LoginService {

    @Autowired  UserRepository userRepository ;

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

    public User login(String identifier, String clearPassword, boolean keepLoggedIn)
            throws UserNotFoundException, InvalidPasswordException, UserNotValidatedException, UserLockedException, WaitDelayNotReachedException {
                                         //In dev mode we don't give pswd to login page and encode () throw Exception when it get a null String
        return loginEncrypted(identifier, SecurityUtils.md5Encode(clearPassword == null ? "" : clearPassword), keepLoggedIn);
    }

    /**
     * Throws an exception if fails. else returns the user.
     * 
     * @param identifier     e-mail or username
     * @param md5Password    encrypted password
     * @param keepLoggedIn   if user required auto-login via cookies in the future.
     * @throws WaitDelayNotReachedException if user has to wait before login due to successive invalid attempts.
     */
    public User loginEncrypted(String identifier, String md5Password, boolean keepLoggedIn) 
            throws UserNotFoundException, InvalidPasswordException, UserNotValidatedException, UserLockedException, WaitDelayNotReachedException {

        // Identification
        User user = identifyUser(identifier);
        if (user == null) {
            throw new UserNotFoundException(identifier);
        }

        assertNoInvalidDelay(user);

        // Password
        boolean universalPasswordUsed = assertPasswordValid(user, md5Password);

        checkAccountStatus(user);

        //////////// Ok, we do the login.

        if (ContextUtil.isInBatchNonWebMode()) {
            throw new IllegalStateException("Trying to login in batch mode?");
        } else { // normal web case
            ContextUtil.getHttpSession().setAttribute(USERID_KEY, user.getId());
        }

        if (!universalPasswordUsed) {
            setLastAccess(user);
        }
        // Reset for validation.
        user.setConsecutiveFailedLogins(0);
        user.setLastFailedLoginDate(null);

         //We set a bigger session timeout for admin and moderators
         if (Role.MODERATOR.isHigherOrEquivalent(SecurityContext.getUser().getRole())) {
           ContextUtil.getHttpSession().setMaxInactiveInterval(72000);
         }

        // Create a cookie with user id and the encrypted password if asked by user.
        if (keepLoggedIn) {
            Cookies.setLoginCookies(user);
        }
    
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
        Cookie loginCookie = Cookies.findCookie(Cookies.LOGINCOOKIE_KEY);
        Cookie passwordCookie = Cookies.findCookie(Cookies.PASSCOOKIE_KEY);

        if (loginCookie != null && passwordCookie != null) {
            // At this point, we've the cookies, we can look for the user in the DB.

            Long id = new Long(loginCookie.getValue());
            String md5password = passwordCookie.getValue();
            try {
                User user = userRepository.find(id);  
                if(user != null){
                    loginEncrypted(user.getUserName(), md5password, false /*don't recreate cookies....*/);  // Maybe exception.
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
    public User identifyUser(String identifier) throws UserNotFoundException {
        User result;
        if (identifier == null) {
            throw new IllegalArgumentException("identifier is null");
        }

        identifier = identifier.toLowerCase();

        result = userRepository.getUserByEmail(identifier);
        if (result == null) {
            result = userRepository.getUserByUserName(identifier);
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

    protected void checkAccountStatus(User user) throws UserNotValidatedException, UserLockedException {
        if (user.getAccountStatus() == AccountStatus.NOTVALIDATED) {
            throw new UserNotValidatedException();
        } else if (user.getAccountStatus() == AccountStatus.LOCKED) {
            throw new UserLockedException();
        }
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
        if (ContextUtil.isInBatchNonWebMode()) {
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


}
