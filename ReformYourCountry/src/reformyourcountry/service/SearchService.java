package reformyourcountry.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import reformyourcountry.model.Article;
import reformyourcountry.search.ArticleSearchResult;

@Component
public class SearchService {
	
    @Autowired private IndexManagerService indexManagerService;
    
    public ArticleSearchResult searchArticle(String keyWord, Article article, boolean inAllArticles) {
    	
        return new ArticleSearchResult(indexManagerService.search(keyWord, article, inAllArticles), keyWord, indexManagerService);
    }
    
    
    
    
    

}
