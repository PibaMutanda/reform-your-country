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
    
    @RequestMapping("/article/contentedit")
    public ModelAndView articleContentEdit(@ModelAttribute Article article)
    {
        SecurityContext.assertUserHasPrivilege(Privilege.EDIT_ARTICLE);
        ModelAndView mv = new ModelAndView("articlecontentedit");
        mv.addObject("article",article);
        return mv;
    }
    
    /** Auto save or ctrl + s */
    @RequestMapping(value = "/ajax/article/contenteditsubmit", method = RequestMethod.POST)
    public ResponseEntity<?> articleContentEditSubmitAjax(@RequestParam(value="content",required=false)String content,
    													  @RequestParam(value="summary",required=false)String summary,
    													  @RequestParam(value="toClassify",required=false)String toClassify,
    													  @RequestParam(value="id")Long id){
    	SecurityContext.assertUserHasPrivilege(Privilege.EDIT_ARTICLE);
    	Article article = getRequiredEntity(id);
    	if ((!content.equals(null))&& !content.equals(article.getContent())) article.setContent(content);
    	if ((!summary.equals(null))&& !summary.equals(article.getSummary())) article.setSummary(summary);
    	if ((!toClassify.equals(null))&& !toClassify.equals(article.getToClassify())) article.setToClassify(toClassify);        	
    	articleRepository.merge(article);
    	return new ResponseEntity<String>("sauvegarde",  // that tiny message will appear next to the save button and a save hour.
    			HttpStatus.OK);
    }
    
    /** Press the save button and close the page */
    @RequestMapping("/article/contenteditsubmit")
    public ModelAndView articleContentEditSubmit(@RequestParam(value="content",required=false)String content,
    											 @RequestParam(value="summary",required=false)String summary,
    											 @RequestParam(value="toClassify",required=false)String toClassify,
    											 @RequestParam(value="id")Long id){
    	SecurityContext.assertUserHasPrivilege(Privilege.EDIT_ARTICLE);
    	Article article = getRequiredEntity(id);
    	if (content!=null && (!content.equals(""))&& !content.equals(article.getContent())) article.setContent(content);
    	if (summary!=null && (!summary.equals(""))&& !summary.equals(article.getSummary())) article.setSummary(summary);
    	if (summary!=null && (!toClassify.equals(""))&& !toClassify.equals(article.getToClassify())) article.setToClassify(toClassify);        	
    	articleRepository.merge(article);
    	return new ModelAndView("redirect:"+article.getUrl());

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
