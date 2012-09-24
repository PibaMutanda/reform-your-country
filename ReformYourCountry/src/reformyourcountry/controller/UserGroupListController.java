package reformyourcountry.controller;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import reformyourcountry.model.Group;
import reformyourcountry.model.GroupReg;
import reformyourcountry.model.User;
import reformyourcountry.repository.GroupRepository;
import reformyourcountry.repository.UserRepository;

@Controller
public class UserGroupListController extends BaseController<GroupReg> {
	
	@Autowired GroupRepository groupRepository;
	@Autowired UserRepository userRepository;
	
	@RequestMapping("/manageGroup")
	public ModelAndView userGroupList(@RequestParam("id")long id){
		
		ModelAndView mv= new ModelAndView("usergrouplist");

        List<Group> allGroups = groupRepository.findAll();
        mv.addObject("allGroups", allGroups);

        User user=userRepository.find(id);
//        List<boolean> isInGroupList = new ArrayList<boolean>();
//        for (Group group : allGroups) {
//            if (user.getGroupRegs().)
//        }
		Set<Group> myGroups = new HashSet<Group>();
		for (GroupReg groupReg : user.getGroupRegs()) {
		    if (allGroups.contains(groupReg.getGroup())) {
		        myGroups.add(groupReg.getGroup());
		    }
		}
		mv.addObject("myGroups", myGroups);          
		  
		return mv;
	}
	
}
