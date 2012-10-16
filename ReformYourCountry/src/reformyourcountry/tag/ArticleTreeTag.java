package reformyourcountry.tag;

import java.io.IOException;
import java.util.Collection;
import java.util.List;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.SimpleTagSupport;

import reformyourcountry.model.Article;
import reformyourcountry.repository.ArticleRepository;
import reformyourcountry.web.ContextUtil;
import reformyourcountry.web.UrlUtil;

public class ArticleTreeTag extends SimpleTagSupport{


	
	ArticleRepository articleRepository;

	private String cssClass;
	private boolean radio; // inserts a radio button in front on each article. 
	private boolean link;  // writes articles titles as links
	private Article articleFromRequest;  // Article concerned, passed by the controller (if any). 

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
	public void doTag() throws JspException {
		try {
			articleFromRequest =  (Article)(((PageContext)getJspContext()).getRequest().getAttribute("article")); // Placed by the controller in case of edit

			JspWriter out = this.getJspContext().getOut();
			articleRepository =  ContextUtil.getSpringBean(ArticleRepository.class);  // No Spring injection from this class (managed by Tomcat).
			List<Article> articles = articleRepository.findAllWithoutParent();

			// If we show radio buttons to select a parent, we display an extra radio button on the top to select a (virtual) root (= no parent).

			if (radio) {
				out.println("<input type='radio' name='parentid' value=''" +
						((articleFromRequest == null || articleFromRequest.getParent() == null) ? " checked='checked'" : "") + 
						 "'/>" +
						 " pas d'article parent");
			}

			displayArticleList(articles, out,true);
			
		} catch(IOException e) {
			throw new RuntimeException(e);
		}
	}
	
	private void displayArticleList(Collection<Article> articles, JspWriter out,boolean isFirstPass) throws JspException, IOException { 
		if (isFirstPass){
		    out.write("<ul id=\"articletree\">");         
		}else{
            out.write("<ul class=\"subarticle\">");    
		    
		}
		for (Article child: articles) {
			displayArticle(child, out); 
		}
		out.write("</ul>");          
	}


	private void displayArticle(Article article, JspWriter out) throws JspException, IOException { 
	    // class=\"current_page_item\"
		out.write("<li>"+getArticleString(article));
		displayArticleList(article.getChildren(), out,false);   
		out.write("</li>");
	}
	
	private String getArticleString(Article article) {
		String result = "";
		if (radio == true) {
			if (articleFromRequest == null) {
				throw new RuntimeException("Bug: if radio = true, an article should be attached to the request");
			}
			
			// In general, we display radio buttons. but not for the article itself and its children (would not like to create a cycle by begin parent of your child).
			if (! articleFromRequest.equalsOrIsParentOf(article)) { 

				boolean check = false; // should the radio button be (pre-)selected?
				if (article != null && article.equals(articleFromRequest.getParent())) {
					check = true;
				}
				result += "<input type=\"radio\" name=\"parentid\" value=\""+article.getId()+"\" " +
						(check ? " checked='checked'" : "") + "/>";
			}
		}
		if (link == true) {
            if (ContextUtil.getHttpServletRequest().getRequestURL().toString().endsWith(article.getUrl())) {
				result += "<a class=\"current_page_item\" href =\"" + UrlUtil.getAbsoluteUrl("article/")
						+ article.getUrl() + "\"><span>";
			}else{
				result += "<a href =\"" + UrlUtil.getAbsoluteUrl("article/")
						+ article.getUrl() + "\"><span>";
			}
		}
		result += article.getTitle();
		if (link == true) {
			result += "</a>";
		}
		return result;
	}	
	
}





