package reformyourcountry.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import reformyourcountry.model.Article;
import reformyourcountry.repository.ArticleRepository;
import reformyourcountry.util.DateUtil;

@Controller
public class HomeController {
	
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
	public ModelAndView home(){
		Date toDay = new Date();
		List<ArticleAndDate> listart = new ArrayList<ArticleAndDate>(); 
		List<Article> articleListByDate = articleRepository.findByDate(toDay, 4);  // 4 last articles.
		for(Article article:articleListByDate){
			listart.add(new ArticleAndDate(article));
		}
        return new ModelAndView("home", "articleListByDate", listart);
		
	}
	
//	public ModelAndView displayArticleDate(){
//
//	}
}
