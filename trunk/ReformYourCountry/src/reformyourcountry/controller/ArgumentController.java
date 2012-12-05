package reformyourcountry.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import reformyourcountry.exception.AjaxValidationException;
import reformyourcountry.model.Action;
import reformyourcountry.model.Argument;
import reformyourcountry.model.Comment;
import reformyourcountry.model.User;
import reformyourcountry.model.VoteArgument;
import reformyourcountry.repository.ActionRepository;
import reformyourcountry.repository.ArgumentRepository;
import reformyourcountry.repository.CommentRepository;
import reformyourcountry.repository.UserRepository;
import reformyourcountry.repository.VoteActionRepository;
import reformyourcountry.repository.VoteArgumentRepository;
import reformyourcountry.security.SecurityContext;
import reformyourcountry.service.ActionService;
import reformyourcountry.service.ArgumentService;
import reformyourcountry.service.BadgeService;
import reformyourcountry.util.HTMLUtil;
import reformyourcountry.web.PropertyLoaderServletContextListener;
import reformyourcountry.controller.BaseController;

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
    @Autowired BadgeService badgeService;
   
    @RequestMapping("/argument")
    public ModelAndView showArgument(@RequestParam("id") Long id) {
        ModelAndView mv = new ModelAndView("commentlist");
        Argument arg = getRequiredEntity(id);
        SecurityContext.assertCurrentUserCanEditArgument(arg);
        
        mv.addObject("urlParent", ("/action/"+arg.getAction().getUrl()));
        mv.addObject("parentContent",arg.getContent());
        mv.addObject("commentList",arg.getCommentList());
        return mv;
    }
    
    @RequestMapping("ajax/argument/edit")
    public ModelAndView argumentEdit(@RequestParam(value="argumentId",required=false) Long argumentId,   // For editing existing arguments.
            @RequestParam(value="idAction",required=false)Long actionId,@RequestParam(value="isPos",required=false)Boolean positiveArg      // For creating a new argument.
            ){
        //FIXME no verif if user can edit --maxime 30/11/12
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
        mv.addObject("urlAction","/ajax/argument/editsubmit");
        mv.addObject("positiveArg",positiveArg);
        mv.addObject("idParent",actionId); 
        
        mv.addObject("itemHelpContent",PropertyLoaderServletContextListener.getProprerty("p_argument_help"));  // Text in yellow div.
        
        return mv;
    }
  
    
    @RequestMapping("/ajax/argument/editsubmit")
    public ModelAndView argumentEditSubmit(
            @RequestParam(value="idParent", required=false)Long actionId, @RequestParam("ispos")Boolean isPos,  // In case of create
            @RequestParam(value="idItem", required=false)Long argumentId,  // In case of edit
            @RequestParam("content")String content, @RequestParam("title")String title) throws Exception{
        SecurityContext.assertUserIsLoggedIn();
        //TODO review
        //check if content or title haven't dangerous html
        if (!HTMLUtil.isHtmlSecure(title) || !HTMLUtil.isHtmlSecure(content)) {
             throw new AjaxValidationException("vous avez introduit du HTML/Javascript invalide dans le commentaire");
        }
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
        
        return returnitemDetail(argument);
    }        
        
    @RequestMapping("ajax/argument/refresh")
    public ModelAndView argumentVote(@RequestParam("id")Long idArg)throws Exception{
        Argument arg = argumentRepository.find(idArg);
        return returnitemDetail(arg);
    }
    
    @RequestMapping("ajax/argument/vote")
    public ModelAndView argumentVote(@RequestParam("id")Long idArg,@RequestParam("value")int value)throws Exception{
        User user = SecurityContext.getUser();
        if (user !=null){
            Argument arg = argumentRepository.find(idArg);
            argumentService.updateVoteArgument(idArg, value, user, arg);
            badgeService.grandBadgeForArgumentVoter(user);
            return returnitemDetail(arg);
           
        } else {
            throw new Exception("no user logged");  // Catched by the JavaScript (should not happen because we don't send the ajax request with non logged in user)
        }
    }
   
    @RequestMapping("ajax/argument/commentadd")
    public ModelAndView commentAdd(@RequestParam("id")Long idArg, @RequestParam("value")String content) throws Exception{
        //TODO review
        //check if content or title haven't dangerous html
        if (!HTMLUtil.isHtmlSecure(content)) {
             throw new AjaxValidationException("vous avez introduit du HTML/Javascript invalide dans le commentaire");
        }
        User user = SecurityContext.getUser();
        if (user !=null){
            Argument argument = argumentRepository.find(idArg);
            Comment comment = new Comment(content, argument, user);
            commentRepository.persist(comment);
            argument.addComment(comment);
            argumentRepository.merge(argument); 
            
            argumentService.notifyByEmailNewCommentPosted(argument,comment);
            badgeService.grandBadgeForComment(comment.getCreatedBy());
            
            return returnitemDetail(argument);
        }else {
            throw new Exception("no user logged");
        }
    }
    @RequestMapping("ajax/argument/unvote")
    public ModelAndView unVote(@RequestParam("id")Long idArg) throws Exception{
        
        //FIXME no verif if user can edit --maxime 30/11/12
        User user = SecurityContext.getUser();
        Argument argument = argumentRepository.find(idArg);
        List<VoteArgument> listVote = argument.getVoteArguments();
        for(VoteArgument vote : listVote){
            if (vote.getUser()== user){
                argument.getVoteArguments().remove(vote);
                argument.recalculate();
                argumentRepository.merge(argument);
                voteArgumentRepository.remove(vote);
                badgeService.grandBadgeForArgumentAuthor(argument.getCreatedBy());
                break;
            }
        }
        badgeService.grandBadgeForArgumentVoter(user);
        return returnitemDetail(argument);
    }
    public ModelAndView returnitemDetail(Argument arg){
        //FIXME no verif if user can edit --maxime 30/11/12
        ModelAndView mv = new ModelAndView("itemdetail");
        mv.addObject("canNegativeVote",true);
        mv.addObject("currentItem",arg);
        return mv;
    }
    @RequestMapping("/ajax/argument/commentdelete")
    public ModelAndView deleteComment(@RequestParam("id")Long idComment) throws Exception{
        //FIXME no verif if user can edit --maxime 30/11/12
        Comment com = commentRepository.find(idComment);
        
        if(com ==null){
            throw new Exception("this id doesn't reference any comment.");
        }
        if(!com.isEditable()){
             throw new Exception("this person can't suppress this comment(hacking).");
        }
        Argument argument = com.getArgument();
        argument.getCommentList().remove(com);
        commentRepository.remove(com);
        argumentRepository.merge(argument);
        return returnitemDetail(argument);
        
    }
    
    @RequestMapping("/ajax/argument/commentedit")
    public ModelAndView commentEdit(@RequestParam("id")Long idComment,@RequestParam("value")String content) throws Exception{
        //FIXME no verif if user can edit --maxime 30/11/12
        //TODO review
        //check if content or title haven't dangerous html
        if (!HTMLUtil.isHtmlSecure(content)) {
             throw new AjaxValidationException("vous avez introduit du HTML/Javascript invalide dans le commentaire");
        }
        Comment com = commentRepository.find(idComment);
        if(com ==null){
            throw new Exception("this id doesn't reference any comment.");
        }
        if(!com.isEditable()){
            throw new Exception("this person can't edit this comment(hacking).");
        }
        com.setContent(content);
        commentRepository.merge(com);
        return returnitemDetail(com.getArgument());
    }

    @RequestMapping("/ajax/argument/argdelete")
    @ResponseBody
    public String deleteArgument(@RequestParam("id")Long idArg) throws Exception{
        //FIXME no verif if user can edit --maxime 30/11/12
        Argument arg = argumentRepository.find(idArg);
        if(arg ==null){
            throw new Exception("this id doesn't reference any comment.");
        }
        if(!arg.isEditable()){
             throw new Exception("this person can't suppress this comment(hacking).");
        }
        argumentService.deleteArgument(arg);
        return "";
        
    }
    @RequestMapping("/ajax/argument/commenthide")
    public ModelAndView commentHide(@RequestParam("id")Long idComment){
        Comment com = commentRepository.find(idComment);
        //FIXME check if comment is already hidden
        if (!SecurityContext.canCurrentUserHideComment(com)) {
            throw new AjaxValidationException("vous ne pouvez pas cacher ce commentaire");
        }
        
        com.setHidden(true);
        Argument argument = com.getArgument();
        return returnitemDetail(argument);
    }
    @RequestMapping("/ajax/argument/commentunhide")
    public ModelAndView commentUnhide(@RequestParam("id")Long idComment) throws AjaxValidationException{
        Comment com = commentRepository.find(idComment);
        
        if (com.isHidden()) {
            throw new AjaxValidationException("ce commentaire est déjà caché");
        }
        if (!SecurityContext.canCurrentUserHideComment(com)) {
            throw new AjaxValidationException("vous ne pouvez pas cacher ce commentaire");
        }
        
        com.setHidden(false);
        ModelAndView mv = new ModelAndView("commentlist");
        Argument arg = com.getArgument();
        mv.addObject("parentContent",arg.getContent());
        mv.addObject("commentList",arg.getCommentList());
        return mv;
    }
}
