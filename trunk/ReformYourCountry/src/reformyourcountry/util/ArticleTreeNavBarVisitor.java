package reformyourcountry.util;

import reformyourcountry.model.Article;

public class ArticleTreeNavBarVisitor implements ArticleTreeVisitor {

	String htmlResult="";
	boolean isList=false;
	
	
	@Override
	public void startArticle(Article article) {

		String style = null;
		String title = null;
		
		if(!article.isPublished()) {
			title = "article non publié";
			if (article.getPublishDate() != null){
				title+=" - publié "+DateUtil.formatIntervalFromToNowFR(article.getPublishDate() );
			} 

			style = "color:#AAA; font-style:italic;";
		}
		
		
		htmlResult+="<li>";
		
		htmlResult += "<a href='/article/" + article.getUrl() + "' " +
				(style==null ? "" : "style='"+style+"' ") +
                (title==null ? "" : "title='"+title+"'") + 
				"><span>";
	    htmlResult += article.getTitle();
	    htmlResult += "</a>";
	}

	@Override
	public void endArticle(Article article) {
	    htmlResult+="</li>";
	}
	    
	
	@Override
	public void beforeChildren(int recurtionLevel){
		htmlResult += "<ul class=\"articletreelevel"+recurtionLevel+"\">";
	}

	@Override
	public void afterChildren() {
		htmlResult += "</ul>";
	}
	
	public String getHtmlResult(){
		return htmlResult;
	}

}
