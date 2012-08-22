package reformyourcountry.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import reformyourcountry.converter.BBConverter;
import reformyourcountry.model.Article;

@Controller
public class ArticleDisplayController extends BaseController<Article> {

	@RequestMapping(value={"/article"})
	public ModelAndView displayArticle(
			@RequestParam(value="id", required=true) long id){

		Article article = getRequiredEntity(id);
		
		
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

		ModelAndView mv = new ModelAndView("articledisplay");
		mv.addObject("article", article);
		mv.addObject("parentsTree",parentArticles);
		BBConverter bbc = new BBConverter();
		mv.addObject("articleContent", bbc.transformBBCodeToHtmlCode(article.getContent()));
		return mv;
	}

}
