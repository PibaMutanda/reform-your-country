package blackbelt.ui;
//TODO uncoment import
import org.apache.commons.lang.StringUtils;

import reformyourcountry.dao.UserDao;
import reformyourcountry.model.User;
import reformyourcountry.model.User.Role;
import reformyourcountry.service.UserService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Configurable;
//import org.vaadin.navigator7.Page;
//import org.vaadin.navigator7.PageResource;
//import org.vaadin.navigator7.ParamChangeListener;
//import org.vaadin.navigator7.Navigator.NavigationEvent;
//import org.vaadin.navigator7.uri.Param;
//import org.vaadin.navigator7.uri.ParamPageResource;

//import be.loop.jbb.dao.exception.ConstraintViolatingEntityException;
import blackbelt.util.SecurityUtils;
//import blackbelt.model.Group;
//import blackbelt.model.GroupCandidacy;
//import blackbelt.security.SecurityContext;
//import blackbelt.service.GroupService;
//import blackbelt.ui.common.ComponentFactory;
//import blackbelt.ui.common.NotificationUtil;
//import blackbelt.ui.common.PictureResource;
//import blackbelt.ui.common.WindowUtil;
//import blackbelt.ui.common.PictureResource.PicturePath;
//import blackbelt.ui.common.validator.AbstractStringMessageValidator;
//import blackbelt.ui.group.GroupInvitationPage;
//import blackbelt.ui.user.NickNameField;
//import blackbelt.ui.user.UserPage;
import blackbelt.web.UrlUtil;

import com.vaadin.data.validator.EmailValidator;
import com.vaadin.ui.Button;
import com.vaadin.ui.Embedded;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;

//@SuppressWarnings("serial")
//@Configurable(preConstruction=true)
//@Page
public class RegisterPage extends VerticalLayout /*implements ParamChangeListener*/ {
    //TODO maxime uncomment
    /*@Autowired*/ private UserService userService = new UserService(); // TODO: Use autowiring with Spring instead of new.
    /*@Autowired*/ private UserDao userDao = new UserDao();// TODO: Use autowiring with Spring instead of new.

    public static final String RECRUITER_PARAMNAME = "recruiter";
    public static final String EMAIL_PARAMNAME = "email";
    public static final String GROUP_CANDIDACY_PARAMNAME = "groupCandidacy";
    //TODO maxime uncomment
    //	@Param(name=RECRUITER_PARAMNAME, required=false)   String recruiterParam;
    //	@Param(name=EMAIL_PARAMNAME, required=false)       String emailParam;
    //	@Param(name=GROUP_CANDIDACY_PARAMNAME, required=false)  GroupCandidacy groupCandidacyParam;

    public RegisterPage(){}

