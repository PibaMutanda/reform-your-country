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
    @ExceptionHandler(RuntimeException.class)
    public ModelAndView handleException2(Exception ex)
    {
        
        return new ModelAndView("error");
    }
    @RequestMapping("pagenotfound")
    public String handleException2(NoSuchRequestHandlingMethodException ex)
    {
        return "page-not-found";
    }
    @RequestMapping("error")
    public ModelAndView error(Exception ex)
    {
        System.out.println("****************************************errorRuntime******************************");
        return new ModelAndView("error");
    }
    @RequestMapping("error500")
    public ModelAndView error500(Exception ex)
    {
        System.out.println("****************************************500******************************");
        return new ModelAndView("error");
    }
}


