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
	
	
	public ArticleTreeWalker(ArticleTreeVisitor atv,ArticleRepository articleRepository){
		this.atv=atv;
		this.articleRepository=articleRepository;
	}
	

	public void walk() throws IOException { 
		List<Article> articles = articleRepository.findAllWithoutParent();
		atv.preWalk();

		processArticleList(articles,true);
		
		atv.postWalk();
	}
	
	private void processArticleList(Collection<Article> articles, boolean isFirstPass) throws IOException {
		for (Article child: articles) {
			processArticle(child,isFirstPass); 
		}
	}


	private void processArticle(Article article, boolean isFirstPass) throws  IOException { 
		atv.processArticle(article, isFirstPass);
		processArticleList(article.getChildren(), false);
		if(!isFirstPass) atv.postWalk();
	}
}
