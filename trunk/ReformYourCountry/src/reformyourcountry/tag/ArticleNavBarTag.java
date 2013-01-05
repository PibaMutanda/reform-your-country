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
			} 

			// We are going to highlight in the navBar the selected Article are looking at (if we are looking at an article).
			String currentUrl = ContextUtil.getHttpServletRequest().getRequestURL().toString();
			int posArtUrlDisplayed = currentUrl.indexOf("/article/");
			if (posArtUrlDisplayed != -1) {  // We are displaying an article.
				// But which article?  Try to get article.url from the string.
				String artUrlDisplayed = currentUrl.substring(posArtUrlDisplayed);   // if currentUrl = "sitename.com/aricle/toto", we hope to extract "/article/toto"

				// Do we have that url fragment ("/article/toto") in the left nav bar html? 
				// example, search in the string: "... <li><a href="http://localhost:8080/article/toto"> .... 
				int pos =  htmlResult.indexOf(artUrlDisplayed+"'");  // We add "'" to prevent catching another article  having its name starting with our article name (if we look for "/article/toto", we don't want to stop on "/article/tototo"). 
				
				if (pos >= 0) {  // found

					// Find the position of the char just before href (going backward from position pos)
					int posJustBeforeHref= htmlResult.lastIndexOf("href", pos);  // We are looking for the previous corresponding href position.
					if (posJustBeforeHref > 0)  { // Else it's just strange to find the url with no href before...
						StringBuffer sb1 = new StringBuffer(htmlResult);
						sb1.insert(posJustBeforeHref," class=\'current_page_item\' ");
						htmlResult = sb1.toString();
					}

				}
			}

			out.write(htmlResult);
				
			
			
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
