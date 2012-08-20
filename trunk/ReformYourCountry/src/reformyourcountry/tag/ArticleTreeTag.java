package reformyourcountry.tag;

import java.io.IOException;
import java.util.Collection;
import java.util.List;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.SimpleTagSupport;

import reformyourcountry.model.Article;
import reformyourcountry.repository.ArticleRepository;
import reformyourcountry.web.ContextUtil;


public class ArticleTreeTag extends SimpleTagSupport{


	ArticleRepository articleRepository;


	private String cssClass;
	private boolean radio; // inserts a radio button in front on each article. 
	private boolean link;  // writes articles titles as links

	public String getCssClass() {
		return cssClass;
	}
	public void setCssClass(String cssClass) {
		this.cssClass = cssClass;
	}
	public boolean isRadio() {
		return radio;
	}
	public void setRadio(boolean radio) {
		this.radio = radio;
	}
	public boolean isLink() {
		return link;
	}
	public void setLink(boolean link) {
		this.link = link;
	}
	@Override
	public void doTag() throws JspException, IOException {
		JspWriter out = this.getJspContext().getOut();
		articleRepository =  (ArticleRepository) ContextUtil.getSpringBean("articleRepository");  // No Spring injection from this class (managed by Tomcat).
		List<Article> articles = articleRepository.findAllWithoutParent();

		displayArticleList(articles, out);
	}

	private void displayArticleList(Collection<Article> articles, JspWriter out) throws JspException, IOException { 
		out.write("<ul>");         
		for (Article child: articles) {
			displayArticle(child, out); 
		}
		out.write("</ul>");          
	}


	private void displayArticle(Article article, JspWriter out) throws JspException, IOException {
		out.write("<li>");      
		out.write(getArticleString(article));
		displayArticleList(article.getChildren(), out);
		out.write("</li>");       
	}
	
	private String getArticleString(Article article) {
		String result = "<div class=\""+cssClass+"\">";
		if (radio == true) {
			result += "<input type=\"radio\" name=\"parentid\" value=\""+article.getId()+"\" />";
		}
		if (link == true) {
            result += "<a href =\"Display?id="+article.getId().toString()+"\">";
		}
		result += article.getTitle();
		if (link == true) {
			result += "</a>";
		}
		result += "</div>";
		return result;
	}	
	
}





