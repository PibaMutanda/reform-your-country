package reformyourcountry.controller;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import reformyourcountry.model.Book;
import reformyourcountry.repository.BookRepository;
import reformyourcountry.security.Privilege;
import reformyourcountry.security.SecurityContext;
import reformyourcountry.util.FileUtil;
import reformyourcountry.util.FileUtil.InvalidImageFileException;
import reformyourcountry.util.ImageUtil;

@Controller
public class BookDisplayController extends BaseController<Book> {

    @Autowired BookRepository bookRepository;

    @RequestMapping("/book")
    public ModelAndView bookDisplay(@RequestParam(value = "id",required = false) Long id ,@RequestParam(value = "abrev",required = false) String abrev){
        Book book = null;
        if(abrev != null)
         book = bookRepository.findBookByAbrev(abrev);      
        else
         book = getRequiredEntity(id);
        
        ModelAndView mv = new ModelAndView("bookdisplay");
        mv.addObject("book", book);

        return mv;
    }


    @RequestMapping("/bookimageadd")
    public ModelAndView bookImageAdd(@RequestParam("id") long bookid,
            @RequestParam("file") MultipartFile multipartFile) throws Exception{    
        
        SecurityContext.assertUserHasPrivilege(Privilege.EDIT_BOOK);

        Book book = bookRepository.find(bookid);

        ModelAndView mv = new ModelAndView("bookdisplay");
        mv.addObject("book", book);

        ///// Save original image, scale it and save the resized image.
        try {
            FileUtil.uploadFile(multipartFile, FileUtil.getGenFolderPath() + FileUtil.BOOK_SUB_FOLDER + FileUtil.BOOK_ORIGINAL_SUB_FOLDER, 
                    FileUtil.assembleImageFileNameWithCorrectExtention(multipartFile, Long.toString(book.getId())));

            BufferedImage resizedImage = ImageUtil.scale(new ByteArrayInputStream(multipartFile.getBytes()),120 * 200, 200, 200);

            ImageUtil.saveImageToFileAsJPEG(resizedImage,  
                    FileUtil.getGenFolderPath() + FileUtil.BOOK_SUB_FOLDER + FileUtil.BOOK_RESIZED_SUB_FOLDER, book.getId() + ".jpg", 0.9f);

            book.setHasImage(true);
            bookRepository.merge(book);


        } catch (InvalidImageFileException e) {  //Tell the user that its image is invalid.
            setMessage(mv, e.getMessageToUser());
        }

        return mv;
    }
    @RequestMapping("/bookimagedelete")
    public ModelAndView bookImageDelete(@RequestParam("id") long bookid){
        SecurityContext.assertUserHasPrivilege(Privilege.EDIT_BOOK);

        Book book = bookRepository.find(bookid);

        FileUtil.deleteFilesWithPattern(FileUtil.getGenFolderPath() + FileUtil.BOOK_SUB_FOLDER + FileUtil.BOOK_ORIGINAL_SUB_FOLDER, book.getId()+".*");
        FileUtil.deleteFilesWithPattern(FileUtil.getGenFolderPath() + FileUtil.BOOK_SUB_FOLDER + FileUtil.BOOK_RESIZED_SUB_FOLDER, book.getId()+".*");
        book.setHasImage(false);
        bookRepository.merge(book);

        ModelAndView mv = new ModelAndView("redirect:book");
        mv.addObject("id", book.getId());
        mv.addObject("book", book);
        return mv;
    }

}
