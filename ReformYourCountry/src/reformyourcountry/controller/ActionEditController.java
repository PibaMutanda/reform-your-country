package reformyourcountry.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import reformyourcountry.model.Action;
import reformyourcountry.repository.ActionRepository;
import reformyourcountry.security.Privilege;
import reformyourcountry.security.SecurityContext;
import reformyourcountry.service.ActionService;

@Controller
@RequestMapping("/action")
public class ActionEditController extends BaseController<Action>{
    
    @Autowired ActionRepository actionRepository;
    @Autowired ActionService actionService;
    
    @RequestMapping("/create")
    public ModelAndView actionCreate(){
        SecurityContext.assertUserHasPrivilege(Privilege.EDIT_ACTION);
        return new ModelAndView("actionedit","action", new Action());
    }
    
    
    @RequestMapping("/edit")
    public ModelAndView actionEdit(@RequestParam("id") long actionId){
        Action action = getRequiredEntity(actionId); 
        
        return new ModelAndView("actionedit","action",action);
    }
    @RequestMapping("/delete")
    public ModelAndView actionDelete(@RequestParam("id") long actionId){
        Action action = getRequiredEntity(actionId); 
        SecurityContext.assertUserHasPrivilege(Privilege.EDIT_ACTION);
        return getConfirmBeforeDeletePage(action.getTitle(), "/action/deleteconfirmed", "/action/"+action.getUrl(), actionId);
    }
    @RequestMapping("/deleteconfirmed")
    public ModelAndView actionDeleteConfirm(@RequestParam("id") long actionId){
        Action action = getRequiredEntity(actionId); 
        SecurityContext.assertUserHasPrivilege(Privilege.EDIT_ACTION);
        actionService.delete(action);
        return new ModelAndView("/action","message","L'action a bien été supprimée");
    }
   
    @RequestMapping("/editsubmit")
    public ModelAndView actionEditSubmit(@Valid @ModelAttribute Action action, Errors errors) {
      
        SecurityContext.assertUserHasPrivilege(Privilege.EDIT_ACTION);

        if (errors.hasErrors()) {
            return new ModelAndView("actionedit","action", action);
        }
       
        if (action.getId() == null){ //New action instance (not from DB)
            actionRepository.persist(action);
        } else { // Edited action instance.
            actionRepository.merge(action);
        }
            
        return new ModelAndView("redirect:/action/"+action.getUrl());
    }
        
    @ModelAttribute
    public Action findAction(@RequestParam("id") Long id){
        if (id == null){ //create
            return new Action();
        } else { //edit
            return getRequiredDetachedEntity(id);
        }
    }
        
        
}
    
