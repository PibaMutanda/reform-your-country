package reformyourcountry.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import reformyourcountry.model.Action;
import reformyourcountry.model.Argument;
import reformyourcountry.model.User;
import reformyourcountry.model.VoteAction;
import reformyourcountry.repository.ActionRepository;
import reformyourcountry.repository.VoteActionRepository;
import reformyourcountry.security.SecurityContext;

@Service
public class ActionService {
	@Autowired VoteActionRepository voteActionRepository;
	@Autowired ActionRepository actionRepository;
	public void putGraphNumbersInModelAndView(ModelAndView mv, Action action){
		List<Long> resultNumbers = getResultNumbersForAction(action);
        mv.addObject("resultNumbers", resultNumbers);
        mv.addObject("positiveWeightedPercentage",getWeightedPercentage(resultNumbers, true));
        mv.addObject("positiveAbsolutePercentage",getAbsolutePercentage(resultNumbers, true));
        mv.addObject("negativeAbsolutePercentage",getAbsolutePercentage(resultNumbers, false));
        mv.addObject("negativeWeightedPercentage",getWeightedPercentage(resultNumbers, false));
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
	
	public int getAbsolutePercentage(List<Long> resultNumbers,boolean positive){
		long total=0;
		for(Long nbr : resultNumbers){
			total+=nbr;
		}
		float result;
        //No division by 0 please ;)
		if (total==0){
		    return 0;
		}
		if (positive) {
			result = (float) ((resultNumbers.get(0) * 100 / total) + (resultNumbers
					.get(1) * 100 / total));
		}else{
			result = (float) ((resultNumbers.get(3) * 100 / total) + (resultNumbers
					.get(4) * 100 / total));
		}
    	return Math.round(result);
    }
    
	public int getWeightedPercentage(List<Long> resultNumbers, boolean positive){
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
    	//No division by 0 please ;)
        if (weightedTotal==0){
            return 0;
        }
        float result;
		if (positive) {
			result = (float) ((weightedNumbers.get(0) * 100 / weightedTotal) + (weightedNumbers
					.get(1) * 100 / weightedTotal));
		}else{
			result = (float) ((weightedNumbers.get(3) * 100 / weightedTotal) + (weightedNumbers
					.get(4) * 100 / weightedTotal));
		}
		return Math.round(result);
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
	 public List<Long> getResultNumbersForAction( Action action){
	        
	        
	        List<Long> resultNumbers = new ArrayList<Long>();
	        for(int i=-2;i<=2;i++){
	            resultNumbers.add(voteActionRepository.getNumberOfVotesByValue(action.getId(), i));
	        }
	        
	        return resultNumbers;
	    }
	 
	   public List<Long> getResultNumbersForAction( Long Idaction){
           
           
           List<Long> resultNumbers = new ArrayList<Long>();
           for(int i=-2;i<=2;i++){
               resultNumbers.add(voteActionRepository.getNumberOfVotesByValue(Idaction, i));
           }
           
           return resultNumbers;
       }
	   
	   public VoteAction userVoteForAction(User user,Long idAction,int vote){
	        
	        VoteAction va = voteActionRepository.findVoteActionForUser(SecurityContext.getUser(), idAction);
	        if (va==null){
	            va = new VoteAction(vote, actionRepository.find(idAction), SecurityContext.getUser());
	            voteActionRepository.persist(va);
	        }else{
	            va.setValue(vote);
	            voteActionRepository.merge(va);
	        }
	        
	        return va;
	        
	    }


}
