package reformyourcountry.util;

import reformyourcountry.model.Article;

public class ArticleTreeRadioVisitor implements ArticleTreeVisitor {

	@Override
	public String getArticleString(Article article) {
		if (article == null) {
			throw new RuntimeException("Bug: if radio = true, an article should be attached to the request");
		}
		
		// In general, we display radio buttons. but not for the article itself and its children (would not like to create a cycle by begin parent of your child).
		if (! article.equalsOrIsParentOf(article)) { 

			boolean check = false; // should the radio button be (pre-)selected?
			if (article != null && article.equals(article.getParent())) {
				check = true;
			}
			result += "<input type=\"radio\" name=\"parentid\" value=\""+article.getId()+"\" " +
					(check ? " checked='checked'" : "") + "/>";
		}
	}

}
