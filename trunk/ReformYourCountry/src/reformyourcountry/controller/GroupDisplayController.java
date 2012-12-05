package reformyourcountry.controller;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import reformyourcountry.model.Group;
import reformyourcountry.model.GroupReg;
import reformyourcountry.model.User;
import reformyourcountry.repository.GroupRegRepository;
import reformyourcountry.repository.GroupRepository;
import reformyourcountry.security.Privilege;
import reformyourcountry.security.SecurityContext;
import reformyourcountry.util.CurrentEnvironment;
import reformyourcountry.util.FileUtil;
import reformyourcountry.util.FileUtil.InvalidImageFileException;
import reformyourcountry.util.ImageUtil;
import reformyourcountry.util.NotificationUtil;

@Controller
public class GroupDisplayController extends BaseController<Group> {

    @Autowired  GroupRepository groupRepository;
    @Autowired  GroupRegRepository groupRegRepository;
    @Autowired  CurrentEnvironment currentEnvironment;
    

	@RequestMapping("/group")
    public ModelAndView groupDisplay(@RequestParam("id") long id) {
        Group group = getRequiredEntity(id);
        List<GroupReg>groupRegList = groupRegRepository.findAllByGroup(group);
        
        List<User> userByGroupList = new ArrayList<User>();
        for(GroupReg greg:groupRegList){
           	if(!userByGroupList.contains(greg.getUser())){
        	   	userByGroupList.add(greg.getUser());
        	}
             	
        }
        
        ModelAndView mv = new ModelAndView("groupdisplay");
        mv.addObject("userbygrouplist", userByGroupList);
        mv.addObject("groupRegList", groupRegList);
        mv.addObject("group", group);   
        mv.addObject("random", System.currentTimeMillis());
        return mv;
    }
    
    
}