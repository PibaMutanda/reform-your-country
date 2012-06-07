package reformyourcountry.service;

import java.io.InputStream;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import javax.management.RuntimeErrorException;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import reformyourcountry.exception.UseAlreadyExistsException;
import reformyourcountry.exception.UseAlreadyExistsException.identifierType;

//import org.springframework.beans.factory.annotation.Autowired;
//import org.vaadin.navigator7.PageResource;
//import org.vaadin.navigator7.uri.ParamPageResource;
//
//import be.loop.jbb.bl.BaseService;
//import be.loop.jbb.bl.ContributionService;
//import be.loop.jbb.bl.UserService;
//import be.loop.jbb.bl.FileService.ImageSaveFormat;
//import be.loop.jbb.bl.exceptions.InvalidPasswordException;
//import be.loop.jbb.bl.exceptions.NotEnoughAvailablePointsException;
//import be.loop.jbb.bl.exceptions.UserLockedException;
//import be.loop.jbb.bl.exceptions.UserNotFoundException;
//import be.loop.jbb.bl.exceptions.UserNotValidatedException;
//import be.loop.jbb.bo.CommunityUser;
//import be.loop.jbb.bo.Competition;
//import be.loop.jbb.bo.CompetitionInscription;
//import be.loop.jbb.bo.corp.CorpUser;
//import be.loop.jbb.bo.tests.Exam;
//import be.loop.jbb.bo.tests.ExamPerformed;
//import be.loop.jbb.bo.tests.ExamTask;
//import be.loop.jbb.bo.tests.ExamTaskPerformed;
//import be.loop.jbb.bo.tests.Questionnaire;
//import be.loop.jbb.comparators.ExamTaskPerformedDateDescComparator;
//import be.loop.jbb.dao.CommunityUserDao;
//import be.loop.jbb.util.DateUtil;
import blackbelt.util.SecurityUtils;
//import blackbelt.dao.QuestionCriteriaVoteDao;
import blackbelt.dao.UserDao;
import blackbelt.exceptions.UserNotFoundException;
//import blackbelt.dao.V5QuestionDao;
//import blackbelt.model.Badge;
//import blackbelt.model.BadgeTypeGroup;
//import blackbelt.model.BeltV5;
//import blackbelt.model.Community;
//import blackbelt.model.MailCategory;
//import blackbelt.model.MailType;
//import blackbelt.model.Organization;
//import blackbelt.model.Privilege;
//import blackbelt.model.User;
//import blackbelt.model.User.AccountStatus;
//import blackbelt.model.User.ContributorCredibility;
//import blackbelt.model.exam.V5Question;
//import blackbelt.security.SecurityContext;
//import blackbelt.service.BadgeService;
//import blackbelt.service.CleanShutdownService;
//import blackbelt.service.CourseRegService;
//import blackbelt.service.GroupService;
//import blackbelt.service.MailService;
//import blackbelt.service.ModerationService;
//import blackbelt.service.OrganizationService;
//import blackbelt.ui.ReactivatePage;
import blackbelt.model.User;
import blackbelt.model.User.AccountStatus;
import blackbelt.model.User.CommunityRole;
import blackbelt.model.User.Gender;
import blackbelt.ui.RegisterPage;
import blackbelt.ui.RegisterValidatePage;
//import blackbelt.ui.document.DocumentPage;
//import blackbelt.ui.user.UserPage;
//import blackbelt.util.BlackBeltException;
import blackbelt.web.ContextUtil;
import blackbelt.web.UrlUtil;

public class UserService {

    // Constant values used in the automatic calculation of the influence factor
    public static final int INFLUENCE_PERCENTAGE_OF_RELEASED_QUESTIONS_WITH_NO_CONCERNS = 90;
    public static final int INFLUENCE_AMOUNT_OF_RELEASED_QUESTIONS_WITH_NO_CONCERNS = 20;
    public static final int INFLUENCE_AMOUNT_OF_RELEASED_QUESTIONS = 50;
    // private TemplateService templateService;
    // private String registrationTemplate;
    // private String passwordRecoveryTemplate;

