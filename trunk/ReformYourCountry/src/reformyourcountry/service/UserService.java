package reformyourcountry.service;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import javax.imageio.ImageIO;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionRepository;
import org.springframework.social.connect.UsersConnectionRepository;
import org.springframework.social.connect.web.ProviderSignInUtils;
import org.springframework.social.facebook.api.Facebook;
import org.springframework.social.facebook.api.ImageType;
import org.springframework.social.google.api.Google;
import org.springframework.social.twitter.api.ImageSize;
import org.springframework.social.twitter.api.Twitter;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.MultiValueMap;
import org.springframework.web.context.request.WebRequest;

import reformyourcountry.exception.InvalidPasswordException;
import reformyourcountry.exception.UserAlreadyExistsException;
import reformyourcountry.exception.UserAlreadyExistsException.IdentifierType;
import reformyourcountry.exception.UserLockedException;
import reformyourcountry.exception.UserNotFoundException;
import reformyourcountry.exception.UserNotValidatedException;
import reformyourcountry.mail.MailCategory;
import reformyourcountry.mail.MailType;
import reformyourcountry.model.Badge;
import reformyourcountry.model.BadgeType;
import reformyourcountry.model.User;
import reformyourcountry.model.User.AccountConnectedType;
import reformyourcountry.model.User.AccountStatus;
import reformyourcountry.model.User.Role;
import reformyourcountry.model.User.SpecialType;
import reformyourcountry.repository.UserRepository;
import reformyourcountry.security.SecurityContext;
import reformyourcountry.service.LoginService.WaitDelayNotReachedException;
import reformyourcountry.util.CurrentEnvironment;
import reformyourcountry.util.DateUtil;
import reformyourcountry.util.FileUtil;
import reformyourcountry.util.HTMLUtil;
import reformyourcountry.util.ImageUtil;
import reformyourcountry.util.Logger;
import reformyourcountry.util.NotificationUtil;
import reformyourcountry.util.SecurityUtils;
import reformyourcountry.web.ContextUtil;
import reformyourcountry.web.UrlUtil;
import reformyourcountry.web.UrlUtil.Mode;

@Transactional
@Service(value="userService")
@Scope("singleton")
public class UserService {

    // Constant values used in the automatic calculation of the influence factor
    public static final int INFLUENCE_PERCENTAGE_OF_RELEASED_QUESTIONS_WITH_NO_CONCERNS = 90;
    public static final int INFLUENCE_AMOUNT_OF_RELEASED_QUESTIONS_WITH_NO_CONCERNS = 20;
    public static final int INFLUENCE_AMOUNT_OF_RELEASED_QUESTIONS = 50;
    // private TemplateService templateService;
    // private String registrationTemplate;
    // private String passwordRecoveryTemplate;

    @Logger Log log;
    
    @Autowired   private UserRepository userRepository;
    @Autowired   private MailService mailService;
    @Autowired   private LoginService loginService;
    @Autowired   private UsersConnectionRepository usersConnectionRepository;
    @Autowired   private CurrentEnvironment currentEnvironment;
    @Autowired  IndexManagerService indexManagerService;
    /**
     * Register a user and sends a validation mail.
     * 
     * @param directValidation
     *            : validate an account directly without send a mail
     */
    public User registerUser(boolean directValidation, String username, String passwordInClear, String mail, boolean isSocial) throws UserAlreadyExistsException {
        //exception if html is dangerous
    	//TODO review
    	if(!HTMLUtil.isHtmlSecure(username)) {
    		throw new RuntimeException("String used for register a user is dangerous : " + username);
    	}
    	
        if (userRepository.getUserByUserName(username) != null)    {
            throw new UserAlreadyExistsException(IdentifierType.USERNAME, username);
        }
       
        if (userRepository.getUserByEmail(mail) != null){
            throw new UserAlreadyExistsException(IdentifierType.MAIL, mail);
        }
        
        User newUser = new User();
        newUser.setUserName(username);
        newUser.setPassword(SecurityUtils.md5Encode(passwordInClear));
        newUser.setMail(mail);
        newUser.setSpecialType(SpecialType.PRIVATE);
        newUser.setAskedGroup(false);
        
        //// Validation mail.
        String base = newUser.getMail() + newUser.getPassword() + Math.random();  // Could be pure random.
        newUser.setValidationCode(SecurityUtils.md5Encode(base.toString()));

        if (directValidation) {
            newUser.setAccountStatus(AccountStatus.ACTIVE);
        } else {
            newUser.setAccountStatus(AccountStatus.NOTVALIDATED);
        }
                    
        newUser.setPasswordKnownByTheUser(!isSocial);
            
        //// Save the user in the db
        userRepository.persist(newUser);

        // All is ok lets eventually send a validation email
        if (!directValidation) {
            sendRegistrationValidationMail(newUser);
        }

        ///// update index
    
           indexManagerService.add(newUser);
      
        
        
        return newUser;
    }


