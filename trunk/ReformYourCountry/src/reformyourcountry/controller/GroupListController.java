package reformyourcountry.controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import reformyourcountry.model.Group;
import reformyourcountry.repository.GroupRepository;

@Controller
public class GroupListController extends BaseController<Group>{

    @Autowired GroupRepository groupRepository;

    @RequestMapping("/grouplist")
    public ModelAndView showGroupList(){
        List<Group> group = groupRepository.findAll();
        return new ModelAndView("grouplist", "groupList", group);

    }

}
