package reformyourcountry.tag;

import java.io.IOException;

import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.SimpleTagSupport;

import reformyourcountry.model.Article;
import reformyourcountry.repository.ArticleRepository;
import reformyourcountry.util.ArticleListVisitor;
import reformyourcountry.util.ArticleTreeWalker;
import reformyourcountry.web.ContextUtil;

public class ArticleListTag extends SimpleTagSupport{

	private Article parentArticle;

	@Override
	public void doTag(){
		try{
			JspWriter out = this.getJspContext().getOut();

			ArticleRepository articleRepository =  ContextUtil.getSpringBean(ArticleRepository.class);
			ArticleListVisitor atv = new ArticleListVisitor();
			ArticleTreeWalker atw = new ArticleTreeWalker(atv, articleRepository, parentArticle);
			atw.walk();
			out.write(atv.getHtmlResult());

		} catch(IOException e) {
			throw new RuntimeException(e);
		}
	}
	
	
	
	public Article getParentArticle() {
		return parentArticle;
	}

	public void setParentArticle(Article parentArticle) {
		this.parentArticle = parentArticle;
	}

}
