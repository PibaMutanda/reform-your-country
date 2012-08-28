package reformyourcountry.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import reformyourcountry.misc.FileUtil;

@Controller
public class ImageUploadController {
    
    
    private Logger logger = Logger.getLogger(ImageUploadController.class);
    File file = null;
    @RequestMapping("/imageupload")
    public String imageUpload() {
        return "imageupload";
    }

//    @RequestMapping("/imageuploadtest")
//    public String imageUploadTest() {
//        return "imageuploadtest";
//    }

    
    public String uploadPicture(String path,MultipartFile multipartFile)throws IOException{
        File genFolder = FileUtil.ensureFolderExists(path);
        String type = multipartFile.getContentType();
        String extension ;
      
        if(logger.isDebugEnabled()){
            logger.debug("genFolder : "+genFolder.getAbsolutePath());
            logger.debug("file type is :"+multipartFile.getContentType());
            logger.debug("file original name is "+multipartFile.getOriginalFilename());
        }
        if (!multipartFile.isEmpty()){
            if (type.contains("image")) {
                //to get the right extension
                switch (type) {
                case "image/gif":
                    extension = "gif";
                    break;
                case "image/jpeg" :
                case "image/pjpeg" ://internet explorer IFuckDevWhenTheyWantToMakeItSimple special MimeType for jpeg
                    extension = "jpg";
                    break;
                case "image/png" : 
                case "image/x-png"://internet explorer IFuckDevWhenTheyWantToMakeItSimple special MimeType for png
                    extension = "png";
                    break;
                case "image/svg+xml" :
                    extension = "svg";
                    break;
                default:
                    return "bad image type : png , svg , jpeg and gif are only accepted";
                }
                //now the file is good
                file = new File(genFolder, multipartFile.getOriginalFilename()+"."+extension);  // multipartFile.getFileName()
                if (file.exists()){
                    return "file already exist!";
                }
                FileOutputStream fos = null;
                try {
                    fos = new FileOutputStream(file);
                    fos.write(multipartFile.getBytes());
                } catch (final java.io.FileNotFoundException e) {
                    throw new RuntimeException(e);
                } finally {
                    fos.close();
                }

            } else {
                if(logger.isDebugEnabled()){
                    logger.debug("someone try to upload this fille but this isn't an image : "+multipartFile.getOriginalFilename());}
                return "file is not an image";
            }
        } else {
            if(logger.isDebugEnabled()){
                logger.debug("someone try to submit an empty file : "+multipartFile.getOriginalFilename());}
            return  "no file to transfer";
        }

        logger.info("file succesfull uploaded : "+file.getCanonicalPath());
        return"";
    }
    
    @RequestMapping("/imageuploadsubmit")
    public ModelAndView imageUploadSubmit(@RequestParam("file") MultipartFile multipartFile) throws IOException  {
        ModelAndView mv = new ModelAndView("imageupload");
        String msg = uploadPicture(FileUtil.getGenFolderPath(),multipartFile);
        //        File genFolder = FileUtil.ensureFolderExists(FileUtil.getGenFolderPath()); //dir gen who's not created in eclipse webcontent path but only in tomcat working path
        //        
        //        String type = multipartFile.getContentType();
        //        String extension ;
        //      
        //        if(logger.isDebugEnabled()){
        //            logger.debug("genFolder : "+genFolder.getAbsolutePath());
        //            logger.debug("file type is :"+multipartFile.getContentType());
        //            logger.debug("file original name is "+multipartFile.getOriginalFilename());
        //        }
        //
        //        if (!multipartFile.isEmpty()){
        //            if (type.contains("image")) {
        //                //to get the right extension
        //                switch (type) {
        //                case "image/gif":
        //                    extension = "gif";
        //                    break;
        //                case "image/jpeg" :
        //                case "image/pjpeg" ://internet explorer IFuckDevWhenTheyWantToMakeItSimple special MimeType for jpeg
        //                    extension = "jpg";
        //                    break;
        //                case "image/png" : 
        //                case "image/x-png"://internet explorer IFuckDevWhenTheyWantToMakeItSimple special MimeType for png
        //                    extension = "png";
        //                    break;
        //                case "image/svg+xml" :
        //                    extension = "svg";
        //                    break;
        //                default:
        //                    mv.addObject("errorMsg", "bad image type : png , svg , jpeg and gif are only accepted");
        //                    return mv;
        //                }
        //                //now the file is good
        //                file = new File(genFolder, multipartFile.getOriginalFilename()+"."+extension);  // multipartFile.getFileName()
        //                if (file.exists()){
        //                    mv.addObject("errorMsg", "file already exist!");
        //                    return mv;
        //                }
        //                FileOutputStream fos = null;
        //                try {
        //                    fos = new FileOutputStream(file);
        //                    fos.write(multipartFile.getBytes());
        //                } catch (final java.io.FileNotFoundException e) {
        //                    throw new RuntimeException(e);
        //                } finally {
        //                	fos.close();
        //                }
        //
        //            } else {
        //                mv.addObject("errorMsg", "file is not an image");
        //                if(logger.isDebugEnabled()){
        //                    logger.debug("someone try to upload this fille but this isn't an image : "+multipartFile.getOriginalFilename());}
        //                return mv;
        //            }
        //        } else {
        //            mv.addObject("errorMsg", "no file to transfer");
        //            if(logger.isDebugEnabled()){
        //                logger.debug("someone try to submit an empty file : "+multipartFile.getOriginalFilename());}
        //            return mv;
        //        }
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