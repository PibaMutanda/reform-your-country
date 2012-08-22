package reformyourcountry.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import reformyourcountry.model.Book;
import reformyourcountry.repository.BookRepository;

@Controller

public class CreateBookController {
    @Autowired BookRepository bookRepository;
    
    @Autowired BookListController booklistController;
    
    @RequestMapping ("/createbook") 
    public ModelAndView createBook(){
        
        ModelAndView mv = new ModelAndView("createbook");
        mv.addObject("bk", new Book()); 
        return mv;
        
    }
    
    @RequestMapping ("/sendnewbook")
    public ModelAndView sendNewBook(@ModelAttribute Book bk ){
        
       // ModelAndView mv = new ModelAndView ("displaybook");
        bookRepository.persist(bk);
       // List<Book> bookList = bookRepository.findAll();
       // mv.addObject("bookList", bookList);
        return booklistController.showBookList() ;
    }
        
    
    
    
    

}
