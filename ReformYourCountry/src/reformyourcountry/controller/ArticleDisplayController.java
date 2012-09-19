package reformyourcountry.controller;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import reformyourcountry.converter.BBConverter;
import reformyourcountry.model.Article;
import reformyourcountry.repository.ArticleRepository;
import reformyourcountry.repository.BookRepository;
import reformyourcountry.security.Privilege;
import reformyourcountry.security.SecurityContext;

@Controller
public class ArticleDisplayController extends BaseController<Article> {
    
    @Autowired BookRepository bookRepository;
    @Autowired ArticleRepository articleRepository;
    
	@RequestMapping(value={"/article/{articleUrl}"})
	public ModelAndView displayArticle( @PathVariable("articleUrl") String articleUrl){

		ModelAndView mv = new ModelAndView("articledisplay");

		Article article = articleRepository.findArticleByUrl(articleUrl);
        mv.addObject("article", article);

        // For the breadcrumb
        List<Article> parentArticles = new ArrayList<Article>();
        Article current =  article;
        while((current = current.getParent()) != null){
            if(!current.getChildren().isEmpty())
            parentArticles.add(current);
            

        }
        Collections.reverse(parentArticles);
        mv.addObject("parentsPath",parentArticles);


        ///// Get publishDate in an usable int format for the countdown
        if (article.getPublishDate() != null) {
            Calendar publishCalendar = Calendar.getInstance();
            publishCalendar.setTime(article.getPublishDate());
            int year = publishCalendar.get(Calendar.YEAR);
            int month = publishCalendar.get(Calendar.MONTH);
            int day = publishCalendar.get(Calendar.DAY_OF_MONTH);
            mv.addObject("publishYear", year);
            mv.addObject("publishMonth", month);
            mv.addObject("publishDay", day);
        }

        mv.addObject("showContent", (article.isPublished() || SecurityContext.isUserHasPrivilege(Privilege.EDIT_ARTICLE)));
        BBConverter bbc = new BBConverter(bookRepository, articleRepository);
        mv.addObject("articleContent", bbc.transformBBCodeToHtmlCode(article.getContent()));
        
        return mv;
	}
}
