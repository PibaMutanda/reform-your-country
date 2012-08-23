package reformyourcountry.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import reformyourcountry.model.Book;
import reformyourcountry.repository.BookRepository;

@Controller

public class BookEditController {
    
    @Autowired BookRepository bookRepository;
    @Autowired BookListController booklistController;
    @Autowired DisplayBookController displayBookController;
    
    @RequestMapping ("/bookedit")
    public ModelAndView bookEdit(@ModelAttribute Book book){
        
        ModelAndView mv = new ModelAndView("bookedit");
        mv.addObject("book",book);
        return mv;
    }
    
    @RequestMapping("/bookeditsubmit")
    public ModelAndView bookEditSubmit(@ModelAttribute Book book){
        bookRepository.merge(book);
        return booklistController.showBookList();
    }
    
        
    @ModelAttribute
    public Book findBook(@RequestParam("id")Long id){
        Book result=bookRepository.find(id);
        return result;
    }

}
