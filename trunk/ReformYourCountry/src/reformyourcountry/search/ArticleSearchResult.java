package reformyourcountry.search;

import java.util.ArrayList;
import java.util.List;

import org.apache.lucene.search.ScoreDoc;
import org.springframework.beans.factory.annotation.Autowired;

import reformyourcountry.model.Article;
import reformyourcountry.repository.ArticleRepository;
import reformyourcountry.service.IndexManagerService;

public class ArticleSearchResult{
	
	@Autowired ArticleRepository articleRepository;
	private ScoreDoc[] scoreDocs; // Array containing the id and score of each documents ("result") find by Lucene after a search.
	public List<ArticleSearchUnit> publicResults= new ArrayList<ArticleSearchUnit>();
	public List<ArticleSearchUnit> privateResults= new ArrayList<ArticleSearchUnit>();
	
	public ArticleSearchResult(ScoreDoc[] scoreDocs, String keyWord, IndexManagerService indexManagerService){
		this.scoreDocs = scoreDocs;
		for(ScoreDoc scoreDoc : scoreDocs){
			ArticleDocument articleDocument = 
							new ArticleDocument(scoreDoc.score, keyWord, indexManagerService.findDocument(scoreDoc));
			Article article = articleRepository.find(articleDocument.getId());
			if(article.isPublicView() == true ){
				this.publicResults.add(new ArticleSearchUnit(articleDocument, article));
			}
	
			else{
				this.privateResults.add(new ArticleSearchUnit(articleDocument, article));
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
	public List<ArticleSearchUnit> getPublicResults() {
		return publicResults;
	}
	public List<ArticleSearchUnit> getPrivateResults() {
		return privateResults;
	}
	
}
