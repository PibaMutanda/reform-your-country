package reformyourcountry.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import reformyourcountry.misc.FileUtil;

@Controller
public class ImageUploadController {

    
    @RequestMapping("/imageupload")
    public String imageUpload() {
        return "imageupload";
    }

    @RequestMapping("/imageuploadsubmit")
    public String imageUploadSubmit(@RequestParam("files") MultipartFile multipartFile) throws IOException  {
       File folder = FileUtil.ensureFolderExists(FileUtil.getGenFolderPath());
       
       if (!multipartFile.isEmpty()) {
           File file = new File(folder, "testFile.txt");  // multipartFile.getFileName()
           FileOutputStream fos;
           try {
               fos = new FileOutputStream(file);
               fos.write(multipartFile.getBytes());
           } catch (final java.io.FileNotFoundException e) {
               throw new RuntimeException(e);
           }
           fos.close();
           
       }   
          return "redirect:home";
      // System.out.println("XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX");
//       System.out.println(files.getSize()); 
       
     //   return "imageupload";
    }
}
