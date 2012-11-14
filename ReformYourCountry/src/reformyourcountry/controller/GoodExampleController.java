package reformyourcountry.controller;

import java.util.List;

import javax.validation.Valid;

import org.apache.commons.logging.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import reformyourcountry.exception.InvalidUrlException;
import reformyourcountry.model.Article;
import reformyourcountry.model.GoodExample;
import reformyourcountry.repository.ArticleRepository;
import reformyourcountry.repository.GoodExampleRepository;
import reformyourcountry.util.Logger;

@Controller
@RequestMapping("/goodexample")
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
    @RequestMapping(value={"/{articleUrl}"})
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
    
    @RequestMapping(value="/edit")
    public String editGoodExample(@RequestParam("goodExampleId") Long goodExampleId,@RequestParam(value="description",required=false)String description, @RequestParam("articleId") Long articleId){
        GoodExample goodExample = getRequiredEntity(goodExampleId);
        log.debug("editGoodExample i'm call");
        if(description != null) {
            log.debug("i will edit description"+description);
            goodExample.setDescription(description);
        }
        //TODO title edit
        //TODO url edit?
        
        goodExampleRepository.merge(goodExample);
        
        return "redirect:/goodexample/"+getRequiredArticle(articleId).getUrl();
    }
    
    @RequestMapping(value="/create")
    public String createGoodExample(@Valid @ModelAttribute GoodExample goodExample, @RequestParam("articleId") Long articleId){
        
        if(goodExample.getId() == null){
            goodExampleRepository.persist(goodExample);  
        } else {
            throw new InvalidUrlException("le goodExample ayant l'id "+goodExample.getId()+" a déjà été créé");
        }
        
        return "redirect:/goodexample/"+getRequiredArticle(articleId).getUrl();
    }

    @RequestMapping(value="/edit/addarticle")
    public String addArticle(@RequestParam("goodExampleId") Long goodExampleId, @RequestParam("articleUrl") String articleUrl){
        Article article = getRequiredArticle(articleUrl);
        GoodExample goodExample = getRequiredEntity(goodExampleId);
        
        ////if they are already linked this is an url hacking or a bug
        if (assertArticleLinkedToGoodExample(goodExample, article)) {
            throw new InvalidUrlException("goodExample est déjà lié à l'article "+article.getTitle());
        } else { ////all id ok now add link and merge
            goodExample.getArticles().add(article);
            article.getGoodExamples().add(goodExample);
            
            articleRepository.merge(article);//the article is the strongest entity link so we merge it before
            goodExampleRepository.merge(goodExample);
        }
        return "redirect:/goodexample/"+article.getUrl();
    }
    
    @RequestMapping(value="/edit/deletearticle")
    public String deleteArticle(@RequestParam("goodExampleId") Long goodExampleId, @RequestParam("articleId") Long articleId){
        Article article = getRequiredArticle(articleId);
        GoodExample goodExample = getRequiredEntity(goodExampleId);
        ////if they are already linked we can delete them
        if (assertArticleLinkedToGoodExample(goodExample, article)) {
            if (log.isDebugEnabled()) {
                log.debug("deleteArticle articles list linkde to goodExample sizes is "+goodExample.getArticles().size());
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
    
    private Article getRequiredArticle(Long articleId) {
        Article article;
        if((article = articleRepository.find(articleId)) == null){
            throw new InvalidUrlException("article ayant l'id '"+articleId+"' est introuvable.");
        }
        return article;
    }
    
    private Article getRequiredArticle(String articleUrl) {
        Article article;
        if((article = articleRepository.findByUrl(articleUrl)) == null){
            throw new InvalidUrlException("article ayant l'url '"+articleUrl+"' est introuvable.");
        }
        return article;
    }
    
    private boolean assertArticleLinkedToGoodExample(GoodExample goodExample,Article article){
        List<Article> articleList = goodExample.getArticles();
        //if goodExample isn't attache to this article
        if(articleList == null || !articleList.contains(article)){
            return false;
        }
        //goodExampe have contains article in his list
        //so we check if article have the reference to the goodexample otherwise this is a bug
        List<GoodExample> goodExampleList = article.getGoodExamples();
        if(goodExampleList == null || !goodExampleList.contains(goodExample)){
            throw new RuntimeException("bug: article contained in goodExample articleList has his goodExampleList who doesn't contains the goodExample.The two lists aren't synchronized but would be.");
        }
        
        //so they are linked
        return true;
    }
}
