package reformyourcountry.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionRepository;
import org.springframework.social.connect.UsersConnectionRepository;
import org.springframework.social.connect.web.ProviderSignInUtils;
import org.springframework.social.facebook.api.Facebook;
import org.springframework.social.twitter.api.Twitter;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;

import reformyourcountry.model.User;

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
    public ModelAndView userDisplayByUrl(@PathVariable("userName") String userName,WebRequest request) {
        Connection<?> connection =  ProviderSignInUtils.getConnection(request); 
        User user = userRepository.getUserByUserName(userName);

        ModelAndView mv = new ModelAndView("userdisplay", "user", user);

        if(connection != null){
            switch(connection.getKey().getProviderId()){

            case "facebook" : 
                if(checkValidConnection(user,Facebook.class) != null)
                    mv.addObject("accountType","Facebook");
                break;
            case "twitter" :  
                if(checkValidConnection(user,Twitter.class) != null)
                    mv.addObject("accountType","Twitter");
                break;
                //TODO LINKEDIN , GOOGLE
            default :        mv.addObject("accountType","Local");
            break;


            }
        }
        else
        {
            mv.addObject("accountType","Local");

        }


        mv.addObject("canEdit", canEdit(user));
        return mv;

    }

    private boolean canEdit(User user) {
        return user.equals(SecurityContext.getUser()) || SecurityContext.isUserHasPrivilege(Privilege.MANAGE_USERS);
    }



    private Connection<?> checkValidConnection(User user,Class<?> provider){

        ConnectionRepository connectionRepository = usersConnectionRepository.createConnectionRepository(user.getId()+"");
        Connection<?> connection = connectionRepository.findPrimaryConnection(provider);

        if(connection != null) return connection;
        else
            return null;

    }

}
