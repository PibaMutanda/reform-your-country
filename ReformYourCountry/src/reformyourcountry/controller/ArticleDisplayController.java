package reformyourcountry.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import reformyourcountry.converter.BBConverter;
import reformyourcountry.misc.InvalidUrlException;
import reformyourcountry.model.Article;
import reformyourcountry.repository.ArticleRepository;

@Controller
public class ArticleDisplayController {


	@Autowired ArticleRepository articleRepository;

	@RequestMapping(value={"/article"})
	public ModelAndView displayArticle(
			@RequestParam(value="id", required=true) long id){

		Article article = articleRepository.find(id);
		if(article == null) {
			throw new InvalidUrlException("No artricle with this id found in the DB. id = " + id);
		}

		ModelAndView mv = new ModelAndView("articledisplay");
		mv.addObject("article", article);
		BBConverter bbc = new BBConverter();
		mv.addObject("articleContent", bbc.transformBBCodeToHtmlCode(article.getContent()));
		return mv;
	}

}
