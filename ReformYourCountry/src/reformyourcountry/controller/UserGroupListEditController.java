package reformyourcountry.controller;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import reformyourcountry.model.User;
import reformyourcountry.repository.GroupRegRepository;
import reformyourcountry.repository.GroupRepository;
import reformyourcountry.repository.UserRepository;
import reformyourcountry.service.GroupService;


@Controller
public class UserGroupListEditController extends BaseController<User> {


    @Autowired  GroupRegRepository groupRegRepository;
    @Autowired  GroupRepository groupRepository;
    @Autowired UserRepository userRepository;
    @Autowired GroupService groupService;

    @RequestMapping("/usergrouplistsubmit")
    public ModelAndView userGroupListEditSubmit(@RequestParam("id")long userid, 
            @RequestParam(value="groupIds", required=false)Long[] groupIds)  // required = false because there is no such param if all groups have been unchecked.
    {

        User user=getRequiredEntity(userid);
        
        if (groupIds == null) {  // All groups have been unchecked
            groupIds = new Long[0];  // Empty array, better than null variable for our algorithms.
        }
        
        ModelAndView mv=new ModelAndView("redirect:user/"+user.getUserName());
        
        groupService.changeGroupReg(user, groupIds);       
        return mv;
    }
}
