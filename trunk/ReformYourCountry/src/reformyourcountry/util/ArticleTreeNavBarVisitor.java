package reformyourcountry.util;

import java.util.Date;

import reformyourcountry.model.Article;
import reformyourcountry.web.ContextUtil;
import reformyourcountry.web.UrlUtil;

public class ArticleTreeNavBarVisitor implements ArticleTreeVisitor {

	String htmlResult="";
	boolean isList=false;
	
	public ArticleTreeNavBarVisitor(){}
	public ArticleTreeNavBarVisitor(boolean isList){
		this.isList = isList;
	}
	@Override
	public void processArticle(Article article) {
		
		htmlResult+="<li>";
		
	    if (ContextUtil.getHttpServletRequest().getRequestURL().toString().endsWith(article.getUrl())) {
	    	htmlResult += "<a class=\"current_page_item\" href =\"" + UrlUtil.getAbsoluteUrl("article/")
							+ article.getUrl() + "\"><span>";
		}else{
			htmlResult += "<a href =\"" + UrlUtil.getAbsoluteUrl("article/")
							+ article.getUrl() + "\"><span>";
		}
	    htmlResult += article.getTitle();
	    htmlResult += "</a>";
	    if(isList) {
			if(!article.isPublished()) {
				if (article.getPublishDate() != null && article.getPublishDate().after(new Date())){
					htmlResult+="<span class=\"datepublication\">publié dans "+DateUtil.formatDuration(new Date(), article.getPublishDate() )+"</span>";
					htmlResult+="<div class=\"descriptNotPublish\">"+article.getDescription()+"<div/>";
				} else if (article.getPublishDate() != null && article.getPublishDate().before(new Date())){
					htmlResult+="<span class=\"datepublication\">publié il y a "+DateUtil.formatDuration(new Date(), article.getPublishDate() )+"</span>";
					htmlResult+="<div class=\"descriptPublish\">"+article.getDescription()+"<div/>";
				} else {
					htmlResult+="<span class=\"datepublication\">non publié</span>";
					htmlResult+="<div class=\"descriptNotPublish\">"+article.getDescription()+"<div/>";
				}
				
				
			} else {
				htmlResult+="<span class=\"datepublication\">publié</span>";
				htmlResult+="<div class=\"descriptPublish\">"+article.getDescription()+"<div/>";
			}
			
			
		}
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
