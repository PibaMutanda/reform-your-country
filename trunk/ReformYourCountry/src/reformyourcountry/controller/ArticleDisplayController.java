package reformyourcountry.controller;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import reformyourcountry.controller.ActionListController.ActionItem;
import reformyourcountry.converter.BBConverter;
import reformyourcountry.model.Action;
import reformyourcountry.model.Article;
import reformyourcountry.model.VoteAction;
import reformyourcountry.repository.ActionRepository;
import reformyourcountry.repository.ArticleRepository;
import reformyourcountry.repository.BookRepository;
import reformyourcountry.repository.VideoRepository;
import reformyourcountry.repository.VoteActionRepository;
import reformyourcountry.security.Privilege;
import reformyourcountry.security.SecurityContext;
import reformyourcountry.service.ActionService;
import reformyourcountry.util.DateUtil;
import reformyourcountry.util.Logger;

@Controller
@RequestMapping(value={"/article"})
public class ArticleDisplayController extends BaseController<Article> {

	@Logger Log log;
	@Autowired ActionRepository actionRepository;
	@Autowired BookRepository bookRepository;
	@Autowired ArticleRepository articleRepository;
	@Autowired VideoRepository videoRepository;
	@Autowired VoteActionRepository voteActionRepository;
	@Autowired ActionService actionService;

	@RequestMapping(value={"/{articleUrl}"})
	public ModelAndView articleDisplay( @PathVariable("articleUrl") String articleUrl){

		log.debug("display article i get articleUrl"+articleUrl);

		ModelAndView mv = new ModelAndView("articledisplay");

		List<ActionItem> actionItemsChildrenUnsorted = new ArrayList<ActionItem>();

		List<ActionItem> actionItemsParent = new ArrayList<ActionItem>();
		

		Article article = getRequiredEntityByUrl(articleUrl);

		//////// Prepares the list of Action(Item)s to display beside the summary.
		/*we find all article's actions and we create actionItem for the action widget*/
		for (Action action : article.getActions()){
			VoteAction va = voteActionRepository.findVoteActionForUser(SecurityContext.getUser(), action.getId());
			ActionItem actionItem = new ActionItem(action, actionService.getResultNumbersForAction(action), va);
			actionItemsParent.add(actionItem);   
		}

		//this one serves for sort the action list of children, because we should have the same action in differents children
		Set<ActionItem> actionItemChildren = new HashSet<ActionItem>();
		
		if(!article.getChildren().isEmpty()){
			for(Article a : article.getChildren()){
				for(Action ac : a.getActions()){
					VoteAction va = voteActionRepository.findVoteActionForUser(SecurityContext.getUser(), ac.getId());
					if(!actionItemsParent.contains(ac)){
  					    ActionItem actionItem = new ActionItem(ac, actionService.getResultNumbersForAction(ac), va);
						actionItemsChildrenUnsorted.add(actionItem);
					}
				}
			}
			Set<Action> action = new HashSet<Action>();
			for(ActionItem a : actionItemsChildrenUnsorted){
				if(!action.contains(a.action)){
					action.add(a.action);
					actionItemChildren.add(a);
				}
			}
		}
		mv.addObject("actionItemChildren", actionItemChildren);
		mv.addObject("actionItemsParent", actionItemsParent);
		log.debug("i found "+article.getTitle());
		log.debug("his content"+article.getLastVersion().getContent());

		mv.addObject("article", article);

		// For the breadcrumb
		mv.addObject("parentsPath", article.getParentPath());


		///// Get publishDate in an usable int format for the countdown
		if (article.getPublishDate() != null && article.getPublishDate().after(new Date())){
			Calendar publishCalendar = Calendar.getInstance();
			publishCalendar.setTime(article.getPublishDate());
			int year = publishCalendar.get(Calendar.YEAR);
			int month = publishCalendar.get(Calendar.MONTH);
			int day = publishCalendar.get(Calendar.DAY_OF_MONTH);
			mv.addObject("publishYear", year);
			mv.addObject("publishMonth", month);
			mv.addObject("publishDay", day);
			mv.addObject("displayDate", DateUtil.formatyyyyMMdd(article.getPublishDate()));
		}

		mv.addObject("showContent", (article.isPublished() || SecurityContext.isUserHasPrivilege(Privilege.EDIT_ARTICLE)));
		mv.addObject("articleContent",article.getLastVersionRenderedContent());
		mv.addObject("articleSummary",article.getLastVersionRenderdSummary());

		mv.addObject("videoList",videoRepository.findAllVideoForAnArticle(article));
		return mv;
	}



	@RequestMapping(value={"/a_classer/{articleUrl}"})
	public ModelAndView toClassifyDisplay( @PathVariable("articleUrl") String articleUrl){
		SecurityContext.assertUserHasPrivilege(Privilege.EDIT_ARTICLE);
		ModelAndView mv = new ModelAndView("articledisplaytoclassify");

		Article article = getRequiredEntityByUrl(articleUrl);
		mv.addObject("article", article);

		// For the breadcrumb
		mv.addObject("parentsPath", article.getParentPath());

		mv.addObject("showContent", (article.isPublished() || SecurityContext.isUserHasPrivilege(Privilege.EDIT_ARTICLE)));
		BBConverter bbc = new BBConverter(bookRepository, articleRepository,actionRepository);
		mv.addObject("articleToClassify", bbc.transformBBCodeToHtmlCode(article.getLastVersion().getToClassify()));
		return mv;
	}


}
