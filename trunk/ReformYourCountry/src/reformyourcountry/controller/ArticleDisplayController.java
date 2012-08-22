package reformyourcountry.controller;

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

		ModelAndView mv = new ModelAndView("articledisplay");
		mv.addObject("article", article);
		BBConverter bbc = new BBConverter();
		mv.addObject("articleContent", bbc.transformBBCodeToHtmlCode(article.getContent()));
		return mv;
	}

}
