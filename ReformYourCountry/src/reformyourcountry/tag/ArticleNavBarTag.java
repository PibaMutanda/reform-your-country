package reformyourcountry.tag;

import java.io.IOException;

import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.SimpleTagSupport;

import reformyourcountry.repository.ArticleRepository;
import reformyourcountry.util.ArticleTreeNavBarVisitor;
import reformyourcountry.util.ArticleTreeWalker;
import reformyourcountry.web.ContextUtil;

public class ArticleNavBarTag extends SimpleTagSupport{
	
	final String LEFTNAV_CACHE = getLeftNavBarCache();  // TODO ?????????????????????????????? pas besoin (Jérôme)
	
	@Override
	public void doTag(){
		try{
			JspWriter out = this.getJspContext().getOut();
			

			String htmlResult="";
			htmlResult = getLeftNavBarCache();
			if (htmlResult == null) { // Empty cache (1st time since web app started, or been invalidated by article add or change)
				ArticleRepository articleRepository =  ContextUtil.getSpringBean(ArticleRepository.class);
				ArticleTreeNavBarVisitor atv = new ArticleTreeNavBarVisitor();
				ArticleTreeWalker atw = new ArticleTreeWalker(atv, articleRepository);
				atw.walk();
				htmlResult = atv.getHtmlResult();
				out.write(htmlResult);
				setLeftNavBarCache(htmlResult);
			} else {  // Not the left nav bar => no cache.
				out.write(htmlResult);
			}
				
			
			
		}catch(IOException e){
			throw new RuntimeException(e);
		}
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
