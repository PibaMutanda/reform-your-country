package reformyourcountry.util;

import reformyourcountry.model.Article;

public interface ArticleTreeVisitor {
	
	void startArticle(Article article);

	void endArticle(Article article);

	void beforeChildren(int recurtionLevel);

	void afterChildren();
	

}
