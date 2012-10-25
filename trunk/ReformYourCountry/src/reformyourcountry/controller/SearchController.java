package reformyourcountry.controller;

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
	public ModelAndView search(@RequestParam("keyword") String keyWord){
		ModelAndView mv= new ModelAndView("testsearch");
		searchservice.search(keyWord, true, false, false, false, false, null, true);
		List<ScoreDoc> resultList = searchservice.getResults();
		List<ArticleSearchResult> 
		for(ScoreDoc scoreDoc : resultList){
		
//			System.out.println(searchservice.getResult(scoreDoc));
		}
		return mv;
	}
	@RequestMapping("/createIndex")
	public ModelAndView createIndex(){
		indexmanagerservice.createIndexes();
		return new ModelAndView("/home");
		
	}
}

