package reformyourcountry.controller;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import reformyourcountry.controller.ActionListController.ActionItem;
import reformyourcountry.converter.BBConverter;
import reformyourcountry.model.Action;
import reformyourcountry.model.Article;
import reformyourcountry.model.GoodExample;
import reformyourcountry.model.VoteAction;
import reformyourcountry.repository.ActionRepository;
import reformyourcountry.repository.ArticleRepository;
import reformyourcountry.repository.BookRepository;
import reformyourcountry.repository.GoodExampleRepository;
import reformyourcountry.repository.VideoRepository;
import reformyourcountry.repository.VoteActionRepository;
import reformyourcountry.security.Privilege;
import reformyourcountry.security.SecurityContext;
import reformyourcountry.service.ActionService;
import reformyourcountry.service.ArticleService;
import reformyourcountry.tag.ArticleNavBarTag;
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
	@Autowired GoodExampleRepository goodExampleRepository;
	@Autowired ArticleService articleService;

	@RequestMapping(value={"/{articleUrl}"})
	public ModelAndView articleDisplay( @PathVariable("articleUrl") String articleUrl){

		log.debug("display article i get articleUrl"+articleUrl);

		ModelAndView mv = new ModelAndView("articledisplay");

		List<ActionItem> actionItemsChildren = new ArrayList<ActionItem>();

		List<ActionItem> actionItemsParent = new ArrayList<ActionItem>();
		
		Article article = getRequiredEntityByUrl(articleUrl);

		//////// Prepares the list of Action(Item)s to display beside the summary.
		/*we find all article's actions and we create actionItem for the action widget*/
		for (Action action : article.getActions()){
			VoteAction va = voteActionRepository.findVoteActionForUser(SecurityContext.getUser(), action.getId());
			ActionItem actionItem = new ActionItem(action, va);
			actionItemsParent.add(actionItem);   
		}
		
		//this one serves for sort the action list of children, because we should have the same action in differents children
		for(Article a : article.getChildrenAndSubChildren()){
			for(Action ac : a.getActions()){
				VoteAction va = voteActionRepository.findVoteActionForUser(SecurityContext.getUser(), ac.getId());
			    ActionItem actionItem = new ActionItem(ac,  va);
				if(!actionItemsParent.contains(actionItem) && !actionItemsChildren.contains(actionItem)){
					actionItemsChildren.add(actionItem);
				}
			}
		}
		
		//we find the lats five GoodExample for this article
		List<GoodExample> lastFiveExample = goodExampleRepository.findLastGoodExample(article, 5);
		
		mv.addObject("lastFiveExample", lastFiveExample);
		mv.addObject("actionItemsChildren", actionItemsChildren);
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

		mv.addObject("showContent", (article.isPublished() || SecurityContext.isUserHasPrivilege(Privilege.VIEW_UNPUBLISHED_ARTICLE)));

		mv.addObject("videoList",videoRepository.findAllVideoForAnArticle(article));
		return mv;
	}
	

	@RequestMapping(value={"/a_classer/{articleUrl}"})
	public ModelAndView toClassifyDisplay( @PathVariable("articleUrl") String articleUrl){
		SecurityContext.assertUserHasPrivilege(Privilege.VIEW_UNPUBLISHED_ARTICLE);
		ModelAndView mv = new ModelAndView("articledisplaytoclassify");

		Article article = getRequiredEntityByUrl(articleUrl);
		mv.addObject("article", article);
		
		// For the breadcrumb
		mv.addObject("parentsPath", article.getParentPath());

		mv.addObject("showContent", (article.isPublished() || SecurityContext.isUserHasPrivilege(Privilege.VIEW_UNPUBLISHED_ARTICLE)));
		BBConverter bbc = new BBConverter(bookRepository, articleRepository,actionRepository,false);
		mv.addObject("articleToClassify", bbc.transformBBCodeToHtmlCode(article.getLastVersion().getToClassify()));
		return mv;
	}


}
