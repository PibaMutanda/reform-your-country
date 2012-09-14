package reformyourcountry.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import reformyourcountry.model.Action;
import reformyourcountry.model.VoteAction;
import reformyourcountry.repository.VoteActionRepository;
import reformyourcountry.security.SecurityContext;

@Controller
public class ActionDisplayController extends BaseController<Action> {
    
    
    @Autowired VoteActionRepository voteActionRepository;

    @RequestMapping("action")
    public ModelAndView displayAction(@RequestParam("id") long actionId) {
        Action action = getRequiredEntity(actionId);
        ModelAndView mv = new ModelAndView("actiondisplay"); 
        mv.addObject("action", action);
        VoteAction va = voteActionRepository.findVoteActionForUser(SecurityContext.getUser(), actionId);
        if (va != null){ 
            mv.addObject("resultVote", voteActionRepository.getTotalVoteValue(actionId));
            mv.addObject("vote",va);
        }
        
        return mv;
    }

}
