package reformyourcountry.util;

import reformyourcountry.model.Article;

public class ArticleTreeRadioVisitor implements ArticleTreeVisitor {

	String htmlResult="";
	
	@Override
	public void processArticle(Article article, boolean isFirstPass) {
		
		if(!isFirstPass) htmlResult+="<ul class=\"subarticle\">";
		htmlResult+="<li>";
		
		// We don't display the radio button for the article we are editing and its children (we wouldn't want to create a cycle by making an article the child of one of its children, or itself).
		if (! article.equalsOrIsParentOf(article)) {

			boolean check = false; // should the radio button be (pre-)selected?
			if (article != null && article.equals(article.getParent())) {
				check = true;
			}
			htmlResult += "<input type=\"radio\" name=\"parentid\" value=\""+article.getId()+"\" " +
					(check ? " checked='checked'" : "") + "/>";
		}
		htmlResult += article.getTitle();
		htmlResult+="</li>";
	}

	@Override
	public void preWalk() {
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
