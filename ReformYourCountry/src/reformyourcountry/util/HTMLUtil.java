package reformyourcountry.util;

import reformyourcountry.model.Book;
import reformyourcountry.security.Privilege;
import reformyourcountry.security.SecurityContext;

public class HTMLUtil {
    
    
    public static String getBookFragment(Book book,boolean readOnly){
        String block = "";
        
        block += "<div class = \"bookdialog\">"+
                       "<h5>Titre: "+book.getTitle()+"</h5>" +
                       "<img src=\"http://site.enseignement2.be/bibliographie/McKinsey2007.png\" height=\"150\" width=\"150\">"+
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
