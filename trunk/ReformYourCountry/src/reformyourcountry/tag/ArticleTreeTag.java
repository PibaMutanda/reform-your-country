package reformyourcountry.tag;

import java.io.IOException;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.SimpleTagSupport;

import reformyourcountry.model.Article;
import reformyourcountry.repository.ArticleRepository;
import reformyourcountry.service.ArticleService;
import reformyourcountry.util.DateUtil;
import reformyourcountry.web.ContextUtil;
import reformyourcountry.web.UrlUtil;

public class ArticleTreeTag extends SimpleTagSupport{
	
	ArticleRepository articleRepository;
	ArticleService articleService;

	private String cssClass;
	private boolean radio; // inserts a radio button in front on each article. 
	private boolean link;  // writes articles titles as links
	private boolean description; //if we are in the article list page, we want a short description under the link
	
	final String LEFTNAV_CACHE = getLeftNavBarCache();
		
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

	public boolean isDescription() {
		return description;
	}
	public void setDescription(boolean description) {
		this.description = description;
	}
	
	
	@Override
	public void doTag() throws JspException {
		try {
			articleFromRequest =  (Article)(((PageContext)getJspContext()).getRequest().getAttribute("article")); // Placed by the controller in case of edit

			JspWriter out = this.getJspContext().getOut();
			articleRepository =  ContextUtil.getSpringBean(ArticleRepository.class);  // No Spring injection from this class (managed by Tomcat).
			articleService =  ContextUtil.getSpringBean(ArticleService.class); 
			List<Article> articles = null;
						
			// If we show radio buttons to select a parent, we display an extra radio button on the top to select a (virtual) root (= no parent).

			if (radio) {
				out.println("<input type='radio' name='parentid' value=''" +
						((articleFromRequest == null || articleFromRequest.getParent() == null) ? " checked='checked'" : "") + 
						"'/>" +
						" pas d'article parent");
			}
			
			String htmlResult="";
			if (!radio && link) { // For the left nav bar, we cache the results.
				htmlResult = getLeftNavBarCache();

				if (htmlResult == null) { // Empty cache (1st time since web app started, or been invalidated by article add or change)
					htmlResult = displayArticleList(articles, true);
					setLeftNavBarCache(htmlResult);
				} 
			} else {  // Not the left nav bar => no cache.
				htmlResult = displayArticleList(articles, true);
			}
			
			
			out.write(htmlResult);

		} catch(IOException e) {
			throw new RuntimeException(e);
		}
	}
	
	private String displayArticleList(Collection<Article> articles, boolean isFirstPass) throws JspException, IOException { 
		
		String result = "";
		
		if (isFirstPass) {
			articles=articleRepository.findAllWithoutParent();
			
		    result+="<ul id=\"articletree\">";         
		} else {
            result+="<ul class=\"subarticle\">";    
		}

		for (Article child: articles) {
			result += displayArticle(child); 
		}
		
		result+="</ul>";   
		
		return result;
	}


	private String displayArticle(Article article) throws JspException, IOException { 
	    // class=\"current_page_item\"
		String result="";		
		result += "<li>" + getArticleString(article);
		result += displayArticleList(article.getChildren(), false);
		result += "</li>";
		return result;
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
		
		if(description == true) {
			if(!article.isPublished()) {
				if(article.getPublishDate()==null) {
					result+="<span class=\"datepublication\">non publié</span>";
				} else {
					result+="<span class=\"datepublication\">publié dans "+DateUtil.formatDuration(new Date(), article.getPublishDate() )+"</span>";
				}
				
				result+="<div class=\"descriptNotPublish\">"+article.getDescription()+"<div/>";
			} else {
				result+="<div class=\"descriptPublish\">"+article.getDescription()+"<div/>";
			}
			
		}
		
		return result;
	}	
	public static void setLeftNavBarCache(String htmlTreeMenu){
		ContextUtil.getServletContext().setAttribute("LeftNavBarCache", htmlTreeMenu);
    }
	 
	public static String getLeftNavBarCache(){
		 return	 (String) ContextUtil.getServletContext().getAttribute("LeftNavBarCache");
	}
	
	public static void invalidateNavBarCache(){
		 setLeftNavBarCache(null);
	}
}





