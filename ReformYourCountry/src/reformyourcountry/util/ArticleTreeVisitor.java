package reformyourcountry.util;

import reformyourcountry.model.Article;

public interface ArticleTreeVisitor {
	
	void processArticle(Article article);
	
	void beforeChildren(int recurtionLevel);

	void afterChildren();
	

}
