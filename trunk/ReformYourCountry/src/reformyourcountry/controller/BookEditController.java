package reformyourcountry.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import reformyourcountry.model.Book;
import reformyourcountry.repository.BookRepository;
import reformyourcountry.security.Privilege;
import reformyourcountry.security.SecurityContext;
import reformyourcountry.util.NotificationUtil;

@Controller
@RequestMapping("/book")
public class BookEditController extends BaseController<Book> {

    @Autowired BookRepository bookRepository;
    @Autowired BookListController booklistController;

    @RequestMapping("/create")
    public ModelAndView bookCreate(){
        SecurityContext.assertUserHasPrivilege(Privilege.MANAGE_BOOK);
        return prepareModelAndView(new Book());
    }

    @RequestMapping("/edit")
    public ModelAndView bookEdit(@RequestParam("id") long id){
        SecurityContext.assertUserHasPrivilege(Privilege.MANAGE_BOOK);
        Book book = getRequiredEntity(id);
        return prepareModelAndView(book);
    }	


    private ModelAndView prepareModelAndView(Book book ) {
        ModelAndView mv = new ModelAndView("bookedit");
        mv.addObject("id", book.getId());
        mv.addObject("book", book); 
        return mv; 
    }

    @RequestMapping("/editsubmit")
    public ModelAndView bookEditSubmit(@Valid @ModelAttribute Book book, BindingResult result){
       
        SecurityContext.assertUserHasPrivilege(Privilege.MANAGE_BOOK);
        
        if (result.hasErrors()){
            return new ModelAndView ("bookedit", "book", book);
        }

        Book bookHavingThatAbrev = (Book) bookRepository.findBookByAbrev(book.getAbrev());

        if (book.getId() == null) { // New book instance (not from DB)
            if(bookHavingThatAbrev != null) {
                ModelAndView mv = new ModelAndView ("bookedit", "book", book);
                NotificationUtil.addNotificationMessage("Un autre livre utilise déjà cette abrévation '" + book.getAbrev() + "'");
                return mv;
            }
            if(book.getUrl()==null){
            	ModelAndView mv = new ModelAndView ("bookedit", "book", book);
            	NotificationUtil.addNotificationMessage("Vous devez entrer un fragment d'url");
                return mv;
            }
            bookRepository.persist(book);

        } else {  // Edited book instance.
            if(bookHavingThatAbrev != null && !book.equals(bookHavingThatAbrev)) {
                ModelAndView mv = new ModelAndView ("bookedit", "book", book);
                NotificationUtil.addNotificationMessage("Un autre livre utilise déjà cette abrévation '" + book.getAbrev() + "'");
                return mv;
            }
            bookRepository.merge(book);
        }
        return new ModelAndView ("redirect:/book/"+book.getUrl());//redirect from book display
    }
    
    @RequestMapping ("/remove")
    public ModelAndView removeBook(@RequestParam("id")Long id){
        bookRepository.remove(bookRepository.find(id));
    
        return new ModelAndView ("redirect:/book");
    }


    @ModelAttribute
    public Book findBook(@RequestParam(value ="id", required = false) Long id) {
        if (id == null){ //create
            return new Book();
        } else { //edit
            return getRequiredDetachedEntity(id);
        }
    }

}
