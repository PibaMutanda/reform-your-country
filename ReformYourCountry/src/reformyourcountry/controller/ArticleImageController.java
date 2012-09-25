package reformyourcountry.controller;

import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.List;

import org.apache.commons.io.comparator.LastModifiedFileComparator;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import reformyourcountry.security.Privilege;
import reformyourcountry.security.SecurityContext;
import reformyourcountry.util.FileUtil;
import reformyourcountry.util.FileUtil.InvalidImageFileException;

@Controller
public class ArticleImageController {    
    
    /**
     * Find the names of the files in the Article pictures folder and send it to the jsp
     * @param error Will show the error (from uploading) in the jsp
     * @return 
     */
    @RequestMapping("/articleimage")
    public ModelAndView articleImage(@RequestParam(value="errorMsg", required=false)String error){

        SecurityContext.assertUserHasPrivilege(Privilege.EDIT_ARTICLE);
        FileUtil.ensureFolderExists(FileUtil.getGenFolderPath() + FileUtil.ARTICLE_SUB_FOLDER);
        List<File> listFiles = FileUtil.getFilesFromFolder(FileUtil.getGenFolderPath() + FileUtil.ARTICLE_SUB_FOLDER);
        Collections.sort(listFiles, LastModifiedFileComparator.LASTMODIFIED_COMPARATOR);
        
        ModelAndView mv =new ModelAndView("articleimage");
        mv.addObject("listFiles",listFiles); 
        mv.addObject("errorMsg",error);
        mv.addObject("validUser",true);
        return mv;
    }
    
    /**
     * Upload the file to the server Article pictures folder
     * @throws Exception 
     */
    @RequestMapping("/articleimageadd")
    public ModelAndView articleImageAdd(@RequestParam("file") MultipartFile multipartFile) throws Exception{
        SecurityContext.assertUserHasPrivilege(Privilege.EDIT_ARTICLE);
        
        ModelAndView mv = new ModelAndView("redirect:articleimage");
        try {
            FileUtil.uploadFile(multipartFile, FileUtil.getGenFolderPath() + FileUtil.ARTICLE_SUB_FOLDER, multipartFile.getOriginalFilename());
        } catch (InvalidImageFileException iife) {
            mv.addObject("errorMsg", iife.getMessageToUser());
        }
        return mv;
    }
    
    /**
     * Will delete the file
     */
    @RequestMapping("/articleimagedel")
    public ModelAndView articleImageDel(@RequestParam("fileName") String fileName) throws IOException{
        SecurityContext.assertUserHasPrivilege(Privilege.EDIT_ARTICLE);
        
        File file = new File(FileUtil.getGenFolderPath() + FileUtil.ARTICLE_SUB_FOLDER + '/'+fileName);
        file.delete();
        return this.articleImage("");
    }
}

