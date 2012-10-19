package reformyourcountry.service;

import java.util.Date;

import org.apache.commons.logging.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import reformyourcountry.exception.UserAlreadyExistsException;
import reformyourcountry.exception.UserAlreadyExistsException.IdentifierType;
import reformyourcountry.mail.MailCategory;
import reformyourcountry.mail.MailType;
import reformyourcountry.model.User;
import reformyourcountry.model.User.AccountStatus;
import reformyourcountry.repository.UserRepository;
import reformyourcountry.util.CurrentEnvironment;
import reformyourcountry.util.DateUtil;
import reformyourcountry.util.Logger;
import reformyourcountry.util.SecurityUtils;
import reformyourcountry.web.UrlUtil;

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
    
    @Autowired 
    private UserRepository userRepository;
    
    @Autowired 
    private MailService mailService;


    /**
     * Register a user and sends a validation mail.
     * 
     * @param directValidation
     *            : validate an account directly without send a mail
     */

    public User registerUser(boolean directValidation, String username, String passwordInClear, String mail, boolean isSocialAccount) throws UserAlreadyExistsException {
        
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
        
        //// Validation mail.
        String base = newUser.getMail() + newUser.getPassword() + Math.random();  // Could be pure random.
        newUser.setValidationCode(SecurityUtils.md5Encode(base.toString()));

        if (directValidation) {
            newUser.setAccountStatus(AccountStatus.ACTIVE);
        } /*//TODO uncomment else  if(isSocialAccount){
            newUser.setAccountStatus(AccountStatus.NOTVALIDATEDSOCIAL);
        }*/  else {
            newUser.setAccountStatus(AccountStatus.NOTVALIDATED);
        }

        //// Save the user in the db
        userRepository.persist(newUser);

        // All is ok lets eventually send a validation email
        if (!directValidation) {
            sendRegistrationValidationMail(newUser);
        }

        return newUser;
    }


    public void sendRegistrationValidationMail(User user) {

        String validationUrl = UrlUtil.getAbsoluteUrl( "validationsubmit?code=" + user.getValidationCode());
        String htmlMessage = "Bienvenue sur "+CurrentEnvironment.webSiteName+", " + user.getUserName() + "." + 
                "<br/>Il reste une dernière étape pour créer votre compte : " +
                "veuillez s'il vous plait cliquer sur le lien ci-dessous pour valider votre e-mail !" +
                "<br/><a href='"+ validationUrl + "'>" + validationUrl + "</a>" +
                "<br/><br/>Si vous rencontrez un problème, essayez de copier/coller l'URL dans votre navigateur (au lieu de cliquer sur le lien), ou en dernier recours " +
                "<a href='" + 
                //TODO add a contact page
                //UrlUtil.getAbsoluteUrl(####TO IMPLEMENT####) + 
                "'>nous contacter</a>" + 
                "<br/><br/>merci de vous être inscrit sur "+CurrentEnvironment.webSiteName+".";
        mailService.sendMail(user, "Votre nouveau compte", htmlMessage, MailType.IMMEDIATE, MailCategory.USER);
        
        log.debug("mail sent: " + htmlMessage);  
    }

    public void generateNewPasswordForUserAndSendEmail(User user) {
        // We generate a password
        String newPassword = SecurityUtils.generateRandomPassword(8, 12);
        // we set the new password to the user
        user.setPassword(SecurityUtils.md5Encode(newPassword));
        userRepository.merge(user);

        mailService.sendMail(user.getMail(), "Password Recovery",
                "You requested a new password for your account '"+ user.getUserName()+"' on KnowledgeBlackBelt.com<br/>" + 
                        "We could not give you back your old password because we do not store it directly, for security and (your own) privacy reason it is encrypted in a non reversible way. <br/><br/>" + 
                        "Here is your new password : "+ newPassword + 
                        "<ol>" +  
                        "<li>password are case sensitive,</li>" +                  
                        "<li>This is a temporary password, feel free to change it using <a href='"+/*XXX LINK to user page+*/"'>your profile page</a>.</li>" +
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


}