    public void sendRegistrationValidationMail(User user) {

        String validationUrl = UrlUtil.getAbsoluteUrl( "validationsubmit?code=" + user.getValidationCode());
        String htmlMessage = "Bienvenue sur "+ currentEnvironment.getSiteName()+", " + user.getUserName() + "." + 
                "<br/>Il reste une dernière étape pour créer votre compte : " +
                "veuillez s'il vous plait cliquer sur le lien ci-dessous pour valider votre e-mail !" +
                "<br/><a href='"+ validationUrl + "'>" + validationUrl + "</a>" +
                "<br/><br/>Si vous rencontrez un problème, essayez de copier/coller l'URL dans votre navigateur (au lieu de cliquer sur le lien), ou en dernier recours " +
                "<a href='" + UrlUtil.getAbsoluteUrl("contact") + "'>nous contacter</a>" + 
                "<br/><br/>merci de vous être inscrit sur "+currentEnvironment.getSiteName()+".";
        mailService.sendMail(user, "Votre nouveau compte", htmlMessage, MailType.IMMEDIATE, MailCategory.USER);
        
        log.debug("mail sent: " + htmlMessage);  
    }

    public void generateNewPasswordForUserAndSendEmail(User user) {
        // We generate a password
        String newPassword = SecurityUtils.generateRandomPassword(8, 12);
        // we set the new password to the user
        user.setPassword(SecurityUtils.md5Encode(newPassword));
        user.setPasswordKnownByTheUser(true);  // it's a random pwd, but the user knows it.
        userRepository.merge(user);

        mailService.sendMail(user.getMail(), "Recupération de mot de passe",  
                "Vous avez demandé un nouveau mot de passe pour votre compte '"+ user.getUserName()+"' sur "+ContextUtil.servletContext.getAttribute("p_website_name")+"<br/>" + 
                        "Nous ne pouvons pas vous transmettre votre ancien mot de passe parce que nous ne l'enregistrons pas directement, pour des raisons de sécurité et de protection de votre vie privée, il est crypté de mannière irréverssible.<br/><br/>"+
                      
                        "Voici votre nouveau mot de passe : "+ newPassword + 
                        "<ol>" +  
                        "<li>le mot de passe respecte la casse des caractères,</li>" +                  
                        "<li>Il s'agit d'un mot de passe auto-généré, libre à vous de le changer sur votre <a href='"+UrlUtil.getAbsoluteUrl("user/"+user.getUserName(),Mode.DEV)+"'>page de profile</a>.</li>" +
                        "</ol>", 
                        MailType.IMMEDIATE, MailCategory.USER);

    }

    /** Change the name of the user and note it in the log */
    public void changeUserName(User user, String newUserName, String newFirstName, String newLastName) {
        if (user.getNameChangeLog() == null) {
            user.addNameChangeLog("Previous name: " + user.getFirstName()
                    + " - " + user.getLastName() + " - " + user.getUserName());
        }
        user.addNameChangeLog("\n"+ DateUtil.formatyyyyMMdd(new Date()) + ": "
                + newFirstName + " - " + newLastName + " - " + newUserName);
        user.setFirstName(newFirstName);
        user.setLastName(newLastName);
        user.setUserName(newUserName);
        userRepository.merge(user);
    }

