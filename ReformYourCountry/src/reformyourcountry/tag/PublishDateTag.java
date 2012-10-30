package reformyourcountry.tag;

import java.io.IOException;
import java.util.Date;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.SimpleTagSupport;

import reformyourcountry.model.Article;
import reformyourcountry.repository.ArticleRepository;
import reformyourcountry.util.DateUtil;
import reformyourcountry.web.ContextUtil;

public class PublishDateTag extends SimpleTagSupport{
	
	ArticleRepository articleRepository;
	private String id;
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Override
	public void doTag() throws JspException {
		articleRepository =  ContextUtil.getSpringBean(ArticleRepository.class);
		JspWriter out = this.getJspContext().getOut();
		Article article = articleRepository.find(Long.parseLong(id));
		String result="<span class=\"datepublication\">publié dans "+DateUtil.formatDuration(new Date(), article.getPublishDate() )+"</span>";
		try {
			out.println(result);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
}
