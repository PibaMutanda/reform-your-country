package reformyourcountry.controller;

import java.util.TreeMap;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.NoSuchRequestHandlingMethodException;

import reformyourcountry.exception.UnauthorizedAccessException;
import reformyourcountry.exception.UserNotFoundException;

@Controller
public class ExceptionController {
//    @RequestMapping("error.htm")
//    public ModelAndView handleException2(UnauthorizedAccessException ex)
//    {
//        
//        TreeMap<String,Object> map=new TreeMap<String,Object>();
//        map.put("isDataChange", true);
//        map.put("isBigError", true);
//        return new ModelAndView(JSONView.RenderObject(map, response));
//    }
    @RequestMapping("pagenotfound")
    public String handleException2(NoSuchRequestHandlingMethodException ex)
    {
        return "page-not-found";
    }
}