    protected Logger logger = Logger.getLogger(getClass());

    // @Autowired private GroupService groupService;
    // @Autowired private ContributionService contributionService;
    /*@Autowired*/ private UserDao UserDao = new UserDao(); // TODO: Use autowiring with Spring instead of new.
    // @Autowired private MailService mailService;
    // @Autowired private V5QuestionDao questionDao;
    // @Autowired private BadgeService badgeService;
    // @Autowired private QuestionCriteriaVoteDao questionCriteriaVoteDao;
    // @Autowired private ModerationService moderationService;
    // @Autowired private CommunityUserDao communityUserDao;
    // @Autowired private CourseRegService courseRegService;
    // @Autowired private OrganizationService organizationService;

    // TODO maxime javadoc
    /**
     * register a user in the db
     * 
     * @param user
     *            : a user
     * @param directValidation
     *            : validate an account directly
     */
    @Deprecated
    public void registerUser(User user, boolean directValidation) {
	Date now = new Date();
	user.setRegistrationDate(now);
	user.setLastAccess(now);

	String base = user.getMail() + user.getPassword() + Math.random();
	user.setValidationCode(SecurityUtils.md5Encode(base.toString()));

	if (directValidation) {
	    user.setAccountStatus(AccountStatus.ACTIVE);
	} else {
	    user.setAccountStatus(AccountStatus.NOTVALIDATED);
	}
	// Save the user in the db
	UserDao.save(user);

	// All is ok lets eventually send a validation email
	if (!directValidation) {
	    sendRegistrationValidationMail(user);
	}

    }

    /**
     * Register a user and sends a validation mail.
     * 
     * @param directValidation
     *            : validate an account directly without send a mail
     */

    public User registerUser(boolean directValidation, String firstName, String lastName,
	    Gender gender, String username, String passwordInClear, String mail) throws UseAlreadyExistsException {
	if(UserDao.getUserByUserName(username) != null)
	{
	    throw new UseAlreadyExistsException(identifierType.USERNAME, username);
	}
	if(UserDao.getUserByEmail(mail) != null)
	{
	    throw new UseAlreadyExistsException(identifierType.MAIL, username);
	}
	System.out.println("register user je suis appelé");
	User toRegister = new User();
	toRegister.setFirstName(firstName);
	toRegister.setLastName(lastName);
	toRegister.setGender(gender);
	toRegister.setUserName(username);
	toRegister.setPassword(SecurityUtils.md5Encode(passwordInClear));
	toRegister.setMail(mail);

	//// Validation mail.
	String base = toRegister.getMail() + toRegister.getPassword() + Math.random();  // Could be pure random.
	toRegister.setValidationCode(SecurityUtils.md5Encode(base.toString()));

	if (directValidation) {
	    toRegister.setAccountStatus(AccountStatus.ACTIVE);
	} else {
	    toRegister.setAccountStatus(AccountStatus.NOTVALIDATED);
	}

	//// Save the user in the db
	UserDao.save(toRegister);

	// All is ok lets eventually send a validation email
	if (!directValidation) {
	    sendRegistrationValidationMail(toRegister);
	}

	return toRegister;
    }

