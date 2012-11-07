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
        
        VoteArgument vote = null;
        for (VoteArgument vt :arg.getVoteArguments()){
            if(vt.getUser()==SecurityContext.getUser()){
                vt.setValue(value);
                voteArgumentRepository.merge(vt);
                arg.recalculate();
                argumentRepository.merge(arg);
                vote =vt;
                break;
            }
        }
        if (vote==null) {  // A new vote entity
            vote = new VoteArgument(value, arg, user);
            vote.setArgument(arg);
            voteArgumentRepository.persist(vote);
            arg.addVoteArgument(vote);
            argumentRepository.merge(arg);
        }
    }   

    public void putArgumentListInModelAndView(ModelAndView mv, Action action) {
        //Divide all the arguments in 2 lists: positive ones and negative ones.
        List<Argument> listArgs = action.getArguments();
        List<Argument> listPosArgs = new ArrayList<Argument>();
        List<Argument> listNegArgs = new ArrayList<Argument>();
        for(Argument args :listArgs){
            if (args.isPositiveArg()) {
                listPosArgs.add(args);
            } else {
                listNegArgs.add(args);
            }
        }
        mv.addObject("listPosArgs",listPosArgs);
        mv.addObject("listNegArgs",listNegArgs);
    }

    public ModelAndView getActionModelAndView(Action action,String jsp) {
        ModelAndView mv = new ModelAndView(jsp); 
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
