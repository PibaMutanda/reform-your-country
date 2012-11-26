package reformyourcountry.controller;


import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import reformyourcountry.model.Article;
import reformyourcountry.repository.ArticleRepository;
import reformyourcountry.security.Privilege;
import reformyourcountry.security.SecurityContext;
import reformyourcountry.service.ArticleService;
import reformyourcountry.tag.ArticleNavBarTag;
import reformyourcountry.util.NotificationUtil;


@Controller
@RequestMapping(value={"/article"})
public class ArticleEditController extends BaseController<Article>{
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                      
	@Autowired ArticleRepository articleRepository;
	@Autowired ArticleDisplayController displayArticleController;
	@Autowired ArticleService articleService;


	@RequestMapping(value={"/edit","/create"})
	public ModelAndView articleEdit(@ModelAttribute Article article){
		SecurityContext.assertUserHasPrivilege(Privilege.EDIT_ARTICLE);
		ModelAndView mv = new ModelAndView("articleedit");
		mv.addObject("article",article);
        mv.addObject("parentsPath", article.getPath()); // For the breadcrumb
		return mv;
	}

	@RequestMapping("/editsubmit")
	public ModelAndView articleEditSubmit(@Valid @ModelAttribute Article article, Errors errors){
		SecurityContext.assertUserHasPrivilege(Privilege.EDIT_ARTICLE);
		
		Article otherArticleInDB = null;
	    ModelAndView mv = new ModelAndView("articleedit", "article", article);
		// For the breadcrumb
        mv.addObject("parentsPath", article.getPath());
		
		if (errors.hasErrors()) {
		    return mv;

		} else if ((otherArticleInDB = articleRepository.findByTitle(article.getTitle())) != null && ! otherArticleInDB.equals(article)) {
            NotificationUtil.addNotificationMessage("Le titre est déja utilisé par un autre article");
            return mv;
        } else if ((otherArticleInDB = articleRepository.findByShortName(article.getShortName())) != null && ! otherArticleInDB.equals(article)) {
        	NotificationUtil.addNotificationMessage("Le raccourci est déja utilisé par un autre article");
            return mv;
        } else if ((otherArticleInDB = articleRepository.findByUrl(article.getUrl())) != null && ! otherArticleInDB.equals(article)) {
        	NotificationUtil.addNotificationMessage("L'url est déja utilisée par un autre article");
            return mv;
        } else {//if the article has no error
        
            if (article.getId() == null) {//if this is a new article
                articleService.saveArticle(article,null,null,null);
                ArticleNavBarTag.invalidateNavBarCache();
                return new ModelAndView("redirect:parentedit","id",article.getId()); // Next step after creation: select the parent.
            } else {
                articleRepository.merge(article);
                ArticleNavBarTag.invalidateNavBarCache();
                return new ModelAndView("redirect:"+article.getUrl());
            }
            
          
            
        }
	}
	
	@ModelAttribute
	public Article findArticle(@RequestParam(value="id",required=false)Long id){
	    if(id==null){
			return new Article();
		} else {
			return getRequiredDetachedEntity(id);
		}
	}
	
	
}
