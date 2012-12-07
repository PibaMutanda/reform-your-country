package reformyourcountry.controller;

import java.util.List;

import org.apache.commons.logging.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import reformyourcountry.exception.AjaxValidationException;
import reformyourcountry.exception.InvalidUrlException;
import reformyourcountry.model.Article;
import reformyourcountry.model.Comment;
import reformyourcountry.model.GoodExample;
import reformyourcountry.repository.ArticleRepository;
import reformyourcountry.repository.CommentRepository;
import reformyourcountry.repository.GoodExampleRepository;
import reformyourcountry.security.SecurityContext;
import reformyourcountry.service.BadgeService;
import reformyourcountry.service.GoodExampleService;
import reformyourcountry.service.IndexManagerService;
import reformyourcountry.util.HTMLUtil;
import reformyourcountry.util.Logger;
import reformyourcountry.web.PropertyLoaderServletContextListener;

@Controller
public class GoodExampleController extends BaseController<GoodExample>{
    @Logger Log log;
    @Autowired
    GoodExampleRepository goodExampleRepository;
    @Autowired
    ArticleRepository articleRepository;
    @Autowired
    GoodExampleService goodExampleService;
    @Autowired
    CommentRepository commentRepository;
    @Autowired
    BadgeService badgeService;
    
    @Autowired 
    IndexManagerService indexManagerService;

