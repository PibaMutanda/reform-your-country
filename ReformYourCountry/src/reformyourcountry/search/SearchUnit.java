package reformyourcountry.search;

import reformyourcountry.model.Article;

public class SearchUnit{
	   private ArticleSearchResult articleSearchResult;
	   private Article article;
	   
	   public SearchUnit(ArticleSearchResult articleSearchResult, Article article){
		   this.articleSearchResult = articleSearchResult;
		   this.article = article;
	   }

	public ArticleSearchResult getArticleSearchResult() {
		return articleSearchResult;
	}

	public Article getArticle() {
		return article;
	}
	   
}
