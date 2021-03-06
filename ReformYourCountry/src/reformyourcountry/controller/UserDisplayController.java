package reformyourcountry.controller;


import java.util.List;
import java.util.Random;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionRepository;
import org.springframework.social.connect.UsersConnectionRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;

import reformyourcountry.exception.InvalidUrlException;
import reformyourcountry.model.Argument;
import reformyourcountry.model.User;
import reformyourcountry.model.User.AccountConnectedType;
import reformyourcountry.repository.ArgumentRepository;
import reformyourcountry.repository.UserRepository;
import reformyourcountry.security.Privilege;
import reformyourcountry.security.SecurityContext;
import reformyourcountry.service.BadgeService;
import reformyourcountry.service.UserService;


@Controller
@RequestMapping("/user")
public class UserDisplayController extends BaseController<User> {

    @Autowired UserRepository userRepository;
    @Autowired UsersConnectionRepository usersConnectionRepository;
    @Autowired UserService userService;
    @Autowired BadgeService badgeService;
    @Autowired ArgumentRepository argumentRepository;
    
    @RequestMapping("/{userName}")
    public ModelAndView userDisplayByUrl(@PathVariable("userName") String userName, @RequestParam(value="random",required=false) Long random) {
       
        User user = userRepository.getUserByUserName(userName);
        if (user == null) {
        	throw new InvalidUrlException("L'utilisateur ayant le pseudonyme (userName) '"+userName+"' est introuvable.");
        }
        
        ModelAndView mv = new ModelAndView("userdisplay", "user", user);

        List<Argument> arguments = argumentRepository.findByUser(user);
        mv.addObject("arguments", arguments);
        
        if (random != null) {
            mv.addObject("random", random);
        }
        mv.addObject("canEdit", canEdit(user));        
        return mv;

    }

    private boolean canEdit(User user) {
        return user.equals(SecurityContext.getUser()) || SecurityContext.isUserHasPrivilege(Privilege.MANAGE_USERS);
    }



    
    @RequestMapping("/updateusersocialimage")   
    public ModelAndView updateusersocialimage(@RequestParam("provider") String provider, @RequestParam("id") long id,WebRequest request,HttpServletResponse response){
        User user = userRepository.find(id); 
        AccountConnectedType type = AccountConnectedType.getProviderType(provider);

        ConnectionRepository connectionRepository = usersConnectionRepository.createConnectionRepository(user.getId()+"");
        Connection<?> connection = connectionRepository.findPrimaryConnection(type.getProviderClass());      
        userService.addOrUpdateUserImageFromSocialProvider(user,connection);
          
        ModelAndView mv = new ModelAndView("redirect:/user/"+user.getUserName());
        Random random = new Random();
        mv.addObject("random",random.nextInt(1000));
         
        return mv;
    }
 

    @RequestMapping("/recomputebadge")
    public ModelAndView recomputeBadgeForUser(@RequestParam("userid")long userId){
    	User user= getRequiredEntity(userId);
    	badgeService.recomputeBadges(user);

    	return  new ModelAndView("redirect:/user/"+user.getUserName());	
    }

}
