package reformyourcountry.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import reformyourcountry.model.Article;
import reformyourcountry.repository.ArticleRepository;
import reformyourcountry.repository.ArticleVersionRepository;
import reformyourcountry.security.Privilege;
import reformyourcountry.security.SecurityContext;
import reformyourcountry.service.ArticleService;

@Controller
public class ArticleContentEditController extends BaseController<Article>{
    
    @Autowired ArticleRepository articleRepository;
    @Autowired ArticleVersionRepository articleVersionRepository;
    @Autowired ArticleService articleService;
    
    @RequestMapping(value={"/article/contentedit"})
    public ModelAndView articleContentEdit(@RequestParam("id")Long id) {
        SecurityContext.assertUserHasPrivilege(Privilege.MANAGE_ARTICLE);
        ModelAndView mv = new ModelAndView("articlecontentedit");
        Article article = getRequiredEntity(id);
        mv.addObject("article", article);
        mv.addObject("pageName", "édition du contenu");
        mv.addObject("submitUrl", "contenteditsubmit");
        mv.addObject("thingToEdit", article.getLastVersion().getContent());
        mv.addObject("parentsPath", article.getPath()); // For the breadcrumb
        return mv;
    }
    
    @RequestMapping(value={"/article/summaryedit"})
    public ModelAndView articleSummaryEdit(@RequestParam("id")Long id) {
        SecurityContext.assertUserHasPrivilege(Privilege.MANAGE_ARTICLE);
        ModelAndView mv = new ModelAndView("articlecontentedit");
        Article article = getRequiredEntity(id);
        mv.addObject("article", article);
        mv.addObject("pageName", "édition du résumé");
        mv.addObject("submitUrl", "editsubmitsummary");
        mv.addObject("thingToEdit", article.getLastVersion().getSummary());
        mv.addObject("parentsPath", article.getPath()); // For the breadcrumb
        return mv;
    }
    
    @RequestMapping(value={"/article/toclassifyedit"})
    public ModelAndView articleToClassifyEdit(@RequestParam("id")Long id) {
        SecurityContext.assertUserHasPrivilege(Privilege.MANAGE_ARTICLE);
        ModelAndView mv = new ModelAndView("articlecontentedit");
        Article article = getRequiredEntity(id);
        mv.addObject("article", article);
        mv.addObject("pageName", "édition du contenu à classer");
        mv.addObject("submitUrl", "editsubmittoclassify");
        mv.addObject("thingToEdit", article.getLastVersion().getToClassify());
        return mv;
    }
    
    /** Auto save or ctrl + s */
    @RequestMapping(value = "/ajax/article/contenteditsubmit", method = RequestMethod.POST)
    public ResponseEntity<?> articleContentEditSubmitAjax(@RequestParam(value="content",required=false)String content,
                                                          @RequestParam(value="summary",required=false)String summary,
                                                          @RequestParam(value="toClassify",required=false)String toClassify,
                                                          @RequestParam(value="id") Long id){
        SecurityContext.assertUserHasPrivilege(Privilege.MANAGE_ARTICLE);
        
        articleService.saveArticle(getRequiredEntity(id), content, summary, toClassify);
        return new ResponseEntity<String>("sauvegarde",  // that tiny message will appear next to the save button and a save hour.
                                          HttpStatus.OK);
    }
    
    /** Press the save button and close the page */

	@RequestMapping("/article/contenteditsubmit")
    public ModelAndView articleContentEditSubmit(@RequestParam(value="value")String content,
                                                 @RequestParam(value="id") Long id){
        
    	SecurityContext.assertUserHasPrivilege(Privilege.MANAGE_ARTICLE);
    	content = content == null ? "" : content;
    	
    	
        articleService.saveArticle(getRequiredEntity(id), content, null, null);
        return new ModelAndView("redirect:"+getRequiredEntity(id).getUrl());
    }
    
	@RequestMapping("/article/editsubmitsummary")
    public ModelAndView articleSummaryEditSubmit(@RequestParam(value="value")String summary,
                                                 @RequestParam(value="id") Long id){
        
    	SecurityContext.assertUserHasPrivilege(Privilege.MANAGE_ARTICLE);
    	summary = summary == null ? "" : summary;
    	
        articleService.saveArticle(getRequiredEntity(id), null, summary, null);
        return new ModelAndView("redirect:"+getRequiredEntity(id).getUrl());
    }
	
	@RequestMapping("/article/editsubmittoclassify")
    public ModelAndView articleToClassifyEditSubmit(@RequestParam(value="value")String toClassify,
                                                 	@RequestParam(value="id") Long id){
        
    	SecurityContext.assertUserHasPrivilege(Privilege.MANAGE_ARTICLE);
    	toClassify = toClassify == null ? "" : toClassify;
    	
                
        articleService.saveArticle(getRequiredEntity(id), null, null, toClassify);
        return new ModelAndView("redirect:"+getRequiredEntity(id).getUrl());
    }
	
	@RequestMapping("/ajax/article/editsubmittoclassify")
    public ResponseEntity<?> articleToClassifyEditSubmitAjax(@RequestParam(value="value")String toClassify,
                                                        @RequestParam(value="id") Long id){
        
        SecurityContext.assertUserHasPrivilege(Privilege.MANAGE_ARTICLE);
        toClassify = toClassify == null ? "" : toClassify;
        
                
        articleService.saveArticle(getRequiredEntity(id), null, null, toClassify);
        return new ResponseEntity<String>("sauvegarde",  // that tiny message will appear next to the save button and a save hour.
                HttpStatus.OK);
    }
	
}
