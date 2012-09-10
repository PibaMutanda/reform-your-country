package reformyourcountry.util;

import reformyourcountry.model.Book;
import reformyourcountry.security.Privilege;
import reformyourcountry.security.SecurityContext;

public class HTMLUtil {
    
    public static String getBookFragment(Book book, boolean inToolTip) {
        String block = "";

        if (inToolTip) {
            block += "<div id ='book-"+book.getAbrev()+"' style='display:none;'>"; // This div will be "removed" by jQuery bubbles (tooltip)
        }
        block += "<div class='book'>";  // Will be in the tooltip too.
        
        
        if(inToolTip)
            block += "<a href='booklist#"+book.getAbrev()+"'>"; 
        else
            block += "<a id ='"+book.getAbrev()+"'>";
        block += book.getTitle()+"</a><br/>";
        block += book.getAuthor()+"<br/> ";
        block += book.getPubYear()+"<br/> ";
        if(book.isHasImage())
            block += "<img src='/ReformYourCountry/gen/book/resized/"+book.getId()+".jpg' alt='"+book.getTitle()+"' class='imgbook'>";

        block += "<p>"+book.getDescription()+"</p>"+
                "<a href = '"+book.getExternalUrl()+"' target = '_blank'\">Lien externe</a>";
        
        block +="</div>\n";
        if (inToolTip) {
            block +="</div>\n";
        }


        return block;

    }

    

}
