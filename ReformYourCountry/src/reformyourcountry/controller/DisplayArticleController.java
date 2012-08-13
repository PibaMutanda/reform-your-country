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

@Controller
public class DisplayArticleController {
    
    
    @Autowired ArticleRepository articleRepository;
    
    @RequestMapping(value={"Display"})
    public ModelAndView displayArticle(@RequestParam("idArticle") long id){
        
        Article article = articleRepository.find(id);
        //construct the list to display the article parent 's tree
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
