package reformyourcountry.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import reformyourcountry.model.Action;
import reformyourcountry.model.Article;
import reformyourcountry.model.ArticleVersion;
import reformyourcountry.model.User;
import reformyourcountry.repository.ActionRepository;
import reformyourcountry.repository.ArticleRepository;
import reformyourcountry.repository.ArticleVersionRepository;


@Controller
@RequestMapping("/lastevent")
public class LastEventController {

	@Autowired ArticleRepository articleRepository;
	@Autowired ArticleVersionRepository articleVersionRepository;
	@Autowired ActionRepository actionRepository;
	
	@RequestMapping("")
	public ModelAndView lastEventDisplay(){
		
		return new ModelAndView("lastevent");
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping("/listupdatedevent")
	public ModelAndView listUpdatedEvent(){
		
		List<ArticleVersion> updatedArticleVersions = articleVersionRepository.findAllLastVersion();
		List<Action> listAction = actionRepository.findByUpdateDate();
		
		List<LastEvent> listLastEvent = new ArrayList<LastEvent>();
		
		for(ArticleVersion articleVersion : updatedArticleVersions){
			ArticleVersion previousVersion;  // We look for the other version to compare to.
			List<ArticleVersion> listArticleVersion=articleVersionRepository.findAllVersionForAnArticle(articleVersion.getArticle());
			int idx = listArticleVersion.indexOf(articleVersion);
			if (idx >= listArticleVersion.size()-1) {  // There is no previous version.
				previousVersion = articleVersion;
			} else {
				previousVersion = listArticleVersion.get(idx+1);
			}
			/// Make URL.
			
			listLastEvent.add(new LastEvent(articleVersion.getArticle().getTitle(),
											"/article/versioncompare?id="+previousVersion.getId()+"&id2="+articleVersion.getId(),
											articleVersion.getArticle().getUpdatedOn(),articleVersion.getArticle().getUpdatedOrCreatedBy()));
		 }
			
		 for(Action action: listAction){
			 
			 listLastEvent.add(new LastEvent(action.getTitle(),"/action/"+action.getUrl(),action.getUpdatedOn(),action.getUpdatedOrCreatedBy()));
		 }
			
		    Collections.sort(listLastEvent);
			
			ModelAndView mv = new ModelAndView("lastevent");
			mv.addObject("listlastevent",listLastEvent);
			return mv;			
					
		}
		

	public static class LastEvent implements Comparable<LastEvent>{
		
		private String name;
		private String url;

		private Date date;
		private User user;
		
		public LastEvent(String name, String url,Date date, User user){
			this.name=name;
			this.url=url;
			this.date=date;
			this.user=user;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getUrl() {
			return url;
		}

		public void setUrl(String url) {
			this.url = url;
		}

		public Date getDate() {
			return date;
		}

		public void setDate(Date date) {
			this.date = date;
		}
		public User getUser() {
			return user;
		}

		public void setUser(User user) {
			this.user = user;
		}
		
		@Override
		public int compareTo(LastEvent lastEvent) {
			return this.date.compareTo(lastEvent.date);
		}

		
		
	}
	
}
