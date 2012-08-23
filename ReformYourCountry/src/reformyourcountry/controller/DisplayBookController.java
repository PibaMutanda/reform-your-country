package reformyourcountry.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import reformyourcountry.model.Book;
import reformyourcountry.repository.BookRepository;

@Controller
public class DisplayBookController {
    
    
    
    @Autowired BookRepository bookRepository;
    
    
    @RequestMapping("/book")
    public ModelAndView showBook(@RequestParam long id){
        
        
        Book book = bookRepository.find(id);
        
        ModelAndView mv = new ModelAndView("displaybook");
        mv.addObject(book);
        
        return mv;
    }

}
