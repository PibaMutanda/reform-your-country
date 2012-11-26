package reformyourcountry.tag;

import java.io.IOException;

import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.SimpleTagSupport;

import reformyourcountry.repository.ArticleRepository;
import reformyourcountry.util.ArticleTreeNavBarVisitor;
import reformyourcountry.util.ArticleTreeWalker;
import reformyourcountry.web.ContextUtil;

public class ArticleListTag extends SimpleTagSupport{

	@Override
	public void doTag(){
		try{
			JspWriter out = this.getJspContext().getOut();

			ArticleRepository articleRepository =  ContextUtil.getSpringBean(ArticleRepository.class);
			ArticleTreeNavBarVisitor atv = new ArticleTreeNavBarVisitor(true);
			ArticleTreeWalker atw = new ArticleTreeWalker(atv, articleRepository);
			atw.walk();
			out.write(atv.getHtmlResult());

		}catch(IOException e){
			throw new RuntimeException(e);
		}
	}

}
