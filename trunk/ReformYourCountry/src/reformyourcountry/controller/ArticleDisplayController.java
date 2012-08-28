package reformyourcountry.controller;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import reformyourcountry.converter.BBConverter;
import reformyourcountry.model.Article;
import reformyourcountry.security.Privilege;
import reformyourcountry.security.SecurityContext;

@Controller
public class ArticleDisplayController extends BaseController<Article> {

	@RequestMapping(value={"/article"})
	public ModelAndView displayArticle(
			@RequestParam(value="id") Long id){

		ModelAndView mv = new ModelAndView("articledisplay");

		Article article = getRequiredEntity(id);
		mv.addObject("article", article);

		// For the breadcrumb
		List<Article> parentArticles = new ArrayList<Article>();
		Article current =  article;
		while(current.getParent() != null){
			parentArticles.add(current);
			current = current.getParent();

		}
		Collections.reverse(parentArticles);
		mv.addObject("parentsPath",parentArticles);

		///// Get publishDate in an usable int format for the coundown
		if (article.getPublishDate()!=null) {
			Calendar publishCalendar;
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

		boolean showContent = (article.isPublished() || SecurityContext.isUserHasPrivilege(Privilege.EDIT_ARTICLE));
		mv.addObject("showContent", showContent);
		BBConverter bbc = new BBConverter();
		mv.addObject("articleContent", bbc.transformBBCodeToHtmlCode(article.getContent()));
		
		return mv;
	}

}
