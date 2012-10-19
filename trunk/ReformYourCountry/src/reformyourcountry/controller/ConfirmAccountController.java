package reformyourcountry.controller;

import java.util.Date;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.UsersConnectionRepository;
import org.springframework.social.connect.web.ProviderSignInUtils;
import org.springframework.social.facebook.api.Facebook;
import org.springframework.social.twitter.api.Twitter;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;

import reformyourcountry.model.User;
import reformyourcountry.model.User.AccountStatus;
import reformyourcountry.repository.UserRepository;
import reformyourcountry.service.LoginService;
import reformyourcountry.service.UserService;

@Controller
public class ConfirmAccountController extends BaseController<User>{
    
    @Autowired UserRepository userRepository;
    @Autowired UserService userService;
    @Autowired LoginService loginService;
    @Autowired UsersConnectionRepository usersConnectionRepository;
    
	//when a user try to log in, for example with a facebook account, and no RYC account exists, he is invited to confirm the creation of a new account .
	@RequestMapping("/confirmaccount")  
	public ModelAndView confirmAccount(WebRequest request){
	    Connection<?> connection =  ProviderSignInUtils.getConnection(request);
	    
	    ModelAndView mv = new ModelAndView("confirmaccount");
        mv.addObject("socialnetworkname", connection.getKey().getProviderId());
        
	    if (connection.getApi() instanceof Twitter) {

	    } else if(connection.getApi() instanceof Facebook){
            String mail = connection.fetchUserProfile().getEmail();
	        mv.addObject("email", mail);
	    }
	    return mv;
	}

	// the user just confirmed the RYC account creation (to be bound with its social network account).
	@RequestMapping("/confirmaccountsubmit") 
	public ModelAndView confirmAccountSubmit(@RequestParam(value="email",required=false) String email, WebRequest request){

	    Connection<?> connection =  ProviderSignInUtils.getConnection(request);  // retreive it from the HttpSession
	    
	    String mail = null;
	    boolean mailIsValid = false;  // Has the mail of the user been verified?
	    if(email == null && !(connection.getApi() instanceof Twitter)) {  // Twitter does not give the mail.
	        mail = connection.fetchUserProfile().getEmail();
	        mailIsValid = true;  // mail from facebook, for example.
	    } else {
	        mail = email;
	    }
	    
	    if(mail == null || (mail != null && mail.isEmpty())){
	        ModelAndView mv = new ModelAndView("redirect:confirmaccount");  // Redirect in ordre to go through the controller.
	        mv.addObject("message","Vueillez entrer un adresse email valide.");
	        return mv;
	    }
	    
        User user = null;
	    try {
            Date date = new Date();
            
            // 1. Username based on Time stamp (temporary name until we persist it and have it's id)
            String username = date.getTime()+"";
            int begin = username.length()-12;
            username= "tmp"+username.substring(begin);
            Random random = new Random();

            user = userService.registerUser(true, 
                    username,  // This name will change at the next line, as soon as we have the id. 
                    random.nextLong()+"",   // Nobody should never use this password (because the user logs in through it's social network). 
                    mail, true);
            
            if (mailIsValid) {
                user.setAccountStatus(AccountStatus.ACTIVE_SOCIAL);
            }
                
	        // 2. Now we have the ID and can asign a better username.
	        user.setUserName("user"+user.getId());
	        em.merge(user);
	        
	        //When the user is created in local , add a new Connection to userconnection table
	        ProviderSignInUtils.handlePostSignUp(user.getId()+"", request);   
	       
	    } catch(Exception e) {
            throw new RuntimeException("Problem while registering "+mail+" account through "+ connection.getKey().getProviderId(), e);
        }

	    ///// We get additional info from facebook and others, to complete our user profile.
	    ModelAndView mv = new ModelAndView("confirmsignup");
        SocialNetworkType socialNetworkType = null;
	    try {
	        user.setLastName(connection.fetchUserProfile().getLastName());
            user.setFirstName(connection.fetchUserProfile().getFirstName());

	        ///// Saves additional social network specific information.
	        if(connection.getApi() instanceof Facebook){
	            socialNetworkType = SocialNetworkType.FACEBOOK;
	            Facebook facebook = (Facebook)connection.getApi();
	    
	            if(facebook.userOperations().getUserProfile().getBirthday() != null) {
	                System.out.println(facebook.userOperations().getUserProfile().getBirthday());
	                // user.setBirthDate(facebook.userOperations().getUserProfile().getBirthday());   TODO
	            }
	            
	        } else  if(connection.getApi() instanceof Twitter) {
                socialNetworkType = SocialNetworkType.TWITTER;
                mv.addObject("email", email);  // for display
            }
	        em.merge(user);

	        //login in local if not twitter because we need to verify email
	        if((user.getAccountStatus().equals(AccountStatus.ACTIVE_SOCIAL))){  
	           loginService.login(null,null,false,user.getId());
	        }

	    } catch (Exception e) {  // No exception is supposed to happen during register or login. 
	        // throw new RuntimeException("Problem while registering "+mail+" account through "+ connection.getKey().getProviderId(), e);
	        this.setMessage(mv, "Nous n'arrivons plus à contacter "+ socialNetworkType.getProvider() +". La création de votre utilisateur a échoué. Veuillez réessayer plus tard.");
	    }

        mv.addObject("accounttype", socialNetworkType.getProvider());
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
