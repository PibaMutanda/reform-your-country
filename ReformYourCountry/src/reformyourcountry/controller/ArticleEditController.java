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


@Controller
@RequestMapping(value={"/article"})
public class ArticleEditController extends BaseController<Article>{

	@Autowired ArticleRepository articleRepository;
	@Autowired ArticleDisplayController displayArticleController;


	@RequestMapping(value={"/edit","/create"})
	public ModelAndView articleEdit(@ModelAttribute Article article){
		SecurityContext.assertUserHasPrivilege(Privilege.EDIT_ARTICLE);
		ModelAndView mv = new ModelAndView("articleedit");
		mv.addObject("article",article);
		return mv;
	}

	@RequestMapping("/editsubmit")
	public ModelAndView articleEditSubmit(@Valid @ModelAttribute Article article, Errors errors){
		SecurityContext.assertUserHasPrivilege(Privilege.EDIT_ARTICLE);
		
		Article otherArticleInDB = null;
		
		if(errors.hasErrors()){
		    ModelAndView mv = new ModelAndView("articleedit","article",article);
		    return mv;

		}else if ((otherArticleInDB = articleRepository.findByTitle(article.getTitle())) != null && ! otherArticleInDB.equals(article)) {
            ModelAndView mv = new ModelAndView("articleedit","article",article);
            setMessage(mv, "Le titre est déja utilisé par un autre article");
            return mv;
        }else if ((otherArticleInDB = articleRepository.findByShortName(article.getShortName())) != null && ! otherArticleInDB.equals(article)) {
            ModelAndView mv = new ModelAndView("articleedit","article",article);
            setMessage(mv, "Le raccourci est déja utilisé par un autre article");
            return mv;
        }else if ((otherArticleInDB = articleRepository.findByUrl(article.getUrl())) != null && ! otherArticleInDB.equals(article)) {
            ModelAndView mv = new ModelAndView("articleedit","article",article);
            setMessage(mv, "L'url est déja utilisée par un autre article");
            return mv;
        }else{//if the article has no error
            if(article.getId() == null){//if this is a new article
                articleRepository.persist(article);
                return new ModelAndView("redirect:articleparentedit","id",article.getId()); // Next step after creation: select the parent.
            }else{
                articleRepository.merge(article);
                return new ModelAndView("redirect:article/"+article.getUrl());
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