    public void deleteUser(User user, boolean deleteQuestions, boolean deleteVotes) {

	//FIXME PARKING We should do a soft delete here!
	//Soft delete is possible (like for candidates) 
	//But users owns lot of stuffs that can 
	//be use to access user informations (like 
	//when we display a question'author)
	// ---> See comment in Fixme of CorpUserManager
	throw new UnsupportedOperationException("Not supported yet");

	//    	User specialUser = this.getDaoFacade().getUserDao().getUserByUserName("unsubscribeuser");
	//    	
	//    	List<Question> questions = this.getDaoFacade().getQuestionDao().getAllQuestionsByUser(user.getId());
	//    	
	//    	// change owner of questions.
	//    	for (Question question : questions) {
	//    		
	//    		ContextUtil.getBLFacade().getQuestionService().changeQuestionsOwner(question, specialUser);
	//    		
	//    		if (logger.isDebugEnabled()) {
	//    			logger.debug("Change owner of question #" + question.getPublicId() + "(was " + user.getFullName() + ")");
	//    		}
	//    		
	//    		if (deleteQuestions) {
	//    			
	//    			question.setDeleted(true);
	//        		
	//        		this.getDaoFacade().getQuestionDao().storeQuestion(question);
	//        		
	//        		if (logger.isDebugEnabled()) {
	//        			logger.debug("Soft deleted question #" + question.getPublicId());
	//        		}
	//        		
	//    		}
	//    		
	//    	}
	//    	
	//    	if (logger.isInfoEnabled()) {
	//    		logger.info("Changed Questions for " + user.getFullName());
	//    	}
	//    	
	//    	 // change owner of questionVersions
	//    	List<QuestionVersion> versions = this.getDaoFacade().getQuestionDao().getQuestionVersionsByUse(user);
	//    	
	//    	for (QuestionVersion version : versions) {
	//    		
	//    		version.setAuthor(specialUser);
	//    		
	//    		this.getDaoFacade().getQuestionDao().storeQuestionVersion(version);
	//    		
	//			if (logger.isDebugEnabled()) {
	//				logger.debug("Changed owner of QuestionVersion #" + version.getId() + "(was " + user.getFullName() + ")");
	//			}
	//    		
	//    	}
	//    	
	//    	if (logger.isInfoEnabled()) {
	//    		logger.info("Changed QuestionVersions for " + user.getFullName());
	//    	}
	//    	
	//   	 	// change owner of userActions
	//    	List<UserAction> actions = this.getDaoFacade().getUserDao().getUserActionsByUser(user);
	//    	
	//    	for (UserAction action : actions) {
	//    		
	//    		action.setUser(specialUser);
	//    		
	//    		this.getDaoFacade().getUserDao().storeUserAction(action);
	//    		
	//			if (logger.isDebugEnabled()) {
	//				logger.debug("Changed owner of UserAction #" + action.getId() + "(was " + user.getFullName() + ")");
	//			}
	//    		
	//    	}
	//    	
	//    	if (logger.isInfoEnabled()) {
	//    		logger.info("Changed UserActions for " + user.getFullName());
	//    	}
	//    	
	//    	// change owner of votes or delete them.
	//    	if (deleteVotes) {
	//    		ContextUtil.getBLFacade().getVoteService().deleteUserVotes(user);
	//    	} else {
	//			ContextUtil.getBLFacade().getVoteService().changeVoteOwner(user, specialUser);
	//		}
	//    	
	//    	if (logger.isInfoEnabled()) {
	//    		logger.info("Changed/Deleted Votes for " + user.getFullName());
	//    	}
	//    	
	//    	// change owner of bids.
	//    	ContextUtil.getBLFacade().getAuctionService().changeBidsOwner(user, specialUser);
	//    	
	//    	if (logger.isInfoEnabled()) {
	//    		logger.info("Changed Bids for " + user.getFullName());
	//    	}
	//    	
	//        List<Comment> comments = this.getDaoFacade().getCommentDao().getCommentsByUser(user);
	//        
	//    	// change owner of comments and close/hide them.
	//        for (Comment comment : comments) {
	//        	
	//        	comment.setAuthor(specialUser);
	//        	comment.setHidden(true);
	//        	comment.setStatus(Comment.CLOSED);
	//        	
	//        	this.getDaoFacade().getCommentDao().store(comment);
	//        	
	//        	if (logger.isDebugEnabled()) {
	//        		logger.debug("Changed owner of Comment #" + comment.getId() + "(was" + user.getFullName() + ")");
	//        	}
	//        	
	//        }
	//    	
	//    	if (logger.isInfoEnabled()) {
	//    		logger.info("Changed Comments for " + user.getFullName());
	//    	}
	//        
	//        // Delete User's contributions.
	//        ContextUtil.getBLFacade().getContributionService().deleteUsersContributions(user);
	//    	
	//    	if (logger.isInfoEnabled()) {
	//    		logger.info("Deleted Contributions for " + user.getFullName());
	//    	}
	//        
	//        
	//        // Delete User's Events.
	//        List<Event> events = this.getDaoFacade().getEventDao().getUsersEvents(user);
	//        
	//        for (Event event : events) {
	//        	
	//        	if (logger.isDebugEnabled()) {
	//        		logger.debug("Deleted Event #" + event.getId());
	//        	}
	//        	
	//        	this.getDaoFacade().getEventDao().deleteEvent(event);
	//        	
	//        }
	//    	
	//    	if (logger.isInfoEnabled()) {
	//    		logger.info("Deleted Events for " + user.getFullName());
	//    	}
	//
	//        // Delete ExamTaskPerformed.
	//        ContextUtil.getBLFacade().getExamService().deleteUsersExamTasksPerformed(user);
	//    	
	//    	if (logger.isInfoEnabled()) {
	//    		logger.info("Deleted ExamTasksPerformed for " + user.getFullName());
	//    	}
	//        
	//        // Delete ExamPerformed.
	//        ContextUtil.getBLFacade().getExamService().deleteUsersExamsPerformed(user);
	//    	
	//    	if (logger.isInfoEnabled()) {
	//    		logger.info("Deleted ExamsPerformed for " + user.getFullName());
	//    	}
	//        
	//        // Delete Questionnaires.
	//        ContextUtil.getBLFacade().getQuestionnaireService().deleteUsersQuestionnaire(user);
	//    	
	//    	if (logger.isInfoEnabled()) {
	//    		logger.info("Deleted Questionnaires for " + user.getFullName());
	//    	}
	//        
	//        this.getDaoFacade().getUserDao().delete(user);
	//    	
	//    	if (logger.isInfoEnabled()) {
	//    		logger.info("Deleted " + user.getFullName());
	//    	}

    }

