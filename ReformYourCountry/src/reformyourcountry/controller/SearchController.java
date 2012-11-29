package reformyourcountry.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import reformyourcountry.model.BaseEntity;
import reformyourcountry.repository.ArticleRepository;
import reformyourcountry.search.ArticleSearchResult;
import reformyourcountry.security.Privilege;
import reformyourcountry.security.SecurityContext;
import reformyourcountry.service.IndexManagerService;
import reformyourcountry.service.SearchService;

@Controller
@RequestMapping("/search")
public class SearchController{


	@Autowired SearchService searchService;
	@Autowired IndexManagerService indexManagerService;
	@Autowired ArticleRepository articleRepository;
	

	@RequestMapping(method=RequestMethod.GET)
	public ModelAndView search(@RequestParam("searchtext") String searchtext){
		ModelAndView mv= new ModelAndView("search");
		mv.addObject("searchtext",searchtext);
		ArticleSearchResult articleSearchResult;
		if(SecurityContext.isUserHasPrivilege(Privilege.MANAGE_ARTICLE)){
			articleSearchResult = searchService.searchArticle(searchtext, null, true, true);
		}else{
			articleSearchResult = searchService.searchArticle(searchtext, null, false, true);
		}
		
		mv.addObject("searchResult", articleSearchResult);
			
		if (articleSearchResult.getResults().isEmpty() ) {
			mv.addObject("noResult", true);
		}
		return mv;
	}
	

	
	
}

