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

import reformyourcountry.service.IndexManagerService;
import reformyourcountry.service.SearchService;
import reformyourcountry.service.SearchService.ArticleSearchResult;

@Controller
@RequestMapping("/search")
public class SearchController {


	@Autowired SearchService searchservice;
	@Autowired IndexManagerService indexmanagerservice;

	@RequestMapping(method=RequestMethod.GET)
	public ModelAndView search(@RequestParam("searchtext") String searchtext){
		String errorMsg = null;
		ModelAndView mv= new ModelAndView("search");
		mv.addObject("searchtext",searchtext);
		searchservice.search(searchtext, true, false, false, false, false, null, true);
		List<ScoreDoc> scoredocList = searchservice.getResults();
		List<ArticleSearchResult> resultList = new ArrayList<ArticleSearchResult>(); 
		for(ScoreDoc scoreDoc : scoredocList){

			resultList.add(searchservice.getResult(scoreDoc));

		}
		if(resultList.isEmpty()) {
			errorMsg="Il n'existe aucun article ayant "+searchtext+" comme élément.";
			mv.addObject("errorMsg",errorMsg);

		}
		mv.addObject("resultList",resultList);
		return mv;
	}
	@RequestMapping("/createIndex")
	public ModelAndView createIndex(){
		indexmanagerservice.createIndexes();
		return new ModelAndView("/home");

	}
}

