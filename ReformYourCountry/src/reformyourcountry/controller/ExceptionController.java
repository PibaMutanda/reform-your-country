package reformyourcountry.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.multiaction.NoSuchRequestHandlingMethodException;

import reformyourcountry.exception.UnauthorizedAccessException;
import reformyourcountry.exception.UserNotFoundException;

@Controller
public class ExceptionController {
    @ExceptionHandler(UnauthorizedAccessException.class)
    public String handleException2(UnauthorizedAccessException ex)
    {
        
        return "error";
    }
    @ExceptionHandler(NoSuchRequestHandlingMethodException.class)
    public String handleException2(NoSuchRequestHandlingMethodException ex)
    {
        return "page-not-found";
    }
}
