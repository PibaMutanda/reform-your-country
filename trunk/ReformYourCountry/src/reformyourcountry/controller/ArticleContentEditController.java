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
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;

import reformyourcountry.exception.InvalidUrlException;
import reformyourcountry.model.Article;
import reformyourcountry.model.ArticleVersion;
import reformyourcountry.model.Book;
import reformyourcountry.repository.ArticleRepository;
import reformyourcountry.repository.ArticleVersionRepository;
import reformyourcountry.security.Privilege;
import reformyourcountry.security.SecurityContext;
import reformyourcountry.service.ArticleService;
import reformyourcountry.service.IndexManagerService;

@Controller
public class ArticleContentEditController extends BaseController<Article>{
    
    @Autowired ArticleRepository articleRepository;
    @Autowired ArticleVersionRepository articleVersionRepository;
    @Autowired ArticleService articleService;
    
    @RequestMapping("/article/contentedit")
    public ModelAndView articleContentEdit(@RequestParam("id")Long id) {
        SecurityContext.assertUserHasPrivilege(Privilege.EDIT_ARTICLE);
        ModelAndView mv = new ModelAndView("articlecontentedit");
        Article article = getRequiredEntity(id);
        mv.addObject("article", article);
        mv.addObject("parentsPath", article.getPath()); // For the breadcrumb
        return mv;
    }
    
    /** Auto save or ctrl + s */
    @RequestMapping(value = "/ajax/article/contenteditsubmit", method = RequestMethod.POST)
    public ResponseEntity<?> articleContentEditSubmitAjax(@RequestParam(value="content",required=false)String content,
                                                          @RequestParam(value="summary",required=false)String summary,
                                                          @RequestParam(value="toClassify",required=false)String toClassify,
                                                          @RequestParam(value="id") Long id){
        SecurityContext.assertUserHasPrivilege(Privilege.EDIT_ARTICLE);
        
        articleService.saveArticle(getRequiredEntity(id), content, summary, toClassify);
        return new ResponseEntity<String>("sauvegarde",  // that tiny message will appear next to the save button and a save hour.
                                          HttpStatus.OK);
    }
    
    /** Press the save button and close the page */

	@RequestMapping("/article/editsubmitcontent")
    public ModelAndView articleContentEditSubmit(@RequestParam(value="content")String content,
                                                 @RequestParam(value="id") Long id){
        
    	SecurityContext.assertUserHasPrivilege(Privilege.EDIT_ARTICLE);
    	content = content == null ? "" : content;
    	
    	
        articleService.saveArticle(getRequiredEntity(id), content, null, null);
        return new ModelAndView("redirect:"+getRequiredEntity(id).getUrl());
    }
    
	@RequestMapping("/article/editsubmitsummary")
    public ModelAndView articleSummaryEditSubmit(@RequestParam(value="summary")String summary,
                                                 @RequestParam(value="id") Long id){
        
    	SecurityContext.assertUserHasPrivilege(Privilege.EDIT_ARTICLE);
    	summary = summary == null ? "" : summary;
    	
        articleService.saveArticle(getRequiredEntity(id), null, summary, null);
        return new ModelAndView("redirect:"+getRequiredEntity(id).getUrl());
    }
	
	@RequestMapping("/article/editsubmittoclassify")
    public ModelAndView articleToClassifyEditSubmit(@RequestParam(value="toClassify")String toClassify,
                                                 	@RequestParam(value="id") Long id){
        
    	SecurityContext.assertUserHasPrivilege(Privilege.EDIT_ARTICLE);
    	toClassify = toClassify == null ? "" : toClassify;
    	
                
        articleService.saveArticle(getRequiredEntity(id), null, null, toClassify);
        return new ModelAndView("redirect:"+getRequiredEntity(id).getUrl());
    }
	
}
