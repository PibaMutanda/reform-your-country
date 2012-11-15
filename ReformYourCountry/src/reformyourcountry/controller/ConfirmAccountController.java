package reformyourcountry.controller;


import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.UsersConnectionRepository;
import org.springframework.social.connect.web.ProviderSignInUtils;
import org.springframework.social.twitter.api.Twitter;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;

import reformyourcountry.exception.UserAlreadyExistsException;
import reformyourcountry.exception.UserLockedException;
import reformyourcountry.model.User;
import reformyourcountry.model.User.AccountConnectedType;
import reformyourcountry.model.User.AccountStatus;
import reformyourcountry.repository.UserRepository;
import reformyourcountry.service.LoginService;
import reformyourcountry.service.UserService;
import reformyourcountry.util.CurrentEnvironment;
import reformyourcountry.util.NotificationUtil;
import reformyourcountry.web.ContextUtil;



@Controller
public class ConfirmAccountController extends BaseController<User>{
    
    @Autowired UserRepository userRepository;
    @Autowired UserService userService;
    @Autowired LoginService loginService;
    @Autowired UsersConnectionRepository usersConnectionRepository;
    @Autowired CurrentEnvironment currentEnvironment;
   
    //when a user try to log in, for example with a facebook account, and no RYC account exists, he is invited to confirm the creation of a new account.
    @RequestMapping("/confirmaccount")  
    public ModelAndView confirmAccount(WebRequest request){

        Connection<?> connection =  ProviderSignInUtils.getConnection(request);       
        String mail = null;
        User userCreatedSuccesfully =null;
        try {
            userCreatedSuccesfully = userService.tryToAttachSocialLoginToAnExistingUser(connection);
        } catch (UserLockedException e) {
            NotificationUtil.addNotificationMessage("Votre un compte existait déjà chez nous pour vous. Nous sommes désolé, mais ce compte est locké. Vous pouvez  (<a href='contact'>contacter</a>) un administrateur pour tirer cela au clair. Merci de préciser votre nom d'utilisateur ("+e.getUser().getUserName()+")",request);
            return new ModelAndView("redirect:home");
        }
        if (userCreatedSuccesfully != null) { // Everything is done: social credentials attached to an existing user.
            ProviderSignInUtils.handlePostSignUp(userCreatedSuccesfully.getId()+"", request);
            NotificationUtil.addNotificationMessage("Félicitation vous venez de reassocier votre compte "+connection.getKey().getProviderId()+" à votre compte "+ContextUtil.servletContext.getAttribute("website_name"), request);
            ModelAndView mv = new ModelAndView("redirect:socialaccountmanage","id",userCreatedSuccesfully.getId());    
            return mv;

        } else {  // No existing user found => need to create / a new user (but we ask confirmation in the jsp before doing that).
            mail = connection.fetchUserProfile().getEmail();
            ModelAndView mv = new ModelAndView("accountcreationconfirm");
            mv.addObject("socialnetworkname", connection.getKey().getProviderId());
            if(mail != null){
                mv.addObject("email", mail);
            }
            return mv;
        }
    }

