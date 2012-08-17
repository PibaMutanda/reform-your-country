package reformyourcountry.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import reformyourcountry.model.Article;
import reformyourcountry.repository.ArticleRepository;

@Controller
public class ArticleCreateController {

	@Autowired ArticleRepository articleRepository;
    @Autowired DisplayArticleController displayArticleController;
    
	@RequestMapping("/articlecreate")
	public ModelAndView ArticleCreate(){
	    List<Article> listArticles=articleRepository.findAllWithoutParent();
	    ModelAndView mv = new ModelAndView("ArticleCreate");
	    mv.addObject("listArticles", listArticles);
		return mv;
	}
	
	@RequestMapping("/articlecreatesubmit")
	 public ModelAndView articleCreateSubmit(@ModelAttribute Article article){
		 articleRepository.persist(article);
		 return displayArticleController.displayArticle(article.getId());
	 }
	
}
