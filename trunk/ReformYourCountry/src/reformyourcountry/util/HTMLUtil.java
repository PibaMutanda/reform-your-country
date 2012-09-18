package reformyourcountry.util;

import reformyourcountry.model.Book;

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
            block += "<img src='gen" + FileUtil.BOOK_SUB_FOLDER + FileUtil.BOOK_RESIZED_SUB_FOLDER +"/"+book.getId()+".jpg' alt='"+book.getTitle()+"' class='imgbook'>";

        block += "<p>"+book.getDescription()+"</p>"+
                "<a href = '"+book.getExternalUrl()+"' target = '_blank'\">Lien externe</a>";
        
        block +="</div>\n";
        
        if (inToolTip) {
            block +="</div>\n";
        }

        return block;

    }

    public static String getRewritedUrl (String title){
        
        title = title.replace(" ", "-");
        title = title.replace(".", "%2E");
        return title;
        
    }

}
