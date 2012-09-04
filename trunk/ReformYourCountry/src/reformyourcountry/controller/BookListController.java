package reformyourcountry.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import reformyourcountry.model.Book;
import reformyourcountry.repository.BookRepository;

@Controller
public class BookListController extends BaseController<Book>{
    
    @Autowired BookRepository bookRepository;
   
    
    @RequestMapping ("/showbooklist")
    public ModelAndView showBookList(){
       ModelAndView mv = new ModelAndView("displaybooklist");
       List<Book> b = bookRepository.findAllTop();
       List<Book> bother = bookRepository.findAllOther();
        mv.addObject("bookListTop", b);
        mv.addObject("bookListOther",bother);
        return mv;
    }
    
 
    
    @RequestMapping ("/removebook")
    public ModelAndView removeBook(@RequestParam("id")Long id){
        bookRepository.remove(bookRepository.find(id));
    
        return showBookList();
    }
    
   
    
    @RequestMapping ("/bookdetail")
    public ModelAndView detailBook (@RequestParam("id")Long id){
        ModelAndView mv = new ModelAndView("detailbook");
        mv.addObject("book", bookRepository.find(id));
        return mv;
         
        
    }

}
