package reformyourcountry.util;

import reformyourcountry.model.Article;

public interface ArticleTreeVisitor {
	
	void processArticle(Article article, boolean isFirstPass);
	
	/**
	 * Used to add informations before the first recursive traversal
	 */
	void preWalk();
	
	/**
	 * Used to add informations after each recursive traversal
	 */
	void postWalk();
	

}
