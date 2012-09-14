package reformyourcountry.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import reformyourcountry.exception.ExceptionUtil;
import reformyourcountry.exception.UnauthorizedAccessException;
import reformyourcountry.web.ContextUtil;
import reformyourcountry.web.UrlUtil;

@Controller
public class ErrorController {

    @RequestMapping("error")  // We usually come here because a rule in web.xml
    public ModelAndView error(HttpServletRequest request) {
        String stackTrace;
        ModelAndView mv = new ModelAndView("error");
        Throwable throwable = (Throwable) request.getAttribute("javax.servlet.error.exception");
        
        // Should the error page redirect automatically to the home page after a few seconds?
        if (!ContextUtil.devMode  // We should not redirect in dev mode (the developer wants to look at the exception ;-) 
                && throwable instanceof UnauthorizedAccessException) {
            mv.addObject("redirectUrl", UrlUtil.getAbsoluteUrl("home"));
        }
        
        // Should we display the stacktrace?
        if(ContextUtil.devMode) {
            stackTrace = ExceptionUtil.getStringBatchUpdateExceptionStackTrace(throwable, true);
            mv.addObject("stackTrace", stackTrace);
        }
        
        return mv;
    }
}


