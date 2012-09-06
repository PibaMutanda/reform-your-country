package reformyourcountry.tag;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.SimpleTagSupport;

import reformyourcountry.model.Book;
import reformyourcountry.repository.BookRepository;
import reformyourcountry.security.Privilege;
import reformyourcountry.security.SecurityContext;
import reformyourcountry.utils.HTMLUtil;
import reformyourcountry.web.ContextUtil;

public class BookInfoTag extends SimpleTagSupport {
    
    
    private Book book;
    private boolean readOnly = false;
    //private BookRepository bookRepository;
    
  



    public Book getBook() {
        return book;
    }



    public void setBook(Book book) {
        this.book = book;
    }
    
    



    public boolean isReadOnly() {
        return readOnly;
    }



    public void setReadOnly(boolean readOnly) {
        this.readOnly = readOnly;
    }



    @Override
    public void doTag() throws JspException {
        
     //   bookRepository =  (BookRepository) ContextUtil.getSpringBean("bookRepository");
       // Book book = bookRepository.find(id);
        
        JspWriter out = this.getJspContext().getOut();
        
        try {
        
        
        out.write(HTMLUtil.getBookFragment(book, readOnly));
        
        
       
        } catch (IOException e) {
            
           throw new RuntimeException(e);
        }
    }

}
