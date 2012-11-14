package reformyourcountry.controller;


import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import reformyourcountry.model.Action;
import reformyourcountry.repository.ArgumentRepository;
import reformyourcountry.repository.VoteActionRepository;
import reformyourcountry.service.ActionService;
import reformyourcountry.service.ArgumentService;

@Controller
@RequestMapping("/action")
public class ActionDisplayController extends BaseController<Action> {
    
    
    @Autowired VoteActionRepository voteActionRepository;
    @Autowired ArgumentRepository argumentRepository;
    @Autowired ActionService actionService;

    @RequestMapping("/{actionUrl}")
    public ModelAndView displayAction(@PathVariable("actionUrl") String actionUrl) {
        Action action = getRequiredEntityByUrl(actionUrl);
        ModelAndView mv = actionService.getActionModelAndView(action,"actiondisplay");
        
        
        actionService.putGraphNumbersInModelAndView(mv,action);
        
        return  mv;
    }
}
