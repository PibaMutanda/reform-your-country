package reformyourcountry.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/article")
public class ArticleVideoManagerController {

	@RequestMapping("/videomanager")
	public ModelAndView articleVideoManager(@RequestParam("id") int articleId){
		
		ModelAndView mv = new ModelAndView("articlevideomanager");
		
				
		return mv;
	}
	
}
