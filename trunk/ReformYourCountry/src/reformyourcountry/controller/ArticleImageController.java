package reformyourcountry.controller;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.List;

import org.apache.commons.io.comparator.LastModifiedFileComparator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import reformyourcountry.model.Article;
import reformyourcountry.security.Privilege;
import reformyourcountry.security.SecurityContext;
import reformyourcountry.util.CurrentEnvironment;
import reformyourcountry.util.FileUtil;
import reformyourcountry.util.NotificationUtil;
import reformyourcountry.util.FileUtil.InvalidImageFileException;
import reformyourcountry.util.ImageUtil;

@Controller
@RequestMapping(value={"/article"})
public class ArticleImageController extends BaseController<Article>{    
    
	@Autowired  CurrentEnvironment currentEnvironment;
    
    /**
     * Find the names of the files in the Article pictures folder and send it to the jsp
     * @param error Will show the error (from uploading) in the jsp
     * @return 
     */
    @RequestMapping("/image")
    public ModelAndView articleImage(@RequestParam(value="errorMsg", required=false)String error){

        SecurityContext.assertUserHasPrivilege(Privilege.MANAGE_ARTICLE);
        FileUtil.ensureFolderExists(FileUtil.getGenFolderPath(currentEnvironment) + FileUtil.ARTICLE_SUB_FOLDER);
        List<File> listFiles = FileUtil.getFilesFromFolder(FileUtil.getGenFolderPath(currentEnvironment) + FileUtil.ARTICLE_SUB_FOLDER);
        Collections.sort(listFiles, LastModifiedFileComparator.LASTMODIFIED_COMPARATOR);
        
        ModelAndView mv =new ModelAndView("articleimage");
        mv.addObject("listFiles",listFiles); 
        NotificationUtil.addNotificationMessage(error);
        return mv;
    }
    
    /**
     * Upload the file to the server Article pictures folder
     * @throws Exception 
     */
    @RequestMapping("/imageadd")
    public ModelAndView articleImageAdd(@RequestParam("file") MultipartFile multipartFile){
        
        SecurityContext.assertUserHasPrivilege(Privilege.MANAGE_ARTICLE);
        
        ModelAndView mv = new ModelAndView("redirect:/article/image");
        try {
            FileUtil.uploadFile(multipartFile, FileUtil.getGenFolderPath(currentEnvironment) + FileUtil.ARTICLE_SUB_FOLDER, multipartFile.getOriginalFilename());
        } catch (InvalidImageFileException iife) {
        	NotificationUtil.addNotificationMessage(iife.getMessageToUser());
        } catch (IOException e) {
        	NotificationUtil.addNotificationMessage(e.getMessage());
        }
        
        return mv;
    }
    
    /**
     * Download the image from url to the server Article pictures folder 
     * @throws Exception 
     */
    @RequestMapping("/imageaddfromurl")
    public ModelAndView articleImageAddFromUrl(@RequestParam("fileurl") String url,@RequestParam("name")String imageName) {
        
        SecurityContext.assertUserHasPrivilege(Privilege.MANAGE_ARTICLE);
        ModelAndView mv = new ModelAndView("redirect:/article/image");
        BufferedImage image = null;
        
        try{
            image = ImageUtil.readImage(url);
        }catch (RuntimeException e) {
        	NotificationUtil.addNotificationMessage("veuillez indiquer une URL valide");
            return mv;//useless to try to save image if we don't have it
        }
        
        try {
            ImageUtil.saveImageToFileAsJPEG(image, FileUtil.getGenFolderPath(currentEnvironment) + FileUtil.ARTICLE_SUB_FOLDER, imageName+".jpg",0.9f);
        } catch (IOException e) {
            throw new RuntimeException();
        }
       
        return mv;
    }
    
    /**
     * Will delete the file
     */
    @RequestMapping("/imagedel")
    public ModelAndView articleImageDel(@RequestParam("fileName") String fileName) throws IOException{
        SecurityContext.assertUserHasPrivilege(Privilege.MANAGE_ARTICLE);
        
        File file = new File(FileUtil.getGenFolderPath(currentEnvironment) + FileUtil.ARTICLE_SUB_FOLDER + '/'+fileName);
        file.delete();
        return this.articleImage("");
    }
}


