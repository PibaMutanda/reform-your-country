package reformyourcountry.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import reformyourcountry.model.User;
import reformyourcountry.model.User.SpecialType;
import reformyourcountry.repository.UserRepository;

@Controller
public class SpecialUserListController extends BaseController<User> {
    
    @Autowired UserRepository userRepository;
    
    @RequestMapping("/specialuserslist")
    public ModelAndView specialUserList(){

        ///// we populate a hashmap with one list by association and this list is populate with users of this association
        // It makes this page generic and usable if we add a value in User.SpecialType.
        HashMap<String,List<User>> userMapGroupByType = new HashMap<String,List<User>>();
        for (SpecialType st : User.SpecialType.values()) {
            userMapGroupByType.put(st.getName(),  new ArrayList<User>());
        }
        for (User user : userRepository.getUsersWithSpecialType()) {
            userMapGroupByType.get(user.getSpecialType().getName()).add(user);
        }
       
        ModelAndView mv =  new ModelAndView("specialuserslist","userMapGroupByType",userMapGroupByType);
        mv.addObject("values", User.SpecialType.getValuesExceptPrivate());
        return mv;
        
    }
    

}
