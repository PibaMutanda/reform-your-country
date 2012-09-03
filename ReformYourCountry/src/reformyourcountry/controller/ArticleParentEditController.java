package reformyourcountry.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import reformyourcountry.model.Article;
import reformyourcountry.repository.ArticleRepository;
import reformyourcountry.security.Privilege;
import reformyourcountry.security.SecurityContext;
import reformyourcountry.service.ArticleService;

// Create and Edition of the parent (and title)
@Controller
public class ArticleParentEditController extends BaseController<Article>{

	@Autowired ArticleRepository articleRepository;
	@Autowired ArticleDisplayController displayArticleController;
	@Autowired ArticleService articleService;
	
	
	@RequestMapping(value={"/articleparentedit", "/articlecreate"})
	public ModelAndView articleParentEdit(@ModelAttribute Article article){
		SecurityContext.assertUserHasPrivilege(Privilege.EDIT_ARTICLE);
		ModelAndView mv = new ModelAndView("articleparentedit");
	    return mv.addObject("article", article); 
	}	

	
	@RequestMapping("/articleparenteditsubmit")
	public ModelAndView articleParentEditSubmit(@Valid @ModelAttribute Article article, BindingResult result,
			@RequestParam("title")String title,  // Only useful for create 
			@RequestParam("parentid") Long parentId){
		
		SecurityContext.assertUserHasPrivilege(Privilege.EDIT_ARTICLE);
		
		if (result.hasErrors()){
			return new ModelAndView ("articleparentedit", "article", article);
		} 
		
		if (article.getId() == null) { // New article instance (not from DB) 
			article.setContent("Editez le contenu");
			articleRepository.persist(article);
			articleService.attachWithParent(article, parentId);
		} else {  // Edited article instance.
			articleRepository.merge(article);
			articleService.changeParent(article, parentId);
		}
		
		return new ModelAndView ("redirect:article", "id", article.getId());
	}


	 @ModelAttribute
	 public Article findArticle(@RequestParam(value="id",required=false) Long id){
	 	 if (id == null) { // create
	 		 return new Article();
	 	 } else { // edit
			 return getRequiredEntity(id);
	 	 }
	 }
	

	
}
