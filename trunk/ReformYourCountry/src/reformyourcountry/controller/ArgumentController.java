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
    
    public ModelAndView returnitemDetail(Argument arg){
        //FIXME no verif if user can edit --maxime 30/11/12
        ModelAndView mv = new ModelAndView("itemdetail");
        mv.addObject("canNegativeVote",true);
        mv.addObject("currentItem",arg);
        return mv;
    }
    
    @RequestMapping("argument/switch")
    public ModelAndView argumentSwitch(@RequestParam(value="argumentId",required=false) Long argumentId) throws Exception{
    	 SecurityContext.assertUserIsLoggedIn();
    	 if(argumentId != null) {
             Argument argument =  getRequiredEntity(argumentId);
             SecurityContext.assertCurrentUserCanEditArgument(argument);
             argument.setPositiveArg(!argument.getPositiveArg());
//             ModelAndView mv = actionService.getActionModelAndView(argument.getAction(),"actiondisplay");
//             actionService.putGraphNumbersInModelAndView(mv,argument.getAction());
             
             return  new ModelAndView("redirect:/action/"+argument.getAction().getUrl());
    	 }
    	 throw new Exception("No argument to switch");
    }
    
    @RequestMapping("ajax/argument/edit")
    public ModelAndView argumentEdit(@RequestParam(value="argumentId",required=false) Long argumentId,   // For editing existing arguments.
            @RequestParam(value="idAction",required=false)Long actionId,@RequestParam(value="isPos",required=false)Boolean positiveArg      // For creating a new argument.
            ){
        SecurityContext.assertUserIsLoggedIn();
        ModelAndView mv = new ModelAndView("ckeditorform");
        
        if(argumentId != null) {
            Argument argument =  getRequiredEntity(argumentId);
            SecurityContext.assertCurrentUserCanEditArgument(argument);
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
        
        mv.addObject("helpContent",PropertyLoaderServletContextListener.getProprerty("p_argument_help"));  // Text in yellow div.
        
        return mv;
    }
  
    
    @RequestMapping("/ajax/argument/editsubmit")
    public ModelAndView argumentEditSubmit(
            @RequestParam(value="idParent", required=false)Long actionId, @RequestParam("ispos")Boolean isPos,  // In case of create
            @RequestParam(value="idItem", required=false)Long argumentId,  // In case of edit
            @RequestParam("content")String content, @RequestParam("title")String title) throws AjaxValidationException{
        SecurityContext.assertUserIsLoggedIn();
        //TODO review
        //check if content or title haven't dangerous html
        if (!HTMLUtil.isHtmlSecure(title) || !HTMLUtil.isHtmlSecure(content)) {
             throw new AjaxValidationException("vous avez introduit du HTML/Javascript invalide dans le commentaire");
        }
        
        ////we check if there is some character who can make the page bug (<,>,",etc...)
        String forbiddenCHaracter;
        
        if ((forbiddenCHaracter = HTMLUtil.getContainedForbiddenHtmlCharacter(title)) != null) {
            throw new AjaxValidationException("vous avez introduit des charactères interdit dans le titre : "+forbiddenCHaracter);
        }
        Argument argument;
        if (argumentId != null) {  // It's an edit (vs a create)
            argument = getRequiredEntity(argumentId);
            SecurityContext.assertCurrentUserCanEditArgument(argument);
        } else {  // It's a create
            Action action = (Action)getRequiredEntity(actionId, Action.class);
            argument = new Argument(action, isPos);
            
            argumentRepository.persist(argument);
            
            action.addArgument(argument);
            actionRepository.merge(action);
        }
        
        argument.setTitle(title);
        argument.setContent(content);
        argumentRepository.merge(argument);
        return returnitemDetail(argument);
    }   
    
    @RequestMapping("/ajax/argument/delete")
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
        
    @RequestMapping("ajax/argument/refresh")
    public ModelAndView argumentVote(@RequestParam("id")Long idArg){
        SecurityContext.assertUserIsLoggedIn();
        Argument arg = argumentRepository.find(idArg);
        return returnitemDetail(arg);
    }
    
    @RequestMapping("ajax/argument/vote")
    public ModelAndView argumentVote(@RequestParam("id")Long idArg,@RequestParam("value")int value){
        SecurityContext.assertUserIsLoggedIn();
        
        User user = SecurityContext.getUser();
        
        Argument argument = (Argument) getRequiredEntity(idArg,Argument.class);
        argumentService.updateVoteArgument(idArg, value, user, argument);
        badgeService.grandBadgeForArgumentVoter(user);
        
        return returnitemDetail(argument);
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

    @RequestMapping("/ajax/argument/commenteditsubmit")
    public ModelAndView commentEdit(@RequestParam(value="idCommentedItem",required=false)Long idArgument,//for create a new comment
                                    @RequestParam(value="idComment",required=false)Long idComment,//for editing
                                    @RequestParam("content")String content) throws Exception{
        SecurityContext.assertUserIsLoggedIn();
        //TODO review
        //check if content or title haven't dangerous html
        if (!HTMLUtil.isHtmlSecure(content)) {
             throw new AjaxValidationException("vous avez introduit du HTML/Javascript invalide dans le commentaire");
        }
        
        ////we check if there is some character who can make the page bug (<,>,",etc...)
        String forbiddenCHaracter;
        
        if ((forbiddenCHaracter = HTMLUtil.getContainedForbiddenHtmlCharacter(content)) != null) {
            throw new AjaxValidationException("vous avez introduit des charactères interdit dans le contenu : "+forbiddenCHaracter);
        }
        
        Comment comment = null;
        
        if (idComment == null && idArgument == null) {
            throw new IllegalArgumentException("idComment and idArgument are null : can't determine if it's a create or an edit");
        } else if ( idComment == null ) {//it's a create, only idComment is null 
            Argument argument =  getRequiredEntity(idArgument);
            comment = new Comment();
            comment.setArgument(argument);
            
            commentRepository.persist(comment);
            argument.addComment(comment);
            argumentRepository.merge(argument); 
            badgeService.grandBadgeForComment(comment.getCreatedBy());
        } else { //it's an edit , only idArgument is null
            
            comment = (Comment) getRequiredEntity(idComment, Comment.class);
            SecurityContext.assertCurrentUserCanEditComment(comment);
        }

        comment.setContent(content);
        commentRepository.merge(comment);
        return returnitemDetail(comment.getArgument());
        
    }

    @RequestMapping("/ajax/argument/commenthide")
    public ModelAndView commentHide(@RequestParam("id")Long idComment) throws AjaxValidationException{
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
        
        if (!com.isHidden()) {
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
    
    @RequestMapping("/ajax/argument/commentdelete")
    public ModelAndView deleteComment(@RequestParam("id")Long idComment) throws Exception{
        Comment com = commentRepository.find(idComment);
    	SecurityContext.assertCurrentUserCanEditComment(com);
        
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
}
