package reformyourcountry.controller;


import javax.validation.Valid;
import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;



import reformyourcountry.misc.DateUtil;
import reformyourcountry.model.Article;
import reformyourcountry.repository.ArticleRepository;
import reformyourcountry.security.Privilege;
import reformyourcountry.security.SecurityContext;

@Controller
public class ArticleEditController {

	@Autowired ArticleRepository articleRepository;
	@Autowired ArticleDisplayController displayArticleController;


	@RequestMapping("/articleedit")
	public ModelAndView articleEdit(@ModelAttribute Article article){

		SecurityContext.assertUserHasPrivilege(Privilege.EDIT_ARTICLE);
		ModelAndView mv = new ModelAndView("articleedit");
		mv.addObject("article",article);
		return mv;

	}


	@RequestMapping("/articleeditsubmit")
	public ModelAndView articleEditSubmit(@Valid @ModelAttribute Article article, BindingResult result,
			@RequestParam("publishDate") String publishDate){
		SecurityContext.assertUserHasPrivilege(Privilege.EDIT_ARTICLE);
		
		if(result.hasErrors()){
			
		    return new ModelAndView("redirect:articleedit","id",article.getId());
		}else{
			if (publishDate.length()!=0) { //KESAKO
				Date date = DateUtil.parseyyyyMMdd(publishDate);
				article.setPublishDate(date);
			}
			articleRepository.merge(article);
			return new ModelAndView("redirect:article", "id", article.getId());//NOOOOOO
		}

	}


	@ModelAttribute
	public Article findArticle(@RequestParam("id")Long id){
		Article result=articleRepository.find(id);
		return result;

	}

}
