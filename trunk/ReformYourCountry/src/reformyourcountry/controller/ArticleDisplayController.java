package reformyourcountry.controller;

import java.util.Calendar;
import java.util.Date;

import org.apache.commons.logging.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import reformyourcountry.converter.BBConverter;
import reformyourcountry.model.Article;
import reformyourcountry.repository.ArticleRepository;
import reformyourcountry.repository.BookRepository;
import reformyourcountry.repository.VideoRepository;
import reformyourcountry.security.Privilege;
import reformyourcountry.security.SecurityContext;
import reformyourcountry.util.DateUtil;
import reformyourcountry.util.Logger;

@Controller
@RequestMapping(value={"/article"})
public class ArticleDisplayController extends BaseController<Article> {
    
    @Logger Log log;
    
    @Autowired BookRepository bookRepository;
    @Autowired ArticleRepository articleRepository;
    @Autowired VideoRepository videoRepository;
    
	@RequestMapping(value={"/{articleUrl}"})
	public ModelAndView displayArticle( @PathVariable("articleUrl") String articleUrl){
		
	    log.debug("display article i get articleUrl"+articleUrl);
	    
		ModelAndView mv = new ModelAndView("articledisplay");

		Article article = getRequiredEntityByUrl(articleUrl);
		
		log.debug("i found "+article.getTitle());
		log.debug("his content"+article.getLastVersion().getContent());
		
        mv.addObject("article", article);

		// For the breadcrumb
        mv.addObject("parentsPath", article.getParentPath());


        ///// Get publishDate in an usable int format for the countdown
        if (article.getPublishDate() != null && article.getPublishDate().after(new Date())){
            Calendar publishCalendar = Calendar.getInstance();
            publishCalendar.setTime(article.getPublishDate());
            int year = publishCalendar.get(Calendar.YEAR);
            int month = publishCalendar.get(Calendar.MONTH);
            int day = publishCalendar.get(Calendar.DAY_OF_MONTH);
            mv.addObject("publishYear", year);
            mv.addObject("publishMonth", month);
            mv.addObject("publishDay", day);
            mv.addObject("displayDate", DateUtil.formatyyyyMMdd(article.getPublishDate()));
        }
        
        mv.addObject("showContent", (article.isPublished() || SecurityContext.isUserHasPrivilege(Privilege.EDIT_ARTICLE)));
        mv.addObject("articleContent", new BBConverter(bookRepository, articleRepository).transformBBCodeToHtmlCode(article.getLastVersion().getContent()));
        mv.addObject("articleSummary", new BBConverter(bookRepository, articleRepository).transformBBCodeToHtmlCode(article.getLastVersion().getSummary()));
        mv.addObject("videoList",videoRepository.findAllVideoForAnArticle(article));
        return mv;
	}



	@RequestMapping(value={"/a_classer/{articleUrl}"})
	public ModelAndView displayToClassify( @PathVariable("articleUrl") String articleUrl){
		SecurityContext.assertUserHasPrivilege(Privilege.EDIT_ARTICLE);
		ModelAndView mv = new ModelAndView("articledisplaytoclassify");

		Article article = getRequiredEntityByUrl(articleUrl);
		mv.addObject("article", article);
		
		// For the breadcrumb
        mv.addObject("parentsPath", article.getParentPath());
        
        mv.addObject("showContent", (article.isPublished() || SecurityContext.isUserHasPrivilege(Privilege.EDIT_ARTICLE)));
        BBConverter bbc = new BBConverter(bookRepository, articleRepository);
        mv.addObject("articleToClassify", bbc.transformBBCodeToHtmlCode(article.getLastVersion().getToClassify()));
        return mv;
	}
}