	// the user just confirmed the RYC account creation (to be bound with its social network account).
	@RequestMapping("/confirmaccountsubmit") 
	public ModelAndView confirmAccountSubmit(@RequestParam(value="email",required=false) String emailParam, WebRequest request){

	    Connection<?> connection =  ProviderSignInUtils.getConnection(request);  // retrieve it from the HttpSession

	    ///// 1. Retreive the e-mail, either from the user's form or the social network provider.
	    String email = null;
	    boolean mailIsValid;  // Has the mail of the user been verified?
	    if(emailParam == null && !(connection.getApi() instanceof Twitter)) {  // Twitter does not give the mail.
	        email = connection.fetchUserProfile().getEmail();
	        mailIsValid = true;  // mail from facebook, for example.
	    } else {
	        email = emailParam;
	        mailIsValid = false;
	    }

	    ///// 2. Do we have a valid mail?
	    if(email == null || StringUtils.isBlank(email)){
	        ModelAndView mv = new ModelAndView("redirect:confirmaccount");  // Redirect in order to go through the controller.
	        mv.addObject("mailerrormessage", "Veuillez entrer un adresse email valide.");  
	        return mv;
	    }

	    ///// 3. Get the user (create or retreive)
	    User user =null;
	    try {
	        user = userService.registerSocialUser(request, email, mailIsValid);
	        NotificationUtil.addNotificationMessage("Félicitation, vous venez de  creer un compte sur "+ currentEnvironment.getSiteName() +" associé à votre compte "+connection.getKey().getProviderId()+"."+
	                "<br>A l'avenir vous pourrez vous connecter en cliquant sur l'icone de connection "+connection.getKey().getProviderId()+".",request);
	        
	        if(!mailIsValid){//in case of a register with a twitter account,the user need to veriry his email address by clicking the link in the confirm email.
	        	NotificationUtil.addNotificationMessage("<br>Nous venons de vous envoyer un e-mail sur votre adresse: "+user.getMail()+ ". Merci de cliquer sur le lien de confirmation de votre enregistrement avec votre compte "+connection.getKey().getProviderId(),request);
	        } 
	    } catch (UserAlreadyExistsException e) {
	        // The only case to arrive here should be: the social account provider (such as Twitter) did not give the e-mail,
	        // so we just asked it to the user and we got it through emailParam.
	        // But this is the mail of an existing RYC user and for security reasons, we'll not login the visitor automatically to that user.
	        // We explain the situation and we ask the user to first login.
	        user = userRepository.getUserByEmail(email);
 
	        ModelAndView mvlogin = new ModelAndView("redirect:login");

	        NotificationUtil.addNotificationMessage("Notre site comporte déjà un utilisateur avec cette adress e-mail : " + user.getMail()+" ("+user.getFullName()+" "+ user.getUserName()+")." +
	                " Peut-être est-ce vous qui l'avez créé. Pour des raisons de sécurité, nous ne vous connectons pas automatiquement.<br>"+
	                "Vous devez d'abord vous connecter avec cet utilisateur "+  user.getUserName() + ", puis vous pourrez le lier à votre compte "+connection.getKey().getProviderId() + loginService.getRemainderLoginMessage(user),request);
	        return mvlogin;
	    }

    
	    ///// 4. Fetch additional info from facebook and others, to complete our user profile.
	    ModelAndView mv = new ModelAndView("redirect:/user/"+user.getUserName());
	    try {
	        userService.completUserFromSocialProviderData(connection, user);
	    } catch (Exception e) {  // No exception is supposed to happen during register or login. 
	        // throw new RuntimeException("Problem while registering "+mail+" account through "+ connection.getKey().getProviderId(), e);
	    	NotificationUtil.addNotificationMessage("Nous n'arrivons plus à contacter "+ connection.getKey().getProviderId() +"." +
	        		" L'ajout de vos informations utilisateur depuis "+ connection.getKey().getProviderId()+" a échoué.",request);
	    }

	    ////// 5. Automatic login
	    try {
	        if((user.getAccountStatus().equals(AccountStatus.ACTIVE))){  // Would not be the case for Twitter, for example, because we would still need to verify the e-mail.  
	            loginService.login(null,null,false,user.getId(),connection != null ? AccountConnectedType.getProviderType(connection.getKey().getProviderId()) : AccountConnectedType.LOCAL);
	        }
	    } catch (Exception e) {
	    	NotificationUtil.addNotificationMessage("Nous n'avons pas pu vous connecter automatiquement après la création (réussie) de votre compte. Pour avoir plus d'information, merci de vous connecter via la page de connexion. ", request);
	    }

	    return mv;
	}

	
	
	private enum SocialNetworkType {
	    FACEBOOK("Facebook"),
	    TWITTER("Twitter"),
	    LINKEDIN("LinkedIn"),
	    GOOGLE("Google");
	    
	    private String provider;  // Provider name as recorded by Spring Social.
	    
	    SocialNetworkType(String provider){
	        this.provider = provider;
	    }
          
	    public String getProvider(){
	        return provider;
	    }
	    
	}
	
}
