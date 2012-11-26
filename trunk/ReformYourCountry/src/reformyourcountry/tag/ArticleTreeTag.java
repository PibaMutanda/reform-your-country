package reformyourcountry.tag;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.SimpleTagSupport;

import reformyourcountry.model.Article;
import reformyourcountry.repository.ArticleRepository;
import reformyourcountry.service.ArticleService;
import reformyourcountry.util.ArticleTreeRadioVisitor;
import reformyourcountry.util.ArticleTreeWalker;
import reformyourcountry.web.ContextUtil;

public class ArticleTreeTag extends SimpleTagSupport{
	
	ArticleRepository articleRepository;
	ArticleService articleService;

	private String cssClass;
	
	private Article articleFromRequest;  // Article concerned, passed by the controller (if any). 

	public String getCssClass() {
		return cssClass;
	}
	public void setCssClass(String cssClass) {
		this.cssClass = cssClass;
	}
	
	@Override
	public void doTag() throws JspException {
		try {
			articleFromRequest =  (Article)(((PageContext)getJspContext()).getRequest().getAttribute("article")); // Placed by the controller in case of edit

			JspWriter out = this.getJspContext().getOut();
			articleRepository =  ContextUtil.getSpringBean(ArticleRepository.class);  // No Spring injection from this class (managed by Tomcat).
						
			// We display an extra radio button on the top to select a (virtual) root (= no parent).
			out.println("<input type='radio' name='parentid' value=''"+
						((articleFromRequest == null || articleFromRequest.getParent() == null) ? " checked='checked'" : "")+
						"'/> pas d'article parent");
			
			ArticleTreeRadioVisitor atv = new ArticleTreeRadioVisitor(articleFromRequest);
			ArticleTreeWalker atw = new ArticleTreeWalker(atv, articleRepository);
			atw.walk();
			out.write(atv.getHtmlResult());

		} catch(IOException e) {
			throw new RuntimeException(e);
		}
	}
	
}