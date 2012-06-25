package reformyourcountry.service;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.log4j.Logger;

import reformyourcountry.dao.UserDao;
import reformyourcountry.exceptions.UserAlreadyExistsException;
import reformyourcountry.exceptions.UserAlreadyExistsException.identifierType;
import reformyourcountry.mail.MailCategory;
import reformyourcountry.mail.MailService;
import reformyourcountry.mail.MailType;
import reformyourcountry.model.User;
import reformyourcountry.model.User.AccountStatus;
import reformyourcountry.model.User.Gender;
import blackbelt.util.SecurityUtils;

public class UserService {

    private static UserService uniqueInstance = new UserService();
    private UserService() {}
    public static UserService getInstance() {
        return uniqueInstance;
    }
    
    
    // Constant values used in the automatic calculation of the influence factor
    public static final int INFLUENCE_PERCENTAGE_OF_RELEASED_QUESTIONS_WITH_NO_CONCERNS = 90;
    public static final int INFLUENCE_AMOUNT_OF_RELEASED_QUESTIONS_WITH_NO_CONCERNS = 20;
    public static final int INFLUENCE_AMOUNT_OF_RELEASED_QUESTIONS = 50;
    // private TemplateService templateService;
    // private String registrationTemplate;
    // private String passwordRecoveryTemplate;

    protected Logger logger = Logger.getLogger(getClass());
    //@Autowired// 
    private UserDao userDao = UserDao.getInstance(); // TODO: Use autowiring with Spring instead of new.
    // @Autowired 
    private MailService mailService = MailService.getInstance();


    /**
     * Register a user and sends a validation mail.
     * 
     * @param directValidation
     *            : validate an account directly without send a mail
     */

    public User registerUser(boolean directValidation, String firstname, String lastname,
    		Gender gender, String username, String passwordInClear, String mail) throws UserAlreadyExistsException {
    	if (userDao.getUserByUserName(username) != null)	{
    		throw new UserAlreadyExistsException(identifierType.USERNAME, username);
    	}
    	if (userDao.getUserByEmail(mail) != null){
    		throw new UserAlreadyExistsException(identifierType.MAIL, username);
    	}
    	User newUser = new User();
    	newUser.setFirstName(firstname);
    	newUser.setLastName(lastname);
    	newUser.setGender(gender);
    	newUser.setUserName(username);
    	newUser.setPassword(SecurityUtils.md5Encode(passwordInClear));
    	newUser.setMail(mail);
        newUser.setId(145l);
    	//// Validation mail.
    	String base = newUser.getMail() + newUser.getPassword() + Math.random();  // Could be pure random.
    	newUser.setValidationCode(SecurityUtils.md5Encode(base.toString()));

    	if (directValidation) {
    		newUser.setAccountStatus(AccountStatus.ACTIVE);
    	} 
    	else {
    		newUser.setAccountStatus(AccountStatus.NOTVALIDATED);
    	}

    	//// Save the user in the db
    	userDao.save(newUser);

    	// All is ok lets eventually send a validation email
    	if (!directValidation) {
    		sendRegistrationValidationMail(newUser);
    	}

    	return newUser;
    }


    public void sendRegistrationValidationMail(User user) {

        String validationUrl = "TO IMPLEMENT"; // UrlUtil.getAbsoluteUrl( XXXXXXXXXXXXXX REGISTER PAGE with user & user.getValidationCode().
        String htmlMessage = "Welcome on KnowledgeBlackBelt.com, " + user.getFullName() + "." + 
                "<br/>There is just one step left to get your new account : " +
                "please click on the link below to validate your email address !" +
                "<br/><a href='"+ validationUrl + "'>" + validationUrl + "</a>" +
                "<br/><br/>If you are experiencing any problems, try copy/pasting the URL in your browser (instead clicking on it), or ultimately " +
                "<a href='" + 
                //TODO uncomment when for web
                //UrlUtil.getAbsoluteUrl(new PageResource(DocumentPage.class, "ContactUs")) + 
                "'>contact us.</a>" + 
                "<br/><br/>Thank you for registering and have a nice time on KnowledgeBlackBelt.";
        //maxime uncomment when using mail
        //	 mailService.sendMail(user.getMail(), "Your new account", htmlMessage, MailType.IMMEDIATE, MailCategory.USER);
        System.out.println("mail sent: " + htmlMessage);  // To simulate the mailService until we have it.
    }




