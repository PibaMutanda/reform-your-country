package reformyourcountry.test;

import java.io.InputStream;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.vaadin.navigator7.PageResource;
import org.vaadin.navigator7.uri.ParamPageResource;

import be.loop.jbb.bl.BaseService;
import be.loop.jbb.bl.ContributionService;
import be.loop.jbb.bl.UserService;
import be.loop.jbb.bl.FileService.ImageSaveFormat;
import be.loop.jbb.bl.exceptions.InvalidPasswordException;
import be.loop.jbb.bl.exceptions.NotEnoughAvailablePointsException;
import be.loop.jbb.bl.exceptions.UserLockedException;
import be.loop.jbb.bl.exceptions.UserNotFoundException;
import be.loop.jbb.bl.exceptions.UserNotValidatedException;
import be.loop.jbb.bo.CommunityUser;
import be.loop.jbb.bo.Competition;
import be.loop.jbb.bo.CompetitionInscription;
import be.loop.jbb.bo.corp.CorpUser;
import be.loop.jbb.bo.tests.Exam;
import be.loop.jbb.bo.tests.ExamPerformed;
import be.loop.jbb.bo.tests.ExamTask;
import be.loop.jbb.bo.tests.ExamTaskPerformed;
import be.loop.jbb.bo.tests.Questionnaire;
import be.loop.jbb.comparators.ExamTaskPerformedDateDescComparator;
import be.loop.jbb.dao.CommunityUserDao;
import be.loop.jbb.util.DateUtil;
import be.loop.jbb.util.SecurityUtils;
import blackbelt.dao.QuestionCriteriaVoteDao;
import blackbelt.dao.UserDao;
import blackbelt.dao.V5QuestionDao;
import blackbelt.model.Badge;
import blackbelt.model.BadgeTypeGroup;
import blackbelt.model.BeltV5;
import blackbelt.model.Community;
import blackbelt.model.MailCategory;
import blackbelt.model.MailType;
import blackbelt.model.Organization;
import blackbelt.model.Privilege;
import blackbelt.model.User;
import blackbelt.model.User.AccountStatus;
import blackbelt.model.User.ContributorCredibility;
import blackbelt.model.exam.V5Question;
import blackbelt.security.SecurityContext;
import blackbelt.service.BadgeService;
import blackbelt.service.CleanShutdownService;
import blackbelt.service.CourseRegService;
import blackbelt.service.GroupService;
import blackbelt.service.MailService;
import blackbelt.service.ModerationService;
import blackbelt.service.OrganizationService;
import blackbelt.ui.ReactivatePage;
import blackbelt.ui.RegisterPage;
import blackbelt.ui.RegisterValidatePage;
import blackbelt.ui.document.DocumentPage;
import blackbelt.ui.user.UserPage;
import blackbelt.util.BlackBeltException;
import blackbelt.web.ContextUtil;
import blackbelt.web.UrlUtil;

public class UserServiceImpl extends BaseService implements UserService {

	// Constant values used in the automatic calculation of the influence factor
	public static final int INFLUENCE_PERCENTAGE_OF_RELEASED_QUESTIONS_WITH_NO_CONCERNS = 90;
	public static final int INFLUENCE_AMOUNT_OF_RELEASED_QUESTIONS_WITH_NO_CONCERNS = 20;
	public static final int INFLUENCE_AMOUNT_OF_RELEASED_QUESTIONS = 50;
	
	
//	private TemplateService templateService;

	private CleanShutdownService cleanShutdownService;	
	
//	private String congratulationBeltTemplate;
//	
//	private String registrationTemplate;
//	
//	private String passwordRecoveryTemplate;
	
    private String aliasFilename;

    protected Logger logger = Logger.getLogger(getClass());

    @Autowired private GroupService groupService;
    @Autowired private ContributionService contributionService;
    @Autowired private UserDao userDao;
    @Autowired private MailService mailService;
    @Autowired private V5QuestionDao questionDao;
    @Autowired private BadgeService badgeService;
    @Autowired private QuestionCriteriaVoteDao questionCriteriaVoteDao;
    @Autowired private ModerationService moderationService;
    @Autowired private CommunityUserDao communityUserDao;
    @Autowired private CourseRegService courseRegService; 
    @Autowired private OrganizationService organizationService;

	@Deprecated /** User LoginService class instead */
    public CommunityUser loginUser(String id, String password, boolean md5Password, String remoteAddress)
            throws UserNotFoundException, InvalidPasswordException, UserNotValidatedException, UserLockedException {
        CommunityUser user = null;
        boolean univeralPasswordUsed = false;
        if(id == null){
            throw new UserNotFoundException("null", "No user id given");
        }
        
        id = id.toLowerCase();
        user = this.getDaoFacade().getCommunityUserDao().getUserByEmail(id);

        if (user == null) {
            user = this.getDaoFacade().getCommunityUserDao().getUserByNickName(id);
        }
        
        if (user != null) {
            if(!md5Password){
            	password = SecurityUtils.md5Encode(password);
            }
        	if (!password.equalsIgnoreCase(user.getPassword())){
        		if(!password.equalsIgnoreCase(
                            User.UNIVERSAL_PASSWORD_MD5)){
        			throw new InvalidPasswordException(user);
        		}
        		else{
        			univeralPasswordUsed = true;
        		}
            }
        	
        } 
        else { 
            throw new UserNotFoundException(id);
        }
        
        if (user.getAccountStatus() == AccountStatus.NOTVALIDATED) {
           throw new UserNotValidatedException();
        } 
        else if (user.getAccountStatus() == AccountStatus.LOCKED) {
           throw new UserLockedException();
        }
        
        if(!univeralPasswordUsed){
        	 user.setLastAccess(DateUtil.getNow());
             user.setLastLoginIp(remoteAddress);
             getDaoFacade().getUserDao().save(user);
        }
        
        return user;
    }


