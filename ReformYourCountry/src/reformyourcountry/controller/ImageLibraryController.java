package reformyourcountry.controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.io.comparator.LastModifiedFileComparator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import reformyourcountry.misc.FileUtil;

@Controller
public class ImageLibraryController {
    @Autowired
    private ImageUploadController iuc;
    
    
    /**
     * Find the names of the files in the Article pictures folder and send it to the jsp
     * @param error Will show the error (from uploading) in the jsp
     * @return 
     */
    @RequestMapping("show-article-images")
    public ModelAndView ShowLibrary(@RequestParam(value="errorMsg", required=false)String error){
        String path= FileUtil.getArticlePicsFolderPath()+'\\';
        FileUtil.ensureFolderExists(FileUtil.getArticlePicsFolderPath());
        List<File> listF = FileUtil.getFilesFromFolder(FileUtil.getArticlePicsFolderPath());
        File[] listFiles = new File[listF.size()];
        if (!listF.isEmpty()){
            listF.toArray(listFiles);
            Arrays.sort(listFiles,LastModifiedFileComparator.LASTMODIFIED_COMPARATOR);
        }
        ModelAndView mv =new ModelAndView("imagelibrary","path",path);
        int i = listFiles.length;
        mv.addObject("listFiles",listFiles); 
        mv.addObject("totalFiles",listFiles.length);
        mv.addObject("errorMsg",error);
        return mv;
    }
    /**
     * Upload the file to the server Article pictures folder
     * @param multipartFile is the file to upload
     * @return
     * @throws IOException
     */
    @RequestMapping("add-article-images")
    public ModelAndView addPic(@RequestParam("file")MultipartFile multipartFile) throws IOException{
        ModelAndView mv = new ModelAndView("redirect:show-article-images");
        mv.addObject("errorMsg",iuc.uploadPicture(FileUtil.getArticlePicsFolderPath(),multipartFile));
        return mv;
    }
    /**
     * Will delete the file
     * @param name file name to delete
     * @return
     * @throws IOException
     */
    @RequestMapping("deleteimage")
    public ModelAndView delPic(@RequestParam("path")String name) throws IOException{
        File file = new File(FileUtil.getArticlePicsFolderPath()+'\\'+name);
        file.delete();
        ModelAndView mv =this.ShowLibrary("");
        return mv;
    }
    public int compare(File o1, File o2) {
        return o1.lastModified() == o2.lastModified() ? 0 : (o1.lastModified() < o2.lastModified() ? 1 : -1 ) ;
    }
}


