package reformyourcountry.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import reformyourcountry.model.Action;
import reformyourcountry.repository.ArgumentRepository;
import reformyourcountry.repository.VoteActionRepository;
import reformyourcountry.service.ArgumentService;

@Controller
@RequestMapping("/action")
public class ActionDisplayController extends BaseController<Action> {
    
    
    @Autowired VoteActionRepository voteActionRepository;
    @Autowired ArgumentRepository argumentRepository;
    @Autowired ArgumentService argumentService;

    @RequestMapping("/{actionUrl}")
    public ModelAndView displayAction(@PathVariable("actionUrl") String actionUrl) {
        Action action = getRequiredEntityByUrl(actionUrl);
        return  argumentService.getActionModelAndView(action,"actiondisplay");
    }

}
