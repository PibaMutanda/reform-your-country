package reformyourcountry.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import reformyourcountry.model.Argument;
import reformyourcountry.model.Comment;
import reformyourcountry.model.GoodExample;
import reformyourcountry.repository.ArgumentRepository;
import reformyourcountry.repository.GoodExampleRepository;
import reformyourcountry.security.SecurityContext;
@Controller
public class CommentListController extends BaseController<Argument> {
    
    @Autowired ArgumentRepository argumentRepository;
    @Autowired GoodExampleRepository goodExampleRepository;

    @RequestMapping("/commentlist")
    public ModelAndView commentList(@RequestParam("id") Long id) {
        ModelAndView mv = new ModelAndView("commentlist");
        Argument argument = null;
        GoodExample goodExample = null;
        
        String urlParent = null;
        String parentContent = null;
        List<Comment> commentList = null;
        
        if ((argument = argumentRepository.find(id)) != null) {
            SecurityContext.assertCurrentUserCanEditArgument((Argument) argument);
            
            urlParent = argument.getAction().getUrl();
            parentContent = argument.getContent();
            commentList = argument.getCommentList();
        } else if ((goodExample = goodExampleRepository.find(id)) != null) {
            SecurityContext.assertCurrentUserCanEditGoodExample(goodExample);
            
            urlParent = "/home";//FIXME set the article url or the goodExample url
            parentContent = goodExample.getContent();
            commentList = goodExample.getCommentList();
        }
        
        mv.addObject("urlParent", urlParent);
        mv.addObject("parentContent",parentContent);
        mv.addObject("commentList",commentList);
        return mv;
    }
}
