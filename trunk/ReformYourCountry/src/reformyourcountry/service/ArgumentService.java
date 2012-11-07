package reformyourcountry.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.ModelAndView;

import reformyourcountry.model.Action;
import reformyourcountry.model.Argument;
import reformyourcountry.model.User;
import reformyourcountry.model.VoteAction;
import reformyourcountry.model.VoteArgument;
import reformyourcountry.repository.ActionRepository;
import reformyourcountry.repository.ArgumentRepository;
import reformyourcountry.repository.UserRepository;
import reformyourcountry.repository.VoteActionRepository;
import reformyourcountry.repository.VoteArgumentRepository;
import reformyourcountry.security.SecurityContext;

@Service
@Transactional
public class ArgumentService {
    
    @Autowired ArgumentRepository argumentRepository;
    @Autowired ActionRepository actionRepository;
    @Autowired UserRepository userRepository;
    @Autowired VoteActionRepository voteActionRepository;
    @Autowired VoteArgumentRepository voteArgumentRepository;
    public void updateVoteArgument(Long idArg, int value, User user,
            Argument arg) {
        VoteArgument vote = voteArgumentRepository.findVoteArgumentForUser(user, idArg);
        if (vote!=null) {  // User is changing his mind.
            vote.setValue(value);
            voteArgumentRepository.merge(vote);
            
        } else {  // A new vote entity
            vote = new VoteArgument(value, arg, user);
            vote.setArgument(arg);
            voteArgumentRepository.persist(vote);
            arg.getVoteArguments().add(vote);
            argumentRepository.merge(arg);
        }
    }   
    
    public void putArgumentListInModelAndView(ModelAndView mv, Action action) {
        //Divide all the arguments in 2 lists: positive ones and negative ones.
           List<Argument> listArgs = argumentRepository.findAll(action.getId());
           List<Argument> listPosArgs = new ArrayList<Argument>();
           List<Argument> listNegArgs = new ArrayList<Argument>();
           for(Argument args :listArgs){
               if (args.isPositiveArg()){
                   listPosArgs.add(args);
               }else
               {
                   listNegArgs.add(args);
               }
           }
            mv.addObject("listPosArgs",listPosArgs);
            mv.addObject("listNegArgs",listNegArgs);
    }
    
    public ModelAndView getActionModelAndView(Action action) {
        ModelAndView mv = new ModelAndView("actiondisplay"); 
        mv.addObject("action",action);
        VoteAction va = voteActionRepository.findVoteActionForUser(SecurityContext.getUser(), action.getId());
        if (va != null){ 
            mv.addObject("resultVote", voteActionRepository.getTotalVoteValue(action.getId()));
            mv.addObject("vote",va);
            
        }
        this.putArgumentListInModelAndView(mv, action);
        return mv;
    }
}
