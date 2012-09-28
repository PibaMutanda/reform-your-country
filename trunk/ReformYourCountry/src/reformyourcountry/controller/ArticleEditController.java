package reformyourcountry.controller;


import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;



import reformyourcountry.model.Article;
import reformyourcountry.repository.ArticleRepository;
import reformyourcountry.security.Privilege;
import reformyourcountry.security.SecurityContext;


@Controller
@RequestMapping(value={"/article"})
public class ArticleEditController extends BaseController<Article>{

	@Autowired ArticleRepository articleRepository;
	@Autowired ArticleDisplayController displayArticleController;


	@RequestMapping(value={"/edit","/create"})
	public ModelAndView articleEdit(@ModelAttribute Article article){
		SecurityContext.assertUserHasPrivilege(Privilege.EDIT_ARTICLE);
		ModelAndView mv = new ModelAndView("articleedit");
		mv.addObject("article",article);
		return mv;
	}

	@RequestMapping("/editsubmit")
	public ModelAndView articleEditSubmit(@Valid @ModelAttribute Article article, BindingResult result){
		SecurityContext.assertUserHasPrivilege(Privilege.EDIT_ARTICLE);
		if(result.hasErrors()){
			ModelAndView mv = new ModelAndView("redirect:/article/edit","id",article.getId());
			for (ObjectError error : result.getAllErrors()) {
				setMessage(mv, error.getDefaultMessage());
			}
			return mv;
		}else if(!getRequiredEntityByUrl(article.getUrl()).equals(article)){
			ModelAndView mv = new ModelAndView("redirect:/article/edit","id",article.getId());
			setMessage(mv, "L'url est déja utilisée par un autre article");
			return mv;
		}else{
			articleRepository.merge(article);
			return new ModelAndView("redirect:/article/"+article.getUrl());
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
	public Article findArticle(@RequestParam(value="id",required=false)Long id){
		if(id==null){
			return new Article();
		} else {
			return getRequiredDetachedEntity(id);
		}
	}

}
