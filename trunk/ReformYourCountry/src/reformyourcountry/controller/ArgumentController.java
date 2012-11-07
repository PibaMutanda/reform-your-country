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
import reformyourcountry.model.User;
import reformyourcountry.repository.ActionRepository;
import reformyourcountry.repository.ArgumentRepository;
import reformyourcountry.repository.UserRepository;
import reformyourcountry.repository.VoteActionRepository;
import reformyourcountry.repository.VoteArgumentRepository;
import reformyourcountry.security.Privilege;
import reformyourcountry.security.SecurityContext;
import reformyourcountry.service.ArgumentService;

@Controller
public class ArgumentController extends BaseController<Action>{

    @Autowired ArgumentRepository argumentRepository;
    @Autowired ActionRepository actionRepository;
    @Autowired UserRepository userRepository;
    @Autowired VoteActionRepository voteActionRepository;
    @Autowired VoteArgumentRepository voteArgumentRepository;
    @Autowired ArgumentService argumentService;
    
    
    @RequestMapping("ajax/argumentAdd")
    public ModelAndView argumentAdd(@RequestParam("action")Long idAction, @RequestParam("content")String content, @RequestParam("title")String title,@RequestParam("ispos")boolean isPos) throws Exception{
        SecurityContext.assertUserHasPrivilege(Privilege.CAN_VOTE);
        Action action = actionRepository.find(idAction);
        if(title == null || content == null){
        	throw new Exception("Le titre et le contenu des argument ne peuvent être vide");
        }
        if (action!=null){
            Argument arg = new Argument(title, content, action, SecurityContext.getUser());
            arg.setPositiveArg(isPos);
            argumentRepository.persist(arg);
        }else{
            
        }
        return  argumentService.getActionModelAndView(action);
    }   
    
    
    
    @RequestMapping("ajax/argumentVote")
    public ModelAndView argumentVote(@RequestParam("idArg")Long idArg,@RequestParam("value")int value){
        SecurityContext.assertUserHasPrivilege(Privilege.CAN_VOTE);
        User user = SecurityContext.getUser();
        Argument arg = argumentRepository.find(idArg);
        argumentService.updateVoteArgument(idArg, value, user, arg);
        Action action =  arg.getAction();
      
       return  argumentService.getActionModelAndView(action);
    }
   
   
  
}
