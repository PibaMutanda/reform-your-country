package reformyourcountry.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import reformyourcountry.model.Article;
import reformyourcountry.repository.ArticleRepository;

// Create and Edition of the parent (and title)
@Controller
public class ArticleParentEditController {

	@Autowired ArticleRepository articleRepository;
	@Autowired ArticleDisplayController displayArticleController;
	
	
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

		} else { // Edit

			articleRepository.merge(article);

			// Detach the article from its current parent
			if (article.getParent() != null) {
				Article parent = article.getParent();
				parent.getChildren().remove(article);
				article.setParent(null);
				articleRepository.merge(parent);
				articleRepository.merge(article);
			}
		}

		attachWithParent(article, parentId);

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
	
	 private void attachWithParent(Article article, Long parentId) {
		 if (parentId != null) { // We do not create article as a root article.
			 Article parent = articleRepository.find(parentId);
			 if (parent == null) {
				 throw new IllegalArgumentException("Invalid parentid (request hacking?)");
			 }
			 article.setParent(parent);
			 parent.getChildren().add(article);
			 articleRepository.merge(parent);
			 articleRepository.merge(article);
		 }

	 }
	
}
