package reformyourcountry.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import reformyourcountry.model.Action;
import reformyourcountry.model.Argument;
import reformyourcountry.model.Comment;
import reformyourcountry.model.User;
import reformyourcountry.repository.ActionRepository;
import reformyourcountry.repository.ArgumentRepository;
import reformyourcountry.repository.CommentRepository;
import reformyourcountry.repository.UserRepository;
import reformyourcountry.repository.VoteActionRepository;
import reformyourcountry.repository.VoteArgumentRepository;
import reformyourcountry.security.SecurityContext;
import reformyourcountry.service.ArgumentService;

@Controller
public class ArgumentController extends BaseController<Action>{

    @Autowired ArgumentRepository argumentRepository;
    @Autowired ActionRepository actionRepository;
    @Autowired CommentRepository commentRepository;
    @Autowired UserRepository userRepository;
    @Autowired VoteActionRepository voteActionRepository;
    @Autowired VoteArgumentRepository voteArgumentRepository;
    @Autowired ArgumentService argumentService;
    
    
    @RequestMapping("ajax/argumentAdd")
    public ModelAndView argumentAdd(@RequestParam("action")Long idAction, @RequestParam("content")String content, @RequestParam("title")String title,@RequestParam("ispos")boolean isPos) throws Exception{
        User user = SecurityContext.getUser();
        if (user !=null){
            Action action = actionRepository.find(idAction);
            if(title == null || content == null){
            	throw new Exception("Le titre et le contenu des argument ne peuvent être vide");
            }
            if (action!=null){
                Argument arg = new Argument(title, content, action, SecurityContext.getUser());
                arg.setPositiveArg(isPos);
                argumentRepository.persist(arg);
                action.addArgument(arg);
                actionRepository.merge(action);
            }else{
                throw new Exception("no action selected");  
            }
            return  argumentService.getActionModelAndView(action,"argument");
        }else{
            throw new Exception("no user logged");
        }
    }   
    
    
    @RequestMapping("ajax/argumentedit")
    public ModelAndView argumentEdit(@RequestParam("idArg")Long idArg, @RequestParam("content")String content, @RequestParam("title")String title) throws Exception{
        Argument arg = argumentRepository.find(idArg);
        SecurityContext.assertCurrentUserCanEditArgument(arg);
        arg.setTitle(title);
        arg.setContent(content);
        argumentRepository.merge(arg);
        ModelAndView mv = new ModelAndView("argumentdetail");  // Redisplay that argument (with new vote values)
        mv.addObject("arg",arg);
        return mv;
    }
    
    @RequestMapping("ajax/argumentvote")
    public ModelAndView argumentVote(@RequestParam("idArg")Long idArg,@RequestParam("value")int value)throws Exception{
        User user = SecurityContext.getUser();
        if (user !=null){
            Argument arg = argumentRepository.find(idArg);
            argumentService.updateVoteArgument(idArg, value, user, arg);
            ModelAndView mv = new ModelAndView("argumentdetail");  // Redisplay that argument (with new vote values)
            mv.addObject("arg",arg);
            return mv;
           
        } else {
            throw new Exception("no user logged");  // Catched by the JavaScript (should not happen because we don't send the ajax reqeust with non logged in user)
        }
    }
   
    @RequestMapping("ajax/commentAdd")
	public ModelAndView commentAdd(@RequestParam("arg")Long idArg, @RequestParam("comment")String com) throws Exception{
		User user = SecurityContext.getUser();
		if (user !=null){
			Argument arg = argumentRepository.find(idArg);
			Comment comment = new Comment(com, arg, user);
            commentRepository.persist(comment);
			arg.addComment(comment);
			argumentRepository.merge(arg);       
			ModelAndView mv = new ModelAndView("argumentcomment");
            mv.addObject("arg",arg);
			return mv;
		}else {
			throw new Exception("no user logged");
		}
	}
  
}
