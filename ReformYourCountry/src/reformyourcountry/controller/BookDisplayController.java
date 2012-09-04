package reformyourcountry.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import reformyourcountry.misc.FileUtil;
import reformyourcountry.model.Book;
import reformyourcountry.repository.BookRepository;
import reformyourcountry.security.Privilege;
import reformyourcountry.security.SecurityContext;
import reformyourcountry.utils.FileUtils;
import reformyourcountry.utils.FileUtils.InvalidImageFileException;

@Controller
public class BookDisplayController extends BaseController<Book> {
    
    @Autowired BookRepository bookRepository;
    
    @RequestMapping("/book")
    public ModelAndView bookDisplay(@RequestParam("id") long id){
        Book book = getRequiredEntity(id);
        ModelAndView mv = new ModelAndView("bookdisplay");
        mv.addObject("book",book);
        
        return mv;
    }
    
    // Ajax method (not used anymore) to load an html frag displaying a book
    @Deprecated // Not used anymore. Kept for documentation (how to Ajax)
    @RequestMapping("/ajax/popbook")
    public ModelAndView showBookPop(@RequestParam String abrev){
        Book book = bookRepository.findBookByAbrev(abrev);
        
        ModelAndView mv = new ModelAndView("displaybook");
        mv.addObject(book);
      
        return mv;
    }
    
    @RequestMapping("/bookimageadd")
    public ModelAndView bookImageAdd(@RequestParam("id") long bookid,
            @RequestParam("file") MultipartFile multipartFile) throws IOException{    
        SecurityContext.assertUserHasPrivilege(Privilege.EDIT_BOOK);

        Book book = bookRepository.find(bookid);
        String filename = book.getAbrev() + ".jpg";

//        if(FileUtil.getFilesNamesFromFolder(FileUtil.getBookPicsFolderPath()).contains(filename)){
//
//            File file = new File(FileUtil.getBookPicsFolderPath()+'/'+filename);
//            file.delete();
//        }

 
        ModelAndView mv = new ModelAndView("bookdisplay");
        mv.addObject("book", book);
        mv.addObject("file", multipartFile);  // FIXME UTILE ? XXXXXXXXXXXXXXXXXXXXXXXXXX
        try {
            FileUtils.uploadPicture(FileUtil.getBookPicsFolderPath(),multipartFile, filename,false);
        } catch (InvalidImageFileException e) {
           
            mv.addObject("errorMsg", e.getMessageToUser());
        }
      


        return mv;

    }


}
