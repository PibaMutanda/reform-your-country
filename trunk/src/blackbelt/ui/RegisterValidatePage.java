package blackbelt.ui;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.vaadin.navigator7.Navigator.NavigationEvent;
import org.vaadin.navigator7.Page;
import org.vaadin.navigator7.PageResource;
import org.vaadin.navigator7.ParamChangeListener;
import org.vaadin.navigator7.uri.Param;
import org.vaadin.navigator7.uri.ParamPageResource;

import be.loop.jbb.bl.exceptions.InvalidPasswordException;
import be.loop.jbb.bl.exceptions.UserLockedException;
import be.loop.jbb.bl.exceptions.UserNotFoundException;
import be.loop.jbb.bl.exceptions.UserNotValidatedException;
import be.loop.jbb.util.DateUtil;
import blackbelt.dao.UserDao;
import blackbelt.model.User;
import blackbelt.model.User.AccountStatus;
import blackbelt.service.LoginService;
import blackbelt.service.LoginService.WaitDelayNotReachedException;
import blackbelt.ui.common.ComponentFactory;
import blackbelt.ui.common.NotificationUtil;
import blackbelt.ui.common.PictureResource;
import blackbelt.ui.common.PictureResource.PicturePath;
import blackbelt.ui.common.WindowUtil;
import blackbelt.ui.document.DocumentPage;
import blackbelt.ui.user.UserPage;
import blackbelt.web.UrlUtil;

import com.vaadin.ui.Embedded;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

@Page
@Configurable
public class RegisterValidatePage extends HorizontalLayout implements ParamChangeListener {

	// Getting the user param
	@Param(pos = 0, required = true)     User user;
	@Param(pos = 1, required = true)     String validation;

	@Autowired  private LoginService loginService;
	
	@Autowired private UserDao userDao; // injected by Spring

	private Label registrationValidationLabel = new Label("", Label.CONTENT_XHTML);

	public RegisterValidatePage() {}

	@Override
	public void paramChanged(NavigationEvent navigationEvent) {

		removeAllComponents();

		this.setMargin(true);
		this.setSpacing(true);

		VerticalLayout textVerticalLayout = new VerticalLayout();
		textVerticalLayout.setMargin(true);
		textVerticalLayout.setSpacing(true);
		
		////// Decorative image on the left.
		PictureResource resource = new PictureResource(PicturePath.IMAGE, "Blackbelt.jpg");
		Embedded image = new Embedded("", resource);
		addComponent(image);
		
		textVerticalLayout.addComponent(ComponentFactory.createPageTitle("Registration validation"));
		textVerticalLayout.addComponent(registrationValidationLabel);
		addComponent(textVerticalLayout);
		this.setExpandRatio(textVerticalLayout, 1);
		
		if(!user.getValidationCode().equals(validation)){
			registrationValidationLabel.setValue("<strong>An error occured!</strong><br><br>" +
					"<p>We could not validate your account, because your validation code is invalid.<br/>" +
					"Are you sure you clicked the link from the e-mail to come here?</p>" +
					"<p>If you have any problem validating your account, please <a href='"+ 
					UrlUtil.getAbsoluteUrl(new PageResource(DocumentPage.class, "ContactUs")) +"'>contact us</a>");
		}
		// That user is already validated
		else if((user.getAccountStatus() == AccountStatus.ACTIVE) || (user.getAccountStatus() == AccountStatus.LOCKED)){
			registrationValidationLabel.setValue("<strong>This account has already been validated!</strong>");
		}
		// that the user is now validated
		else{		
			registrationValidationLabel.setValue("Thank your for validating!<br/>" +
					"<p>Your account has been validated and is now ready to be used. " +
					"Right now, you're logged on KnowledgeBlackBelt and you can: " +
					"<ul>"+
					"<li>access to different communities content like Java, .Net, DB...</li>" + 
					"<li>see your user page by clicking on your fullname (top right of the page)</li>" + 
					"<li>improve courses and exams to obtain contribution points</li>" + 
					"<li>test your skills in exams and get belts (contribution points required)</li>" + 
					"</ul></p>" + 
					"All communities first exams are free.<br/><p>Your next step is to pass a free exam and get your community Yellow Belt!</p>");

			user.setAccountStatus(AccountStatus.ACTIVE);
			userDao.save(user);
			
			try {
				loginService.loginEncrypted(user.getMail(), user.getPassword(), true);
				
				WindowUtil.refreshHeaders();
			} catch (UserNotFoundException e) {
				NotificationUtil.showNotification("That user has not been found", user.getFullName());
			} catch (InvalidPasswordException e) {
				NotificationUtil.showNotification("The password is invalid for that user", "");
			} catch (UserNotValidatedException e) {
				NotificationUtil.showNotification("This user has not yet been validated.", "Please check your mails.");
			} catch (UserLockedException e) {
				NotificationUtil.showNotification("This user has been locked", "");
			} catch (WaitDelayNotReachedException e) {
				NotificationUtil.showNotification("This user has to wait before trying to login, because of too many invalid attempts (wrong password)." +
	            		" Next try will be possilbe  " + DateUtil.formatIntervalFromToNow(e.getNextPossibleTry()),
	            		"The more you'll fail, the more you'll have to wait before the next possible attempt. If you think that somebody else tried to hack your account, please contact us.");
			}
		}
	}	
}
