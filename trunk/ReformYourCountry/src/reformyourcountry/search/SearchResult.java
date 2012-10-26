package reformyourcountry.search;

import java.util.ArrayList;
import java.util.List;

import org.apache.lucene.search.ScoreDoc;
import org.springframework.beans.factory.annotation.Autowired;

import reformyourcountry.model.Article;
import reformyourcountry.repository.ArticleRepository;
import reformyourcountry.service.IndexManagerService;

public class SearchResult{
	
	@Autowired ArticleRepository articleRepository;
	private ScoreDoc[] scoreDocs; // Array containing the id and score of each documents ("result") find by Lucene after a search.
	public List<SearchUnit> publicResults= new ArrayList<SearchUnit>();
	public List<SearchUnit> privateResults= new ArrayList<SearchUnit>();
	
	public SearchResult(ScoreDoc[] scoreDocs, String keyWord, IndexManagerService indexManagerService){
		this.scoreDocs = scoreDocs;
		for(ScoreDoc scoreDoc : scoreDocs){
			ArticleSearchResult articleSearchResult = 
							new ArticleSearchResult(scoreDoc.score, keyWord, indexManagerService.findDocument(scoreDoc));
			Article article = articleRepository.find(articleSearchResult.getId());
			if(article.isPublicView() == true ){
				this.publicResults.add(new SearchUnit(articleSearchResult, article));
			}
	
			else{
				this.privateResults.add(new SearchUnit(articleSearchResult, article));
			}
    	}
	}
	/**
     * Use this method to get the number of results found
     */
    public int getTotalHits(){
        return scoreDocs.length;
    }
    
	public ScoreDoc[] getScoreDocs() {
		return scoreDocs;
	}
	public List<SearchUnit> getPublicResults() {
		return publicResults;
	}
	public List<SearchUnit> getPrivateResults() {
		return privateResults;
	}
	
}
