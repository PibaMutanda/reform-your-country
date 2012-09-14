package reformyourcountry.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import reformyourcountry.exception.ExceptionUtil;
import reformyourcountry.exception.InvalidUrlException;
import reformyourcountry.exception.UnauthorizedAccessException;
import reformyourcountry.web.ContextUtil;
import reformyourcountry.web.UrlUtil;

@Controller
public class ErrorController {

    public static String SANS_MALICE = "Si vous êtes parvenu ici, sans malice, en cliquant sur un lien du site, merci de nous avertir.";
    
    @RequestMapping("error")  // We usually come here because a rule in web.xml
    public ModelAndView error(HttpServletRequest request) {
        String stackTrace;
        ModelAndView mv = new ModelAndView("error");
        Throwable throwable = (Throwable) request.getAttribute("javax.servlet.error.exception");
        
        // Should the error page redirect automatically to the home page after a few seconds?
        if (throwable instanceof UnauthorizedAccessException) {
            mv.addObject("message", "Vous n'êtes pas autorisé à voir cette page. " + SANS_MALICE);
            setRedirect(mv);
        } else if (throwable instanceof InvalidUrlException) {
            mv.addObject("message", "L'URL dans votre navigateur est invalide. " + SANS_MALICE + "<br/>" +
            		"Précision: " + ((InvalidUrlException)throwable).getMessageToUser());
        }
        
        // Should we display the stacktrace?
        if(ContextUtil.devMode) {
            stackTrace = ExceptionUtil.getStringBatchUpdateExceptionStackTrace(throwable, true);
            mv.addObject("stackTrace", stackTrace);
        }
        
        return mv;
    }

    // Set the necessary attribute to activate the redirection mechanism.
    private void setRedirect(ModelAndView mv) {
        if (!ContextUtil.devMode) {  // We should not redirect in dev mode (the developer wants to look at the exception ;-) 
            mv.addObject("redirectUrl", UrlUtil.getAbsoluteUrl("home"));
        }
    }
}


