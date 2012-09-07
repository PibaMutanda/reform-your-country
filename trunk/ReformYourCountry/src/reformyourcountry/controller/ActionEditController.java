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

@Controller
public class ActionEditController extends BaseController<Action>{
    
    @Autowired ActionRepository actionRepository;
  
    @RequestMapping("/actionedit")
    public ModelAndView actionEdit(@RequestParam (value="id", required= true) long actionId){
        Action action = getRequiredEntity(actionId); 
        
        ModelAndView mv= new ModelAndView("actionedit");
        mv.addObject("id",actionId);
        mv.addObject("action",action);
        return mv;
    }
   
    @RequestMapping("/actioneditsubmit")
    public ModelAndView userEditSubmit(@RequestParam("title") String newTitle,
            @RequestParam("content") String content,
            @RequestParam("url") String url,
            @RequestParam("shortDescription") String shortDescription,
            @RequestParam("longDescription") String longDescription,
            @RequestParam("id") long id,
            @Valid @ModelAttribute Action doNotUseThisActionInstance,  // To enable the use of errors param.
            Errors errors) {
      
        Action  action  = getRequiredEntity(id);
        
        boolean hasActionAlreadyExist=false;
      
        if (! org.apache.commons.lang3.StringUtils.equalsIgnoreCase(action.getTitle(), newTitle)) {  // Want to change Title
            // check duplicate
            Action otherAction = actionRepository.getActionByTitle(newTitle);
            if (otherAction != null) { // Another user already uses that title
                errors.rejectValue("action", null, "Ce titre d'action est déjà utilisé par un autre utilisateur.");
                hasActionAlreadyExist=true;
            }
            
        }
        if (errors.hasErrors()) {
            ModelAndView mv = new ModelAndView("actionedit");
            if (doNotUseThisActionInstance.getTitle()==""|| hasActionAlreadyExist==true ) {
                doNotUseThisActionInstance.setTitle(action.getTitle()); 
            }
            mv.addObject("action", doNotUseThisActionInstance);  // Get out of here before we change the user entity (Hibernate could save because of dirty checking).
            mv.addObject("id", id); // need to pass 'id' apart because 'doNotUseThisUserInstance.id' is set to null
            return mv;
        }
        action.setTitle(newTitle);
        action.setContent(content);
        action.setUrl(url);
        action.setLongDescription(longDescription);
        action.setShortDescription(shortDescription);
        
        action = actionRepository.merge(action);
    
        return new ModelAndView("redirect:action", "id", action.getId());
    }
        
        
}
    
