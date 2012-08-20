package reformyourcountry.controller;

import java.io.File;

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
    public String imageUploadSubmit(@RequestParam("files") MultipartFile files)  {
       File folder=null; 
       
       folder=FileUtil.ensureFolderExists("gen");
       System.out.println("XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX");
//       System.out.println(files.getSize()); 
       
        return "imageupload";
    }
}
