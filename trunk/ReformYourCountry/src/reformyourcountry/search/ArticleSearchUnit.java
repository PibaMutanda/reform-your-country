package reformyourcountry.search;

import reformyourcountry.model.Article;
import reformyourcountry.security.Privilege;
import reformyourcountry.security.SecurityContext;

public class ArticleSearchUnit{
	   private ArticleDocument articleDocument;
	   private Article article;
	   
	   public ArticleSearchUnit(ArticleDocument articleSearchResult, Article article){
		   this.articleDocument = articleSearchResult;
		   this.article = article;
	   }

	public ArticleDocument getArticleSearchResult() {
		return articleDocument;
	}

	public Article getArticle() {
		return article;
	}

	public ArticleDocument getArticleDocument() {
		return articleDocument;
	}
	 
	public boolean isVisible(){
		return (article.isPublished() || SecurityContext.isUserHasPrivilege(Privilege.MANAGE_ARTICLE));
	}
}
