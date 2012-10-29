package reformyourcountry.search;

import org.apache.lucene.document.Document;

import reformyourcountry.service.IndexManagerService;

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
