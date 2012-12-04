package reformyourcountry.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;


import reformyourcountry.model.Book;
import reformyourcountry.model.Group;

import reformyourcountry.repository.GroupRepository;
import reformyourcountry.security.Privilege;
import reformyourcountry.security.SecurityContext;
import reformyourcountry.util.NotificationUtil;

@Controller
public class GroupEditController extends BaseController<Group> {

    @Autowired
    GroupRepository groupRepository;

    @RequestMapping("/groupcreate")
    public ModelAndView groupCreate(){
        SecurityContext.assertUserHasPrivilege(Privilege.MANAGE_GROUP);
        return prepareModelAndView(new Group());
    }

    @RequestMapping("/groupedit")
    public ModelAndView groupEdit(@RequestParam("id") long id){
        SecurityContext.assertUserHasPrivilege(Privilege.MANAGE_GROUP);
        Group group = getRequiredEntity(id);
        return prepareModelAndView(group);
    }   

    private ModelAndView prepareModelAndView(Group group) {
        ModelAndView mv = new ModelAndView("groupedit");
        return mv.addObject("group", group); 
    }
    
    
    @RequestMapping("/groupeditsubmit")
    public ModelAndView groupEditSubmit(@Valid @ModelAttribute Group group, Errors errors) {
      
        SecurityContext.assertUserHasPrivilege(Privilege.MANAGE_GROUP);
        
        if (errors.hasErrors()) {
            return new ModelAndView("groupedit", "group", group);
        }
       
        Group groupHavingThatName = (Group) groupRepository.findByName(group.getName());
        
        if (group.getId() == null){ //New group instance (not from DB)
            if(groupHavingThatName != null) {
                ModelAndView mv = new ModelAndView ("groupedit", "group", group);
                NotificationUtil.addNotificationMessage("Un autre groupe utilise déjà ce nom '" + group.getName() + '"');
                return mv;
            }
            groupRepository.persist(group);
            
        } else { // Edited group instance.
            if(groupHavingThatName != null && !group.equals(groupHavingThatName)) {
                ModelAndView mv = new ModelAndView ("groupedit", "group", group);
                NotificationUtil.addNotificationMessage("Un autre groupe utilise déjà ce nom'" + group.getName() + '"');
                return mv;
            }
            groupRepository.merge(group);
        }
            
        return new ModelAndView("redirect:group", "id", group.getId());
    }
    
    @RequestMapping ("/groupremove")
    public ModelAndView groupRemove(@RequestParam("id")Long id){
        groupRepository.remove(groupRepository.find(id));
    
        return new ModelAndView ("redirect:grouplist");
    }
        
    @ModelAttribute
    public Group findGroup(@RequestParam("id") Long id){
        if (id == null){ //create
            return new Group();
        } else { //edit
            return getRequiredDetachedEntity(id);
        }
    }
 }
