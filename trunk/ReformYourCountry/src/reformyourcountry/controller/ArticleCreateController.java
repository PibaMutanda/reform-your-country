package reformyourcountry.controller;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.jsp.JspWriter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.servlet.ModelAndView;

import reformyourcountry.model.Article;
import reformyourcountry.repository.ArticleRepository;
import reformyourcountry.web.ContextUtil;
import sun.org.mozilla.javascript.internal.Context;

@Controller
public class ArticleCreateController {

	@Autowired ArticleRepository articleRepository;
    @Autowired DisplayArticleController displayArticleController;
    
	@RequestMapping("/articlecreate")
	public ModelAndView ArticleCreate(){
	    List<Article> listArticles=articleRepository.findAllArticles();
	    ModelAndView mv = new ModelAndView("ArticleCreate");
	    mv.addObject("listArticles", listArticles);
		return mv;
	}
	
	@RequestMapping("/articlecreatesubmit")
	 public ModelAndView articleCreateSubmit(@ModelAttribute Article article){
		 articleRepository.persist(article);
		 return displayArticleController.displayArticle(article.getId());
	 }
	/*@RequestMapping("/articlechildrendisplay")
	public void articleChildrenDisplay() throws IOException{
		List<Article> listArticles=articleRepository.findAllArticles();
		
		for(Article article: listArticles){
			System.out.println("<input type='RADIO' value='"+article.getTitle()+"'>");
			if(!article.getChildren().isEmpty()){
				for(Article children:article.getChildren()){
					System.out.println("--><input type='RADIO' value='"+children.getTitle()+"'>");
				}
			}
			
							   
		}
	}*/
}
