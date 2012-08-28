package reformyourcountry.controller;


import javax.validation.Valid;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;


import reformyourcountry.model.User;
import reformyourcountry.repository.UserRepository;

@Controller
public class UserEditController extends BaseController<User> {
    
    @Autowired UserRepository userRepository;

    @RequestMapping("/useredit")
    public ModelAndView userEdit(@RequestParam(value="id") long userId) {
        
        User user = getRequiredEntity(userId); 
        
        ModelAndView mv=new ModelAndView("useredit");
        mv.addObject("user", user);
        return mv;
    }
    
    
    @RequestMapping("/usereditsubmit")
    public ModelAndView userEditSubmit(@Valid  @ModelAttribute User user, BindingResult result){
        if(result.hasErrors()){
           return new ModelAndView("useredit");
        } else {
            ModelAndView mv=new ModelAndView("redirect:user", "id", user.getId());
            User userM=userRepository.merge(user);
            mv.addObject("user", userM);
            return mv;
        }     

    }
    
    
    
    @ModelAttribute
    public User findUser(@RequestParam("id") Long id){
        return  userRepository.find(id);
    }
}
