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
import reformyourcountry.service.SearchService.ArticleSearchResult;
import reformyourcountry.service.SearchService.SearchResult;

@Controller
@RequestMapping("/search")
public class SearchController {


	@Autowired SearchService searchservice;
	@Autowired IndexManagerService indexmanagerservice;
	@Autowired ArticleRepository articlerepository;
	

	@RequestMapping(method=RequestMethod.GET)
	public ModelAndView search(@RequestParam("searchtext") String searchtext){
		String errorMsg = null;
		ModelAndView mv= new ModelAndView("search");
		mv.addObject("searchtext",searchtext);
		
		SearchResult resultList = searchservice.search(searchtext, null, true);
		List<ArticleSearchResult> resultListPublic = new ArrayList<ArticleSearchResult>();
		List<ArticleSearchResult> resultListPrivate = new ArrayList<ArticleSearchResult>();
		for(ArticleSearchResult articleSearchResult : resultList.getResults()){
			Article art = articlerepository.find(articleSearchResult.getId());
			if(art.isPublicView() == true ){
				resultListPublic.add(articleSearchResult);
			}
	
			else{
				resultListPrivate.add(articleSearchResult);
			}
		}
	
		if(resultListPublic.isEmpty() && resultListPrivate.isEmpty()) {
			errorMsg="Il n'existe aucun article ayant "+searchtext+" comme élément.";
			mv.addObject("errorMsg",errorMsg);

		}
		mv.addObject("resultListPublic",resultListPublic);
		mv.addObject("resultListPrivate",resultListPrivate);
	
		return mv;
	}
	@RequestMapping("/createIndex")
	public ModelAndView createIndex(){
		indexmanagerservice.createIndexes();
		return new ModelAndView("/home");

	}
}

