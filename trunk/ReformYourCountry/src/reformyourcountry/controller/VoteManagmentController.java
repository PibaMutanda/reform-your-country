package reformyourcountry.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import reformyourcountry.model.Action;
import reformyourcountry.model.VoteAction;
import reformyourcountry.repository.ActionRepository;
import reformyourcountry.repository.UserRepository;
import reformyourcountry.repository.VoteActionRepository;
import reformyourcountry.security.Privilege;
import reformyourcountry.security.SecurityContext;

@Controller
public class VoteManagmentController extends BaseController<Action>{
    
    @Autowired ActionRepository actionRepository;
    @Autowired UserRepository userRepository;
    @Autowired VoteActionRepository voteActionRepository;
    
    @RequestMapping("ajax/vote")
    public ModelAndView vote(@RequestParam("action")Long idAction, @RequestParam("vote")int vote, @RequestParam(value="idVote", required=false)Long idVote){
        SecurityContext.assertUserHasPrivilege(Privilege.EDIT_ACTION);
        
        VoteAction va = voteActionRepository.findVoteActionForUser(SecurityContext.getUser(), idAction);
        if (va==null){
            va = new VoteAction(vote, actionRepository.find(idAction), SecurityContext.getUser(), null);
            voteActionRepository.persist(va);
        }else{
            va.setValue(vote);
            voteActionRepository.merge(va);
        }
        ModelAndView mv = new ModelAndView("voteaction");
        Action action = getRequiredEntity(idAction);
        mv.addObject("action", action);
        mv.addObject("resultVote", voteActionRepository.getTotalVoteValue(idAction));
        if (va != null){ 
            mv.addObject("vote",va);
        }
        return mv;
    }   

}