package reformyourcountry.util;

import reformyourcountry.model.Book;
import reformyourcountry.security.Privilege;
import reformyourcountry.security.SecurityContext;

public class HTMLUtil {
    
    
    public static String getBookFragment(Book book,boolean readOnly){
        String block = "";

        block += "<div id = 'book-"+book.getAbrev()+"' class='booktooltip'>"+
                "<h5><a href=\"showbooklist#"+book.getAbrev()+"\">Titre: "+book.getTitle()+"</a></h5>" +
                "<img src=\"/ReformYourCountry/gen/book/"+book.getAbrev()+".jpg\" height=\"150\" width=\"150\">"+
                "<p>"+book.getDescription()+"</p>"+
                "<a href = \""+book.getExternalUrl()+"\">Lien externe</a>"+
                "</div>";

        if(!readOnly && SecurityContext.isUserHasPrivilege(Privilege.EDIT_BOOK)){


            block +=  "<form action=\"bookedit\" method=\"GET\">"+
                    "<input type=\"hidden\" name=\"id\" value=\""+book.getId()+"\" />"+
                    "<input type=\"submit\" value=\"Editer\" />"+
                    "</form>";


        }

        return block;
        
    }
    

}
