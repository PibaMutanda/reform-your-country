package reformyourcountry.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import reformyourcountry.model.Article;
import reformyourcountry.repository.ArticleRepository;
import reformyourcountry.repository.BaseRepository;
@Controller
public class ArticleEditController {
	@Autowired
    ArticleRepository artRep;
	
	@RequestMapping("/articleedit")
	public ModelAndView EditArticle(@RequestParam("id")Long id){
		Article a=artRep.find(id);	
		ModelAndView mv=new ModelAndView("/ArticleEdit");
		mv.addObject("article",a);
		return mv;
	}
	@RequestMapping("/articleeditsubmit")
	public ModelAndView editArticle(@RequestParam("content") String content, 
			                        @RequestParam("title") String title){
		
		ModelAndView mv =new ModelAndView("/TestDisplay");
		mv.addObject("content", content);
		mv.addObject("title", title);
		return mv;
	}
	
}
