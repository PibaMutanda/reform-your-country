package reformyourcountry.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import reformyourcountry.model.Article;
import reformyourcountry.repository.ArticleRepository;
import reformyourcountry.search.ArticleSearchResult;

@Component
public class SearchService {
	
    @Autowired private IndexManagerService indexManagerService;
    @Autowired private ArticleRepository articleRepository;
    
    public ArticleSearchResult searchArticle(String keyWord, Article article, boolean inAllArticles) {
    	
        return new ArticleSearchResult(indexManagerService.search(keyWord, article, inAllArticles), keyWord, indexManagerService, articleRepository);
    }
    
    
    
    
    

}
