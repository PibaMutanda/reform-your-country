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
	public void processArticle(Article article, boolean isFirstPass) {
		
		if(!isFirstPass) htmlResult+="<ul class=\"subarticle\">";
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
				if(article.getPublishDate()==null) {
					htmlResult+="<span class=\"datepublication\">non publié</span>";
				} else {
					htmlResult+="<span class=\"datepublication\">publié dans "+DateUtil.formatDuration(new Date(), article.getPublishDate() )+"</span>";
				}
				
				htmlResult+="<div class=\"descriptNotPublish\">"+article.getDescription()+"<div/>";
			} else {
				htmlResult+="<div class=\"descriptPublish\">"+article.getDescription()+"<div/>";
			}
			
		}
	    htmlResult+="</li>";
	}

	@Override
	public void preWalk	(){
		htmlResult += "<ul id=\"articletree\">";
	}

	@Override
	public void postWalk() {
		htmlResult += "</ul>";
	}
	
	public String getHtmlResult(){
		return htmlResult;
	}

}
