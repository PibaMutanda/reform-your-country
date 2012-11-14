package reformyourcountry.controller;

import java.util.ArrayList;
import java.util.List;

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
import reformyourcountry.service.ArgumentService;

@Controller
public class VoteManagmentController extends BaseController<Action>{
    
    @Autowired ActionRepository actionRepository;
    @Autowired UserRepository userRepository;
    @Autowired VoteActionRepository voteActionRepository;
    @Autowired ArgumentService argumentService;
    
    @RequestMapping("ajax/vote")
    public ModelAndView vote(@RequestParam("action")Long idAction, @RequestParam("vote")int vote, @RequestParam(value="idVote", required=false)Long idVote){
        assert SecurityContext.getUser()!=null;
        VoteAction va = voteActionRepository.findVoteActionForUser(SecurityContext.getUser(), idAction);
        if (va==null){
            va = new VoteAction(vote, actionRepository.find(idAction), SecurityContext.getUser());
            voteActionRepository.persist(va);
        }else{
            va.setValue(vote);
            voteActionRepository.merge(va);
        }
        
        ModelAndView mv = new ModelAndView("voteaction");
        
      //number of votes for each values, for the graph
        List<Long> resultNumbers = new ArrayList<Long>();
        for(int i=-2;i<=2;i++){
        	resultNumbers.add(voteActionRepository.getNumberOfVotesByValue(idAction, i));
        }
        mv.addObject("resultNumbers", resultNumbers);
        mv.addObject("positiveWeightedPercentage",argumentService.getWeightedPercentage(resultNumbers, true));
        mv.addObject("positiveAbsolutePercentage",argumentService.getAbsolutePercentage(resultNumbers, true));
        mv.addObject("negativeAbsolutePercentage",argumentService.getAbsolutePercentage(resultNumbers, false));
        mv.addObject("negativeWeightedPercentage",argumentService.getWeightedPercentage(resultNumbers, false));
        
        
        Action action = getRequiredEntity(idAction);
        mv.addObject("action", action);
        mv.addObject("resultVote", voteActionRepository.getTotalVoteValue(idAction));
        if (va != null){ 
            mv.addObject("vote",va);
        }
        return mv;
    }   

}
