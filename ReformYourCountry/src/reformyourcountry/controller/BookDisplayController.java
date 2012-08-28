package reformyourcountry.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import reformyourcountry.model.Book;
import reformyourcountry.repository.BookRepository;

@Controller
public class BookDisplayController extends BaseController<Book> {
    
    @Autowired BookRepository bookRepository;
    
    @RequestMapping("/book")
    public ModelAndView bookDisplay(@RequestParam("id") long id){
    	Book book = getRequiredEntity(id);
        ModelAndView mv = new ModelAndView("bookdisplay");
        mv.addObject("book",book);
        
        return mv;
    }

}
