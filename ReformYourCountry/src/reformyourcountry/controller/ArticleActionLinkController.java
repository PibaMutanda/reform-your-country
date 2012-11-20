package reformyourcountry.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import reformyourcountry.model.Action;
import reformyourcountry.model.Article;
import reformyourcountry.repository.ActionRepository;
import reformyourcountry.repository.ArticleRepository;

@Controller
public class ArticleActionLinkController extends BaseController<Action>{
	
	@Autowired ArticleRepository articleRepository;
	@Autowired ActionRepository actionRepository;
	
	@RequestMapping("/editaction")
	public ModelAndView editAction(@RequestParam ("id") Long articleId){
		Article article = articleRepository.find(articleId);
		List <Action> actionList = actionRepository.findAll();
		ModelAndView mv = new ModelAndView ("articleactionlink");
		mv.addObject("actionList", actionList);
		mv.addObject("article", article);
		return mv;
	}
	
	@RequestMapping("/editactionsubmit")
	public ModelAndView editSubmitAction(@RequestParam ("id") Long articleId,
			@RequestParam(value="action", required=false)Long[] actionIds){
		Article article = articleRepository.find(articleId);
		article.getActions().clear();
		for(Long id : actionIds){
			Action a =  actionRepository.find(id);
			article.getActions().add(a);
		}
		articleRepository.merge(article);
		return new ModelAndView("redirect:/article/"+article.getTitle()); 
	}
	

}
