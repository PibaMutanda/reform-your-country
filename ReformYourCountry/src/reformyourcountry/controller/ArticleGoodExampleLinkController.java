package reformyourcountry.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import reformyourcountry.model.Action;
import reformyourcountry.model.Article;
import reformyourcountry.model.GoodExample;
import reformyourcountry.repository.ArticleRepository;
import reformyourcountry.repository.GoodExampleRepository;

@Controller
public class ArticleGoodExampleLinkController {
	
	@Autowired ArticleRepository articleRepository;
	@Autowired GoodExampleRepository goodExampleRepository;
	
	@RequestMapping("/articlegoodexamplelinkedit")
	public ModelAndView editGoodExample(@RequestParam ("id") Long articleId){
		Article article = articleRepository.find(articleId);
		List<GoodExample> goodExamplesList = goodExampleRepository.findAll();
		ModelAndView mv = new ModelAndView ("articlegoodexamplelink");
		mv.addObject("goodExamplesList", goodExamplesList);
		mv.addObject("article", article);
		return mv;
	}
	
	@RequestMapping("/articlegoodexamplelinkeditsubmit")
	public ModelAndView editSubmitGoodExample(@RequestParam ("id") Long articleId,
		@RequestParam(value="goodexample", required=false)Long[] goodexampleIds){
		Article article = articleRepository.find(articleId);
		article.getGoodExamples().clear();
		if(goodexampleIds != null){
			for(Long id : goodexampleIds){
				GoodExample ge = goodExampleRepository.find(id);
				article.addGoodExample(ge);
				ge.addArticle(article);
				goodExampleRepository.merge(ge);
			}
		}
		articleRepository.merge(article);
		return new ModelAndView("redirect:/article/"+article.getUrl());
		
	}


}
