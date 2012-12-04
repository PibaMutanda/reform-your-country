package reformyourcountry.controller;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import reformyourcountry.model.Book;
import reformyourcountry.repository.BookRepository;
import reformyourcountry.security.Privilege;
import reformyourcountry.security.SecurityContext;
import reformyourcountry.util.CurrentEnvironment;
import reformyourcountry.util.FileUtil;
import reformyourcountry.util.NotificationUtil;
import reformyourcountry.util.FileUtil.InvalidImageFileException;
import reformyourcountry.util.ImageUtil;

@Controller
@RequestMapping("/book")
public class BookDisplayController extends BaseController<Book> {

    @Autowired BookRepository bookRepository;
    @Autowired  CurrentEnvironment currentEnvironment;
    
    @RequestMapping("/{bookUrl}")
    public ModelAndView bookDisplay(@PathVariable("bookUrl") String bookUrl){
        Book book = getRequiredEntityByUrl(bookUrl);      
        
        ModelAndView mv = new ModelAndView("bookdisplay");
        mv.addObject("book", book);
        return mv;
    }


    @RequestMapping("/imageadd")
    public ModelAndView bookImageAdd(@RequestParam("id") long bookid, @RequestParam("file") MultipartFile multipartFile) {    
        
        SecurityContext.assertUserHasPrivilege(Privilege.MANAGE_BOOK);

        Book book = bookRepository.find(bookid);

        ModelAndView mv = new ModelAndView("redirect:/book/"+book.getUrl());
        mv.addObject("book", book);

        ///// Save original image, scale it and save the resized image.
        try {
            FileUtil.uploadFile(multipartFile, FileUtil.getGenFolderPath(currentEnvironment) + FileUtil.BOOK_SUB_FOLDER + FileUtil.BOOK_ORIGINAL_SUB_FOLDER, 
                    FileUtil.assembleImageFileNameWithCorrectExtention(multipartFile, Long.toString(book.getId())));

            BufferedImage resizedImage = ImageUtil.scale(new ByteArrayInputStream(multipartFile.getBytes()),120 * 200, 200, 200);

            ImageUtil.saveImageToFileAsJPEG(resizedImage, FileUtil.getGenFolderPath(currentEnvironment) + FileUtil.BOOK_SUB_FOLDER + FileUtil.BOOK_RESIZED_SUB_FOLDER, book.getId() + ".jpg", 0.9f);

            book.setHasImage(true);
            bookRepository.merge(book);


        } catch (InvalidImageFileException e) {  //Tell the user that its image is invalid.
        	NotificationUtil.addNotificationMessage(e.getMessageToUser());
        } catch (IOException e) {
            throw new RuntimeException();
        }

        return mv;
    }
    
    @RequestMapping("/imageaddfromurl")
    public ModelAndView bookImageAddFromUrl(@RequestParam("id") long bookid, @RequestParam(value="fileurl") String url) {    
        
        SecurityContext.assertUserHasPrivilege(Privilege.MANAGE_BOOK);

        Book book = bookRepository.find(bookid);

        ModelAndView mv = new ModelAndView("redirect:/book/"+book.getUrl());
        mv.addObject("book", book);
        
        BufferedImage image = null;
        
        try{
            image = ImageUtil.readImage(url);
        }catch (RuntimeException e) {
        	NotificationUtil.addNotificationMessage("veuillez indiquer une URL valide");
            return mv;//useless to try to save image if we don't have it
        }

        ///// Save original image, scale it and save the resized image.
        try {
            ByteArrayOutputStream outStream= new ByteArrayOutputStream();
            ImageIO.write(image, "jpg", outStream);
            
            image = ImageUtil.scale(new ByteArrayInputStream(outStream.toByteArray()),120 * 200, 200, 200);

            ImageUtil.saveImageToFileAsJPEG(image,  
                    FileUtil.getGenFolderPath(currentEnvironment) + FileUtil.BOOK_SUB_FOLDER + FileUtil.BOOK_RESIZED_SUB_FOLDER, book.getId() + ".jpg", 0.9f);

            book.setHasImage(true);
            bookRepository.merge(book);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return mv;
    }
    
    
    @RequestMapping("/imagedelete")
    public ModelAndView bookImageDelete(@RequestParam("id") long bookid){
        SecurityContext.assertUserHasPrivilege(Privilege.MANAGE_BOOK);

        Book book = bookRepository.find(bookid);

        FileUtil.deleteFilesWithPattern(FileUtil.getGenFolderPath(currentEnvironment) + FileUtil.BOOK_SUB_FOLDER + FileUtil.BOOK_ORIGINAL_SUB_FOLDER, book.getId()+".*");
        FileUtil.deleteFilesWithPattern(FileUtil.getGenFolderPath(currentEnvironment) + FileUtil.BOOK_SUB_FOLDER + FileUtil.BOOK_RESIZED_SUB_FOLDER, book.getId()+".*");
        book.setHasImage(false);
        bookRepository.merge(book);

        ModelAndView mv = new ModelAndView("redirect:/book/"+book.getUrl());
        mv.addObject("book", book);
        return mv;
    }

}