    public ModelAndView itemDetail(GoodExample example){
        //FIXME no verif if user can edit --maxime 30/11/12
        ModelAndView mv = new ModelAndView("itemdetail");
        mv.addObject("canNegativeVote",false);
        mv.addObject("currentItem",example);
        return mv;
    }
    
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
    /**
     * send a jsp fragment who contains a form for edit a goodExample
     */
    @RequestMapping("/ajax/goodexample/edit")
    public ModelAndView GoodExampleEdit(@RequestParam(value="idItem",required=false) Long goodExampleId,//for create   
            							@RequestParam(value="idParent",required=false) Long articleId //for edit
            ){
        ModelAndView mv = new ModelAndView("ckeditorform");
        if (goodExampleId==null && articleId==null) {
            throw new AjaxValidationException("problème de donner sur le page veuillez la recharger");
       }
        if ( goodExampleId != null ) {// For editing existing arguments.
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
    public ModelAndView editGoodExample(
            @RequestParam(value="idParent", required=false)Long articleId,  // In case of create
            @RequestParam(value="idItem", required=false)Long goodExampleId,  // In case of edit
            @RequestParam("content")String content, @RequestParam("title")String title) throws AjaxValidationException{
        //TODO review
        //check if content or title haven't dangerous html
        if (!HTMLUtil.isHtmlSecure(title) || !HTMLUtil.isHtmlSecure(content)) {
             throw new AjaxValidationException("vous avez introduit du HTML/Javascript invalide dans le commentaire");
        } 
        
        ////we check if there is some character who can make the page bug (<,>,",etc...)
        String forbiddenCHaracter;
        
        if ((forbiddenCHaracter = HTMLUtil.getContainedForbiddenHtmlCharacter(title)) != null) {
            throw new AjaxValidationException("vous avez introduit des charactères interdit dans le titre : "+forbiddenCHaracter);
        }
       
    	GoodExample goodExample;
    	Article article = null;
    	
        if(goodExampleId == null){//if this is a new goodExample
        	goodExample = new GoodExample();
        	article = (Article) getRequiredEntity(articleId, Article.class);
        	
        	goodExample.setTitle(title);
            goodExample.setContent(content);
            
            goodExample.getArticles().add(article);
            goodExampleRepository.persist(goodExample);
            indexManagerService.add(goodExample);
            badgeService.grandBadgeForGoodExample(goodExample.getCreatedBy());
            article.getGoodExamples().add(goodExample);
            articleRepository.merge(article);
        } else {
        	goodExample = getRequiredEntity(goodExampleId);
        	SecurityContext.assertCurrentUserCanEditGoodExample(goodExample);
        }
        
        goodExample.setTitle(title);
        goodExample.setContent(content);
        
        goodExampleRepository.merge(goodExample);
        indexManagerService.update(goodExample);
        return itemDetail(goodExample);
    }
        
    @RequestMapping("ajax/goodexample/refresh")
    public ModelAndView argumentVote(@RequestParam("id")Long goodExampleId){
        SecurityContext.assertUserIsLoggedIn();
        return itemDetail((GoodExample) getRequiredEntity(goodExampleId, GoodExample.class));
    }
    
    @RequestMapping("ajax/goodexample/vote")
    public ModelAndView vote(@RequestParam("id")Long goodExampleId){
        SecurityContext.assertUserIsLoggedIn();
        
        GoodExample goodExample = (GoodExample) getRequiredEntity(goodExampleId,GoodExample.class);
        goodExampleService.vote(goodExample);
        badgeService.grandBadgeForGoodExample(goodExample.getCreatedBy());
        return itemDetail(goodExample);
    }
   
    @RequestMapping("ajax/goodexample/unvote")
    public ModelAndView unVote(@RequestParam("id")Long goodExampleId) throws Exception{
        SecurityContext.assertUserIsLoggedIn();
        
        GoodExample goodExample = (GoodExample) getRequiredEntity(goodExampleId,GoodExample.class);
        goodExampleService.unVote(goodExample);
        
        return itemDetail(goodExample);
    }
    
    @RequestMapping("/ajax/goodexample/delete")
    @ResponseBody
    public String deleteGoodExample(@RequestParam("id")Long goodExampleId) throws Exception{
        //FIXME no verif if user can edit --maxime 30/11/12
    	GoodExample goodExample = (GoodExample) getRequiredEntity(goodExampleId,GoodExample.class);
        if(goodExample ==null){
            throw new Exception("this id doesn't reference any goodExample.");
        }
        if(!goodExample.isDeletable()){
             throw new Exception("this person can't suppress this goodExample(hacking).");
        }
        goodExampleService.deleteGoodExample(goodExample);
        indexManagerService.delete(goodExample);
        return "";
        
    }

    @RequestMapping("/ajax/goodexample/commenteditsubmit")
    public ModelAndView commentEdit(@RequestParam(value="idCommentedItem",required=false)Long goodExampleId,//for create a new comment
                                    @RequestParam(value="idComment",required=false)Long idComment,//for editing
                                    @RequestParam("content")String content) throws Exception{
        SecurityContext.assertUserIsLoggedIn();
        //TODO review
        //check if content or title haven't dangerous html
        if (!HTMLUtil.isHtmlSecure(content)) {
             throw new AjaxValidationException("vous avez introduit du HTML/Javascript invalide dans le commentaire");
        }
        
        ////we check if there is some character who can make the page bug (<,>,",etc...)
        String forbiddenCHaracter;
        
        if ((forbiddenCHaracter = HTMLUtil.getContainedForbiddenHtmlCharacter(content)) != null) {
            throw new AjaxValidationException("vous avez introduit des charactères interdit dans le contenu : "+forbiddenCHaracter);
        }
        
        Comment comment = null;
        
        if (idComment == null && goodExampleId == null) {
            throw new IllegalArgumentException("idComment and goodExampleId are null : can't determine if it's a create or an edit");
        } else if ( idComment == null ) {//it's a create, only idComment is null 
            GoodExample goodExample =  getRequiredEntity(goodExampleId);
            comment = new Comment();
            comment.setGoodExample(goodExample);
            commentRepository.persist(comment);
            
            goodExample.getCommentList().add(comment);
            goodExampleRepository.merge(goodExample);
            
            badgeService.grandBadgeForComment(comment.getCreatedBy());
        } else { //it's an edit , only idArgument is null
            comment = (Comment) getRequiredEntity(idComment, Comment.class);
            SecurityContext.assertCurrentUserCanEditComment(comment);
        }

        comment.setContent(content);
        commentRepository.merge(comment);
        return itemDetail(comment.getGoodExample());
        
    }

    @RequestMapping("/ajax/goodexample/commenthide")
    public ModelAndView commentHide(@RequestParam("id")Long idComment) throws AjaxValidationException{
        Comment comment = commentRepository.find(idComment);

        if (comment.isHidden()) {
            throw new AjaxValidationException("ce commentaire n'est pas caché");
        }
        if (!SecurityContext.canCurrentUserHideComment(comment)) {
            throw new AjaxValidationException("vous ne pouvez pas cacher ce commentaire");
        }
        
        comment.setHidden(true);
        return itemDetail(comment.getGoodExample());
    }
    
    @RequestMapping("/ajax/goodexample/commentunhide")
    public ModelAndView commentUnhide(@RequestParam("id")Long idComment) throws AjaxValidationException{
        Comment comment = commentRepository.find(idComment);
        
        if (!comment.isHidden()) {
            throw new AjaxValidationException("ce commentaire n'est pas caché");
        }
        if (!SecurityContext.canCurrentUserHideComment(comment)) {
            throw new AjaxValidationException("vous ne pouvez pas cacher ce commentaire");
        }
        
        comment.setHidden(false);
        ModelAndView mv = new ModelAndView("commentlist"); //it's only on a moderation special page we can unhide comment
        GoodExample goodExample = comment.getGoodExample();
        mv.addObject("parentContent",goodExample.getContent());
        mv.addObject("commentList",goodExample.getCommentList());
        return mv;
    }
    
    @RequestMapping("/ajax/goodexample/commentdelete")
    public ModelAndView deleteComment(@RequestParam("id")Long idComment) throws Exception{
        Comment comment = commentRepository.find(idComment);
        
        SecurityContext.assertCurrentUserCanEditComment(comment);
        
        if(comment ==null){
            throw new Exception("this id doesn't reference any comment.");
        }
        if(!comment.isEditable()){
             throw new Exception("this person can't suppress this comment(hacking).");
        }
        GoodExample goodExample = comment.getGoodExample();
        goodExample.getCommentList().remove(comment);
        commentRepository.remove(comment);
        goodExampleRepository.merge(goodExample);
        return itemDetail(goodExample);
    }
    //FIXME useless? --maxime 7/12/12
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
    //FIXME useless? --maxime 7/12/12
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
