package reformyourcountry.service;

import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.Field.Store;
import org.apache.lucene.document.LongField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryparser.classic.MultiFieldQueryParser;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.search.highlight.Highlighter;
import org.apache.lucene.search.highlight.InvalidTokenOffsetsException;
import org.apache.lucene.search.highlight.QueryScorer;
import org.apache.lucene.search.highlight.SimpleHTMLFormatter;
import org.apache.lucene.search.highlight.SimpleSpanFragmenter;
import org.apache.lucene.store.SimpleFSDirectory;
import org.apache.lucene.util.Version;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import reformyourcountry.converter.BBConverter;
import reformyourcountry.model.Action;
import reformyourcountry.model.Article;
import reformyourcountry.repository.ActionRepository;
import reformyourcountry.repository.ArticleRepository;
import reformyourcountry.repository.BookRepository;
import reformyourcountry.util.CurrentEnvironment;
import reformyourcountry.util.FileUtil;
import reformyourcountry.util.HTMLUtil;

@Component
/**
 * Manage Lucene library (create, search, update the index).
 * 
 * Don't use it directly to run a search--> use ArticleSearchResultsManager
 */
public class IndexManagerService {
	
	public static final int MAX_HITS = 1000;
	@Autowired ArticleRepository articleRepository;
	@Autowired BookRepository bookRepository;
    @Autowired ActionRepository actionRepository;
	@Autowired CurrentEnvironment currentEnvironment;


