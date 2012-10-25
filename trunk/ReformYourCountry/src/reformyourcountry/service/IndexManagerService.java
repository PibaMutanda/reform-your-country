package reformyourcountry.service;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.apache.lucene.analysis.Analyzer;
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
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.SimpleFSDirectory;
import org.apache.lucene.util.Version;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import reformyourcountry.model.Article;
import reformyourcountry.repository.ArticleRepository;
import reformyourcountry.util.FileUtil;

@Component
/**
 * Manage Lucene library (create, search, update the index).
 * 
 * Don't use it directly to run a search--> use ArticleSearchResultsManager
 */
public class IndexManagerService {
	
	public static final int MAX_HITS = 1000;
	@Autowired ArticleRepository articleRepository;
	
	
	public void createIndexes() {
        try{
            SimpleFSDirectory sfsd = new SimpleFSDirectory(new File(FileUtil.getLuceneIndex()));
            Analyzer analyzer = new StandardAnalyzer(Version.LUCENE_40);
            IndexWriterConfig iwc = new IndexWriterConfig(Version.LUCENE_40, analyzer);
            // Make an writer to create the index
            IndexWriter writer = new IndexWriter(sfsd, iwc);
            //Index all Accommodation entries       
          List<Article> articles = articleRepository.findAll();
          for (Article article : articles) {
              writer.addDocument(createDocument(article));
          }
          writer.close();
            
            // close the writer to finish the built of the index
//            closeIndexWriter(writer);
        } catch (CorruptIndexException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
	
	private Document createDocument(Article article) {
        
        // Add a new Document to the index
        Document doc = new Document();

        doc.add(new LongField("id",article.getId(),Store.YES));
        // This give more importance to title during the search
        // The user see the result from the title before the result from the text 
        Field titleField=new TextField("title",article.getTitle(),Store.YES);
        titleField.setBoost(1.5f);
        doc.add(titleField);
        
        doc.add(new TextField("shortName",article.getShortName(),Store.YES));
        doc.add(new TextField("summary",article.getLastVersion().getSummary(),Store.YES));
        doc.add(new TextField("toClassify",article.getLastVersion().getToClassify(),Store.YES));
        doc.add(new TextField("content",article.getLastVersion().getContent(),Store.YES));

        return doc;
    }
//	public void updateArticle(Article oldArticle,Article newArticle) {
//        try{
//        	IndexWriter writer = getIndexWriter();
//            writer.updateDocument(new Term("id", String.valueOf(oldArticle.getId())), createDocument(newArticle));
//            writer.commit();
////            closeIndexWriter(writer);
//        } catch (CorruptIndexException e) {
//            throw new RuntimeException(e);
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//    }
//	public void deleteArticle(Article article) {
//        try {
//        	IndexWriter writer = getIndexWriter();
//            writer.deleteDocuments(new Term("id", String.valueOf(article.getId())));
//            writer.commit();
////            closeIndexWriter(writer);
//        } catch (CorruptIndexException e) {
//            throw new RuntimeException(e);
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//    }
//	public void addArticle(Article article) {
//        try {
//            IndexWriter writer = getIndexWriter();
//            writer.addDocument(createDocument(article));
//            writer.commit();
////            closeIndexWriter(writer);
//        } catch (CorruptIndexException e) {
//            throw new RuntimeException(e);
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//    }
	/**
	 * Used to search the index, using one or more keywords, the booleans are used to select the fields.
	 * @param article If searching in an article, the article in question; if searching all articles, null.
	 * @return
	 */
	public ScoreDoc[] search(String keyWord, boolean inTitle, boolean inSummary, boolean inToClassify, 
												boolean inContent, boolean inShortName, Article article, boolean inAllArticles) {

        try {
            String queryString="(" + keyWord + "~)"+ (inAllArticles ? "" : " AND id:"+article.getId());

            SimpleFSDirectory sfsd = new SimpleFSDirectory(new File(FileUtil.getLuceneIndex()));
            IndexReader reader = DirectoryReader.open(sfsd);
            IndexSearcher searcher = new IndexSearcher(reader);
            
            // We select to field(s) to search
            
            String[] inField;
            inField = new String[]{inTitle?"title":null/*,
            						inSummary?"summary":null,
            						inToClassify?"toClassify":null,
            						inContent?"content":null,
            						inShortName?"shortName":null*/};

            // Build a Query object
            MultiFieldQueryParser parser = new MultiFieldQueryParser(Version.LUCENE_40, inField, new StandardAnalyzer(Version.LUCENE_40));
            Query query = parser.parse(queryString);

            TopDocs hits=searcher.search(query, MAX_HITS);
            sfsd.close();
            return hits.scoreDocs;
        } catch (CorruptIndexException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }
	
	public Document findDocument(ScoreDoc scoreDoc){
        try {
            File file = new File(FileUtil.getLuceneIndex());
            SimpleFSDirectory sfsd = new SimpleFSDirectory(file);
            IndexReader reader = DirectoryReader.open(sfsd);
            IndexSearcher searcher = new IndexSearcher(reader);        
            Document doc = searcher.doc(scoreDoc.doc);
            sfsd.close();
            return doc;
        } catch (CorruptIndexException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
	
//	public void removeIndexes(){
//		File file = new File(FileUtil.getLuceneIndex());
//        try {
//            SimpleFSDirectory sfsd = new SimpleFSDirectory(file);
//            String[] listFile=sfsd.listAll();
//            for(String fileStr : listFile){
//                sfsd.deleteFile(fileStr);
//            }
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//    }
	
	 /**
     * Return an IndexWriter allows to read and write in the index of lucene 
     */
//    private IndexWriter getIndexWriter() throws IOException, CorruptIndexException {
//    	SimpleFSDirectory sfsd = new SimpleFSDirectory(new File(FileUtil.getLuceneIndex()));
//        IndexWriterConfig iwc = new IndexWriterConfig(Version.LUCENE_40, new StandardAnalyzer(Version.LUCENE_40));
//        return new IndexWriter(sfsd, iwc);
//    }
    
    /**
     * Close the writer and these the directory
     */
//    private void closeIndexWriter(IndexWriter indexWriter){
//        try {
//            indexWriter.getDirectory().close();
//            indexWriter.close();
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//    }

}
