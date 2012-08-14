package reformyourcountry.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;



import reformyourcountry.model.Article;
import reformyourcountry.repository.ArticleRepository;

@Controller
public class ArticleEditController {

	 @Autowired ArticleRepository articleRepository;
	 @Autowired DisplayArticleController displayArticleController;

	
	 @RequestMapping("/articleedit")
	 public ModelAndView articleEdit(@ModelAttribute Article article){
		
		 ModelAndView mv = new ModelAndView("ArticleEdit");
		 mv.addObject("article",article);
		 return mv;
	 }
	 
	 @RequestMapping("/articleeditsubmit")
	 public ModelAndView articleEditSubmit(@ModelAttribute Article article){
		 articleRepository.merge(article);
		 return displayArticleController.displayArticle(article.getId());
	 }
	 
		 
	 @ModelAttribute
	 public Article findArticle(@RequestParam("id")Long id){
		 Article result=articleRepository.find(id);
		 return result;
	 }
	 
}
