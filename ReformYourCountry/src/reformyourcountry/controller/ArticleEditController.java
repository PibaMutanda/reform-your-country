package reformyourcountry.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import reformyourcountry.model.Article;
import reformyourcountry.repository.ArticleRepository;
import reformyourcountry.*;
@Controller
public class ArticleEditController {

	 @Autowired ArticleRepository articleRepository;
	 
	
	 @RequestMapping("/articleedit")
	 public ModelAndView articleEdit(@RequestParam("id")Long id){
		 ModelAndView mv = new ModelAndView("ArticleEdit");
		 mv.addObject("article",articleRepository.find(id));
		 return mv;
	 }
	 
	 @RequestMapping("/articleeditsubmit")
	 public ModelAndView articleEditSubmit(@RequestParam("id")Long id){
		 Article article = articleRepository.find(id);
		 articleRepository.merge(article);
		
		 List<Article> parentArticles = new ArrayList<Article>();
	        Article current;
	        current =  article;
	        while(current.getParent() != null){
	            parentArticles.add(current);
	            current = current.getParent();
	            
	        }
	        
	        ModelAndView mv = new ModelAndView("displayArticle");
	        mv.addObject("article",article);
	        mv.addObject("parentsTree",parentArticles);
	        
	        
	        return mv;
	 }
	 
}
