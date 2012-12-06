package reformyourcountry.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import reformyourcountry.repository.ArticleRepository;
import reformyourcountry.search.SearchResult;
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
	    List<SearchResult> searchResult;

	    searchResult = searchService.search(searchtext);

	    mv.addObject("searchResult", searchResult);

	    if (searchResult.isEmpty() ) {
	        mv.addObject("noResult", true);
	    }
	    return mv;
	}


	
	
}

