package reformyourcountry.controller;


import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;



import reformyourcountry.model.Article;
import reformyourcountry.repository.ArticleRepository;
import reformyourcountry.security.Privilege;
import reformyourcountry.security.SecurityContext;


@Controller
public class ArticleEditController extends BaseController<Article>{

	@Autowired ArticleRepository articleRepository;
	@Autowired ArticleDisplayController displayArticleController;


	@RequestMapping(value={"/articleedit","/articlecreate"})
	public ModelAndView articleEdit(@ModelAttribute Article article){
		SecurityContext.assertUserHasPrivilege(Privilege.EDIT_ARTICLE);
		ModelAndView mv = new ModelAndView("articleedit");
		mv.addObject("article",article);
		return mv;
	}


	@RequestMapping("/articleeditsubmit")
	public ModelAndView articleEditSubmit(@Valid @ModelAttribute Article article, BindingResult result){
		SecurityContext.assertUserHasPrivilege(Privilege.EDIT_ARTICLE);
		
		if(result.hasErrors()){
		    return new ModelAndView("redirect:articleedit","id",article.getId());
		}else{
			articleRepository.merge(article);
			return new ModelAndView("redirect:article/"+article.getUrl());
		}

	}

	
	@RequestMapping("/ajax/articleeditsubmit")
    public ResponseEntity<?> articleEditSubmitAjax(@Valid @ModelAttribute Article article, BindingResult result){
        SecurityContext.assertUserHasPrivilege(Privilege.EDIT_ARTICLE);
     
        if(result.hasErrors()){
            System.out.println(result.getAllErrors());
            return new ResponseEntity<BindingResult>(result,HttpStatus.OK);
            
           
        }else{
          
            articleRepository.merge(article);
            return new ResponseEntity<String>("sauvegarde",HttpStatus.OK);
        }

    }
	

	@ModelAttribute
	public Article findArticle(@RequestParam("id")Long id){
		if(id==null){
			return new Article();
		} else {
			return getRequiredEntity(id);
		}
	}

}
