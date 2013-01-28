package reformyourcountry.util;

import java.io.IOException;
import java.util.Collection;
import java.util.List;

import reformyourcountry.model.Article;
import reformyourcountry.repository.ArticleRepository;

/** Class doing recursive calls to go through each node of the tree. Depth first. */
public class ArticleTreeWalker {
	ArticleRepository articleRepository;
	ArticleTreeVisitor atv;
	int recurtionLevel;  // root = 0
	private Article parentArticle;  // if not null, we start from the root. Else, we only show the childs of this parent.

	
	public ArticleTreeWalker(ArticleTreeVisitor atv,ArticleRepository articleRepository){
		this(atv, articleRepository, null);
	}
	

	public ArticleTreeWalker(ArticleTreeVisitor atv, ArticleRepository articleRepository, Article parentArticle) {
		this.atv=atv;
		this.articleRepository=articleRepository;
		this.parentArticle = parentArticle;
	}


	public void walk() throws IOException {
		List<Article> articles;
		if (parentArticle == null) {
		    articles = articleRepository.findAllWithoutParent();
		} else {
			articles = parentArticle.getChildren();
		}
		recurtionLevel = 0;
		processArticleList(articles);
	}
	
	private void processArticleList(Collection<Article> articles) throws IOException {
		if (articles.size() > 0) {
			atv.beforeChildren(recurtionLevel);
			for (Article child: articles) {
				processArticle(child); 
			}
			atv.afterChildren();
		}
	}


	private void processArticle(Article article) throws  IOException { 
		atv.startArticle(article);
		recurtionLevel++;
		processArticleList(article.getChildren());
		recurtionLevel--;
		atv.endArticle(article);
	}
}
