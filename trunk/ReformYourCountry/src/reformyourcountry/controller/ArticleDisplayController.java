package reformyourcountry.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import reformyourcountry.converter.BBConverter;
import reformyourcountry.model.Article;
import reformyourcountry.repository.ArticleRepository;

@Controller
public class ArticleDisplayController {
    
    
    @Autowired ArticleRepository articleRepository;
    
    @RequestMapping(value={"/article"})
    public ModelAndView displayArticle(@RequestParam("id") long id){
        
        Article article = articleRepository.find(id);
        if(article != null){
        //construct the list to display the article parent 's tree
        List<Article> parentArticles = new ArrayList<Article>();
        Article current;
        current =  article;
      
        while(current.getParent() != null){
            parentArticles.add(current);
            current = current.getParent();
           
        }
        List<Article> parentArticlesReverse = new ArrayList<Article>();
       for(int i =parentArticles.size()-1; i>0 ;i--){
           
           parentArticlesReverse.add(parentArticles.get(i));
           
           
       }
       
       BBConverter bbc = new BBConverter(); 
       
        
        ModelAndView mv = new ModelAndView("articledisplay");
        mv.addObject("articleTitle",article.getTitle());
        mv.addObject("releaseDate",article.getReleaseDate());
        mv.addObject("articleContent",bbc.transformBBCodeToHtmlCode(article.getContent()));
        mv.addObject("parentsTree",parentArticlesReverse);
        mv.addObject("id",article.getId());
        return mv;
        }
        return null;
        
       
    }
    
    
   

}
