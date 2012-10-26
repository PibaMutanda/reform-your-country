package reformyourcountry.search;

import java.io.File;
import java.io.IOException;
import java.io.StringReader;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.highlight.Highlighter;
import org.apache.lucene.search.highlight.InvalidTokenOffsetsException;
import org.apache.lucene.search.highlight.QueryScorer;
import org.apache.lucene.search.highlight.SimpleHTMLFormatter;
import org.apache.lucene.search.highlight.SimpleSpanFragmenter;
import org.apache.lucene.store.SimpleFSDirectory;
import org.apache.lucene.util.Version;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import reformyourcountry.service.IndexManagerService;
import reformyourcountry.util.FileUtil;

/** One article found by Lucene, inside a ArticleSearchResult */
public class ArticleDocument {
	
	private IndexManagerService indexManagerService;
    private float score;
    private long id;
    private String title;
    private String content;
    private String shortName;
    private String summary;
    private String toClassify;

    public ArticleDocument(float score, String keyWord, Document doc, IndexManagerService indexManagerService){
    	this.indexManagerService = indexManagerService;
    	this.score=score;
    	this.id=Long.valueOf(doc.get("id"));
    	//Highlights
    	////add <b> </b> around the searched word in the relevant field
    	title=this.indexManagerService.getHighlight(keyWord, doc.get("title"));
    	if (title==null || title.isEmpty()){
    		title=doc.get("title");
    	}
    	content=this.indexManagerService.getHighlight(keyWord, doc.get("content"));
    	shortName=this.indexManagerService.getHighlight(keyWord, doc.get("shortName"));
    	summary=this.indexManagerService.getHighlight(keyWord, doc.get("summary"));
    	toClassify=this.indexManagerService.getHighlight(keyWord, doc.get("toClassify"));
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
