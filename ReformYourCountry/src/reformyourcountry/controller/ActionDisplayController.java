package reformyourcountry.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import reformyourcountry.model.Action;
import reformyourcountry.model.VoteAction;
import reformyourcountry.repository.ActionRepository;
import reformyourcountry.repository.ArgumentRepository;
import reformyourcountry.repository.UserRepository;
import reformyourcountry.repository.VoteActionRepository;
import reformyourcountry.security.SecurityContext;
import reformyourcountry.service.ActionService;
import reformyourcountry.service.BadgeService;

@Controller
public class ActionDisplayController extends BaseController<Action> {
    
    
    @Autowired VoteActionRepository voteActionRepository;
    @Autowired ArgumentRepository argumentRepository;
    @Autowired ActionService actionService;
    @Autowired ActionRepository actionRepository;
    @Autowired UserRepository userRepository;
    @Autowired BadgeService badgeService;
    
    @RequestMapping("/action/{actionUrl}")
    public ModelAndView displayAction(@PathVariable("actionUrl") String actionUrl) {
        Action action = getRequiredEntityByUrl(actionUrl);
        ModelAndView mv = actionService.getActionModelAndView(action,"actiondisplay");
        
      
        actionService.putGraphNumbersInModelAndView(mv,action);
        
        return  mv;
    }
    @RequestMapping("ajax/vote")
    public ModelAndView vote(@RequestParam("actionId") long actionId, @RequestParam("vote") int vote){
        SecurityContext.assertUserIsLoggedIn();  
        VoteAction va = voteActionRepository.findVoteActionForUser(SecurityContext.getUser(), actionId);
        if (va==null){
            va = new VoteAction(vote, actionRepository.find(actionId), SecurityContext.getUser());
            voteActionRepository.persist(va);
        }else{
            va.setValue(vote);
            voteActionRepository.merge(va);
        }
        
        badgeService.grantBadgeForVoteAction(SecurityContext.getUser());
        ModelAndView mv = new ModelAndView("voteaction");
        
        Action action = getRequiredEntity(actionId);
        
        actionService.putGraphNumbersInModelAndView(mv,action);
        
        mv.addObject("action", action);
        mv.addObject("resultVote", voteActionRepository.getTotalVoteValue(actionId));
        if (va != null){ 
            mv.addObject("vote",va);
        }
        return mv;
    }   

    
}
