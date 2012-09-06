package reformyourcountry.util;

import reformyourcountry.model.Book;
import reformyourcountry.security.Privilege;
import reformyourcountry.security.SecurityContext;

public class HTMLUtil {
    
    public static String getBookFragment(Book book,boolean readOnly){
        String block = "";
        
        block += "<div id ='book-"+book.getAbrev()+"' class='booktooltip'>";
        
              if(readOnly)
                      block += "<h5><a href='showbooklist#"+book.getAbrev()+"'>Titre: "+book.getTitle()+"</a></h5>"; 
              else
                  block += "<h5><a id ='"+book.getAbrev()+"'>Titre: "+book.getTitle()+"</a></h5>"; 
                  block += "<h5>"+book.getAuthor()+" "+book.getPubYear()+"</h5>";
                  if(book.isHasImage())
                  block += "<img src='/ReformYourCountry/gen/book/resized/"+book.getId()+".jpg' alt='"+book.getTitle()+"'>";
                  
                   block += "<p>"+book.getDescription()+"</p>"+
                      "<a href = '"+book.getExternalUrl()+"' target = '_blank'\">Lien externe</a>"+
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
