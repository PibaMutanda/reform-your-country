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
    @Autowired VoteActionService voteActionService;
    
    // A user is voting
    public void updateVoteArgument(Long idArg, int value, User user, Argument arg) {
        VoteArgument vote = getVoteArgument(user, arg);
        
        if (vote==null) {  // A new vote entity
            vote = new VoteArgument(value, arg, user);
            vote.setArgument(arg);
            voteArgumentRepository.persist(vote);
            arg.addVoteArgument(vote);
            argumentRepository.merge(arg);
        } else {
            vote.setValue(value);
            voteArgumentRepository.merge(vote);
            arg.recalculate();
            argumentRepository.merge(arg);
        }
    }   

    // Finds the voteArguemnt for the given user on the given argument.
    public VoteArgument getVoteArgument(User user, Argument arg) {
        for (VoteArgument vt :arg.getVoteArguments()){
            if(vt.getUser().equals(user)){
                return vt;
            }
        }
        return null;  // Not found
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
        //number of votes for each values, for the graph
        List<Long> resultNumbers = voteActionService.getResultNumbersForAction(action);
        //graph numbers
        mv.addObject("resultNumbers", resultNumbers);
        mv.addObject("positiveWeightedPercentage",getWeightedPercentage(resultNumbers, true));
        mv.addObject("positiveAbsolutePercentage",getAbsolutePercentage(resultNumbers, true));
        mv.addObject("negativeAbsolutePercentage",getAbsolutePercentage(resultNumbers, false));
        mv.addObject("negativeWeightedPercentage",getWeightedPercentage(resultNumbers, false));
        
        VoteAction va = voteActionRepository.findVoteActionForUser(SecurityContext.getUser(), action.getId());
        if (va != null){ 
            mv.addObject("resultVote", voteActionRepository.getTotalVoteValue(action.getId()));
            mv.addObject("vote",va);
        }
        this.putArgumentListInModelAndView(mv, action);
        return mv;
    }
    
   
    
    public float getAbsolutePercentage(List<Long> resultNumbers,boolean positive){
		long total=0;
		for(Long nbr : resultNumbers){
			total+=nbr;
		}
		float result;
		if (positive) {
			result = (float) ((resultNumbers.get(0) * 100 / total) + (resultNumbers
					.get(1) * 100 / total));
		}else{
			result = (float) ((resultNumbers.get(3) * 100 / total) + (resultNumbers
					.get(4) * 100 / total));
		}
    	return result;
    }
    
    public float getWeightedPercentage(List<Long> resultNumbers, boolean positive){
    	List<Long> weightedNumbers = new ArrayList<Long>();
    	long weightedTotal = 0;
    	for(int i=0; i<5;i++){
    		if(i<1 || i>3){// first and last of the list
    			weightedTotal+=resultNumbers.get(i)*2;
    			weightedNumbers.add(resultNumbers.get(i)*2);
    		}else{
    			weightedTotal+=resultNumbers.get(i);
    			weightedNumbers.add(resultNumbers.get(i));
    		}
    	}
        float result;
		if (positive) {
			result = (float) ((weightedNumbers.get(0) * 100 / weightedTotal) + (weightedNumbers
					.get(1) * 100 / weightedTotal));
		}else{
			result = (float) ((weightedNumbers.get(3) * 100 / weightedTotal) + (weightedNumbers
					.get(4) * 100 / weightedTotal));
		}
    	return result;
    }
}
