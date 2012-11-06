package reformyourcountry.controller;

import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import reformyourcountry.exception.InvalidUrlException;
import reformyourcountry.model.Article;
import reformyourcountry.security.Privilege;
import reformyourcountry.security.SecurityContext;
import reformyourcountry.service.ArticleService;

// Edition of the parents
@Controller
@RequestMapping("/article")
public class ArticleParentEditController extends BaseController<Article>{

	@Autowired ArticleService articleService;
	
	
	@RequestMapping("/parentedit")
	public ModelAndView articleParentEdit(@ModelAttribute Article article){
		SecurityContext.assertUserHasPrivilege(Privilege.EDIT_ARTICLE);
		ModelAndView mv = new ModelAndView("articleparentedit");
        mv.addObject("parentsPath", article.getPath()); // For the breadcrumb
	    return mv.addObject("article", article); 
	}	

	
	@RequestMapping("/parenteditsubmit")
	public ModelAndView articleParentEditSubmit(@Valid @ModelAttribute Article article, BindingResult result,
			@RequestParam("parentid") Long parentId){
		
		SecurityContext.assertUserHasPrivilege(Privilege.EDIT_ARTICLE);
	
		if (result.hasErrors()) {
			ModelAndView mv = new ModelAndView ("articleparentedit", "article", article);
	        mv.addObject("parentsPath", article.getPath()); // For the breadcrumb
			return mv;
		} else { 
			//article do not need a merge because the following method do it already
		    articleService.changeParent(article, parentId);
		}
	    return new ModelAndView ("redirect:/article/"+article.getUrl());
	}


    @ModelAttribute
    public Article findArticle(@RequestParam(value="id",required=false)Long id){
        if(id==null){
            throw new InvalidUrlException("You can't create an article with this page");
        } else {
            return getRequiredDetachedEntity(id);
        }
    }
	

	
}
