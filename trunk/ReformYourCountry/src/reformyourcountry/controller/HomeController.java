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

import reformyourcountry.controller.ActionListController.ActionItem;
import reformyourcountry.model.Action;
import reformyourcountry.model.Article;
import reformyourcountry.model.GoodExample;
import reformyourcountry.model.User;
import reformyourcountry.model.VoteAction;
import reformyourcountry.model.User.AccountConnectedType;
import reformyourcountry.repository.ActionRepository;
import reformyourcountry.repository.ArticleRepository;
import reformyourcountry.repository.GoodExampleRepository;
import reformyourcountry.repository.VoteActionRepository;
import reformyourcountry.security.SecurityContext;
import reformyourcountry.service.ActionService;
import reformyourcountry.util.DateUtil;

@Controller
public class HomeController {
    @Autowired UsersConnectionRepository usersConnectionRepository;
    

	
	@Autowired ArticleRepository articleRepository;
	@Autowired ActionRepository actionRepository;
	@Autowired GoodExampleRepository goodExampleRepository;
    @Autowired VoteActionRepository voteActionRepository;
    @Autowired ActionService actionService;
	
	@RequestMapping(value={"/home","/"})
	public ModelAndView home( NativeWebRequest request){
		Date toDay = new Date();
		List<ArticleAndDate> listart = new ArrayList<ArticleAndDate>();
		List<GoodExampleAndDate> listgoodexample = new ArrayList<GoodExampleAndDate>();
		
		
		List<Article> articleListByDate = articleRepository.findByDate(toDay, 4);  // 4 last articles.
		List<Action> actionListByDate = actionRepository.findByDate(toDay, 4); // 4 last actions.
		List<GoodExample> goodExampleListByDate = goodExampleRepository.findByDate(toDay, 4);
		
		for(Article article:articleListByDate){
			listart.add(new ArticleAndDate(article));
		}
		
		List<ActionItem> actionItems = new ArrayList<ActionItem>();

//		for(Action action:actionListByDate){
//		    VoteAction va = voteActionRepository.findVoteActionForUser(SecurityContext.getUser(), action.getId());
//            ActionItem actionItem = new ActionItem(action, actionService.getResultNumbersForAction(action), va);
//            actionItems.add(actionItem);        
//		}
//		
		if(!actionListByDate.isEmpty()){
		    List<VoteAction> votesActions = voteActionRepository.findVotesActionForUser(SecurityContext.getUser(), actionListByDate);
		    for(Action action : actionListByDate) {
		        VoteAction correspondingVoteForThisAction = null;  // we search for it.
		        for(VoteAction va:votesActions){
		            if (va.getAction().equals(action)) {  // That's the vote action for the current action.
		                correspondingVoteForThisAction = va;
		                break;
		            }
		        }
		        ActionItem actionItem = new ActionItem(action, correspondingVoteForThisAction);
		        actionItems.add(actionItem);        
		    }
		}
		
		for(GoodExample ge:goodExampleListByDate){
			listgoodexample.add(new GoodExampleAndDate(ge));
		}
		
		ModelAndView mv = new ModelAndView("home");
		mv.addObject("articleListByDate", listart);
		mv.addObject("actionListByDate",actionItems);
		mv.addObject("goodExampleListByDate", listgoodexample);
		
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
	
	public static class ArticleAndDate{
		private Article article;
		
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
	

	
	public static class GoodExampleAndDate{
		
		private GoodExample goodExample;
		
		public GoodExampleAndDate(GoodExample goodexample){
			this.goodExample=goodexample;
		}
		public GoodExample getGoodExample(){
			return goodExample;
		}
		public void setGoodExample(GoodExample goodexample){
			this.goodExample=goodexample;
		}
		public String getDifference() {
			return DateUtil.formatIntervalFromToNowFR(goodExample.getCreatedOn());
		}
	}
}
