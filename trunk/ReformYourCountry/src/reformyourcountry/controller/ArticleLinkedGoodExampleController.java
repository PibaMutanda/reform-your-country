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
import reformyourcountry.model.User.Role;
import reformyourcountry.repository.ArticleRepository;
import reformyourcountry.repository.GoodExampleRepository;
import reformyourcountry.security.Privilege;
import reformyourcountry.security.SecurityContext;

@Controller
public class ArticleLinkedGoodExampleController extends BaseController<GoodExample>{
	
	@Autowired ArticleRepository articleRepository;
	@Autowired GoodExampleRepository goodExampleRepository;

	@RequestMapping("/articlelinkedgoodexample")
	public ModelAndView articleLinkedGoodExample(@RequestParam("id") Long id){
		SecurityContext.assertUserHasPrivilege(Privilege.EDIT_GOODEXAMPLE);
		ModelAndView mv = new ModelAndView("articleslinkedtogoodexample");
		GoodExample goodExample = goodExampleRepository.find(id);
		List<Article> articleList = goodExample.getArticles();	
		mv.addObject("articleList", articleList);
		mv.addObject("goodExample", goodExample);
		return mv;
	}

	@RequestMapping("/articlelinkedgoodexamplesubmit")
	public ModelAndView articleLinkedGoodExampleSubmit(@RequestParam ("id") Long goodExampleId,
			@RequestParam(value="selectedarticleid", required=false)Long[] articleId){

		SecurityContext.assertUserHasPrivilege(Privilege.EDIT_GOODEXAMPLE);
		GoodExample goodExample = this.getRequiredEntity(goodExampleId);
		
		/// Unbind that goodExample from all articles.
		for(Article a : goodExample.getArticles()) {
			a.getGoodExamples().remove(goodExample);
			articleRepository.merge(a);
		}
		goodExample.getArticles().clear();
		
		/// Bind the good example to the necessary articles.
		if(articleId != null){
			for(Long id : articleId){
				Article a = articleRepository.find(id); 
				goodExample.addArticle(a);
				a.addGoodExample(goodExample);
				articleRepository.merge(a);
			}
		}
		goodExampleRepository.merge(goodExample);
		
		return new ModelAndView("redirect:/goodexamplelist");
	}
}
