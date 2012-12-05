package reformyourcountry.controller;

import java.util.List;

import org.apache.commons.logging.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import reformyourcountry.exception.InvalidUrlException;
import reformyourcountry.model.Article;
import reformyourcountry.model.GoodExample;
import reformyourcountry.repository.ArticleRepository;
import reformyourcountry.repository.GoodExampleRepository;
import reformyourcountry.util.HTMLUtil;
import reformyourcountry.util.Logger;
import reformyourcountry.util.NotificationUtil;
import reformyourcountry.web.PropertyLoaderServletContextListener;

@Controller
public class GoodExampleController extends BaseController<GoodExample>{
    @Logger Log log;
    @Autowired
    GoodExampleRepository goodExampleRepository;
    @Autowired
    ArticleRepository articleRepository;

    /**
     * display goodExampleList for an article or call displayGoodExample if the pathvariable isn't an article url
     * @param articleUrl or the goodexample if can't find an article for this url
     */
    @RequestMapping(value={"/goodexample/{articleUrl}"})
    public ModelAndView displayGoodExampleListForAnArticle(@PathVariable String articleUrl){
        log.debug("displayGoodExampleListForAnArticle i'm call");
        ////check if this is an article url and throw exception otherwise
        Article article = null;
        if((article = articleRepository.findByUrl(articleUrl)) == null){
            throw new InvalidUrlException("article ayant l'url '"+articleUrl+"' est introuvable.");
        }
        
        if(log.isDebugEnabled()){
            log.debug("displayGoodExampleListForAnArticle I found article : "+article.getId());
            for (GoodExample goodExample : article.getGoodExamples()) {
                log.debug("displayGoodExampleListForAnArticle article found contains goodExample "+goodExample.getId());
            }
        }
        ModelAndView mv = new ModelAndView("goodexample");

        mv.addObject("article", article);
        return mv;
    }
    
    @RequestMapping("/ajax/goodexample/edit")
    public ModelAndView GoodExampleEdit(@RequestParam(value="idItem",required=false) Long goodExampleId,   
            							@RequestParam("id") Long articleId
            ){
        ModelAndView mv = new ModelAndView("ckeditorform");
        
        if(goodExampleId != null) {// For editing existing arguments.
            GoodExample goodExample =  getRequiredEntity(goodExampleId);
            mv.addObject("idItem",goodExampleId);
            mv.addObject("titleItem",goodExample.getTitle());
            mv.addObject("contentItem",goodExample.getContent());
        } 
        mv.addObject("urlAction","/ajax/goodexample/editsubmit");
        mv.addObject("idParent",articleId); 
        mv.addObject("helpContent",PropertyLoaderServletContextListener.getProprerty("p_argument_help"));  // Text in yellow div.
        return mv;
    }
    
    @RequestMapping(value="/ajax/goodexample/editsubmit")
    public ModelAndView editGoodExample(@RequestParam("goodExampleId") Long goodExampleId,
    									@RequestParam("title") String title,
    									@RequestParam("description") String description, 
    									@RequestParam("articleId") Long articleId){
        //TODO review
        //check if content or title haven't dangerous html
        if (!HTMLUtil.isHtmlSecure(title) || !HTMLUtil.isHtmlSecure(description)) {
        	NotificationUtil.addNotificationMessage("vous avez introduit du HTML/Javascript invalide dans le titre ou le content");
        	return null;
        }
    	
    	
    	ModelAndView mv = new ModelAndView("goodexampledisplay");
    	
    	GoodExample goodExample = null;
    	
        if(goodExampleId == null){//if this is a new goodExample
        	goodExample = new GoodExample();
        } else {
        	goodExample = getRequiredEntity(goodExampleId);
        }
        
        goodExample.setTitle(title);
        goodExample.setContent(description);
        
        Article article = (Article) getRequiredEntity(articleId, Article.class);//check if the id of an article is good before persist goodExample
        
        if(goodExample.getId() == null) { //link article-goodExample only needed in case of a new goodExample

        	goodExample.getArticles().add(article);
        	goodExampleRepository.persist(goodExample);

        	article.getGoodExamples().add(goodExample);
        	articleRepository.merge(article);
        } else {
        	
        	goodExampleRepository.merge(goodExample);
        }

        mv.addObject("goodExample",goodExample);
        mv.addObject("article",article);
        return mv;
    }
    
    @RequestMapping(value="/ajax/goodexample/edit/addarticle")
    public String addArticle(@RequestParam("goodExampleId") Long goodExampleId, 
    						 @RequestParam("articleUrl") String articleUrl){
        Article article = (Article) getRequiredEntityByUrl(articleUrl, Article.class);
        GoodExample goodExample = getRequiredEntity(goodExampleId);
        
        ////if they are already linked this is an url hacking or a bug
        if (assertArticleLinkedToGoodExample(goodExample, article)) {
            throw new InvalidUrlException("goodExample is already linked with "+article.getTitle());
        } else { ////all id ok now add link and merge
            goodExample.getArticles().add(article);
            article.getGoodExamples().add(goodExample);
            
            articleRepository.merge(article);//the article is the strongest entity link so we merge it before
            goodExampleRepository.merge(goodExample);
        }
        return "redirect:/goodexample/"+article.getUrl();
    }
    
    @RequestMapping(value="ajax/goodexample/edit/deletearticle")
    public String deleteArticle(@RequestParam("goodExampleId") Long goodExampleId, 
    							@RequestParam("articleId") Long articleId){
        Article article = (Article) getRequiredEntity(articleId, Article.class);
        GoodExample goodExample = getRequiredEntity(goodExampleId);
        ////if they are already linked we can delete them
        if (assertArticleLinkedToGoodExample(goodExample, article)) {
            if (log.isDebugEnabled()) {
                log.debug("deleteArticle articles list linked to goodExample sizes is "+goodExample.getArticles().size());
            }
            if (goodExample.getArticles().size() < 2) { // button should not be shown = url hacking
                throw new InvalidUrlException("a goodExample must be linked at least at 1 article");
            }
            
            goodExample.getArticles().remove(article);
            article.getGoodExamples().remove(goodExample);
            
            articleRepository.merge(article);//the article is the strongest entity link so we merge it before
            goodExampleRepository.merge(goodExample);
        } else { ////otherwise this is an url hacking or a bug
            throw new InvalidUrlException("goodExample can be unlinked from this article because it is not linked : "+article.getTitle());
        }
        return "redirect:/goodexample/"+article.getUrl();
    }
    
    private boolean assertArticleLinkedToGoodExample(GoodExample goodExample,Article article){
    	List<Article> articleList = goodExample.getArticles();
        //if goodExample isn't attache to this article
        if(articleList == null || !articleList.contains(article)){
            return false;
        }
        //goodExample contains article in his list
        //so we check if article have the reference to the goodexample otherwise this is a bug
        List<GoodExample> goodExampleList = article.getGoodExamples();
        if(goodExampleList == null || !goodExampleList.contains(goodExample)){
            throw new RuntimeException("bug: article contained in goodExample articleList has his goodExampleList who doesn't contains the goodExample.The two lists aren't synchronized but would be.");
        }
        
        //so they are linked
        return true;
    }
}
