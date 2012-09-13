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
import reformyourcountry.model.Book;
import reformyourcountry.repository.ActionRepository;
import reformyourcountry.security.Privilege;
import reformyourcountry.security.SecurityContext;

@Controller
public class ActionEditController extends BaseController<Action>{
    
    @Autowired ActionRepository actionRepository;
  
    
    @RequestMapping("/actioncreate")
    public ModelAndView actionCreate(){
        SecurityContext.assertUserHasPrivilege(Privilege.EDIT_ACTION);
        return new ModelAndView("actionedit","action", new Action());
    }
    
    
    @RequestMapping("/actionedit")
    public ModelAndView actionEdit(@RequestParam("id") long actionId){
        Action action = getRequiredEntity(actionId); 
        return new ModelAndView("actionedit","action",action);
    }
   
    @RequestMapping("/actioneditsubmit")
    public ModelAndView userEditSubmit(@Valid @ModelAttribute Action action, Errors errors) {
      
        SecurityContext.assertUserHasPrivilege(Privilege.EDIT_ACTION);

        if (errors.hasErrors()) {
            return new ModelAndView("actionedit","action", action);
        }
       
        if (action.getId() == null){ //New action instance (not from DB)
            actionRepository.persist(action);
        } else { // Edited action instance.
            actionRepository.merge(action);
        }
            
        return new ModelAndView("redirect:action", "id", action.getId());
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
    
