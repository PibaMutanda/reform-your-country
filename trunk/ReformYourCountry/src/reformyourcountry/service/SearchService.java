package reformyourcountry.service;

import java.io.IOException;
import java.io.StringReader;
import java.util.Arrays;
import java.util.List;

import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.highlight.Highlighter;
import org.apache.lucene.search.highlight.InvalidTokenOffsetsException;
import org.apache.lucene.search.highlight.QueryScorer;
import org.apache.lucene.search.highlight.SimpleHTMLFormatter;
import org.apache.lucene.search.highlight.SimpleSpanFragmenter;
import org.apache.lucene.util.Version;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import reformyourcountry.model.Article;

@Component
public class SearchService {
	
	private ScoreDoc[] scoreDocs; // Array containing the id and score of each documents ("result") find by Lucene after a search.
    private String keyWord;
    @Autowired private IndexManagerService indexManagerService;
    
    /**
     * Use this method to get the number of results found
     */
    public int getTotalHits(){
        return scoreDocs.length;
    }
    
    public List<ScoreDoc> getResults(){
        return Arrays.asList(scoreDocs);
    }
    
    public void search(String keyWord, boolean inTitle, boolean inSummary, boolean inToClassify, 
			boolean inContent, boolean inShortName, Article article, boolean inAllArticles) {
    	
        this.keyWord=keyWord;
        scoreDocs=indexManagerService.search(keyWord, inTitle, inSummary, inToClassify, inContent, inShortName, article, inAllArticles);
    }
    
    /**
     * Get a displayable result by this Id.
     * Typically used to display a result.
     * 
     * I want the 125th result --> getResult(125);
     */
    public ArticleSearchResult getResult(int index){
    	return getResult(scoreDocs[index]);
    }
    public ArticleSearchResult getResult(ScoreDoc scoreDoc){
        Document doc=indexManagerService.findDocument(scoreDoc);
        return new ArticleSearchResult(scoreDoc.score, keyWord, doc);
    }
    
    public static class ArticleSearchResult {
        private float score;
        private long id;
        private String title;
        private String content;
        private String shortName;
        private String summary;
        private String toClassify;

        public ArticleSearchResult(float score, String keyWord, Document doc){
        	this.score=score;
        	this.id=Long.valueOf(doc.get("id"));
        	//Highlights
        	////add <b> </b> around the searched word in the relevant field
        	title=getHighlight(keyWord, doc.get("title"));
        	if (title.isEmpty()){
        		title=doc.get("title");
        	}
        	content=getHighlight(keyWord, doc.get("content"));
        	shortName=getHighlight(keyWord, doc.get("shortName"));
        	summary=getHighlight(keyWord, doc.get("summary"));
        	toClassify=getHighlight(keyWord, doc.get("toClassify"));
        }

        /**
         * We use Highlight lucene library to highlight the result
         */
        private String getHighlight(String keyword, String textToHighlight){

            try {
                QueryParser queryParser = new QueryParser(Version.LUCENE_40, "field", new StandardAnalyzer(Version.LUCENE_40));//(new Term("field", keyword));
                Query query = queryParser.parse(keyword);
                SimpleHTMLFormatter formatter = new SimpleHTMLFormatter("<b>","</b>");
                QueryScorer scorer = new QueryScorer(query,"field");
                Highlighter highlighter = new Highlighter(formatter,scorer);
                highlighter.setTextFragmenter(new SimpleSpanFragmenter(scorer,45));
                TokenStream tokens = new StandardAnalyzer(Version.LUCENE_40).tokenStream("field",new StringReader(textToHighlight));
                return highlighter.getBestFragments(tokens, textToHighlight,4 ,"<BR/>...");
            } catch (IOException e) {
                throw new RuntimeException(e);
            } catch (InvalidTokenOffsetsException e) {
                throw new RuntimeException(e);
            } catch (ParseException e) {
                throw new RuntimeException(e);
            } finally{
            	
            }
        }

        public float getScore() {
            return score;
        }

        public String getTitle() {
            return title;
        }
        
        public long getId() {
			return id;
		}

		public String getContent() {
			return content;
		}

		public String getShortName() {
			return shortName;
		}

		public String getSummary() {
			return summary;
		}

		public String getToClassify() {
			return toClassify;
		}

		@Override
        public String toString() {
            return "Score: " + score + " // title: " + title+ " // shortName: " + shortName+ " // summary: " + summary+ " // toClassify: " + toClassify+ " // content: " + content;
        }
    }

}
