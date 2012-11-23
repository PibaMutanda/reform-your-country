package reformyourcountry.util;

import java.io.IOException;
import java.util.Collection;

import reformyourcountry.model.Article;
import reformyourcountry.repository.ArticleRepository;

public class ArticleTreeWalker {
	ArticleRepository articleRepository;
	ArticleTreeVisitor atv;
	public ArticleTreeWalker(ArticleTreeVisitor atv,ArticleRepository articleRepository){
		this.atv=atv;
		this.articleRepository=articleRepository;
	}
	
	public void processArticleList(Collection<Article> articles, boolean isFirstPass) throws IOException { 
		
		
		if (isFirstPass) {
			articles=articleRepository.findAllWithoutParent();
		}

		for (Article child: articles) {
			processArticle(child); 
		}
	}


	private void processArticle(Article article) throws  IOException { 
	    // class=\"current_page_item\"
		atv.getArticleString(article);
		processArticleList(article.getChildren(), false);
	}
}
