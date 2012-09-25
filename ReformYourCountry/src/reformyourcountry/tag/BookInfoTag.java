package reformyourcountry.tag;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.SimpleTagSupport;

import reformyourcountry.model.Book;
import reformyourcountry.util.HTMLUtil;

public class BookInfoTag extends SimpleTagSupport {
    
    
    private Book book;
    private boolean readOnly = false;
    
    

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
        
        
        JspWriter out = this.getJspContext().getOut();
        
        try {
        
  
// TODO: turn this custom tag into a .tag (used by BookDisplay and BookList (booktable.jsp))        	
//        out.write(HTMLUtil.getBookFragment(book,readOnly));
          out.write("TODO: turn this custom tag into a .tag (used by BookDisplay and BookList (booktable.jsp))");
        
       
        } catch (IOException e) {
            
           throw new RuntimeException(e);
        }
    }

}
