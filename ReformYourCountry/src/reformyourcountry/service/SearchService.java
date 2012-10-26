package reformyourcountry.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import reformyourcountry.model.Article;
import reformyourcountry.search.SearchResult;

@Component
public class SearchService {
	
    @Autowired private IndexManagerService indexManagerService;
    
    public SearchResult search(String keyWord, Article article, boolean inAllArticles) {
    	
        return new SearchResult(indexManagerService.search(keyWord, article, inAllArticles), keyWord, indexManagerService);
    }
    
    
    
    
    

}
