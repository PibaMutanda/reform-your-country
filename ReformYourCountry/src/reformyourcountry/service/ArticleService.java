package reformyourcountry.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import reformyourcountry.misc.InvalidUrlException;
import reformyourcountry.model.Article;
import reformyourcountry.repository.ArticleRepository;

@Service
@Transactional
public class ArticleService {

	@Autowired	ArticleRepository articleRepository;
	
	/** @param newParentId if null, removes the current parent and makes a root node. */
	public void changeParent(Article article, Long newParentId) {
		
		// Verify that the parent is not the article itself or one of its children (it would create a cycle in the tree).
		Article newParent = newParentId == null ? null : articleRepository.find(newParentId);
		if (newParent != null && newParent.equalsOrIsParentOf(article)) {
			throw new RuntimeException("Bug: should not try to set a child as parent. Child id = " + article.getId() + "; new parent id = " + newParentId);
		}
				
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
}
