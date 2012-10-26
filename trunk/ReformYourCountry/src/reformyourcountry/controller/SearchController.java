package reformyourcountry.controller;

import java.util.ArrayList;
import java.util.List;

import org.apache.lucene.search.ScoreDoc;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import reformyourcountry.model.Article;
import reformyourcountry.repository.ArticleRepository;
import reformyourcountry.service.IndexManagerService;
import reformyourcountry.service.SearchService;
import reformyourcountry.search.ArticleSearchResult;
import reformyourcountry.search.SearchResult;

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
		
		SearchResult searchResult = searchService.search(searchtext, null, true);
			
		if(searchResult.getPublicResults().isEmpty() && searchResult.getPrivateResults().isEmpty()) {
			errorMsg="Il n'existe aucun article ayant "+searchtext+" comme élément.";
			mv.addObject("errorMsg",errorMsg);

		}
		mv.addObject("searchResult", searchResult);
		return mv;
	}
	@RequestMapping("/createIndex")
	public ModelAndView createIndex(){
		indexManagerService.createIndexes();
		return new ModelAndView("/home");

	}
}

