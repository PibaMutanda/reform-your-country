package reformyourcountry.controller;

import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import reformyourcountry.util.FileUtil;

@Controller
public class ImageUploadController {
    
    
    private Logger logger = Logger.getLogger(ImageUploadController.class);
    File file = null;
    @RequestMapping("/imageupload")
    public String imageUpload() {
        return "imageupload";
    }


   
    @RequestMapping("/imageuploadsubmit")
    public ModelAndView imageUploadSubmit(@RequestParam("file") MultipartFile multipartFile) throws IOException  {
        ModelAndView mv = new ModelAndView("imageupload");
        String msg = FileUtil.uploadPicture(FileUtil.getGenFolderPath(),multipartFile);
     
        if (msg.equals("")){
            mv = new ModelAndView("redirect:home");
            logger.info("file succesfull uploaded : "+file.getCanonicalPath());
        }
        return mv;
    }

    @RequestMapping(value="imageuploadsubmittest", method=RequestMethod.POST) 
    public void addFile(HttpServletRequest request, HttpServletResponse response) throws IOException {
        if (!ServletFileUpload.isMultipartContent(request)) {
            throw new IllegalArgumentException("Request is not multipart, please 'multipart/form-data' enctype for your form.");
        }
        ServletFileUpload uploadHandler = new ServletFileUpload(new DiskFileItemFactory());

        try {
            List<FileItem> items = uploadHandler.parseRequest(request);
            for (FileItem item : items) {
                if (!item.isFormField()) {
                    byte [] fileBytes = item.get();
                }
            }} catch(FileUploadException e) {
                throw new RuntimeException(e);
            }

    }
}