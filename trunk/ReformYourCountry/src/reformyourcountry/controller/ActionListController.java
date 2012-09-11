package reformyourcountry.controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;


import reformyourcountry.model.Action;
import reformyourcountry.repository.ActionRepository;

@Controller
public class ActionListController {
    
    @Autowired ActionRepository actionRepository;
  
    @RequestMapping("/actionlist")
    public ModelAndView actionListDisplay(){
        
        List<Action> actionList = actionRepository.findAll();
        return new ModelAndView("actionlistdisplay","actions",actionList);
    }
}
