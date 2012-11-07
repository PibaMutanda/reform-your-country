package reformyourcountry.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import reformyourcountry.model.Action;
import reformyourcountry.model.Argument;
import reformyourcountry.model.VoteAction;
import reformyourcountry.repository.ArgumentRepository;
import reformyourcountry.repository.VoteActionRepository;
import reformyourcountry.security.SecurityContext;

@Controller
@RequestMapping("/action")
public class ActionDisplayController extends BaseController<Action> {
    
    
    @Autowired VoteActionRepository voteActionRepository;
    @Autowired ArgumentRepository argumentRepository;

    @RequestMapping("/{actionUrl}")
    public ModelAndView displayAction(@PathVariable("actionUrl") String actionUrl) {
        Action action = getRequiredEntityByUrl(actionUrl);
        List<Argument> listArgs = argumentRepository.findAll(action.getId());
        List<Argument> listPosArgs = new ArrayList<Argument>();
        List<Argument> listNegArgs = new ArrayList<Argument>();
        for(Argument arg :listArgs){
            if (arg.isPositiveArg()){
                listPosArgs.add(arg);
            }else
            {
                listNegArgs.add(arg);
            }
        }
        ModelAndView mv = new ModelAndView("actiondisplay"); 
        mv.addObject("action", action);
        VoteAction va = voteActionRepository.findVoteActionForUser(SecurityContext.getUser(), action.getId());
        if (va != null){ 
            mv.addObject("resultVote", voteActionRepository.getTotalVoteValue(action.getId()));
            mv.addObject("vote",va);
            
        }
        mv.addObject("listPosArgs",listPosArgs);
        mv.addObject("listNegArgs",listNegArgs);
        
        return mv;
    }

}
