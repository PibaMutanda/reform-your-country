package reformyourcountry.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.connect.UsersConnectionRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.servlet.ModelAndView;

import reformyourcountry.model.Article;
import reformyourcountry.model.User;
import reformyourcountry.model.User.AccountConnectedType;
import reformyourcountry.repository.ArticleRepository;
import reformyourcountry.security.SecurityContext;
import reformyourcountry.util.DateUtil;

@Controller
public class HomeController {
    @Autowired UsersConnectionRepository usersConnectionRepository;
	public class ArticleAndDate{
		private Article article;
		private String difference;
		
		public ArticleAndDate(Article article){
			this.article=article;
			
		}
		public Article getArticle() {
			return article;
		}
		public void setArticle(Article article) {
			this.article = article;
		}
		public String getDifference() {
			return DateUtil.formatIntervalFromToNowFR(article.getPublishDate());
		}
	}
	
	
	@Autowired ArticleRepository articleRepository;
	
	@RequestMapping(value={"/home","/"})
	public ModelAndView home( NativeWebRequest request){
		Date toDay = new Date();
		List<ArticleAndDate> listart = new ArrayList<ArticleAndDate>(); 
		List<Article> articleListByDate = articleRepository.findByDate(toDay, 4);  // 4 last articles.
		for(Article article:articleListByDate){
			listart.add(new ArticleAndDate(article));
		}
		ModelAndView mv = new ModelAndView("home", "articleListByDate", listart);
		//detect if the user is connected with a social account
		User user = SecurityContext.getUser();
		if(user != null){
		    
		   AccountConnectedType providerSignedIn = (AccountConnectedType) request.getAttribute(LoginController.PROVIDERSIGNEDIN_KEY, RequestAttributes.SCOPE_SESSION);
		 
		    if(providerSignedIn != null)
		    {
		        mv.addObject("accountConnectedType",providerSignedIn);
		    }
		    else
		        mv.addObject("accountConnectedType",AccountConnectedType.LOCAL);
		    
		    //request.removeAttribute(LoginController.PROVIDERSIGNEDIN, RequestAttributes.SCOPE_SESSION);
		    
		}
		
        return mv;
		
	}
	
//	public ModelAndView displayArticleDate(){
//
//	}
}
