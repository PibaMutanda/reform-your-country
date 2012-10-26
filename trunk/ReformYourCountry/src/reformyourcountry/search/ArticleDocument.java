package reformyourcountry.search;

import java.io.IOException;
import java.io.StringReader;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.highlight.Highlighter;
import org.apache.lucene.search.highlight.InvalidTokenOffsetsException;
import org.apache.lucene.search.highlight.QueryScorer;
import org.apache.lucene.search.highlight.SimpleHTMLFormatter;
import org.apache.lucene.search.highlight.SimpleSpanFragmenter;
import org.apache.lucene.util.Version;


/** One article found by Lucene, inside a ArticleSearchResult */
public class ArticleDocument {
    private float score;
    private long id;
    private String title;
    private String content;
    private String shortName;
    private String summary;
    private String toClassify;

    public ArticleDocument(float score, String keyWord, Document doc){
    	this.score=score;
    	this.id=Long.valueOf(doc.get("id"));
    	//Highlights
    	////add <b> </b> around the searched word in the relevant field
    	title=getHighlight(keyWord, doc.get("title"));
    	if (title==null || title.isEmpty()){
    		title=doc.get("title");
    	}
    	content=getHighlight(keyWord, doc.get("content"));
    	shortName=getHighlight(keyWord, doc.get("shortName"));
    	summary=getHighlight(keyWord, doc.get("summary"));
    	toClassify=getHighlight(keyWord, doc.get("toClassify"));
    }

    /**
     * We use the lucene highlight library.
     * @return the highlighted fragment, if there is one, null otherwise
     */
    private String getHighlight(String keyword, String textToHighlight){
        try {
            QueryParser queryParser = new QueryParser(Version.LUCENE_40, "field", new StandardAnalyzer(Version.LUCENE_40));//(new Term("field", keyword));
            Query query = queryParser.parse(keyword);
            SimpleHTMLFormatter formatter = new SimpleHTMLFormatter("<b>","</b>");
            QueryScorer scorer = new QueryScorer(query,"field");
            Highlighter highlighter = new Highlighter(formatter,scorer);
            highlighter.setTextFragmenter(new SimpleSpanFragmenter(scorer,45));
            Analyzer analyzer =new StandardAnalyzer(Version.LUCENE_40);
            TokenStream tokens = analyzer.tokenStream("field",new StringReader(textToHighlight));
            String highlightedFragment = highlighter.getBestFragments(tokens, textToHighlight,4 ,"<BR/>...");
            tokens.close();
            analyzer.close();
            if(highlightedFragment.equals("")){
            	return null;
            }else{
            	return highlightedFragment;
            }
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
