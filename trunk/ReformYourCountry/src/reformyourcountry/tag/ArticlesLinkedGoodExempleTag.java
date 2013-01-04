package reformyourcountry.tag;

import java.io.IOException;
import java.util.List;

import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.SimpleTagSupport;

import reformyourcountry.model.Article;
import reformyourcountry.repository.ArticleRepository;
import reformyourcountry.util.ArticleTreeVisitor;
import reformyourcountry.util.ArticleTreeWalker;
import reformyourcountry.web.ContextUtil;

/** Generates a "tree" of articles with a check box in front of each article */
public class ArticlesLinkedGoodExempleTag extends SimpleTagSupport{


	@SuppressWarnings("unchecked")
	@Override
	public void doTag(){
		try{
			JspWriter out = this.getJspContext().getOut();

			ArticleRepository articleRepository =  ContextUtil.getSpringBean(ArticleRepository.class);
			List<Article> articlesLinkedToGoodExample = (List<Article>)(((PageContext)getJspContext()).getRequest().getAttribute("articleList"));
			ArticleTreeCheckboxVisitor atv = new ArticleTreeCheckboxVisitor(articlesLinkedToGoodExample);
			ArticleTreeWalker atw = new ArticleTreeWalker(atv, articleRepository);
			atw.walk();
			out.write(atv.getHtmlResult());

		}catch(IOException e){
			throw new RuntimeException(e);
		}
	}
	
	
	public class ArticleTreeCheckboxVisitor implements ArticleTreeVisitor {

		String htmlResult="";
		List<Article> articlesLinkedToGoodExample;
		
		public ArticleTreeCheckboxVisitor(List<Article> articlesLinkedToGoodExample){
			this.articlesLinkedToGoodExample=articlesLinkedToGoodExample;
		}
		
		@Override
		public void startArticle(Article article) {
			htmlResult+="<li>";

			/// Is the check box already checkes (goodexample already bound to the article) ? 
			boolean check = false;
			if (article != null && articlesLinkedToGoodExample.contains(article)) {
				check = true;
			}
			htmlResult += "<input type=\"checkbox\" name=\"selectedarticleid\" value=\""+article.getId()+"\" " +
					(check ? " checked='checked'" : "") + "/>";

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
}
