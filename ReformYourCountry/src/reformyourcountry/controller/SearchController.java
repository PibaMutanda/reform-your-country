package reformyourcountry.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import reformyourcountry.repository.ArticleRepository;
import reformyourcountry.search.ArticleSearchResult;
import reformyourcountry.service.IndexManagerService;
import reformyourcountry.service.SearchService;

@Controller
@RequestMapping("/search")
public class SearchController {


	@Autowired SearchService searchService;
	@Autowired IndexManagerService indexManagerService;
	@Autowired ArticleRepository articleRepository;
	

	@RequestMapping(method=RequestMethod.GET)
	public ModelAndView search(@RequestParam("searchtext") String searchtext){
		String errorMsg = null;
		ModelAndView mv= new ModelAndView("search");
		mv.addObject("searchtext",searchtext);
		
		ArticleSearchResult articleSearchResult = searchService.searchArticle(searchtext, null, true);
		mv.addObject("searchResult", articleSearchResult);
			
		if(articleSearchResult.getPublicResults().isEmpty() && articleSearchResult.getPrivateResults().isEmpty()) {
			errorMsg="Il n'existe aucun article ayant "+searchtext+" comme élément.";
			mv.addObject("errorMsg",errorMsg);
		}
		return mv;
	}
	
	
	/** Used by an administrator to recreate the indexes */
	@RequestMapping("/createIndex")
	public ModelAndView createIndex(){
		try {
			indexManagerService.removeIndexes();
		} catch(Exception e){
		}finally {
			indexManagerService.createIndexes();
		}
		
		return new ModelAndView("/home");

	}
}

