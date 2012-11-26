package reformyourcountry.util;

import java.util.LinkedList;
import java.util.List;

import reformyourcountry.model.Article;

public class ArticleTreePdfVisitor implements ArticleTreeVisitor {
	
	List<Article> listResult = new LinkedList<Article>();

	@Override
	public void processArticle(Article article) {
		listResult.add(article);
	}

	@Override
	public void beforeChildren(int recurtionLevel) {
		// This method left intentionally blank
	}

	@Override
	public void afterChildren() {
		// This method left intentionally blank
	}
	public List<Article> getListResult(){
		return this.listResult;
	}
}
