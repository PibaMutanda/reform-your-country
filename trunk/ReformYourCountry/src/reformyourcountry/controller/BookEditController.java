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

@Controller
public class BookEditController extends BaseController<Book> {

	@Autowired BookRepository bookRepository;
	@Autowired BookListController booklistController;

	@RequestMapping("/bookcreate")
	public ModelAndView bookCreate(){
		return prepareModelAndView(new Book());
	}

	@RequestMapping("/bookedit")
	public ModelAndView bookEdit(@RequestParam("id") long id){
		Book book = getRequiredEntity(id);
		return prepareModelAndView(book);
	}	


	private ModelAndView prepareModelAndView(Book book ) {
		ModelAndView mv = new ModelAndView("bookedit");
		return mv.addObject("book", book); 
	}

	@RequestMapping("/bookeditsubmit")
	public ModelAndView bookEditSubmit(@Valid @ModelAttribute Book book, BindingResult result){
		if (result.hasErrors()){
			return new ModelAndView ("bookedit", "book", book);
		} 

		if (book.getId() == null) { // New book instance (not from DB) 
			bookRepository.persist(book);
		} else {  // Edited book instance.
			bookRepository.merge(book);
		}
		return new ModelAndView ("redirect:book", "id", book.getId());//redirect from book display
	}


	@ModelAttribute
	public Book findBook(@RequestParam(value ="id", required = false) Long id) {
		if (id == null){ //create
			return new Book();
		} else { //edit
			return getRequiredEntity(id);
		}
	}

}
