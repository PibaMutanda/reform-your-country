package reformyourcountry.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.UUID;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import reformyourcountry.misc.FileUtil;

@Controller
public class ImageUploadController {


    @RequestMapping("/imageupload")
    public String imageUpload() {
        return "imageupload";
    }

    @RequestMapping("/imageuploadtest")
    public String imageUploadTest() {
        return "imageuploadtest";
    }

    @RequestMapping("/imageuploadsubmit")
    public ModelAndView imageUploadSubmit(@RequestParam("files") MultipartFile multipartFile) throws IOException  {
        File folder = FileUtil.ensureFolderExists(FileUtil.getGenFolderPath());
        System.out.println("********************");
        System.out.println(folder.getAbsolutePath());
        System.out.println(multipartFile.getContentType());
        System.out.println(multipartFile.getName());
        System.out.println(multipartFile.getOriginalFilename());
        System.out.println("********************");
        if (!multipartFile.isEmpty()){
            if (multipartFile.getContentType().contains("image")) {
                File file = new File(folder, UUID.randomUUID()+"."+multipartFile.getContentType().split("/")[1]);  // multipartFile.getFileName()
                FileOutputStream fos;
                try {
                    fos = new FileOutputStream(file);
                    fos.write(multipartFile.getBytes());
                } catch (final java.io.FileNotFoundException e) {
                    throw new RuntimeException(e);
                }
                fos.close();

            }  
            else{
                ModelAndView mv = new ModelAndView("imageupload");
                mv.addObject("errorMsg", "file is not an image");
                return mv;
            }
        }
        else{
            ModelAndView mv = new ModelAndView("imageupload");
            mv.addObject("errorMsg", "no file to transfer");
            return mv;
        }
        ModelAndView mv = new ModelAndView("redirect:home");
        return mv;

    }
}
