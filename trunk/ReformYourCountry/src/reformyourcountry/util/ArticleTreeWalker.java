package reformyourcountry.util;

import java.io.IOException;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import reformyourcountry.model.Article;
import reformyourcountry.repository.ArticleRepository;

@Component
public class ArticleTreeWalker {
	@Autowired ArticleRepository articleRepository;
	ArticleTreeVisitor atv;
	public ArticleTreeWalker(ArticleTreeVisitor atv){
		this.atv=atv;
	}
	private String displayArticleList(Collection<Article> articles, boolean isFirstPass) throws IOException { 
		
		String result = "";
		
		if (isFirstPass) {
			articles=articleRepository.findAllWithoutParent();
			
		    result+="<ul id=\"articletree\">";         
		} else {
            result+="<ul class=\"subarticle\">";    
		}

		for (Article child: articles) {
			result += displayArticle(child); 
		}
		
		result+="</ul>";   
		
		return result;
	}


	private String displayArticle(Article article) throws  IOException { 
	    // class=\"current_page_item\"
		String result="";		
		result += "<li>" + getArticleString(article);
		result += displayArticleList(article.getChildren(), false);
		result += "</li>";
		return result;
	}
	
	private String getArticleString(Article article) {
		return atv.getArticleString(article);
	}	
}
