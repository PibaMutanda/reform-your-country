package reformyourcountry.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import reformyourcountry.misc.InvalidUrlException;
import reformyourcountry.model.Article;
import reformyourcountry.repository.ArticleRepository;
import reformyourcountry.service.ArticleService;

// Create and Edition of the parent (and title)
@Controller
public class ArticleParentEditController {

	@Autowired ArticleRepository articleRepository;
	@Autowired ArticleDisplayController displayArticleController;
	@Autowired ArticleService articleService;
	
	
	@RequestMapping("/articlecreate")
	public ModelAndView articleCreate(){
		return prepareModelAndView(null);
	}
	                  
	@RequestMapping("/articleparentedit")
	public ModelAndView articleParentEdit(@RequestParam("id") Long id){
		Article article=findArticle(id);
		return prepareModelAndView(article);
	}	

	
	private ModelAndView prepareModelAndView(Article article /* Null if create */ ) {
	    ModelAndView mv = new ModelAndView("articleparentedit");
	    if (article != null) {
	    	mv.addObject("article", article); 
	    }
		return mv;
	}
	

	@RequestMapping("/articleparenteditsubmit")
	public ModelAndView articleParentEditSubmit(@ModelAttribute Article article,
			@RequestParam("title")String title,  // Only useful for create 
			@RequestParam("parentid") Long parentId){
		if (article == null) { // Create
			article = new Article(title, "content to edit");
			articleRepository.persist(article);
			articleService.attachWithParent(article, parentId);

		} else { // Edit
			articleRepository.merge(article);  // article title may have been modified (by SpringMVC).
			articleService.changeParent(article, parentId);
		}

		return displayArticleController.displayArticle(article.getId());
	}


	 @ModelAttribute
	 public Article findArticle(@RequestParam("id") Long id){
	 	 if (id != null) { // edit
			 return articleRepository.find(id);
	 	 } else { // create
	 		 return null;
	 	 }
	 }
	

	
}
