package blackbelt.impl;

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
import blackbelt.service.UserService;
import blackbelt.ui.RegisterPage;
import blackbelt.ui.RegisterValidatePage;
//import blackbelt.ui.document.DocumentPage;
//import blackbelt.ui.user.UserPage;
//import blackbelt.util.BlackBeltException;
import blackbelt.web.ContextUtil;
import blackbelt.web.UrlUtil;

public class UserServiceImpl /* extends BaseService */implements UserService {

	// Constant values used in the automatic calculation of the influence factor
	public static final int INFLUENCE_PERCENTAGE_OF_RELEASED_QUESTIONS_WITH_NO_CONCERNS = 90;
	public static final int INFLUENCE_AMOUNT_OF_RELEASED_QUESTIONS_WITH_NO_CONCERNS = 20;
	public static final int INFLUENCE_AMOUNT_OF_RELEASED_QUESTIONS = 50;

	// private TemplateService templateService;

	// private CleanShutdownService cleanShutdownService;

	// private String congratulationBeltTemplate;
	//
	// private String registrationTemplate;
	//
	// private String passwordRecoveryTemplate;

	private String aliasFilename;

	protected Logger logger = Logger.getLogger(getClass());

	// @Autowired private GroupService groupService;
	// @Autowired private ContributionService contributionService;
	// @Autowired private UserDao UserDao;
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
		// TODO maxime delete?
		// // Automatically add groups for which that e-mail would have been 
		// invited.
		// groupService.groupNewUserUsingInvitations(user);

	}

	/**
	 * register a user
	 * 
	 * @param directValidation
	 *            : validate an account directly without send a mail
	 */
	
	//TODO remove sattic when using JPA
	public static User registerUser(boolean directValidation, String firstName, String lastName,
			Gender gender, String nickname, String passwordInClear, String mail) {
		System.out.println("register user je suis appelé");
		User toRegister = new User();
		toRegister.setFirstName(firstName);
		toRegister.setLastName(lastName);
		toRegister.setGender(gender);
		toRegister.setNickName(nickname);
		toRegister.setPassword(SecurityUtils.md5Encode(passwordInClear));
		toRegister.setMail(mail);

		// TODO maxime use?
		String base = toRegister.getMail() + toRegister.getPassword() + Math.random();
		toRegister.setValidationCode(SecurityUtils.md5Encode(base.toString()));

		if (directValidation) {
			toRegister.setAccountStatus(AccountStatus.ACTIVE);
		} else {
			toRegister.setAccountStatus(AccountStatus.NOTVALIDATED);
		}
		System.out.println("je save le user  : "+toRegister.toString());
		// Save the user in the db
		UserDao.save(toRegister);

		// All is ok lets eventually send a validation email
		if (!directValidation) {
			sendRegistrationValidationMail(toRegister);
		}
		// TODO maxime delete?
		// // Automatically add groups for which that e-mail would have been
		// invited.
		// groupService.groupNewUserUsingInvitations(user);
		return toRegister;
	}

	public void deleteUser(User user, boolean deleteQuestions,
			boolean deleteVotes) {

		// FIXME PARKING We should do a soft delete here!
		// Soft delete is possible (like for candidates)
		// But users owns lot of stuffs that can
		// be use to access user informations (like
		// when we display a question'author)
		// ---> See comment in Fixme of CorpUserManager
		throw new UnsupportedOperationException("Not supported yet");

		// User specialUser =
		// this.UserDao.getUserByNickName("unsubscribeuser");
		//
		// List<Question> questions =
		// this.getDaoFacade().getQuestionDao().getAllQuestionsByUser(user.getId());
		//
		// // change owner of questions.
		// for (Question question : questions) {
		//
		// ContextUtil.getBLFacade().getQuestionService().changeQuestionsOwner(question,
		// specialUser);
		//
		// if (logger.isDebugEnabled()) {
		// logger.debug("Change owner of question #" + question.getPublicId() +
		// "(was " + user.getFullName() + ")");
		// }
		//
		// if (deleteQuestions) {
		//
		// question.setDeleted(true);
		//
		// this.getDaoFacade().getQuestionDao().storeQuestion(question);
		//
		// if (logger.isDebugEnabled()) {
		// logger.debug("Soft deleted question #" + question.getPublicId());
		// }
		//
		// }
		//
		// }
		//
		// if (logger.isInfoEnabled()) {
		// logger.info("Changed Questions for " + user.getFullName());
		// }
		//
		// // change owner of questionVersions
		// List<QuestionVersion> versions =
		// this.getDaoFacade().getQuestionDao().getQuestionVersionsByUse(user);
		//
		// for (QuestionVersion version : versions) {
		//
		// version.setAuthor(specialUser);
		//
		// this.getDaoFacade().getQuestionDao().storeQuestionVersion(version);
		//
		// if (logger.isDebugEnabled()) {
		// logger.debug("Changed owner of QuestionVersion #" + version.getId() +
		// "(was " + user.getFullName() + ")");
		// }
		//
		// }
		//
		// if (logger.isInfoEnabled()) {
		// logger.info("Changed QuestionVersions for " + user.getFullName());
		// }
		//
		// // change owner of userActions
		// List<UserAction> actions =
		// this.UserDao.getUserActionsByUser(user);
		//
		// for (UserAction action : actions) {
		//
		// action.setUser(specialUser);
		//
		// this.UserDao.storeUserAction(action);
		//
		// if (logger.isDebugEnabled()) {
		// logger.debug("Changed owner of UserAction #" + action.getId() +
		// "(was " + user.getFullName() + ")");
		// }
		//
		// }
		//
		// if (logger.isInfoEnabled()) {
		// logger.info("Changed UserActions for " + user.getFullName());
		// }
		//
		// // change owner of votes or delete them.
		// if (deleteVotes) {
		// ContextUtil.getBLFacade().getVoteService().deleteUserVotes(user);
		// } else {
		// ContextUtil.getBLFacade().getVoteService().changeVoteOwner(user,
		// specialUser);
		// }
		//
		// if (logger.isInfoEnabled()) {
		// logger.info("Changed/Deleted Votes for " + user.getFullName());
		// }
		//
		// // change owner of bids.
		// ContextUtil.getBLFacade().getAuctionService().changeBidsOwner(user,
		// specialUser);
		//
		// if (logger.isInfoEnabled()) {
		// logger.info("Changed Bids for " + user.getFullName());
		// }
		//
		// List<Comment> comments =
		// this.getDaoFacade().getCommentDao().getCommentsByUser(user);
		//
		// // change owner of comments and close/hide them.
		// for (Comment comment : comments) {
		//
		// comment.setAuthor(specialUser);
		// comment.setHidden(true);
		// comment.setStatus(Comment.CLOSED);
		//
		// this.getDaoFacade().getCommentDao().store(comment);
		//
		// if (logger.isDebugEnabled()) {
		// logger.debug("Changed owner of Comment #" + comment.getId() + "(was"
		// + user.getFullName() + ")");
		// }
		//
		// }
		//
		// if (logger.isInfoEnabled()) {
		// logger.info("Changed Comments for " + user.getFullName());
		// }
		//
		// // Delete User's contributions.
		// ContextUtil.getBLFacade().getContributionService().deleteUsersContributions(user);
		//
		// if (logger.isInfoEnabled()) {
		// logger.info("Deleted Contributions for " + user.getFullName());
		// }
		//
		//
		// // Delete User's Events.
		// List<Event> events =
		// this.getDaoFacade().getEventDao().getUsersEvents(user);
		//
		// for (Event event : events) {
		//
		// if (logger.isDebugEnabled()) {
		// logger.debug("Deleted Event #" + event.getId());
		// }
		//
		// this.getDaoFacade().getEventDao().deleteEvent(event);
		//
		// }
		//
		// if (logger.isInfoEnabled()) {
		// logger.info("Deleted Events for " + user.getFullName());
		// }
		//
		// // Delete ExamTaskPerformed.
		// ContextUtil.getBLFacade().getExamService().deleteUsersExamTasksPerformed(user);
		//
		// if (logger.isInfoEnabled()) {
		// logger.info("Deleted ExamTasksPerformed for " + user.getFullName());
		// }
		//
		// // Delete ExamPerformed.
		// ContextUtil.getBLFacade().getExamService().deleteUsersExamsPerformed(user);
		//
		// if (logger.isInfoEnabled()) {
		// logger.info("Deleted ExamsPerformed for " + user.getFullName());
		// }
		//
		// // Delete Questionnaires.
		// ContextUtil.getBLFacade().getQuestionnaireService().deleteUsersQuestionnaire(user);
		//
		// if (logger.isInfoEnabled()) {
		// logger.info("Deleted Questionnaires for " + user.getFullName());
		// }
		//
		// this.UserDao.delete(user);
		//
		// if (logger.isInfoEnabled()) {
		// logger.info("Deleted " + user.getFullName());
		// }

	}

	//TODO remove static when using JPA
	public static void sendRegistrationValidationMail(User user) {

		// Map<String, Object> map = new HashMap<String, Object>();
		// map.put("user", user);
		//
		//
		// String htmlMessage =
		// templateService.prepareMailContent(registrationTemplate, map);
		// ContextUtil.getBLFacade().getEmailService().sendNotificationEmail(htmlMessage,
		// "JavaBlackBelt: Your new account", user.getMail());

		// TODO maxime uncomment
		// String validationUrl = UrlUtil.getAbsoluteUrl(new ParamPageResource(
		// RegisterValidatePage.class, user, user.getValidationCode()));
		// String htmlMessage = "Welcome on KnowledgeBlackBelt.com, "
		// + user.getFullName()
		// + "."
		// + "<br/>There is just one step left to get your new account : "
		// + "please click on the link below to validate your email address !"
		// + "<br/><a href='"
		// + validationUrl
		// + "'>"
		// + validationUrl
		// + "</a>"
		// +
		// "<br/><br/>If you are experiencing any problems, try copy/pasting the URL in your browser (instead clicking on it), or ultimately "
		// + "<a href='"
		// + UrlUtil.getAbsoluteUrl(new PageResource(DocumentPage.class,
		// "ContactUs"))
		// + "'>contact us.</a>"
		// +
		// "<br/><br/>Thank you for registering and have a nice time on KnowledgeBlackBelt.";
		// TODO maxime uncomment
		// mailService.sendMail(user.getMail(), "Your new account", htmlMessage,
		// MailType.IMMEDIATE, MailCategory.USER);
	}

	// TODO maxime delete?
	// /**
	// * Add contribution points to the User's spentPoints. Points are spent by
	// * User.<br />
	// * Use it if you want to spend points. Don't modify it !
	// *
	// * @param user
	// * User wich spentPoints will be modified.
	// * @param points
	// * points to add to spentPoints.
	// * @throws NotEnoughAvailablePointsException
	// * if availablePoints of the User are lesser than points to be
	// * spent.
	// */
	// // TODO why is it synchronized ?
	// public synchronized void spendPoints(User user, float points)
	// throws NotEnoughAvailablePointsException {
	//
	// // locking of the user to avoid double spent
	// user = this.UserDao.getUserForUpgrade(user.getId());
	//
	// if (user.getContributionPointsEarned()
	// - user.getContributionPointsSpent() < points) {
	// throw new NotEnoughAvailablePointsException();
	// }
	//
	// user.setContributionPointsSpent(user.getContributionPointsSpent()
	// + points);
	// UserDao.save(user);
	//
	// }

	// TODO maxime delete?
	// /**
	// * Substract contribution points from User's spentPoints. Points are
	// refund
	// * to User.<br />
	// * Use it if you want to spend points. Don't modify it !
	// *
	// * @param user
	// * User wich spentPoints will be modified.
	// * @param points
	// * points to add to spentPoints.
	// * @throws NotEnoughAvailablePointsException
	// * if you try to refund more points than spent.
	// */
	// public synchronized void refundPoints(User user, float points)
	// throws NotEnoughAvailablePointsException {
	//
	// // locking of the user to avoid double spent
	// this.UserDao.getUserForUpgrade(user.getId());
	//
	// user.setContributionPointsSpent(Math.max(0,
	// user.getContributionPointsSpent() - points));
	// UserDao.save(user);
	//
	// }

	// TODO maxime uncomment
	// public void saveUserImages(User user, InputStream imageFileStream) {
	// if (user == null || user.getId() == null) {
	// throw new IllegalArgumentException(
	// "Could not save the pircture of an unpersited user");
	// }
	//
	// String oldPictureName = user.isPicture() ? user.getPictureName() : null;
	// String[] scaleFolderNames = new String[] { "", "medium", "small" };
	//
	// try {
	// user.setPicture(true);
	// // add the file name to the user
	// user.setPictureName(user.getId() + "-" + new Date().getTime()
	// + ".png");
	//
	// ContextUtil
	// .getBLFacade()
	// .getFileService()
	// .saveAndScaleImage(imageFileStream, user.getPictureName(),
	// "users", true, "originals", true,
	// new int[] { 100, 44, 22 },
	// new int[] { 150, 66, 33 }, scaleFolderNames,
	// ImageSaveFormat.PNG);
	//
	// contributionService.addImage(user);
	// } catch (Exception e) {
	// logger.error("Could not save user images", e);
	// user.setPicture(false);
	// user.setPictureName(null);
	// }
	// UserDao.save(user);
	//
	// if (oldPictureName != null) {
	// ContextUtil.getBLFacade().getFileService()
	// .deleteUserPictures(oldPictureName);
	// }
	// }
	// TODO maxime uncomment
	// @Override
	// public void removeUserImages(User user) {
	// String oldPictureName = user.isPicture() ? user.getPictureName() : null;
	//
	// user.setPicture(false);
	// user.setPictureName(null);
	// contributionService.removeImage(user);
	// UserDao.save(user);
	//
	// if (oldPictureName != null) {
	// ContextUtil.getBLFacade().getFileService()
	// .deleteUserPictures(oldPictureName);
	// }
	// }

	public void generateNewPasswordForUserAndSendEmail(User user) {
		// We generate a password
		String newPassword = SecurityUtils.generateRandomPassword(8, 12);
		// we set the new password to the user
		user.setPassword(SecurityUtils.md5Encode(newPassword));
		UserDao.save(user);

		// Map<String, Object> map = new HashMap<String, Object>();
		// map.put("password", newPassword);
		// String htmlText =
		// templateService.prepareMailContent(passwordRecoveryTemplate, map);
		// ContextUtil.getBLFacade().getEmailService().sendNotificationEmail(htmlText,
		// "JavaBlackBelt: Your new password", user);
		// TODO maxime uncomment
		// Organization organization =
		// organizationService.getOrganization(user);
		// if (organization == null) {
		// mailService
		// .sendMail(
		// user.getMail(),
		// "Password Recovery",
		// "You requested a new password for your account '"
		// + user.getNickName()
		// + "' on KnowledgeBlackBelt.com<br/>"
		// +
		// "We could not give you back your old password because we do not store it directly, for security and (your own) privacy reason it is encrypted in a non reversible way. <br/><br/>"
		// + "Here is your new password : "
		// + newPassword
		// + "<ol>"
		// + "<li>password are case sensitive,</li>"
		// +
		// "<li>This is a temporary password, feel free to change it using <a href='"
		// + getUserPageUrl(user)
		// + "'>your profile page</a>.</li>" + "</ol>",
		// MailType.IMMEDIATE, MailCategory.USER);
		// } else {
		// mailService
		// .sendMail(
		// user.getMail(),
		// organization.getUrlFragment()
		// + " KnowledgeBlackBelt Portal - Password Recovery",
		// "You requested a new password for your account '"
		// + user.getNickName()
		// + "' on "
		// + organization.getUrlFragment()
		// + ".KnowledgeBlackBelt.com<br/>"
		// +
		// "We could not give you back your old password because we do not store it directly, for security and (your own) privacy reason it is encrypted in a non reversible way. <br/><br/>"
		// + "Here is your new password : "
		// + newPassword
		// + "<ol>"
		// + "<li>password are case sensitive,</li>"
		// +
		// "<li>This is a temporary password, feel free to change it using <a href='"
		// + getUserPageUrl(user)
		// + "'>your profile page</a>.</li>" + "</ol>",
		// MailType.IMMEDIATE, MailCategory.USER);
		// }

	}

	public String getAliasFilename() {
		return aliasFilename;
	}

	public void setAliasFilename(String aliasFilename) {
		this.aliasFilename = aliasFilename;
	}

	// public void setTemplateService(TemplateService templateService) {
	// this.templateService = templateService;
	// }
	// TODO maxime use?
	// public void setCleanShutdownService(
	// CleanShutdownService cleanShutdownService) {
	// this.cleanShutdownService = cleanShutdownService;
	// }

	//
	// public void setRegistrationTemplate(String registrationTemplate) {
	// this.registrationTemplate = registrationTemplate;
	// }
	//
	// public void setPasswordRecoveryTemplate(String passwordRecoveryTemplate)
	// {
	// this.passwordRecoveryTemplate = passwordRecoveryTemplate;
	// }

	// TODO maxime use?
	// public boolean canViewInfluenceField(User viewer, User viewed) {
	// // A user (viewer) can view the influence of another user (viewed) only
	// // if:
	// if (viewed.getInfluence() > 1) { // New simple rule: we show the (non 1)
	// // influence of anybody.
	// return true;
	// } else {
	// if (SecurityContext
	// .isUserHasPrivilege(Privilege.EDIT_INFLUENCE_OF_USERS)) {
	// // super user....
	// return true;
	// }
	// return false;
	// }
	// // // A user (viewer) can view the influence of another user (viewed)
	// // only if:
	// // if (viewer.equals(viewed) && viewed.getInfluence() > 1) {
	// // // The viewer looks at his own profile and has a special influence
	// // (else he must not be aware about the influence).
	// // return true;
	// // } else if (viewer.equals( viewed.getInfluenceAssigner() ) ) {
	// // // The viewer did assign a special influence to the looked viewed
	// // user.
	// // return true;
	// // } else if (
	// // SecurityContext.isUserHasPrivilege(Privilege.EDIT_INFLUENCE_OF_USERS))
	// // {
	// // // super user....
	// // return true;
	// // }
	// // return false;
	//
	// }

	// TODO maxime use?
	// public boolean canEditInfluenceField(User assigner, User target) {
	// if (assigner == null) { // not logged in probably.
	// return false;
	// }
	// if (assigner.getInfluence() * User.INFLUENCE_ASSIGNER_MAX_FACTOR > target
	// .getInfluence()) {
	// // assigner can put a bigger influence to target than target's
	// // current influence.
	// return true;
	// } else if (SecurityContext
	// .isUserHasPrivilege(Privilege.EDIT_INFLUENCE_OF_USERS)) {
	// // super user....
	// return true;
	// }
	// return false;
	// }

	// TODO delete?
	// public void changeInflucenceField(User assigner, User target,
	// float newInfluence) {
	// target.setInfluence(newInfluence);
	// target.setInfluenceAutoComputed(false);
	// target.setInfluenceAssigner(assigner);
	// UserDao.save(target);
	// }

	// TODO uncomment
	// /**
	// * Returns a String describing what is missing in the profile of the user
	// *
	// * @return
	// */
	// @Override
	// public String getProfileCompletionString(User user) {
	// StringBuilder builder = new StringBuilder();
	// int count = 0;
	// if (StringUtils.isBlank(user.getPictureName())) {
	// builder.append("picture");
	// count++;
	// }
	// if (StringUtils.isBlank(user.getShortInfo())) {
	// builder.append((count > 0 ? "," : "") + " short information");
	// count++;
	// }
	// if (StringUtils.isBlank(user.getVideoId())) {
	// builder.append((count > 0 ? "," : "") + " video");
	// count++;
	// }
	//
	// if (count == 0) { // nothing missing
	// return "";
	// } else if (count == 1) {
	// builder.append(" is missing");
	// } else {
	// builder.append(" are missing");
	// }
	// return builder.toString();
	// }

	// TODO maxime uncomment
	// /**
	// * Percentage of completion for a user profile. Is there much information
	// * visible? If you change the logic you should update the above method
	// * accordingly
	// * */
	// @Override
	// public float getProfileCompletionLevel(User user) {
	// float result = 0f;
	// if (StringUtils.isNotBlank(user.getPictureName())) {
	// result += 0.25f;
	// }
	// if (StringUtils.isNotBlank(user.getSkypeId())) {
	// result += 0.25f;
	// }
	// if (StringUtils.isNotBlank(user.getShortInfo())) {
	// result += 0.25f;
	// }
	// if (StringUtils.isNotBlank(user.getVideoId())) {
	// result += 0.25f;
	// }
	// return result;
	// }

	// TODO maxime uncomment
	// @Override
	// public void sendRecruitmentMail(User logged, String to, String
	// htmlContent) {
	//
	// // FIXME How to have a Full Url to pages that are not entity pages for
	// // mails ?
	// String htmlLink = "<br/><br/><a href='"
	// + UrlUtil.getAbsoluteUrl(new ParamPageResource(
	// RegisterPage.class).addParam(
	// RegisterPage.RECRUITER_PARAMNAME, logged.getNickName())
	// .addParam(RegisterPage.EMAIL_PARAMNAME, to)) + "'>"
	// + "Register via this page to help " + logged.getFullName()
	// + "</a>";
	//
	// mailService.sendMail(to, logged, logged.getFullName()
	// + " sent you a message", htmlContent + htmlLink,
	// MailType.IMMEDIATE, MailCategory.USER);
	//
	// }

	// TODO maxime uncomment
	// @Override
	// public String sendReactivationMail(User logged, String to,
	// String htmlContent) {
	// User user = UserDao.getUserByNickName(to);
	// if (user == null && StringUtils.isNumeric(to)) {
	// user = UserDao.get(Long.valueOf(to));
	// }
	//
	// if (user == null) {
	// return "Impossible to find user with nickname or id " + to;
	// }
	//
	// Date oneYearAgo = DateUtils.addYears(new Date(), -1);
	//
	// if (user.getLastAccess().after(oneYearAgo)) {
	// return user.getFullName()
	// + " has been logged in recently, it is not necessary to reactivate him";
	// }
	//
	// if (user.getReactivator() != null) {
	// return user.getFullName() + " has already been reactivated by "
	// + user.getReactivator()
	// + ", it is not necessary to reactivate him";
	// }
	//
	// // FIXME How to have a Full Url to pages that are not entity pages for
	// // mails ?
	// String htmlLink = "<br/><br/><a href='"
	// + UrlUtil.getAbsoluteUrl(new ParamPageResource(
	// ReactivatePage.class)
	// .addParam(ReactivatePage.REACTIVATOR_PARAMNAME,
	// logged.getNickName())
	// .addParam(ReactivatePage.REACTIVATED_PARAMNAME,
	// user.getNickName())
	// .addParam(ReactivatePage.TOKEN_PARAMNAME,
	// ReactivatePage.computeToken(logged, user)))
	// + "'>" + "Visit this page to help " + logged.getFullName()
	// + " getting points</a>";
	//
	// mailService.sendMail(to, logged.getFullName() + " sent you a message",
	// htmlContent + htmlLink, MailType.IMMEDIATE, MailCategory.USER);
	//
	// return null;
	// }

	/**
	 * Encoding user's nickname and registration date to have a parameter sent
	 * to a page
	 */
	@Override
	public String getUserSecurityString(User user) {
		if (user.getRegistrationDate() != null) {
			return SecurityUtils.md5Encode(
					user.getNickName() + user.getRegistrationDate().toString())
					.substring(0, 6);
		} else {
			return ""; // Some old users dont have a registration date. To avoid
						// a NullPointerException, we return an empty String.
		}

	}

	/**
	 * Make the user's account anonymous: replace first name and last name by
	 * first letter replace nickname by user's id,remove email address and
	 * remove long and short description
	 */
	public void removeUserAccount(User user) {
		changeUserName(user, user.getFirstName().charAt(0) + "", user
				.getLastName().charAt(0) + ""); // Logging
		user.setNickName(Long.toString(user.getId()));
		user.setMail(null);
		UserDao.save(user);
	}

	/** Change the name of the user and note it in the log */
	@Override
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

	// TODO maxime uncomment
	// private String getUserPageUrl(User user) {
	// return UrlUtil.getAbsoluteUrl((new ParamPageResource(UserPage.class,
	// user)));
	// }

	/**
	 * Ensure a potential nickname does not already exist Append a number at the
	 * end until not found in DB
	 * @throws UserNotFoundException 
	 */
	@Override
	public String ensureNicknameUniqueness(String nickNameToTest) throws UserNotFoundException {
		User user = null;
		String tempName = nickNameToTest;
		int i = 1;
		do {
			user = UserDao.getUserByNickName(tempName);
			if (user != null) {
				tempName = nickNameToTest + i++;
			}
		} while (user != null);

		return tempName;
	}

	/**
	 * Given a list of KBB nicknames, returns the non existing ones The boolean
	 * tells whether the resulting list should be sorted or not
	 * @throws UserNotFoundException 
	 */
	@Override
	public List<String> assertNicknamesExists(List<String> nicknames,
			boolean resultSorted) throws UserNotFoundException {
		List<String> result = new LinkedList<String>();

		for (String nickname : nicknames) {
			User user = UserDao.getUserByNickName(nickname.trim());
			if (user == null) {
				result.add(nickname);
			}
		}

		if (resultSorted) {
			Collections.sort(result);
		}
		return result;
	}
	// TODO maxime uncomment
	// public String[] getEMailsByPrivilege(Privilege privilege) {
	// List<User> courseManagers = UserDao.getUsersHavingPrivilege(privilege);
	// List<String> mails = new ArrayList<String>();
	// for (User courseManager : courseManagers) {
	// mails.add(courseManager.getMail());
	// }
	// return mails.toArray(new String[mails.size()]);
	// }

}