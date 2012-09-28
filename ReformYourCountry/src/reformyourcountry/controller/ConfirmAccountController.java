package reformyourcountry.controller;

import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionFactoryLocator;
import org.springframework.social.connect.ConnectionRepository;
import org.springframework.social.connect.UsersConnectionRepository;
import org.springframework.social.connect.web.ProviderSignInUtils;
import org.springframework.social.facebook.api.Facebook;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;

import reformyourcountry.model.User;
import reformyourcountry.model.User.AccountStatus;
import reformyourcountry.repository.UserRepository;
import reformyourcountry.service.LoginService;
import reformyourcountry.service.UserService;

@Controller
public class ConfirmAccountController extends BaseController<User>{
    
    
    @Autowired UsersConnectionRepository usersConnectionRepository;
    @Autowired ConnectionRepository connectionRepository;
    @Autowired ConnectionFactoryLocator connectionFactoryLocator;
    
    @Autowired UserRepository userRepository;
    @Autowired UserService userService;
    @Autowired LoginService loginService;

    
	//when a user try to log in, for example with a facebook account, he is invited to confirm the creation of a new account on enseignement2
	@RequestMapping("/confirmaccount")  
	public ModelAndView confirmAccount(WebRequest request){
	    
	    
	    Connection<?> connection =  ProviderSignInUtils.getConnection(request);
	  
	    String mail = connection.fetchUserProfile().getEmail();
	    ModelAndView mv = new ModelAndView("confirmaccount");
        mv.addObject("email", mail);
        return mv;
	}

	@RequestMapping("/confirmaccountsubmit")//signup
	public String confirmAccountSubmit(WebRequest request){
	    
	    Long maxIdValue = userRepository.findMaxIdValue();
	    
	    Connection<?> connection =  ProviderSignInUtils.getConnection(request);
	      
        String mail = connection.fetchUserProfile().getEmail();
	    
	   Random random = new Random();
	   
	    try {
	        
            User user = userService.registerUser(true, "user"+(maxIdValue+1),+random.nextLong()+"", mail);
            
            //When the user is created in local , add a new Connection to userconnection table
            ProviderSignInUtils.handlePostSignUp(user.getId()+"", request);   
            
            user.setLastName(connection.fetchUserProfile().getLastName());
            user.setFirstName(connection.fetchUserProfile().getFirstName());
            
            if(connection.getApi() instanceof Facebook){
                Facebook facebook = (Facebook)connection.getApi();
                if(facebook.userOperations().getUserProfile().getBirthday() != null)
               System.out.println(facebook.userOperations().getUserProfile().getBirthday());
              //  user.setBirthDate(facebook.userOperations().getUserProfile().getBirthday());
                
            }
            user.setAccountStatus(AccountStatus.ACTIVE_SOCIAL);
            em.merge(user);
           
            //login in local
            loginService.login(user.getId());
            
        } catch (Exception e) {
            
         throw new RuntimeException(e);
        }
	    
	   
	    
	    return "home";
	}
	
	
	
}
