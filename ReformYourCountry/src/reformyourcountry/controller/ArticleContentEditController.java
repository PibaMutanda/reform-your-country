package reformyourcountry.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import reformyourcountry.exception.InvalidUrlException;
import reformyourcountry.model.Article;
import reformyourcountry.repository.ArticleRepository;
import reformyourcountry.security.Privilege;
import reformyourcountry.security.SecurityContext;

@Controller
public class ArticleContentEditController extends BaseController<Article>{
    
    @Autowired ArticleRepository articleRepository;
    
    @RequestMapping("/articlecontentedit")
    public ModelAndView articleContentEdit(@ModelAttribute Article article)
    {
        SecurityContext.assertUserHasPrivilege(Privilege.EDIT_ARTICLE);
        ModelAndView mv = new ModelAndView("articlecontentedit");
        mv.addObject("article",article);
        return mv;
    }
    
    /** Auto save or ctrl + s */
    @RequestMapping(value = "/ajax/articlecontenteditsubmit", method = RequestMethod.POST)
    public ResponseEntity<?> articleContentEditSubmitAjax(@Valid @ModelAttribute Article article, BindingResult result){
        SecurityContext.assertUserHasPrivilege(Privilege.EDIT_ARTICLE);
     
        if(result.hasErrors()){
            return new ResponseEntity<BindingResult>(result,HttpStatus.OK);
        }else{
            articleRepository.merge(article);
            return new ResponseEntity<String>("sauvegarde",  // that tiny message will appear next to the save button and a savec hour.
                    HttpStatus.OK);
        }
    }
    
    /** Press the save button and close the page */
    @RequestMapping("/articlecontenteditsubmit")
    public ModelAndView articleContentEditSubmit(@Valid @ModelAttribute Article article, BindingResult result){
        SecurityContext.assertUserHasPrivilege(Privilege.EDIT_ARTICLE);
        
        if(result.hasErrors()){
            return new ModelAndView("articlecontentedit","id",article.getId());
        }else{
            articleRepository.merge(article);
            return new ModelAndView("redirect:article/"+article.getUrl());
        }

    }
    
    @ModelAttribute
    public Article findArticle(@RequestParam(value="id",required=false)Long id){
        if(id==null){
            throw new InvalidUrlException("You can't create an article with this page");
        } else {
            return getRequiredDetachedEntity(id);
        }
    }

}
