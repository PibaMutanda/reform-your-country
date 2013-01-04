package reformyourcountry.util;

import reformyourcountry.model.Article;

public class ArticleTreeRadioVisitor implements ArticleTreeVisitor {

	String htmlResult="";
	Article articleFromRequest;
	/**
	 * Visitor used to create a radio button list (when changing the parents of an article)
	 * @param articleFromRequest The article which parent we are changing
	 */
	public ArticleTreeRadioVisitor(Article articleFromRequest){
		this.articleFromRequest=articleFromRequest;
	}

	@Override
	public void startArticle(Article article) {
		
		htmlResult+="<li>";
		
		// We don't display the radio button for the article we are editing and its children (we wouldn't want to create a cycle by making an article the child of one of its children, or itself).
		if (! articleFromRequest.equalsOrIsParentOf(article)) {

			boolean check = false; // should the radio button be (pre-)selected?
			if (article != null && article.equals(articleFromRequest.getParent())) {
				check = true;
			}
			htmlResult += "<input type=\"radio\" name=\"parentid\" value=\""+article.getId()+"\" " +
					(check ? " checked='checked'" : "") + "/>";
		}
		htmlResult += article.getTitle();
	}

	@Override
	public void endArticle(Article article) {
		htmlResult+="</li>";
	}
	
	@Override
	public void beforeChildren(int recurtionLevel) {
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
