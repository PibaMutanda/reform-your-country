package reformyourcountry.search;

import reformyourcountry.model.Article;

public class ArticleSearchUnit{
	   private ArticleDocument articleSearchResult;
	   private Article article;
	   
	   public ArticleSearchUnit(ArticleDocument articleSearchResult, Article article){
		   this.articleSearchResult = articleSearchResult;
		   this.article = article;
	   }

	public ArticleDocument getArticleSearchResult() {
		return articleSearchResult;
	}

	public Article getArticle() {
		return article;
	}
	   
}