    //TODO maxime uncomment when using web
    //	@Override	
    public void paramChanged(/*NavigationEvent navigationEvent*/) {
	//TODO maxime uncomment when using web
//	(SecurityContext.getUser() != null){
//	    NotificationUtil.showNotification("User already logged", "Logout before you register");
//	    return;
//	} else {

	    this.removeAllComponents();
	    this.setMargin(true);
	    this.setSpacing(true);

	    VerticalLayout titleLayout = new VerticalLayout();
	    titleLayout.setSpacing(true);
	    titleLayout.setMargin(true);
	    this.addComponent(titleLayout);
	    HorizontalLayout imageAndFormLayout = new HorizontalLayout();
	    imageAndFormLayout.setSpacing(true);
	    imageAndFormLayout.setMargin(true);
	    imageAndFormLayout.setWidth("100%");
	    this.addComponent(imageAndFormLayout);

	    String title = "Register";
	  //TODO maxime uncomment when using web
//	    titleLayout.addComponent(ComponentFactory.createPageTitle(title));
//	    WindowUtil.setBrowserWindowTitle(title);

	    Label headLabel = new Label("Register, take real exams and pass belt colors.<br>" +
		    "Once registered, the more you progress, the more features you'll have access" +
		    "to (course access, question authoring, coaching, etc.).<br>", Label.CONTENT_XHTML);
	    titleLayout.addComponent(headLabel);

	  //TODO maxime uncomment when using web
	    ////// Decorative image on the left.
//	    PictureResource resource = new PictureResource(PicturePath.IMAGE, "WhiteKarategi.png");
//	    Embedded image = new Embedded("", resource);
//	    imageAndFormLayout.addComponent(image);

	    ////// Form
	    VerticalLayout fieldsAndLabelVLayout = new VerticalLayout();
	    imageAndFormLayout.addComponent(fieldsAndLabelVLayout);
	    fieldsAndLabelVLayout.setSpacing(true);
	    imageAndFormLayout.setExpandRatio(fieldsAndLabelVLayout, 1);

	    VerticalLayout passwordFieldsLayout = new VerticalLayout();
	    passwordFieldsLayout.setSpacing(false);

	    ////// Create horizontal layouts to contain form text field and label
	    HorizontalLayout nickNameHLayout = new HorizontalLayout();
	    HorizontalLayout firstNameHLayout = new HorizontalLayout();
	    HorizontalLayout lastNameHLayout = new HorizontalLayout();
	    HorizontalLayout emailHLayout = new HorizontalLayout();
	    HorizontalLayout passwordHLayout = new HorizontalLayout();
	    HorizontalLayout recruiterHLayout = new HorizontalLayout();

	    nickNameHLayout.addStyleName("borderBottom");
	    //		firstNameHLayout.addStyleName("borderBottom");
	    //		lastNameHLayout.addStyleName("borderBottom");
	    emailHLayout.addStyleName("borderBottom");
	    passwordHLayout.addStyleName("borderBottom");
	    //		recruiterHLayout.addStyleName("borderBottom");

	    ////// Set spacing and width
	    nickNameHLayout.setSpacing(true);
	    nickNameHLayout.setWidth("100%");
	    firstNameHLayout.setSpacing(true);
	    firstNameHLayout.setWidth("100%");
	    lastNameHLayout.setSpacing(true);
	    lastNameHLayout.setWidth("100%");
	    emailHLayout.setSpacing(true);
	    emailHLayout.setWidth("100%");
	    passwordHLayout.setSpacing(true);
	    passwordHLayout.setWidth("100%");
	    recruiterHLayout.setSpacing(true);
	    recruiterHLayout.setWidth("100%");

	    Panel publicPanel = new Panel("public");
	    fieldsAndLabelVLayout.addComponent(publicPanel);
	    Panel privatePanel = new Panel("private");
	    fieldsAndLabelVLayout.addComponent(privatePanel);

	    publicPanel.addComponent(nickNameHLayout);
	    publicPanel.addComponent(firstNameHLayout);
	    publicPanel.addComponent(lastNameHLayout);
	    privatePanel.addComponent(emailHLayout);
	    privatePanel.addComponent(passwordHLayout);
	  //TODO maxime uncomment when using web
//	    if(null == groupCandidacyParam || (null != groupCandidacyParam && !groupCandidacyParam.getGroup().isEnterpriseGroup())){
//		privatePanel.addComponent(recruiterHLayout);
//	    }
//
//	    final NickNameField nickNameField = new NickNameField("username", true);
	    final TextField firstNameField = new TextField("first name");
	    final TextField lastNameField = new TextField("last name");
	    final TextField emailField = new TextField("e-mail");
	    final PasswordField passwordField = new PasswordField("password");
	    final PasswordField verifyPasswordField = new PasswordField("verify password");
	  //TODO maxime uncomment when using web
//	    final NickNameField recruiterField = new NickNameField("recruiter", false);

	    firstNameField.setDescription("In 'John Kennedy', the first name is 'John'");
	    lastNameField.setDescription("In 'John Kennedy', the last name is 'Kennedy'");
	  //TODO maxime uncomment when using web
//	    if(null != emailParam) {
//		emailField.setValue(emailParam);	
//	    }
//	    if(null != recruiterParam) {
//		recruiterField.setValue(recruiterParam);				
//	    }

	    Label nickNameLabel = new Label("<br>Your nick name is public. For example, it's in the URL to your user page." +
		    "<br/>"+
		  //TODO maxime uncomment when using web
		    //UrlUtil.getAbsoluteUrl(new PageResource(UserPage.class))+
		    "/<b>YourNickName</b><br/>" +
		    "Do NOT use your e-mail address as username (if you don't want to be spammed).<br>" +
		    "Nickname should only contain the following characters : a-z, A-Z, 0-9, -, ., _", Label.CONTENT_XHTML);
	    nickNameLabel.addStyleName("smallText");
	    nickNameLabel.addStyleName("lightGrey");


	    Label firstNameLabel = new Label("<br>FR: Pr&eacute;nom, ES: Nombre, DE: Vorname, NL: Voornam", Label.CONTENT_XHTML);
	    firstNameLabel.addStyleName("smallText");
	    firstNameLabel.addStyleName("lightGrey");

	    Label lastNameLabel = new Label("<br>FR: Nom de famille, ES: Appellido, DE : Nachname , NL: Achternaam", Label.CONTENT_XHTML);
	    lastNameLabel.addStyleName("smallText");
	    lastNameLabel.addStyleName("lightGrey");


	    // Text description under the email TextField.
	    Label emailLabel = new Label("<br>We won't spam your mail neither resell your address! &nbsp; Hidden from the public." +
		    "<br>Your mail adress is required to activate your account.", Label.CONTENT_XHTML);
	    emailLabel.addStyleName("smallText");
	    emailLabel.addStyleName("lightGrey");

	    Label passwordLabel = new Label("");
	    passwordLabel.addStyleName("smallText");
	    passwordLabel.addStyleName("lightGrey");

	    Label recruiterLabel = new Label("<br>Optional: username of the user who made you know KnowledgeBlackBelt<br>(he will receive points).", Label.CONTENT_XHTML);
	    recruiterLabel.addStyleName("smallText");
	    recruiterLabel.addStyleName("lightGrey");
	  //TODO maxime uncomment when using web
//	    nickNameField.setRequired(true);
	    firstNameField.setRequired(true);
	    lastNameField.setRequired(true);
	    emailField.setRequired(true);
	    passwordField.setRequired(true);
	    verifyPasswordField.setRequired(true);
	  //TODO maxime uncomment when using web
//	    recruiterField.setRequired(false);
	  //TODO maxime uncomment when using web
	    // Create a email validator to check if another user with the same email already exist.
//	    final AbstractStringMessageValidator duplicateEmailValidator = new AbstractStringMessageValidator("A user with the email '{0}' already exists.") {
//		@Override
//		protected boolean isValidString(String value) {
//		    return null == userDao.getUserByEmail(value.toLowerCase());
//		}
//	    };
//	    emailField.addValidator(duplicateEmailValidator);
	    emailField.addValidator(new EmailValidator("e-mail not valid"));

	  //TODO maxime uncomment when using web
//	    nickNameHLayout.addComponent(nickNameField);
	    nickNameHLayout.addComponent(nickNameLabel);
	    firstNameHLayout.addComponent(firstNameField);
	    firstNameHLayout.addComponent(firstNameLabel);
	    lastNameHLayout.addComponent(lastNameField);
	    lastNameHLayout.addComponent(lastNameLabel);
	    emailHLayout.addComponent(emailField);
	    emailHLayout.addComponent(emailLabel);
	    passwordFieldsLayout.addComponent(passwordField);
	    passwordFieldsLayout.addComponent(verifyPasswordField);
	    passwordHLayout.addComponent(passwordFieldsLayout);
	    passwordHLayout.addComponent(passwordLabel);
	  //TODO maxime uncomment when using web
//	    recruiterHLayout.addComponent(recruiterField);
	    recruiterHLayout.addComponent(recruiterLabel);

	    nickNameHLayout.setExpandRatio(nickNameLabel, 1);
	    firstNameHLayout.setExpandRatio(firstNameLabel, 1);
	    lastNameHLayout.setExpandRatio(lastNameLabel, 1);
	    emailHLayout.setExpandRatio(emailLabel, 1);
	    recruiterHLayout.setExpandRatio(recruiterLabel, 1);

	    String registerButtonCaption = "Register!";
	  //TODO maxime uncomment when using web
//	    if(null != groupCandidacyParam) {
//		Group group = groupCandidacyParam.getGroup();
//		registerButtonCaption = "Register and join " + groupCandidacyParam.getGroup().getFullName() + (group.isEnterpriseGroup() ? " portal" : " group!"); 
//	    }

	    Button register = new Button(registerButtonCaption, new ClickListener() {
		@Override
		public void buttonClick(ClickEvent event) {
		    // We don't directly call field.isValid() on fields having multiple validators (because we could not know which validator made the validation fail => we'd not know what error message to display).
		    //				if(!duplicateNickNameValidator.isValid(nickNameField.getValue())) {
		    //					NotificationUtil.showNotification(duplicateNickNameValidator.getErrorMessageWithValue(),
		    //					"Please choose another username.");
		    //				} else
		  //TODO maxime uncomment when using web
//		    if(!nickNameField.isValid()) {
//			NotificationUtil.showNotification(nickNameField.getNiceErrorMessage(),
//				"Please choose another username for your new user.");
//
//		    } else if(!duplicateEmailValidator.isValid(emailField.getValue())) {
//			NotificationUtil.showNotification(duplicateEmailValidator.getErrorMessageWithValue(),
//				"Please choose another e-mail.");
//		    } else if(!emailField.isValid()) {
//			NotificationUtil.showNotification("Invalid e-mail",
//				"Please choose another e-mail.");
//
//		    } else if(!((String) passwordField.getValue()).equals((String) verifyPasswordField.getValue())) {
//			NotificationUtil.showNotification("Password verification failed.",
//				"Please enter a new password.");
//
//		    } else if(!recruiterField.isValid()) {
//			NotificationUtil.showNotification(recruiterField.getNiceErrorMessage(),
//				"Please choose another username for the RECRUITER.");
//		    } else {
//			try {
//			    User user = new User();
//			    user.setAccountStatus(User.AccountStatus.NOTVALIDATED);
//			    user.setCommunityRole(CommunityRole.USER);
//			    user.setCountry(countryService.getCountryByIpAddress());
//			    user.setFirstName((String) (firstNameField.getValue()));
//			    user.setNickName((String) (nickNameField.getValue()));
//			    user.setLastName((String) (lastNameField.getValue()));
//			    user.setMail((String) (emailField.getValue()));
//			    user.setPassword((String) (SecurityUtils.md5Encode((String) (passwordField.getValue()))));
//			    // By default the user will receive the newsletter 
//			    user.setNlSubscriber(true);
//			    //						user.setGender(Gender.MR);
//
//			    String recruiterNickname = (String)recruiterField.getValue();
//			    if(StringUtils.isNotBlank(recruiterNickname)){			
//				User recruiterUser = userDao.getUserByNickName(recruiterNickname);
//				user.setRecruiter(recruiterUser);
//			    }
//			    userService.registerUser(user, false);
//
//
//			    NotificationUtil
//			    .showNotification("Subscription completed, thank you for signing up  with KnowledgeBlackBelt!",
//				    "You should be soon receiving an email from KnowledgeBlackBelt which contains instructions on how to complete your registration.");
//
//			    WindowUtil.forward(HomePage.class);
//			} catch (ConstraintViolatingEntityException e) {
//			    // We should never come here, unless a rare concurrency race (because we checked the username and e-mail before).
//			    NotificationUtil.showNotification("A user with the same username or e-mail already exists",
//				    "Please choose another username or email");
//			}
		    }
	    });

	    fieldsAndLabelVLayout.addComponent(register);
	}
    }
