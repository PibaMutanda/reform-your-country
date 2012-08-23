package reformyourcountry.controller;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
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
       
       Calendar publishCalendar;
       ModelAndView mv = new ModelAndView("articledisplay");
       
   ///// Get publishDate in an usable int format
       if (article.getPublishDate()!=null) {
		Date publishDate = article.getPublishDate();
		publishCalendar = Calendar.getInstance();
		publishCalendar.setTime(publishDate);
		int year = publishCalendar.get(Calendar.YEAR);
		int month = publishCalendar.get(Calendar.MONTH);
		int day = publishCalendar.get(Calendar.DAY_OF_MONTH);
		mv.addObject("publishYear", year);
		mv.addObject("publishMonth", month);
		mv.addObject("publishDay", day);
	}
		
		
		mv.addObject("article", article);
		mv.addObject("parentsTree",parentArticles);
		BBConverter bbc = new BBConverter();
		mv.addObject("articleContent", bbc.transformBBCodeToHtmlCode(article.getContent()));
		return mv;
	}

}