	public void createIndexes() {
		try(SimpleFSDirectory sfsd = new SimpleFSDirectory(new File(FileUtil.getLuceneIndexDirectory(currentEnvironment)))){
			
			Analyzer analyzer = new StandardAnalyzer(Version.LUCENE_40);
			IndexWriterConfig iwc = new IndexWriterConfig(Version.LUCENE_40, analyzer);

			try(IndexWriter writer = new IndexWriter(sfsd, iwc)){// Make an writer to create the index (with try-with-resources block)
				//Index all Accommodation entries 
				List<Article> articles = articleRepository.findAll();
				for (Article article : articles) {
					writer.addDocument(createArticleDocument(article));
				}
				writer.commit();
			}               
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}	

////WE DON'T SEARCH FOR ACTIONS YET, but we will
//	private Document createActionDocument(Action action) {
//		// Add a new Document to the index
//        Document doc = new Document();
//
//        doc.add(new LongField("id",action.getId(),Store.YES));
//        // This give more importance to title during the search
//        // The user see the result from the title before the result from the text 
//        Field titleField=new TextField("title",action.getTitle(),Store.YES);
//        titleField.setBoost(1.5f);
//        doc.add(titleField);
//        
//        doc.add(new TextField("shortDescription",action.getShortDescription(),Store.YES));
//        doc.add(new TextField("longDescription",action.getLongDescription(),Store.YES));
//        doc.add(new TextField("content",action.getContent(),Store.YES));
//
//        return doc;
//	}

	private Document createArticleDocument(Article article) {
		// Add a new Document to the index
        Document doc = new Document();
        //Remove bbcode and html
		String summary = cleanAndTransform(article.getLastVersion().getSummary());
		String toClassify = cleanAndTransform(article.getLastVersion().getToClassify());
		String content = cleanAndTransform(article.getLastVersion().getContent());
		
        doc.add(new TextField("id",String.valueOf(article.getId()),Store.YES));//This is a TextField instead of a LongField because updateDocument has problems finding LongFields(no idea why)
        // This give more importance to title during the search
        // The user see the result from the title before the result from the text 
        Field titleField=new TextField("title",article.getTitle(),Store.YES);
        titleField.setBoost(1.5f);
        doc.add(titleField);
        
        doc.add(new TextField("shortName",article.getShortName(),Store.YES));
        doc.add(new TextField("summary",summary,Store.YES));
        doc.add(new TextField("toClassify",toClassify,Store.YES));
        doc.add(new TextField("content",content,Store.YES));

        return doc;
	}

	private String cleanAndTransform(String textToTransform) {
		String result = HTMLUtil.removeHmlTags(new BBConverter(bookRepository, articleRepository,actionRepository,false).transformBBCodeToHtmlCode(textToTransform));
		return result;
	}
	
	public void updateArticle(Article newArticle) {
        try(IndexWriter writer = getIndexWriter()){
        	
            writer.updateDocument(new Term("id", String.valueOf(newArticle.getId())), createArticleDocument(newArticle));
            writer.commit();
        } catch ( IOException e) {
            throw new RuntimeException(e);
        }
    }
	
	public void deleteArticle(Article article) {
		 try(IndexWriter writer = getIndexWriter()){
            writer.deleteDocuments(new Term("id", String.valueOf(article.getId())));
            writer.commit();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
	
	public void addArticle(Article article) {
		 try(IndexWriter writer = getIndexWriter()){
            writer.addDocument(createArticleDocument(article));
            writer.commit();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
	/**
	 * Used to search the index, using one or more keywords
	 * @param article If searching in an article, the article in question; if searching all articles, should be null.
	 * @return
	 */
	public ScoreDoc[] search(String keyWords, Article article, boolean isEditor, boolean inAllArticles) {

        try {
        	// We  build a query using the parameters;
            String queryString="(" + keyWords + "~)"//the "~" enable fuzzy search
            		+ (inAllArticles || article==null ? "" : " AND id:"+article.getId());//search in one article only

            SimpleFSDirectory sfsd = new SimpleFSDirectory(new File(FileUtil.getLuceneIndexDirectory(currentEnvironment)));
            IndexReader reader = DirectoryReader.open(sfsd);
            IndexSearcher searcher = new IndexSearcher(reader);
            
            // Create the search criteria as an array with article fields we search in
            List<String> fields=new ArrayList<String>(Arrays.asList("title","summary","content","shortname"));
			if (isEditor) {
				fields.add("toClassify");
			}
			String[] fieldList = new String[fields.size()];
			fields.toArray(fieldList);
			
            // Build a Query object
            MultiFieldQueryParser parser = new MultiFieldQueryParser(Version.LUCENE_40, fieldList, new StandardAnalyzer(Version.LUCENE_40));
            Query query = parser.parse(queryString);
            
            query.rewrite(reader);

            TopDocs hits=searcher.search(query, MAX_HITS);
            sfsd.close();
            return hits.scoreDocs;
        } catch (IOException | ParseException e) {
            throw new RuntimeException(e);
        }
    }
	
	
	  /**
     * We use the lucene highlight library.
     * @return the highlighted fragment, if there is one, null otherwise
     */
    public String getHighlight(String keyword, String textToHighlight){
        try (SimpleFSDirectory sfsd = new SimpleFSDirectory(new File(FileUtil.getLuceneIndexDirectory(currentEnvironment)))){
        	
        	
            try (IndexReader reader = DirectoryReader.open(sfsd)){
	        	String queryString="(" + keyword + "~)";
	            QueryParser queryParser = new QueryParser(Version.LUCENE_40, "field", new StandardAnalyzer(Version.LUCENE_40));//(new Term("field", keyword));
	            Query query = queryParser.parse(queryString);
	            query.rewrite(reader);
	            
	            SimpleHTMLFormatter formatter = new SimpleHTMLFormatter("<b>","</b>");
	            QueryScorer scorer = new QueryScorer(query,"field");
	            Highlighter highlighter = new Highlighter(formatter,scorer);
	            highlighter.setTextFragmenter(new SimpleSpanFragmenter(scorer,45));
	            try (Analyzer analyzer =new StandardAnalyzer(Version.LUCENE_40)){
		            try(TokenStream tokens = analyzer.tokenStream("field",new StringReader(textToHighlight))){
		            	String highlightedFragment = highlighter.getBestFragments(tokens, textToHighlight,4 ,"<BR/>...");
		            	if(highlightedFragment.equals("")){
		                	return null;
		                }else{
		                	return highlightedFragment;
		                }
		            }
	            }
            }
        } catch (IOException | InvalidTokenOffsetsException | ParseException e) {
            throw new RuntimeException(e);
        }
    }
	public Document findDocument(ScoreDoc scoreDoc){
		File file = new File(FileUtil.getLuceneIndexDirectory(currentEnvironment));
        try (SimpleFSDirectory sfsd = new SimpleFSDirectory(file)){
            try (IndexReader reader = DirectoryReader.open(sfsd)){
	            IndexSearcher searcher = new IndexSearcher(reader);        
	            Document doc = searcher.doc(scoreDoc.doc);
	            return doc;
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
	
	public void removeIndexes(){
		File file = new File(FileUtil.getLuceneIndexDirectory(currentEnvironment));
        try(SimpleFSDirectory sfsd = new SimpleFSDirectory(file)) {
            String[] listFile=sfsd.listAll();
            for(String fileStr : listFile){
                sfsd.deleteFile(fileStr);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
	
	 /**
     * Return an IndexWriter allows to read and write in the index of lucene 
     */
    private IndexWriter getIndexWriter() throws IOException, CorruptIndexException {
    	SimpleFSDirectory sfsd = new SimpleFSDirectory(new File(FileUtil.getLuceneIndexDirectory(currentEnvironment)));
        IndexWriterConfig iwc = new IndexWriterConfig(Version.LUCENE_40, new StandardAnalyzer(Version.LUCENE_40));
        return new IndexWriter(sfsd, iwc);
    }

    public void deleteAction(Action action) {
        // TODO implement me
        
    }
    
}