    //TODO remove static when using web
    public static void sendRegistrationValidationMail(User user) {

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

	// TODO maxime uncomment when the mail service is implemented.
	// mailService.sendMail(user.getMail(), "Your new account", htmlMessage, MailType.IMMEDIATE, MailCategory.USER);
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
	UserDao.save(user);

	// TODO maxime uncomment when mail service available
	//  			mailService.sendMail(user.getMail(), "Password Recovery",
	//  					"You requested a new password for your account '"+ user.getUserName()+"' on KnowledgeBlackBelt.com<br/>" + 
	//  					"We could not give you back your old password because we do not store it directly, for security and (your own) privacy reason it is encrypted in a non reversible way. <br/><br/>" + 
	//  					"Here is your new password : "+ newPassword + 
	//  					"<ol>" +  
	//  					"<li>password are case sensitive,</li>" +  
	//  					"<li>This is a temporary password, feel free to change it using <a href='"+getUserPageUrl(user)+"'>your profile page</a>.</li>" +
	//  					"</ol>", 
	//  					MailType.IMMEDIATE, MailCategory.USER);

    }




    /** Change the name of the user and note it in the log */
    public void changeUserName(User user, String newFirstName,
	    String newLastName) {
	if (user.getNameChangeLog() == null) {
	    user.addNameChangeLog("Previous name: " + user.getFirstName()
		    + " - " + user.getLastName());
	}
	SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
	user.addNameChangeLog(dateFormat.format(new Date()) + ": "
		+ newFirstName + " - " + newLastName);
	user.setFirstName(newFirstName);
	user.setLastName(newLastName);
	UserDao.save(user);
    }


}