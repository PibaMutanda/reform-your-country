package reformyourcountry.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import reformyourcountry.model.Action;
import reformyourcountry.model.VoteAction;
import reformyourcountry.repository.VoteActionRepository;
import reformyourcountry.security.SecurityContext;

@Controller
@RequestMapping("/action")
public class ActionDisplayController extends BaseController<Action> {
    
    
    @Autowired VoteActionRepository voteActionRepository;

    @RequestMapping("/{actionUrl}")
    public ModelAndView displayAction(@PathVariable("actionUrl") String actionUrl) {
        Action action = getRequiredEntityByUrl(actionUrl);
        ModelAndView mv = new ModelAndView("actiondisplay"); 
        mv.addObject("action", action);
        VoteAction va = voteActionRepository.findVoteActionForUser(SecurityContext.getUser(), action.getId());
        if (va != null){ 
            mv.addObject("resultVote", voteActionRepository.getTotalVoteValue(action.getId()));
            mv.addObject("vote",va);
        }
        
        return mv;
    }

}
