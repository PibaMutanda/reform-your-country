package reformyourcountry.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/article")
public class ArticleVideoManagerController {

	@RequestMapping("/videomanager")
	public ModelAndView displayVideo(){
		
		ModelAndView mv = new ModelAndView("articlevideomanager");
		
				
		return mv;
	}
	
}
