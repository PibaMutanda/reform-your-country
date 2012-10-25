package reformyourcountry.controller;


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

import reformyourcountry.model.User;
import reformyourcountry.model.User.AccountConnectedType;
import reformyourcountry.repository.UserRepository;
import reformyourcountry.security.Privilege;
import reformyourcountry.security.SecurityContext;
import reformyourcountry.service.UserService;


@Controller
@RequestMapping("/user")
public class UserDisplayController extends BaseController<User> {

    @Autowired UserRepository userRepository;
    @Autowired UsersConnectionRepository usersConnectionRepository;
    @Autowired UserService userService;

    @RequestMapping("/{userName}")
    public ModelAndView userDisplayByUrl(@PathVariable("userName") String userName, @RequestParam(value="random",required=false) Integer random) {
       
        User user = userRepository.getUserByUserName(userName);
        ModelAndView mv = new ModelAndView("userdisplay", "user", user);
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
 

 

}
