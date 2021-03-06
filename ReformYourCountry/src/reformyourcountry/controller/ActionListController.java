package reformyourcountry.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import reformyourcountry.model.Action;
import reformyourcountry.model.VoteAction;
import reformyourcountry.repository.ActionRepository;
import reformyourcountry.repository.VoteActionRepository;
import reformyourcountry.security.SecurityContext;
import reformyourcountry.service.ActionService;
import reformyourcountry.util.DateUtil;

@Controller
public class ActionListController {
    
    @Autowired ActionRepository actionRepository;
    @Autowired ActionService actionService;
    @Autowired VoteActionRepository voteActionRepository;
    
    @RequestMapping(value="/action")
    public ModelAndView actionListDisplay(){
        ////////// Builds a list of action with connex data
        List<ActionItem> actionItems = new ArrayList<ActionItem>();

        for (Action action : actionRepository.findAll()){
            VoteAction va = voteActionRepository.findVoteActionForUser(SecurityContext.getUser(), action.getId());
            ActionItem actionItem = new ActionItem(action, va);
            actionItems.add(actionItem);           
        }

        ModelAndView mv = new ModelAndView("actionlist");
        mv.addObject("actionItems",actionItems);
        return mv;
    }

    @RequestMapping(value="/ajax/voteactionlist")
    public ModelAndView voteFromList(@RequestParam("idAction")Long idAction, @RequestParam("vote")int vote){
        SecurityContext.assertUserIsLoggedIn();       
        VoteAction va = actionService.userVoteForAction(SecurityContext.getUser(), idAction, vote);
        List<Long> resultNumbers = actionService.getResultNumbersForAction(idAction);
        ModelAndView mv = new ModelAndView("voteactionwidget");
        mv.addObject("vote",va);
        mv.addObject("resultNumbers", resultNumbers);
        mv.addObject("id", idAction);
        return mv;
    }
    
    public static class ActionItem{
        Action action;
        List<Long> resultNumbers;
        VoteAction voteAction;  // Maybe null if the current user did not vote on that action.

      
        public ActionItem(Action action,VoteAction voteAction){
            this.action = action;        
            this.voteAction = voteAction;
        }
        public Action getAction() {
            return action;
        }
      
        public VoteAction getVoteAction() {
            return voteAction;
        }
        public String getDifference() {
            return DateUtil.formatIntervalFromToNowFR(action.getCreatedOn());
        }

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			ActionItem other = (ActionItem) obj;
			if (action == null) {
				if (other.action != null)
					return false;
			} else if (!action.equals(other.action))
				return false;
			return true;
		}
		@Override
		public String toString() {
			return "ActionItem [action=" + action + ", resultNumbers="
					+ resultNumbers + ", voteAction=" + voteAction
					+ ", toString()=" + super.toString() + "]";
		}
        
    }
    
}
