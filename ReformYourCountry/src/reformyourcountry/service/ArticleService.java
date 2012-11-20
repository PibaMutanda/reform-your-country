package reformyourcountry.service;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;

import reformyourcountry.converter.BBConverter;
import reformyourcountry.exception.InvalidUrlException;
import reformyourcountry.model.Article;
import reformyourcountry.model.ArticleVersion;
import reformyourcountry.pdf.ArticlePdfGenerator;
import reformyourcountry.repository.ActionRepository;
import reformyourcountry.repository.ArticleRepository;
import reformyourcountry.repository.ArticleVersionRepository;
import reformyourcountry.repository.BookRepository;
import reformyourcountry.security.SecurityContext;

@Service
@Transactional
public class ArticleService {

    @Autowired 	ActionRepository actionRepository;
	@Autowired	ArticleRepository articleRepository;
	@Autowired  ArticleVersionRepository articleVersionRepository;
	@Autowired  BookRepository bookRepository;
	@Autowired  IndexManagerService indexManagerService;
	
	/** @param newParentId if null, removes the current parent and makes a root node. */
	public void changeParent(Article article, Long newParentId) {
		//FIXME dead code? --maxime 16/10/2012
		// Verify that the parent is not the article itself or one of its children (it would create a cycle in the tree).
		Article newParent = newParentId == null ? null : articleRepository.find(newParentId);
		
		
		/*if (newParent != null && newParent.equalsOrIsParentOf(article)) {
			throw new RuntimeException("Bug: should not try to set a child as parent. Child id = " + article.getId() + "; new parent id = " + newParentId);
		}*/
				
		// Detach the article from its current parent
		if (article.getParent() != null) {
			Article oldParent = article.getParent();
			oldParent.getChildren().remove(article);
			article.setParent(null);
			articleRepository.merge(oldParent);
			articleRepository.merge(article);
		}
		
		attachWithParent(article, newParentId);
	}
	
	 public void attachWithParent(Article article, Long parentId) {
		 if (parentId != null) { // We do not create article as a root article.
			 Article parent = articleRepository.find(parentId);
			 if (parent == null) {
				 throw new InvalidUrlException("Invalid parentid (request hacking?)");
			 }
			 article.setParent(parent);
			 parent.getChildren().add(article);
			 articleRepository.merge(parent);
			 articleRepository.merge(article);
		 }
	 }
	 /**
	  * update version of an article or create new if previous version are one hour old or created by different user. 
	  * In case of a new article and no previous version if parameter null are replaced by a String.
	  */
	 public void saveArticle(Article article, String content, String summary, String toClassify){
         ArticleVersion previousVersion =  null;
         ArticleVersion newVersion = null;
         boolean isNewArticle = false;
         if (article == null) {
             throw new IllegalArgumentException("article can't be null");
         }

         //// 1. Do we need a new version (or do we update the current last version) ?
         ////    Because with the auto-save, we don't want too many versions to be created (unmanageable) 
	     Boolean needNewVersion = null;  
	     Long delayBetweenVersions = new Date().getTime() - 3600000;//it's time in millisecond so 1 hour is 1000ms*60s*60m
	     
	     if (article.getId() == null) {
	         needNewVersion = true; //in case of an new article it's necessarily an new articleVersion
	         isNewArticle = true;
	         
	     } else {
	         previousVersion  = article.getLastVersion();
	         
	         if(previousVersion.getCreatedBy() != SecurityContext.getUser()) {  // The last save has been done by another user.
	             needNewVersion = true;
	         } else if (previousVersion.getCreatedOn().getTime() < delayBetweenVersions ) { //if it is more than one hour old
                needNewVersion = true;
	         } else { //if the lastVersion of article has been created by the same user and is less than one hour old . We don't create a new version
	             needNewVersion = false;
	         } 
	     }
	     
	     ///// 2.create new article or new version if necessary
	     if (article.getId() == null) { //in case of new Article we persist it before set the lastVersion
             articleRepository.persist(article);
             needNewVersion = true;
         }
	     
	     if (needNewVersion) {
	         newVersion = new ArticleVersion(); 
	         newVersion.setArticle(article);
	         newVersion.setVersionNumber(article.getLastVersion() != null ? article.getLastVersion().getVersionNumber()+1 : 1);
	         newVersion.updateOnNotNull();  // We need to order versions by update date in a query, and having a null date would be a problem there.

	         articleVersionRepository.persist(newVersion);
	         
	         article.setLastVersion(newVersion);
	     } else {
	         newVersion = previousVersion;
	     }
	     
	     ///// 3.set content of version 
         newVersion.setContent(content != null ? content : (previousVersion!=null ? previousVersion.getContent() : "Contenu à compléter") );
	     newVersion.setSummary(summary != null ? summary : (previousVersion!=null ? previousVersion.getSummary() : "Résumé à compléter") );
	     newVersion.setToClassify(toClassify != null ? toClassify : (previousVersion!=null ? previousVersion.getToClassify() : "") );

	     ///// 4.store the rendered version of the content and summary, for performance optimization
	     
	     updateRendreredContentAndSummary(article);
	     ///// 5.merge
	     articleVersionRepository.merge(newVersion);
 	     articleRepository.merge(article);
 	     
 	     /////6. update index
 	     if(isNewArticle){
 	    	indexManagerService.addArticle(article);
 	     }else{
 	    	indexManagerService.updateArticle(article);
 	     }
	 }
	 public void updateRendreredContentAndSummary(Article article){
		 article.setLastVersionRenderedContent(new BBConverter(bookRepository, articleRepository,actionRepository).transformBBCodeToHtmlCode(article.getLastVersionRenderedContent()));
	     article.setLastVersionRenderdSummary(new BBConverter(bookRepository, articleRepository,actionRepository).transformBBCodeToHtmlCode(article.getLastVersionRenderdSummary()));
	 }
	
	 public void makePdf(Article article){
	        try(ByteArrayOutputStream baos = new ByteArrayOutputStream()){
	        	ArticlePdfGenerator cPdf = new ArticlePdfGenerator(true);
	        	String title = cPdf.createArticleTitle(article);
	        	cPdf.generatePDF(article.getLastVersionRenderdSummary()+article.getLastVersionRenderedContent(),baos,true);
	        	
	        	File filetest = new File(System.getProperty("java.io.tmpdir")+"pd4ml.pdf");
				try(FileOutputStream fos =new FileOutputStream(filetest)){
					baos.writeTo(fos);
				}
	        } catch (IOException e) {
	        	throw new RuntimeException(e);
			}
		}
}
