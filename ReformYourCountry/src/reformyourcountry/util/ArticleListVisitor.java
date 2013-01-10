package reformyourcountry.util;

import java.util.Date;

import reformyourcountry.model.Article;
import reformyourcountry.web.ContextUtil;
import reformyourcountry.web.UrlUtil;

public class ArticleListVisitor implements ArticleTreeVisitor {

	String htmlResult="";
	
	@Override
	public void startArticle(Article article) {

		htmlResult+="<li>";


		htmlResult += "<a href='/article/" + article.getUrl() + "'><span>";

		htmlResult += article.getTitle();
		htmlResult += "</a>";

		if(!article.isPublished()) {
			if (article.getPublishDate() != null){
				htmlResult+="<span class=\"datepublication\">publié "+DateUtil.formatIntervalFromToNowFR(article.getPublishDate() )+"</span>";

				if(article.getDescription()!=null)
					htmlResult+="<div class=\"descriptNotPublish\">"+article.getDescription()+"<div/>";
				else
					htmlResult+="<br/><br/>";

			} else {
				htmlResult+="<span class=\"datepublication\">non publié</span>";

				if(article.getDescription()!=null)
					htmlResult+="<div class=\"descriptNotPublish\">"+article.getDescription()+"<div/>";
				else
					htmlResult+="<br/><br/>";
			}


		} else {
			htmlResult+="<span class=\"datepublication\">publié</span>";
			if(article.getDescription()!=null)
				htmlResult+="<div class=\"descriptNotPublish\">"+article.getDescription()+"<div/>";
			else
				htmlResult+="<br/><br/>";
		}


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
