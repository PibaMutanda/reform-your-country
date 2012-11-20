package reformyourcountry.controller;


import javax.servlet.http.HttpServletRequest;

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
import reformyourcountry.service.ActionService;
import reformyourcountry.service.ArgumentService;
import reformyourcountry.web.ContextUtil;
import reformyourcountry.web.PropertyLoaderServletContextListener;

@Controller
public class ArgumentController extends BaseController<Argument>{

    @Autowired ArgumentRepository argumentRepository;
    @Autowired ActionRepository actionRepository;
    @Autowired CommentRepository commentRepository;
    @Autowired UserRepository userRepository;
    @Autowired VoteActionRepository voteActionRepository;
    @Autowired VoteArgumentRepository voteArgumentRepository;
    @Autowired ArgumentService argumentService;
    @Autowired ActionService actionService;
    
    
    @RequestMapping("ajax/argumentedit")
    public ModelAndView argumentEdit(@RequestParam(value="argumentId",required=false) Long argumentId,   // For editing existing arguments.
            @RequestParam(value="idAction",required=false)Long actionId,@RequestParam(value="isPos",required=false)Boolean positiveArg      // For creating a new argument.
            ){
        ModelAndView mv = new ModelAndView("ckeditorform");
        
        if(argumentId != null) {
            Argument argument =  (Argument)getRequiredEntity(argumentId, Argument.class);
            mv.addObject("idItem",argumentId);
            mv.addObject("titleItem",argument.getTitle());
            mv.addObject("contentItem",argument.getContent());
            // We should not get both argumentId and other parameters at the same time.
            actionId = argument.getAction().getId();
            positiveArg = argument.isPositiveArg();
        } 
        mv.addObject("urlAction","/ajax/argumenteditsubmit");
        mv.addObject("positiveArg",positiveArg);
        mv.addObject("idParent",actionId); 
        mv.addObject("helpContent",PropertyLoaderServletContextListener.getProprerty("p_argument_help"));  // Text in yellow div.
        return mv;
    }
  
    
    @RequestMapping("/ajax/argumenteditsubmit")
    public ModelAndView argumentEditSubmit(
            @RequestParam(value="idParent", required=false)Long actionId, @RequestParam("ispos")Boolean isPos,  // In case of create
            @RequestParam(value="idItem", required=false)Long argumentId,  // In case of edit
            @RequestParam("content")String content, @RequestParam("title")String title) throws Exception{
        SecurityContext.assertUserIsLoggedIn();
        
        Argument argument;
        if (argumentId != null) {  // It's an edit (vs a create)
            argument = getRequiredEntity(argumentId);
            SecurityContext.assertCurrentUserCanEditArgument(argument);
            argument.setTitle(title);
            argument.setContent(content);
            argumentRepository.merge(argument);
          
        } else {  // It's a create
            Action action = (Action)getRequiredEntity(actionId, Action.class);
            argument = new Argument(title, content, action, SecurityContext.getUser());
            argument.setPositiveArg(isPos);
            argumentRepository.persist(argument);
            action.addArgument(argument);
            actionRepository.merge(action);
        }
        
        ModelAndView mv = new ModelAndView("argumentdetail");  // Redisplay that argument (with new values)
        mv.addObject("argument",argument);
        return mv;
    }        
        
    
    
    @RequestMapping("ajax/argumentvote")
    public ModelAndView argumentVote(@RequestParam("id")Long idArg,@RequestParam("value")int value)throws Exception{
        User user = SecurityContext.getUser();
        if (user !=null){
            Argument arg = argumentRepository.find(idArg);
            argumentService.updateVoteArgument(idArg, value, user, arg);
            ModelAndView mv = new ModelAndView("argumentdetail");  // Redisplay that argument (with new vote values)
            mv.addObject("argument",arg);
            return mv;
           
        } else {
            throw new Exception("no user logged");  // Catched by the JavaScript (should not happen because we don't send the ajax reqeust with non logged in user)
        }
    }
   
    @RequestMapping("ajax/commentAdd")
	public ModelAndView commentAdd(@RequestParam("id")Long idArg, @RequestParam("value")String com) throws Exception{
		User user = SecurityContext.getUser();
		if (user !=null){
			Argument argument = argumentRepository.find(idArg);
			Comment comment = new Comment(com, argument, user);
            commentRepository.persist(comment);
            argument.addComment(comment);
			argumentRepository.merge(argument);       
			ModelAndView mv = new ModelAndView("argumentdetail");
            mv.addObject("argument",argument);
			return mv;
		}else {
			throw new Exception("no user logged");
		}
	}

}