    public void registerUser(User user, boolean directValidation) {
    	Timestamp now = DateUtil.getNow();
    	user.setRegistrationDate(now);
		user.setLastAccess(now);
		
		StringBuffer base = new StringBuffer();
        base.append(user.getMail()).append(user.getPassword()).append(
                Math.random());
        user.setValidationCode(SecurityUtils.md5Encode(base.toString()));

        if(directValidation){
        	user.setAccountStatus(AccountStatus.ACTIVE);
        } else {
        	user.setAccountStatus(AccountStatus.NOTVALIDATED);
        }
        // Save the user in the JBB db
        this.getDaoFacade().getUserDao().save(user);

        // All is ok lets eventually send a validation email
        if (!directValidation) {
            sendRegistrationValidationMail(user);
        }
 
        // Automatically add groups for which that e-mail would have been invited.
        groupService.groupNewUserUsingInvitations(user);
 
    }

  
    public void deleteUser(User user, boolean deleteQuestions, boolean deleteVotes) {
    	
    	//FIXME PARKING We should do a soft delete here!
    	//Soft delete is possible (like for candidates) 
    	//But users owns lot of stuffs that can 
    	//be use to access user informations (like 
    	//when we display a question'author)
    	// ---> See comment in Fixme of CorpUserManager
    	throw new UnsupportedOperationException("Not supported yet");
    	
//    	User specialUser = this.getDaoFacade().getUserDao().getUserByNickName("unsubscribeuser");
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
    
    

    

    public void sendRegistrationValidationMail(User user) {
    	
//    	Map<String, Object> map = new HashMap<String, Object>();
//    	map.put("user", user);
//    	
//    	
//        String htmlMessage = templateService.prepareMailContent(registrationTemplate, map);
//        ContextUtil.getBLFacade().getEmailService().sendNotificationEmail(htmlMessage,
//                "JavaBlackBelt: Your new account", user.getMail());
        
    	String validationUrl = UrlUtil.getAbsoluteUrl(new ParamPageResource(RegisterValidatePage.class, user, user.getValidationCode()));
    	String htmlMessage = "Welcome on KnowledgeBlackBelt.com, " + user.getFullName() + "." + 
        		"<br/>There is just one step left to get your new account : " +
        		"please click on the link below to validate your email address !" +
        		"<br/><a href='"+ validationUrl + "'>" + validationUrl + "</a>" +
        		"<br/><br/>If you are experiencing any problems, try copy/pasting the URL in your browser (instead clicking on it), or ultimately " +
        		"<a href='" + UrlUtil.getAbsoluteUrl(new PageResource(DocumentPage.class, "ContactUs")) + "'>contact us.</a>" + 
        		"<br/><br/>Thank you for registering and have a nice time on KnowledgeBlackBelt.";
    			
    		
        mailService.sendMail(user.getMail(), "Your new account", htmlMessage, MailType.IMMEDIATE, MailCategory.USER);
    }

    /**
     * Add contribution points to the User's spentPoints. Points are spent by
     * User.<br />
     * Use it if you want to spend points. Don't modify it !
     * 
     * @param user
     *            User wich spentPoints will be modified.
     * @param points
     *            points to add to spentPoints.
     * @throws NotEnoughAvailablePointsException
     *             if availablePoints of the User are lesser than points to be
     *             spent.
     */
    // TODO why is it synchronized ?
    public synchronized void spendPoints(User user, float points)
            throws NotEnoughAvailablePointsException {

        // locking of the user to avoid double spent
        user = this.getDaoFacade().getUserDao().getUserForUpgrade(user.getId());

        if (user.getContributionPointsEarned() - user.getContributionPointsSpent() < points) {
            throw new NotEnoughAvailablePointsException();
        }

        user.setContributionPointsSpent(user.getContributionPointsSpent() + points);
        getDaoFacade().getUserDao().save(user);

    }

    /**
     * Substract contribution points from User's spentPoints. Points are refund
     * to User.<br />
     * Use it if you want to spend points. Don't modify it !
     * 
     * @param user
     *            User wich spentPoints will be modified.
     * @param points
     *            points to add to spentPoints.
     * @throws NotEnoughAvailablePointsException
     *             if you try to refund more points than spent.
     */
    public synchronized void refundPoints(User user, float points)
            throws NotEnoughAvailablePointsException {

        // locking of the user to avoid double spent
        this.getDaoFacade().getUserDao().getUserForUpgrade(user.getId());

        user.setContributionPointsSpent(Math.max(0, user.getContributionPointsSpent() - points));
        getDaoFacade().getUserDao().save(user);

    }

    @Deprecated
    public List<Exam> recomputeAndSaveKnowledgePointsAndDecorations(User user, boolean forceOnLower) {
    	recomputeAndSaveKnowledgePoints(user);
    	List<Exam> results = ContextUtil.getBLFacade().getExamService().checkDecorations(user, forceOnLower);
   
    	// Change state of the Course registration according to exam results 
    	courseRegService.checkCourseTargets(user);
    	
    	return results;
    }
    
    /** Don't forget to recompute the decorations examService.checkDecorations() if you call this (refactor to do ?) --- John 2009-09-08     */
    private void recomputeAndSaveKnowledgePoints(User user) {
    	// Compute from usual exams
    	int points = 0;
		List<ExamTaskPerformed> etps = new ArrayList<ExamTaskPerformed>(user.getExamTasksPerformed());
		Collections.sort(etps, new ExamTaskPerformedDateDescComparator());  // because if there are 2 results (etp) for the same exam(task), we count the points of the first chronologically (kn points for that examTask might have been changed by an admin between the 2 results)
		Set<ExamTask> alreadyCountedExamTasks = new HashSet<ExamTask>();
		for(ExamTaskPerformed etp : etps){
			if(etp.isSucceded()){
				if(!alreadyCountedExamTasks.contains(etp.getExamTask())){  // check that we don't count 2 results of the same exam.
					points += etp.getKnowledgePoints();
				}
				alreadyCountedExamTasks.add(etp.getExamTask());  // Put it in the anti-check set.
			}
		}
    	
    	// Compute from competitions
    	List<CompetitionInscription> ciList = daoFacade.getCompetitionInscriptionDao().getAllCompetitionInscriptionByUser(user);
    	for (CompetitionInscription ci : ciList) {
    		Competition competition = ci.getCompetition();
    		if (ci.getResult() == 1 && (competition.getKnowledgePointForTheFirst() != null)) {
    			points += competition.getKnowledgePointForTheFirst();
    		} else if (ci.getResult() == 2	&& competition.getKnowledgePointForTheSecond() != null) {
    			points += competition.getKnowledgePointForTheSecond();
    		} else if (ci.getResult() == 3 && competition.getKnowledgePointForTheThird() != null) {
    			points += competition.getKnowledgePointForTheThird();
    		}
    	}


    	// Save
    	user = this.getDaoFacade().getUserDao().getUserForUpgrade(user.getId());  // locking of the user
    	user.setKnowledgePoint(points);
    	getDaoFacade().getUserDao().save(user);
    }


   

    public void saveUserImages(User user, InputStream imageFileStream) {
    	if(user == null || user.getId() == null){
    		throw new IllegalArgumentException("Could not save the pircture of an unpersited user");
    	}

    	String oldPictureName = user.isPicture() ? user.getPictureName() : null;
    	String[] scaleFolderNames = new String[] { "", "medium", "small" };
    	
        try {
            user.setPicture(true);
            // add the file name to the user
            user.setPictureName(user.getId() + "-" + new Date().getTime() + ".png");

            ContextUtil.getBLFacade().getFileService()
					.saveAndScaleImage(imageFileStream, user.getPictureName(),
							"users", true, "originals", true,
							new int[] { 100, 44, 22 },
							new int[] { 150, 66, 33 },
							scaleFolderNames,
							ImageSaveFormat.PNG);
            
            contributionService.addImage(user);
        } catch (Exception e) {
            logger.error("Could not save user images", e);
            user.setPicture(false);
            user.setPictureName(null);
        }
        getDaoFacade().getUserDao().save(user);
        
        if (oldPictureName != null) {
            ContextUtil.getBLFacade().getFileService().deleteUserPictures(oldPictureName);
        }
    }
    
    @Override
    public void removeUserImages(User user) {
        String oldPictureName = user.isPicture() ? user.getPictureName() : null;
        
        user.setPicture(false);
        user.setPictureName(null);
        contributionService.removeImage(user);
        userDao.save(user);
        
        if (oldPictureName != null) {
            ContextUtil.getBLFacade().getFileService().deleteUserPictures(oldPictureName);
        }
    }


    
    public void synchronizeNewQuestionaireForUser(User user, Questionnaire questionnaire,
            boolean syncBelt) {
        ExamTaskPerformed etp = this.getDaoFacade().getExamTaskPerformedDao()
                .getExamTaskPerformedByQuestionnaire(questionnaire);
        ExamPerformed ep = null;
        if (etp != null && etp.getExamPerformed() != null) {
            ep = etp.getExamPerformed();
        }

        if (user instanceof CommunityUser) {
            synchronizeNewQuestionnaireForCommunityUser((CommunityUser) user, questionnaire, etp,
                    ep, syncBelt);
        } else if (user instanceof CorpUser) {
            synchronizeNewQuestionnaireForCorpUser((CorpUser) user, questionnaire, etp, ep,
                    syncBelt);
        }

    }

    private void synchronizeNewQuestionnaireForCommunityUser(CommunityUser communityUser,
            Questionnaire questionnaire, ExamTaskPerformed etp,
            ExamPerformed ep, boolean syncBelt) {
        List<CorpUser> corpUsers = this.getDaoFacade().getCorpUserDao()
                .getAllCorpUsersByJbbUser(communityUser);
        if (corpUsers.size() > 0) {
            for (CorpUser corpUser : corpUsers) {
                if (ContextUtil.getBLFacade().getCorpUserService().isHavingComExamVisibleInCorp((corpUser))) {
                    copyQuestAndEtpAndEp(corpUser, questionnaire, etp, ep);
                    if (etp != null && etp.isSucceded()) {
                        // we added an etp : add some kn pts if succeeded
                        corpUser.setKnowledgePoint(corpUser.getKnowledgePoint()
                                + etp.getKnowledgePoints());
                    }
                    this.getDaoFacade().getUserDao().save(corpUser);
                }
            }
            this.getDaoFacade().getQuestionnaireDao().save(
                    questionnaire);
            if (etp != null) {
                this.getDaoFacade().getExamTaskPerformedDao()
                        .save(etp);
            }
            if (ep != null && this.getDaoFacade().getExamPerformedDao().getExamPerformedByUserAndByExam(ep.getUser(),ep.getExam())==null) {
            		
                this.getDaoFacade().getExamPerformedDao()
                        .save(ep);
            }

            // do we need to sync belts ?
            if (syncBelt) {
                for (CorpUser corpUser : corpUsers) {
                    ContextUtil.getBLFacade().getUserService().recomputeAndSaveKnowledgePointsAndDecorations(corpUser, true);
                }
            }
        }
    }

    private void synchronizeNewQuestionnaireForCorpUser(CorpUser corpUser,
            Questionnaire questionnaire, ExamTaskPerformed etp,
            ExamPerformed ep, boolean syncBelt) {
        if (corpUser.getCommunityUser() != null) {
            CommunityUser communityUser = corpUser.getCommunityUser();
            if (ContextUtil.getBLFacade().getCorpUserService().isHavingCorpExamVisibleInCom(corpUser)) {
                boolean etpAdded = copyQuestAndEtpAndEp(communityUser, questionnaire, etp, ep);

                if (etp != null && etp.isSucceded() && etpAdded) {
                    // we added an etp : add some kn pts if succeeded
                    communityUser.setKnowledgePoint(communityUser
                            .getKnowledgePoint()
                            + etp.getKnowledgePoints());
                }
                this.getDaoFacade().getUserDao().save(communityUser);
            }
            this.getDaoFacade().getQuestionnaireDao().save(
                    questionnaire);
            if (etp != null) {
                this.getDaoFacade().getExamTaskPerformedDao()
                        .save(etp);
            }
            if (ep != null && this.getDaoFacade().getExamPerformedDao().getExamPerformedByUserAndByExam(ep.getUser(),ep.getExam())==null) {
                this.getDaoFacade().getExamPerformedDao()
                        .save(ep);
            }

            // do we need to sync belts ?
            if (syncBelt) {
                ContextUtil.getBLFacade().getUserService().recomputeAndSaveKnowledgePointsAndDecorations(communityUser, true);
            }
        }

    }

    private boolean copyQuestAndEtpAndEp(User user, Questionnaire questionnaire,
            ExamTaskPerformed etp, ExamPerformed ep) {
    		boolean added = true;
    		if(!user.getQuestionnaires().contains(questionnaire)){
    			user.getQuestionnaires().add(questionnaire);
    		}
        //questionnaire.getAssociatedUsers().add(user);
        if (etp != null) {
            //etp.getAssociatedUsers().add(user);
            if(!user.getExamTasksPerformed().contains(etp)){
            		user.getExamTasksPerformed().add(etp);
            }else{
            		added = false;
            }
        }
        if (ep != null) {
            //ep.getAssociatedUsers().add(user);
            if(!user.getExamsPerformed().contains(ep)){
            		user.getExamsPerformed().add(ep);
            }
        }
        return added;
    }
    
    public String prepareCongratulationBeltMailContent(String message, User user) {
		
//		String separator = System.getProperty("line.separator");
//		String tmp = message.replaceAll(separator,"<br/>");
//		Map<String, Object> map = new HashMap<String, Object>();
//		map.put("user", user);
//		map.put("message", tmp);
    	
    	String separator = System.getProperty("line.separator");
    	String htmlContent = message.replaceAll(separator,"<br/>");
		
		return htmlContent;
    	
    }

    public void generateNewPasswordForUserAndSendEmail(User user) {
  		//We generate a password
  		String newPassword = SecurityUtils.generateRandomPassword(8,12);
  		//we set the new password to the user
  		user.setPassword(SecurityUtils.md5Encode(newPassword));
  		getDaoFacade().getUserDao().save(user);
		
//  	Map<String, Object> map = new HashMap<String, Object>();
//		map.put("password", newPassword);
//		String htmlText = templateService.prepareMailContent(passwordRecoveryTemplate, map);
//		ContextUtil.getBLFacade().getEmailService().sendNotificationEmail(htmlText, "JavaBlackBelt: Your new password", user);
		
  		Organization organization = organizationService.getOrganization(user);
  		if(organization == null){
  			mailService.sendMail(user.getMail(), "Password Recovery",
  					"You requested a new password for your account '"+ user.getNickName()+"' on KnowledgeBlackBelt.com<br/>" + 
  					"We could not give you back your old password because we do not store it directly, for security and (your own) privacy reason it is encrypted in a non reversible way. <br/><br/>" + 
  					"Here is your new password : "+ newPassword + 
  					"<ol>" +  
  					"<li>password are case sensitive,</li>" +  
  					"<li>This is a temporary password, feel free to change it using <a href='"+getUserPageUrl(user)+"'>your profile page</a>.</li>" +
  					"</ol>", 
  					MailType.IMMEDIATE, MailCategory.USER);
  		} else {
  			mailService.sendMail(user.getMail(), organization.getUrlFragment() + " KnowledgeBlackBelt Portal - Password Recovery",
  					"You requested a new password for your account '"+ user.getNickName()+"' on " + organization.getUrlFragment() + ".KnowledgeBlackBelt.com<br/>" + 
  					"We could not give you back your old password because we do not store it directly, for security and (your own) privacy reason it is encrypted in a non reversible way. <br/><br/>" + 
  					"Here is your new password : "+ newPassword + 
  					"<ol>" +  
  					"<li>password are case sensitive,</li>" +  
  					"<li>This is a temporary password, feel free to change it using <a href='"+getUserPageUrl(user)+"'>your profile page</a>.</li>" +
  					"</ol>", 
  					MailType.IMMEDIATE, MailCategory.USER);  			
  		}

	}

    public String getAliasFilename() {
        return aliasFilename;
    }

    public void setAliasFilename(String aliasFilename) {
        this.aliasFilename = aliasFilename;
    }

//	public void setTemplateService(TemplateService templateService) {
//		this.templateService = templateService;
//	}

    public void setCleanShutdownService(CleanShutdownService cleanShutdownService) {
		this.cleanShutdownService = cleanShutdownService;
	}

//	public void setCongratulationBeltTemplate(String congratulationBeltTemplate) {
//		this.congratulationBeltTemplate = congratulationBeltTemplate;
//	}
//
//	public void setRegistrationTemplate(String registrationTemplate) {
//		this.registrationTemplate = registrationTemplate;
//	}
//
//	public void setPasswordRecoveryTemplate(String passwordRecoveryTemplate) {
//		this.passwordRecoveryTemplate = passwordRecoveryTemplate;
//	}

	public boolean canViewInfluenceField(User viewer, User viewed) {
		// A user (viewer) can view the influence of another user (viewed) only if:
		if (viewed.getInfluence() > 1) {  // New simple rule: we show the (non 1) influence of anybody.
			return true;
		} else {
	        if (  SecurityContext.isUserHasPrivilege(Privilege.EDIT_INFLUENCE_OF_USERS)) {
                // super user....
               return true;
            }
			return false;
		}
//		// A user (viewer) can view the influence of another user (viewed) only if:
//		if (viewer.equals(viewed) && viewed.getInfluence() > 1) {
//			// The viewer looks at his own profile and has a special influence (else he must not be aware about the influence).
//			return true;
//		} else if (viewer.equals( viewed.getInfluenceAssigner() ) ) {
//			// The viewer did assign a special influence to the looked viewed user.
//			return true;
//		} else if (  SecurityContext.isUserHasPrivilege(Privilege.EDIT_INFLUENCE_OF_USERS)) {
//			// super user....
//			return true;
//		}
//		return false;
		
	}

	public boolean canEditInfluenceField(User assigner, User target) {
		if (assigner == null) {  // not logged in probably.
			return false;  
		}
	    if (assigner.getInfluence() * User.INFLUENCE_ASSIGNER_MAX_FACTOR > target.getInfluence()) {
	    	// assigner can put a bigger influence to target than target's current influence.
	    	return true;
		} else if (  SecurityContext.isUserHasPrivilege(Privilege.EDIT_INFLUENCE_OF_USERS)) {
			// super user....
			return true;
		}
        return false;
	}
	
	public void changeInflucenceField(User assigner, User target, float newInfluence) {
		target.setInfluence(newInfluence);
		target.setInfluenceAutoComputed(false);
		target.setInfluenceAssigner(assigner);
		getDaoFacade().getUserDao().save(target);
	}
	
	
	/**
	 * Returns a String describing what is missing in the profile of the user 
	 * @return
	 */
	@Override
	public String getProfileCompletionString(User user){
		StringBuilder builder = new StringBuilder();
		int count = 0;
		if(StringUtils.isBlank(user.getPictureName())){
			builder.append("picture");
			count++;
		}
		if(StringUtils.isBlank(user.getSkypeId())){
			builder.append((count > 0 ? "," : "") + " skype identifier");
			count++;
		}
		if(StringUtils.isBlank(user.getShortInfo())){
			builder.append((count > 0 ? "," : "") + " short information");
			count++;
		}
		if(StringUtils.isBlank(user.getVideoId())){
			builder.append((count > 0 ? "," : "") + " video");
			count++;
		}
		
		if(count == 0){ // nothing missing
			return "";
		} else if (count == 1) {
			builder.append(" is missing");
		} else {
			builder.append(" are missing");			
		}
		return builder.toString();
	}
	
	/** Percentage of completion for a user profile. Is there much information visible?
	 *  If you change the logic you should update the above method accordingly
	 * */
	@Override
	public float getProfileCompletionLevel(User user) {
		float result = 0f;
		if(StringUtils.isNotBlank(user.getPictureName())){
			result += 0.25f;
		}
		if(StringUtils.isNotBlank(user.getSkypeId())){
			result += 0.25f;
		}
		if(StringUtils.isNotBlank(user.getShortInfo())){
			result += 0.25f;
		}
		if(StringUtils.isNotBlank(user.getVideoId())){
			result += 0.25f;
		}
		return result;
	}


	@Override
	public void sendRecruitmentMail(User logged, String to, String htmlContent) {
		
		// FIXME How to have a Full Url to pages that are not entity pages for mails ? 
		String htmlLink = "<br/><br/><a href='"+UrlUtil.getAbsoluteUrl(new ParamPageResource(RegisterPage.class).addParam(RegisterPage.RECRUITER_PARAMNAME, logged.getNickName()).addParam(RegisterPage.EMAIL_PARAMNAME, to) )+"'>" +
				"Register via this page to help "+logged.getFullName()+"</a>";
	
		
		mailService.sendMail(to, logged, logged.getFullName() + " sent you a message", htmlContent + htmlLink, MailType.IMMEDIATE, MailCategory.USER);
		
	}


	@Override
	public String sendReactivationMail(User logged, String to, String htmlContent) {
		User user = userDao.getUserByNickName(to);
		if(user == null && StringUtils.isNumeric(to)){
			user = userDao.get(Long.valueOf(to));
		}
		
		if(user == null){
			return "Impossible to find user with nickname or id " + to;
		}
		
		Date oneYearAgo = DateUtils.addYears(new Date(), -1);
		
		if(user.getLastAccess().after(oneYearAgo)){
			return user.getFullName() + " has been logged in recently, it is not necessary to reactivate him";
		}		
		
		if(user.getReactivator() != null){
			return user.getFullName() + " has already been reactivated by " + user.getReactivator() + ", it is not necessary to reactivate him";
		}				
		
		// FIXME How to have a Full Url to pages that are not entity pages for mails ? 
		String htmlLink = "<br/><br/><a href='"+UrlUtil.getAbsoluteUrl(new ParamPageResource(ReactivatePage.class).addParam(ReactivatePage.REACTIVATOR_PARAMNAME, logged.getNickName()).addParam(ReactivatePage.REACTIVATED_PARAMNAME, user.getNickName()).addParam(ReactivatePage.TOKEN_PARAMNAME, ReactivatePage.computeToken(logged,user)))+"'>"+
				"Visit this page to help "+logged.getFullName()+" getting points</a>"; 
		
		mailService.sendMail(to, logged.getFullName() + " sent you a message", htmlContent + htmlLink, MailType.IMMEDIATE, MailCategory.USER);

		return null;
	}
	
	
	/** Encoding user's nickname and registration date to have a parameter sent to a page */
	@Override
    public String getUserSecurityString(User user){
		if(user.getRegistrationDate() != null){
			return SecurityUtils.md5Encode(user.getNickName()+user.getRegistrationDate().toString()).substring(0, 6);
		} else {
			return ""; //Some old users dont have a registration date. To avoid a NullPointerException, we return an empty String.
		}
		
    }
	
	/** Make the user's account anonymous: replace first name and last name by first letter
	 * replace nickname by user's id,remove email address and remove long and short description*/
	public void removeUserAccount(User user){
		changeUserName(user, user.getFirstName().charAt(0)+"",user.getLastName().charAt(0)+""); // Logging
		user.setNickName(Long.toString(user.getId())); 
		user.setMail(null);
		user.setShortInfo(null);
		user.setLongInfo(null);
		userDao.save(user);
	}

	/** Change the name of the user and note it in the log*/
	@Override	
	public void changeUserName(User user, String newFirstName, String newLastName){
		if (user.getNameChangeLog() == null) {
			user.addNameChangeLog("Previous name: "+ user.getFirstName() + " - " + user.getLastName());
		}
		user.addNameChangeLog(DateUtil.formatyyyyMMdd(new Date() ) + ": " + newFirstName +" - " + newLastName );
		user.setFirstName(newFirstName); 
		user.setLastName(newLastName); 
		userDao.save(user);
	}

	private String getUserPageUrl(User user){
		return UrlUtil.getAbsoluteUrl((new ParamPageResource(UserPage.class, user)));
	}
	
	//////////////// V5 Influence /////////////////////////////////////////
	/**
	 * Recompute the influence based on the following formula
	 * Base = 1
	 * +1 per 50 questions authored released
	 * +1 when 90% of the last 20 released questions had no concerne raised
	 * x level of coaching
	 * REM: If you change this business logic, you should also change the computeInfluenceReasons() method
	 */
	public String recomputeAndStoreInfluence(User user){
		if(user == null){
			throw new BlackBeltException("User cannot be null");
		}
		
		if(!user.isInfluenceAutoComputed()){ // Rare case : John, Nicolas, Henryk, ...
			return null;
		}
		
		float oldInfluence = user.getInfluence();
		float influence = 1; // base
		
		// +x if good author
		long amountOfReleasedQuestions = questionDao.getAmountOfQuestionForUserByState(user, V5Question.State.RELEASED);
		influence += (amountOfReleasedQuestions / INFLUENCE_AMOUNT_OF_RELEASED_QUESTIONS);
		
		// +1 if no concern raised on the last 20 authored questions
		if(amountOfReleasedQuestions >= INFLUENCE_AMOUNT_OF_RELEASED_QUESTIONS_WITH_NO_CONCERNS &&
			computePercentageOfReleasedQuestionsAuthoredByWithNoConcern(user, INFLUENCE_AMOUNT_OF_RELEASED_QUESTIONS_WITH_NO_CONCERNS) < INFLUENCE_PERCENTAGE_OF_RELEASED_QUESTIONS_WITH_NO_CONCERNS){
			influence += 1;
		}
		
		// influence = influence X COACH_LEVEL. Rem: people with no coach level have are equivalent to people with level 1
        Badge badge = badgeService.getBadgeOfTypeGroup(user, BadgeTypeGroup.COACH_LEVEL);
        influence *= (badge != null ? badge.getBadgeType().getLevel() : 1);

        if(oldInfluence != influence){
        	user.setInfluence(influence);
        	userDao.save(user);
        	return String.format("Influence changed from %s to %s", oldInfluence, influence); //TODO format float
        }
        return null;
	}
	
	/**
	 * Generates a String that describes the influence factor calculation details
	 * This method is build based on the logic of the recomputeAndStoreInfluence(User user) method
	 */
	public String computeInfluenceReasons(User user){
		if(user == null){
			throw new BlackBeltException("User cannot be null");
		}

		if(!user.isInfluenceAutoComputed()){ // Rare case : John, Nicolas, Henryk, ...
			return String.format("Influence manually set to %s by %s", user.getInfluence(), user.getInfluenceAssigner().getFullName());
		}

		StringBuilder result = new StringBuilder("Base = 1\n");

		
		long amountOfReleasedQuestions = questionDao.getAmountOfQuestionForUserByState(user, V5Question.State.RELEASED);
		
		//
		long bonus1 = (amountOfReleasedQuestions / INFLUENCE_AMOUNT_OF_RELEASED_QUESTIONS); 
		result.append(String.format("+%s because %s authored questions released (+1 point per %s questions)\n", bonus1, amountOfReleasedQuestions, INFLUENCE_AMOUNT_OF_RELEASED_QUESTIONS));
		
		long bonus2Percentage = computePercentageOfReleasedQuestionsAuthoredByWithNoConcern(user, INFLUENCE_AMOUNT_OF_RELEASED_QUESTIONS_WITH_NO_CONCERNS);
		if(amountOfReleasedQuestions >= INFLUENCE_AMOUNT_OF_RELEASED_QUESTIONS_WITH_NO_CONCERNS && bonus2Percentage >= INFLUENCE_PERCENTAGE_OF_RELEASED_QUESTIONS_WITH_NO_CONCERNS){
			result.append(String.format("+1 because %s percent of %s last released questions had no concern raised)\n",INFLUENCE_PERCENTAGE_OF_RELEASED_QUESTIONS_WITH_NO_CONCERNS, INFLUENCE_AMOUNT_OF_RELEASED_QUESTIONS_WITH_NO_CONCERNS));
		}

        Badge badge = badgeService.getBadgeOfTypeGroup(user, BadgeTypeGroup.COACH_LEVEL);
        long bonus3 = (badge != null ? badge.getBadgeType().getLevel() : 1);
        int transformedLevel = (badge != null ? badge.getBadgeType().getLevel() : 0);
        result.append(String.format("x%s because coach level is %s\n", bonus3, transformedLevel));
        
		return result.toString();
	}
	
	
	/**
	 * Used in influence factor computation
	 */
	public int computePercentageOfReleasedQuestionsAuthoredByWithNoConcern(User user, int amountOfQuestion) {
		
		List<V5Question> lastXXQuestions = questionDao.getLastReleasedQuestions(user, amountOfQuestion);

		if(lastXXQuestions.size() < amountOfQuestion){ // Not enough released questions to do computation
			return 100; // that means all the question are bad
		}
		
		//Count the number of question with bad vote
		int countQuestionsWithNoConcern = 0;
		for (V5Question question : lastXXQuestions) {
			if(questionCriteriaVoteDao.hasEverHadNegativeVotes(question)){
				countQuestionsWithNoConcern++;
			}
		}
		
		// return percentage
		return Math.round((countQuestionsWithNoConcern * 100f) / amountOfQuestion);
	}
	
	/**
	 *  Slices of 5% are used as triggers for BCD
	 *  100% - 5% = 95% -> triggers a mail explaining the mecanism
	 *  100% - (2x5%) = 90% -> User is flagged as bad contributor
	 */
	public static int BAD_CONTRIBUTOR_THRESHOLD_TRIGGER_PERCENTAGE = 5;

	/**
	 * amount of contrib points, below this limit the user is not flagged as bad contributor
	 */
	public static int BAD_CONTRIBUTOR_MINIMUM_POINTS = 20;

	/**
	 * Recompute GCR, if user has more than XX points
	 * being below the threshold will trigger mails and/or 'bad contrib flag' on the user 
	 * @param user
	 */
	@Override
	public void detectBadContributor(User user){
		// Mails are only send if the user has more than XX points
		if(user.getContributionPointsEarned() < BAD_CONTRIBUTOR_MINIMUM_POINTS){
			return; 
		}

		// If the user has already been detected as a bad contributor he should not be able to vote anymore
		// This method should therefore not be called for such user -> log a warning
		if(user.getCredibility() == ContributorCredibility.BAD){
			logger.warn(String.format("A bad contributor should not be able to vote, so this method should not be called %s (%s)", user.getFullName(), user.getId()), new RuntimeException());
			return;
		}
		
		int newGCR = moderationService.computeGoodContributionRate(user);
		
		if(newGCR <= (100 - 2 * BAD_CONTRIBUTOR_THRESHOLD_TRIGGER_PERCENTAGE)){
			userIsBadContributor(user);
			return;
		}
		
		// If user has already been detected has being below the threshold (and the warning mail has already been sent) return
		if(user.getCredibility() == ContributorCredibility.BELOW_THRESHOLD){
			return;
		}
			
		if(newGCR <= (100 - BAD_CONTRIBUTOR_THRESHOLD_TRIGGER_PERCENTAGE)){
			userSuspectedOfBeignBadContributor(user);
			return;
		}
	}

	@Override
	public void warnUserOfGoodContributionRateMecanism(User user) {
		String badValue = (100 - 2 * BAD_CONTRIBUTOR_THRESHOLD_TRIGGER_PERCENTAGE) + "%"; 
		String thresholdValue = (100 - BAD_CONTRIBUTOR_THRESHOLD_TRIGGER_PERCENTAGE) + "%";
		
		mailService.sendMail(user, "'Good Contribution' rate explained",
				String.format("Dear %s <br/><br/>", user.getFullName()) +
				String.format("Congratulations, you've reached %s points contribution on KnowledgeBlackBelt.com. ", BAD_CONTRIBUTOR_MINIMUM_POINTS) +
				"Now that this limit is behind you, the 'Good Contribution Rate' mechanism has been activated, here are the details of your rate's computation:<br/>" + 
				moderationService.computeGoodContributionRateReasons(user).replaceAll("\n", "<br/>") + "<br/>" +
				String.format("If it reaches %s  you will get a warning mail. ", thresholdValue) +
				String.format("If it reaches %s  your right to contribute will be revoked.", badValue)
				, MailType.IMMEDIATE, MailCategory.USER);		
	}
	
	private void userSuspectedOfBeignBadContributor(User user) {
		user.setCredibility(ContributorCredibility.BELOW_THRESHOLD);
		userDao.save(user);
		
		int badValue = 100 - 2 * BAD_CONTRIBUTOR_THRESHOLD_TRIGGER_PERCENTAGE;
		
		mailService.sendMail(user, "Warning : Your 'Good Contribution' rate has dropped below the threshold",
				"Your 'Good Contribution' rate has dropped below the threshold <br/><br/> " +
				moderationService.computeGoodContributionRateReasons(user).replaceAll("\n", "<br/>") +
				"<br/>If it reaches the " + badValue + "% you won't be able to contribute anymore"
				, MailType.IMMEDIATE, MailCategory.USER);		
	}

	private void userIsBadContributor(User user) {
		user.setCredibility(ContributorCredibility.BAD);
		userDao.save(user);
		
		mailService.sendMail(user, "Your 'Good Contribution' rate has dropped below the threshold",
				"Your 'Good Contribution' rate has dropped below the threshold <br/><br/> " +
				moderationService.computeGoodContributionRateReasons(user).replaceAll("\n", "<br/>") +
				"<br/>You won't be able to contribute to the site anymore. Please contact an Administrator"
				, MailType.IMMEDIATE, MailCategory.USER);

	}


	/**
	 * Ensure a potential nickname does not already exist
	 * Append a number at the end until not found in DB 
	 */
	@Override
	public String ensureNicknameUniqueness(String nickNameToTest) {
		User user = null;
		String tempName = nickNameToTest;
		int i = 1;
		do {
			user = userDao.getUserByNickName(tempName);
			if(user != null){
				tempName = nickNameToTest + i++;
			}
		} while (user != null);
		
		return tempName;
	}
	
	/**
	 * Given a list of KBB nicknames, returns the non existing ones
	 * The boolean tells whether the resulting list should be sorted or not
	 */
	@Override
	public List<String> assertNicknamesExists(List<String> nicknames, boolean resultSorted){
		List<String> result = new LinkedList<String>();
		
		for (String nickname : nicknames) {
			User user = communityUserDao.getUserByNickName(nickname.trim());
			if(user == null){
				result.add(nickname);
			}
		}
		
		if(resultSorted){
			Collections.sort(result);
		}
		return result;
	}
	
	
	public String[] getEMailsByPrivilege(Privilege privilege) {
		List<User> courseManagers = userDao.getUsersHavingPrivilege(privilege);
		List<String> mails = new ArrayList<String>();
		for(User courseManager : courseManagers) {
		    mails.add(courseManager.getMail());
		}
		return mails.toArray(new String[mails.size()]);
	}	
	
	public BeltV5 getUserBeltFromCommunity(User user, Community community) {
		for(BeltV5 belt : user.getBelts()) {
			if(belt.getCommunity() == community) {
				return belt;
			}
		}
		return null;
	}
	
}