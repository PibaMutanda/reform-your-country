package reformyourcountry.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import reformyourcountry.model.Action;
import reformyourcountry.model.User;
import reformyourcountry.model.User.SpecialType;
import reformyourcountry.model.VoteAction;
import reformyourcountry.repository.ActionRepository;
import reformyourcountry.repository.UserRepository;
import reformyourcountry.repository.VoteActionRepository;

@Controller
public class SpecialUserListController extends BaseController<User> {
    
    @Autowired UserRepository userRepository;
    @Autowired ActionRepository actionRepository;
    @Autowired VoteActionRepository voteActionRepository;
    @RequestMapping("/specialuserslist")
    public ModelAndView specialUserList(){
    	ModelAndView mv = specialUserAndAction(null);
        return mv;
    }
    
    @RequestMapping("/specialuserslist/{actionUrl}")
    public ModelAndView specialUserAndAction(@PathVariable("actionUrl")String actionUrl){
    	Action action = null;
    	if (actionUrl != null) {
    		action = (Action) getRequiredEntityByUrl(actionUrl, Action.class);
    	}
    	///// we populate a hashmap with one list by association and this list is populate with users of this association
        // It makes this page generic and usable if we add a value in User.SpecialType.
        HashMap<String,List<UsersAndVotes>> userMapGroupByType = new HashMap<String,List<UsersAndVotes>>();
        for (SpecialType st : User.SpecialType.values()) {
            userMapGroupByType.put(st.getName(),  new ArrayList<UsersAndVotes>());
        }
        for (User user : userRepository.getUsersWithSpecialType()) {
            userMapGroupByType.get(user.getSpecialType().getName())
            		.add(new UsersAndVotes(user, action == null ? null : voteActionRepository.findVoteActionForUser(user, action.getId())));
        }
       
        ModelAndView mv =  new ModelAndView("specialuserslist","userMapGroupByType",userMapGroupByType);
        mv.addObject("values", User.SpecialType.getValuesExceptPrivate());
        mv.addObject("isVoteResultPage",actionUrl != null ? false : true);
        mv.addObject("action",action);
        return mv;
    }

    public class UsersAndVotes{
    	private User user;
    	private VoteAction voteAction;
    	public UsersAndVotes(User user, VoteAction voteAction){
    		this.user=user;
    		this.voteAction=voteAction;
    	}
		public User getUser() {
			return user;
		}
		public void setUser(User user) {
			this.user = user;
		}
		public VoteAction getVoteAction() {
			return voteAction;
		}
		public void setVoteAction(VoteAction voteAction) {
			this.voteAction = voteAction;
		}
    	
    }
}
