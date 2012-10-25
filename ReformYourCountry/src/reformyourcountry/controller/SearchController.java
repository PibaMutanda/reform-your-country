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

import reformyourcountry.service.ArticleService;
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
		
		ModelAndView mv= new ModelAndView("search");
		mv.addObject("searchtext",searchtext);
		searchservice.search(searchtext, true, false, false, false, false, null, true);
		List<ScoreDoc> resultList = searchservice.getResults();
		List<ArticleSearchResult> articlesResult = new ArrayList<ArticleSearchResult>(); 
		for(ScoreDoc scoreDoc : resultList){
		
			articlesResult.add(searchservice.getResult(scoreDoc));
//			System.out.println(searchservice.getResult(scoreDoc));
		}
		mv.addObject("resultList",articlesResult);
		return mv;
	}
	@RequestMapping("/createIndex")
	public ModelAndView createIndex(){
		indexmanagerservice.createIndexes();
		return new ModelAndView("/home");
		
	}
}