    // TODO maxime uncomment for the web (picture of a user)
    //	public void saveUserImages(User user, InputStream imageFileStream) {
    //	    	if(user == null || user.getId() == null){
    //	    		throw new IllegalArgumentException("Could not save the pircture of an unpersited user");
    //	    	}
    //
    //	    	String oldPictureName = user.isPicture() ? user.getPictureName() : null;
    //	    	String[] scaleFolderNames = new String[] { "", "medium", "small" };
    //	    	
    //	        try {
    //	            user.setPicture(true);
    //	            // add the file name to the user
    //	            user.setPictureName(user.getId() + "-" + new Date().getTime() + ".png");
    //
    //	            ContextUtil.getBLFacade().getFileService()
    //						.saveAndScaleImage(imageFileStream, user.getPictureName(),
    //								"users", true, "originals", true,
    //								new int[] { 100, 44, 22 },
    //								new int[] { 150, 66, 33 },
    //								scaleFolderNames,
    //								ImageSaveFormat.PNG);
    //	            
    //	            contributionService.addImage(user);
    //	        } catch (Exception e) {
    //	            logger.error("Could not save user images", e);
    //	            user.setPicture(false);
    //	            user.setPictureName(null);
    //	        }
    //	        getDaoFacade().getUserDao().save(user);
    //	        
    //	        if (oldPictureName != null) {
    //	            ContextUtil.getBLFacade().getFileService().deleteUserPictures(oldPictureName);
    //	        }
    //	    }
    // TODO maxime uncomment for the web (picture of a user)
    //	    @Override
    //	    public void removeUserImages(User user) {
    //	        String oldPictureName = user.isPicture() ? user.getPictureName() : null;
    //	        
    //	        user.setPicture(false);
    //	        user.setPictureName(null);
    //	        contributionService.removeImage(user);
    //	        userDao.save(user);
    //	        
    //	        if (oldPictureName != null) {
    //	            ContextUtil.getBLFacade().getFileService().deleteUserPictures(oldPictureName);
    //	        }
    //	    }

    public void generateNewPasswordForUserAndSendEmail(User user) {
        // We generate a password
        String newPassword = SecurityUtils.generateRandomPassword(8, 12);
        // we set the new password to the user
        user.setPassword(SecurityUtils.md5Encode(newPassword));
        userDao.save(user);

        // TODO maxime uncomment when mail service available
        mailService.sendMail(user.getMail(), "Password Recovery",
                "You requested a new password for your account '"+ user.getUserName()+"' on KnowledgeBlackBelt.com<br/>" + 
                        "We could not give you back your old password because we do not store it directly, for security and (your own) privacy reason it is encrypted in a non reversible way. <br/><br/>" + 
                        "Here is your new password : "+ newPassword + 
                        "<ol>" +  
                        "<li>password are case sensitive,</li>" +  				   //TODO maxime uncoment for the web
                        "<li>This is a temporary password, feel free to change it using <a href='"+/*getUserPageUrl(user)+*/"'>your profile page</a>.</li>" +
                        "</ol>", 
                        MailType.IMMEDIATE, MailCategory.USER);

    }




    /** Change the name of the user and note it in the log */
    public void changeUserName(User user, String newFirstName,   String newLastName) {
        if (user.getNameChangeLog() == null) {
            user.addNameChangeLog("Previous name: " + user.getFirstName()
                    + " - " + user.getLastName());
        }
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
        user.addNameChangeLog(dateFormat.format(new Date()) + ": "
                + newFirstName + " - " + newLastName);
        user.setFirstName(newFirstName);
        user.setLastName(newLastName);
        userDao.save(user);
    }


}