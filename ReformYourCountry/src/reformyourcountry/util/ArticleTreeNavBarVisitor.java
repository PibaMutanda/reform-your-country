package reformyourcountry.util;

import reformyourcountry.model.Article;
import reformyourcountry.web.ContextUtil;
import reformyourcountry.web.UrlUtil;

public class ArticleTreeNavBarVisitor implements ArticleTreeVisitor {

	@Override
	public String getArticleString(Article article) {
	    if (ContextUtil.getHttpServletRequest().getRequestURL().toString().endsWith(article.getUrl())) {
					result += "<a class=\"current_page_item\" href =\"" + UrlUtil.getAbsoluteUrl("article/")
							+ article.getUrl() + "\"><span>";
				}else{
					result += "<a href =\"" + UrlUtil.getAbsoluteUrl("article/")
							+ article.getUrl() + "\"><span>";
				}
	}

}
