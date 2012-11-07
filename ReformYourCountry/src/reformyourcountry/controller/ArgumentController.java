package reformyourcountry.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import reformyourcountry.model.Action;
import reformyourcountry.model.Argument;
import reformyourcountry.model.VoteAction;
import reformyourcountry.repository.ActionRepository;
import reformyourcountry.repository.ArgumentRepository;
import reformyourcountry.repository.UserRepository;
import reformyourcountry.repository.VoteActionRepository;
import reformyourcountry.security.Privilege;
import reformyourcountry.security.SecurityContext;

@Controller
public class ArgumentController extends BaseController<Action>{

    @Autowired ArgumentRepository argumentRepository;
    @Autowired ActionRepository actionRepository;
    @Autowired UserRepository userRepository;
    @Autowired VoteActionRepository voteActionRepository;
    
    @RequestMapping("ajax/argumentAdd")
    public ModelAndView argumentAdd(@RequestParam("action")Long idAction, @RequestParam("content")String content, @RequestParam("title")String title,@RequestParam("ispos")boolean isPos){
        SecurityContext.assertUserHasPrivilege(Privilege.CAN_VOTE);
        Action action = actionRepository.find(idAction);
        if (action!=null){
            Argument arg = new Argument(title, content, action, SecurityContext.getUser());
            arg.setPositiveArg(isPos);
            argumentRepository.persist(arg);
        }else{
            
        }
        //Divide all the arguments in 2 lists: positive ones and negative ones.
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
        ModelAndView mv = new ModelAndView("argument"); 
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