    public void addOrUpdateUserImage(User user, BufferedImage image){
        try {


            ImageUtil.saveImageToFileAsJPEG(image,  
                    FileUtil.getGenFolderPath(currentEnvironment) + FileUtil.USER_SUB_FOLDER + FileUtil.USER_ORIGINAL_SUB_FOLDER, user.getId() + ".jpg", 0.9f);

            BufferedImage resizedImage = ImageUtil.scale(ImageUtil.convertIntoByteArrayInputStream(image),120 * 200, 200, 200);

            ImageUtil.saveImageToFileAsJPEG(resizedImage,  
                    FileUtil.getGenFolderPath(currentEnvironment) + FileUtil.USER_SUB_FOLDER +FileUtil.USER_RESIZED_SUB_FOLDER+ FileUtil.USER_RESIZED_LARGE_SUB_FOLDER, user.getId() + ".jpg", 0.9f);


            user.setPicture(true);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    public void addOrUpdateUserImageFromSocialProvider(User user ,Connection<?> connection){
        AccountConnectedType type = AccountConnectedType.getProviderType(connection.getKey().getProviderId());      
        byte[] userImage =null;
        switch(type){     
        case FACEBOOK :                                       
            Facebook facebook = (Facebook) connection.getApi();
            userImage =  facebook.userOperations().getUserProfileImage(ImageType.NORMAL);
            if(userImage != null){
            try {
                addOrUpdateUserImage(user, ImageIO.read(new ByteArrayInputStream(userImage)));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }}else{
                NotificationUtil.addNotificationMessage("Aucune image n'est disponnible depuis votre compte "+connection.getKey().getProviderId());
            }
            break;
        case TWITTER:                
            Twitter twitter = (Twitter) connection.getApi();
            // At 30/11/2012 the method getUserProfileImage() seem not work on twitter so we get the image by its url.
          //  userImage =  twitter.userOperations().getUserProfileImage(twitter.userOperations().getScreenName(),ImageSize.ORIGINAL);
            String urlImage = twitter.userOperations().getUserProfile().getProfileImageUrl();
            urlImage = urlImage.replace("_normal", "");
            if(urlImage != null){
            BufferedImage image = ImageUtil.readImage(urlImage);
            // addOrUpdateUserImage(user, ImageIO.read(new ByteArrayInputStream(userImage)));
            addOrUpdateUserImage(user, image);
            }else{
                NotificationUtil.addNotificationMessage("Aucune image n'est disponnible depuis votre compte "+connection.getKey().getProviderId());
            }
            break;

        case GOOGLE : 
            Google google = (Google) connection.getApi();
            String urlProfil = google.userOperations().getUserProfile().getProfilePictureUrl();
            if (urlProfil != null) { // Null if Google has no image.
                BufferedImage image = ImageUtil.readImage(urlProfil);
                addOrUpdateUserImage(user,image);
            }else{
                NotificationUtil.addNotificationMessage("Aucune image n'est disponnible depuis votre compte "+connection.getKey().getProviderId());
            }
            break;

        default : throw new RuntimeException("invalid social provider name submitted");
        }

    }
    

       
    
   public void unsocialiseUser(User user, String newPassword){
       // We remove all uer's social connections.
       ConnectionRepository connectionRepository = usersConnectionRepository.createConnectionRepository(user.getId()+"");
       MultiValueMap<String,Connection<?>> connectionsByProvider = connectionRepository.findAllConnections();
       for(List<Connection<?>> collection : connectionsByProvider.values()) {
           for(Connection<?> con : collection){
               connectionRepository.removeConnection(con.getKey());  
           }
       }
       
       // We make this user a "normal" (not a "through-facebook" user).
       user.setPassword(SecurityUtils.md5Encode(newPassword));
       user.setPasswordKnownByTheUser(true);
       user.setAccountStatus(AccountStatus.ACTIVE);
       user.setAccountConnectedType(AccountConnectedType.LOCAL);

       userRepository.merge(user);
            
       loginService.resetLoginData(user, new ArrayList<Connection<?>>());
   }
   
   public void removeSocialConnection(User user, Connection<?> connection){

       ConnectionRepository connectionRepository = usersConnectionRepository.createConnectionRepository(user.getId()+"");
       connectionRepository.removeConnection(connection.getKey());  
          
       // If the removed connection was the preferred of the user
       if (connection.getKey().getProviderId().equals( user.getAccountConnectedType().getProviderId() )) {
           // then change that preferred to the first of the remaining connections
           Connection<?> firstRemainingConnection = getAllConnections(user).get(0);
           user.setAccountConnectedType(User.AccountConnectedType.getProviderType(firstRemainingConnection.getKey().getProviderId()));
       }
       
       loginService.resetLoginData(user, getAllConnections(user));
   }
   
   public  List<Connection<?>> getAllConnections(User user){
       ConnectionRepository connectionRepository = usersConnectionRepository.createConnectionRepository(user.getId()+"");
       MultiValueMap<String,Connection<?>> connectionsByProvider = connectionRepository.findAllConnections();

       // We build a list that is usable in the <c:foreach> of the jsp (not the case of the map).
       List<Connection<?>> connections = new ArrayList<Connection<?>>();
       for(List<Connection<?>> collection : connectionsByProvider.values()) {
           connections.addAll(collection);
       }
       return connections;
   }
   
   
   public User tryToAttachSocialLoginToAnExistingUser(Connection<?> connection) throws UserLockedException{

       if (SecurityContext.getUser() != null) { 
           User user = SecurityContext.getUser();
           completUserFromSocialProviderData(connection,user);
           // User already logged in.
           return user;

       } else {
           String  mail = connection.fetchUserProfile().getEmail();

           if (mail != null) {  // Some providers, such as Twitter, don't give the e-mail.
               User userfound = userRepository.getUserByEmail(mail);

               if (userfound != null) {  // That visitor already has a user on RYC
                   completUserFromSocialProviderData(connection, userfound);

                   if(userfound.getAccountStatus().equals(AccountStatus.LOCKED)) {  // Cannot login...
                       
                       throw new UserLockedException(userfound);
                       
                   } else if (userfound.getAccountStatus().equals(AccountStatus.NOTVALIDATED)) {  // We have the mail from the social provider (which we trust) => no need to click the mail link to finish the registration.
                       userfound.setAccountStatus(AccountStatus.ACTIVE);
                   }

                   try {
                       if((userfound.getAccountStatus().equals(AccountStatus.ACTIVE))){  
                       loginService.login(null,null,false,userfound.getId(),AccountConnectedType.getProviderType(connection.getKey().getProviderId()));
                       // we place the provider name in the session , will be used by header.jsp
                       }
                   } catch (UserNotFoundException | InvalidPasswordException
                           | UserNotValidatedException | UserLockedException
                           | WaitDelayNotReachedException e) {
                       throw new RuntimeException(e);
                   }

                   userRepository.merge(userfound);
                   return userfound;
               }                   
           }
       }
       return null; // Cannot attach the visitor to an existing RYC user. 

   }

   
   public void completUserFromSocialProviderData(Connection<?> connection,User user){
       
       if(StringUtils.isBlank(user.getLastName())) {
           user.setLastName(connection.fetchUserProfile().getLastName());
       }
       if(StringUtils.isBlank(user.getFirstName())) {
           user.setFirstName(connection.fetchUserProfile().getFirstName());
       }

       ////// Image
       if (!user.isPicture()) {  // No picture yet => try to fetch one from the social provider.
           addOrUpdateUserImageFromSocialProvider(user,connection);
       }       
       
       userRepository.merge(user);
   }
   
   public User registerSocialUser(WebRequest request,String mail,boolean mailIsValid) throws UserAlreadyExistsException {
       Connection<?> connection =  ProviderSignInUtils.getConnection(request); 
     
       User user = null;
       try {
           Date date = new Date();
           
           // 1. Username based on Time stamp (temporary name until we persist it and have it's id)
           String username = date.getTime()+"";
           int begin = username.length()-12;
           username= "tmp"+username.substring(begin);
           Random random = new Random();
           
           if (mailIsValid) {  // Ok, we directly register that user. 
               user = registerUser(true, 
                   username,  // This name will change at the next line, as soon as we have the id. 
                   random.nextLong()+"",   // Nobody should never use this password (because the user logs in through it's social network). 
                   mail,true);         
              
           } else { // is there another user having the same mail? 
               user = userRepository.getUserByEmail(mail);
               if(user == null){
                   //we need to verify the mail before registering so we pass false for directValidation
                   user = registerUser(false, 
                       username,  
                       random.nextLong()+"",   
                       mail,true);     
               } else { //case where the user is already registered in local and try to register with twitter
                  throw new UserAlreadyExistsException(IdentifierType.MAIL, user.getUserName());
               }
           }
               
           // 2. Now we have the ID and can assign a better username.
           user.setUserName("user"+user.getId());
           userRepository.merge(user);
           
           //When the user is created in local , add a new Connection to userconnection table
           ProviderSignInUtils.handlePostSignUp(user.getId()+"", request);   
          
       } catch (Exception e) {
           if(e instanceof UserAlreadyExistsException){
               throw new UserAlreadyExistsException(IdentifierType.MAIL,user.getUserName());
           } else {
               throw new RuntimeException("Problem while registering "+mail+" account through "+ connection.getKey().getProviderId(), e);
           }
       }
       
       return user;
   }
    public List<User> getUserLstWithRoleOrPrivilege(){
    	List<User> list1 = userRepository.getUserWithRoleNotNull();
    	List<User> list2 = userRepository.getUserWithPrivilegeNotEmpty();
    	
    	// Add to list1, the users of list2 which are not already in list1.
    	for(User u : list2){
    		if(!(list1.contains(u))){
    			list1.add(u);
    		}
    	}
		return list1;
    }
    
	/** Encoding user's nickname and registration date to have a parameter sent to a page.
	 * Used in urls (from mails, for example), to directly authentify a user. */
    public String getUserSecurityString(User user){
		return SecurityUtils.md5Encode(user.getUserName()+user.getCreatedOn().toString()).substring(0, 6);
    }
    
    

    

    public void setUser(User user){
        changeUserName(user, "Anonymous"+user.getId(), "", "");
        user.setTitle("");
        user.setCertificationDate(null);
        user.setBirthDate(null);
        user.setGender(null);
        user.setAccountStatus(AccountStatus.LOCKED);
        user.setRole(Role.USER);
    }
    
    public void userImageDelete(User user){

        FileUtil.deleteFilesWithPattern(FileUtil.getGenFolderPath(currentEnvironment) + FileUtil.USER_SUB_FOLDER + FileUtil.USER_ORIGINAL_SUB_FOLDER, user.getId()+".*");
        FileUtil.deleteFilesWithPattern(FileUtil.getGenFolderPath(currentEnvironment) + FileUtil.USER_SUB_FOLDER + FileUtil.USER_RESIZED_SUB_FOLDER + FileUtil.USER_RESIZED_LARGE_SUB_FOLDER, user.getId()+".*");
        FileUtil.deleteFilesWithPattern(FileUtil.getGenFolderPath(currentEnvironment) + FileUtil.USER_SUB_FOLDER + FileUtil.USER_RESIZED_SUB_FOLDER +  FileUtil.USER_RESIZED_SMALL_SUB_FOLDER, user.getId()+".*");
        user.setPicture(false);
    }
	
}