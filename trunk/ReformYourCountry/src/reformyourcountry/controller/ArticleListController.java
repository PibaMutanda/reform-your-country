package reformyourcountry.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/article")
public class ArticleListController {
    
  
    @RequestMapping(method=RequestMethod.GET)
    public ModelAndView articleListDisplay(){
        return new ModelAndView("articlelist");
    }
}

