package reformyourcountry.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import reformyourcountry.model.Book;
import reformyourcountry.repository.BookRepository;

@Controller
@RequestMapping("/book")
public class BookListController extends BaseController<Book>{
    
    @Autowired BookRepository bookRepository;
   
    
    @RequestMapping(method=RequestMethod.GET)
    public ModelAndView showBookList(){
       ModelAndView mv = new ModelAndView("booklist");
       List<Book> b = bookRepository.findAllTop();
       List<Book> bother = bookRepository.findAllOther();
        mv.addObject("bookListTop", b);
        mv.addObject("bookListOther",bother);
        return mv;
    }
}
