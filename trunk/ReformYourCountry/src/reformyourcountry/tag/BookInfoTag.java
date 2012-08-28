package reformyourcountry.tag;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.SimpleTagSupport;

import reformyourcountry.model.Book;
import reformyourcountry.repository.BookRepository;
import reformyourcountry.security.Privilege;
import reformyourcountry.security.SecurityContext;
import reformyourcountry.web.ContextUtil;

public class BookInfoTag extends SimpleTagSupport {
    
    
    private Book book;
    private boolean readOnly;
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
            out.write("<h5>Titre: "+book.getTitle()+"</h5>");
       
        out.write("<h5>Auteur: "+book.getAuthor()+" Année "+book.getPubYear()+"</h5>");
        //TODO update with ryc image folder
        out.write("<img src=\"http://site.enseignement2.be/bibliographie/McKinsey2007.png\" height=\"150\" width=\"150\">");
        out.write("<p>"+book.getDescription()+"</p>");
        out.write("<a href = \""+book.getExternalUrl()+"\">Lien externe</a>");
        
        if(!readOnly && SecurityContext.isUserHasPrivilege(Privilege.EDIT_BOOK)){
            
            out.write("<form action=\"bookedit\" method=\"GET\">");
            out.write("<input type=\"hidden\" name=\"id\" value=\""+book.getId()+"\" />");
            out.write("<input type=\"submit\" value=\"Editer\" />");
            out.write("</form>");
            
        }
        
        
       
        } catch (IOException e) {
            
           throw new RuntimeException(e);
        }
    }

}
