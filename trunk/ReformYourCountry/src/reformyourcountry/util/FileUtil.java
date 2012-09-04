package reformyourcountry.util;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.web.multipart.MultipartFile;

import reformyourcountry.controller.ImageUploadController;
import reformyourcountry.web.ContextUtil;

public abstract class FileUtil {
    
    // In dev mode, returns somthing like C:\Users\forma308\Documents\workspace\.metadata\.plugins\org.eclipse.wst.server.core\tmp0\wtpwebapps\ReformYourCountry\gen 
    public static String getGenFolderPath() {
        return ContextUtil.getServletContext().getRealPath("/gen");
    }

    public static String getArticlePicsFolderPath() {
        return getGenFolderPath() + "/article";
    }
    /**
     * write the picture in the right folder    
     * @param path
     * @param multipartFile
     * @return
     * @throws IOException
     */
    public static void uploadPicture(String path, MultipartFile multipartFile, String filename, boolean overwriteIfFileExists) throws IOException, InvalidImageFileException {
        if (multipartFile.getSize()>1500000)  {
            throw new InvalidImageFileException("file is too large 1.5Mo maximum");
        }
        File genFolder = FileUtil.ensureFolderExists(path);
        String type = multipartFile.getContentType();
        String extension ;
        Logger logger = Logger.getLogger(ImageUploadController.class);
        File file = null;
        if(logger.isDebugEnabled()){
            logger.debug("genFolder : "+genFolder.getAbsolutePath());
            logger.debug("file type is :"+multipartFile.getContentType());
            logger.debug("file original name is "+multipartFile.getOriginalFilename());
        }
        
        
        if (multipartFile.isEmpty()){
            if(logger.isDebugEnabled()){
                logger.debug("someone try to submit an empty file : "+multipartFile.getOriginalFilename());}
            throw new InvalidImageFileException("No file to transfer. File is empty.");

        }
        if (!type.contains("image")) {
            if(logger.isDebugEnabled()){
                logger.debug("someone try to upload this fille but this isn't an image : "+multipartFile.getOriginalFilename());}
            throw new InvalidImageFileException("File is not an image.  Detected type = '"+type+"'");
        }
        
        
        ////// Get the right extension & replace in fileName
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
            throw new InvalidImageFileException("bad image type : png , svg , jpeg and gif are only accepted. Detected type = '"+type+"'");
        }
        filename.replace( filename.substring( filename.lastIndexOf(".")),
                "."+extension);

        
        //now the file is good
        //Replace the extension by the good one. for example, we replace "JPEG" by "jpg".
        file = new File(genFolder, filename);

//            file = new File(genFolder, multipartFile.getOriginalFilename().replace(
//                    multipartFile.getOriginalFilename().substring(0,multipartFile.getOriginalFilename().lastIndexOf(".")),filename));

        //// Writes the file
        if (overwriteIfFileExists && file.exists()){
            throw new InvalidImageFileException("file already exist!");
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

        logger.info("file succesfull uploaded : "+file.getCanonicalPath());
    }

    /**
     * Return a list of file names contained in a given folder.
     * @param folderPath The path to the folder from wich we retrieve the file names
     * @param extentions The extentions we filter on. No filtering if null.
     * @return the list of file names.
     */
    public static List<String> getFilesNamesFromFolder(String folderPath){
        
        List<String> files = new ArrayList<String>();
        File folder = new File(folderPath);
        if(!folder.exists()){
            return files;
        }
        if(!folder.isDirectory()){
            folder = folder.getParentFile();
        }
        files = Arrays.asList(folder.list());
      
        
        return files;
        
    }

    /**
     * Return a list of files contained in a given folder.
     * @param folderPath The path to the folder from wich we retrieve the files
     * @param extentions The extentions we filter on. No filtering if null.
     * @return the list of files.
     */
    public static List<File> getFilesFromFolder(String folderPath){
        
        List<File> files = new ArrayList<File>();
        File folder = new File(folderPath);
        if(!folder.exists()){
            return files;
        }
        if(!folder.isDirectory()){
            folder = folder.getParentFile();
        }
        files = Arrays.asList(folder.listFiles());
      
        
        return files;
        
    }
    
    /**
     * Return a list of files contained in a given folder.
     * @param folderPath The path to the folder from wich we retrieve the files
     * @param extentions The extentions we filter on. No filtering if null.
     * @return the list of files.
     */
    public static List<File> getFilesFromFolder(String folderPath, final String ... extensions){
        
        List<File> files = new ArrayList<File>();
        File folder = new File(folderPath);
        if(!folder.exists()){
            return files;
        }
        if(!folder.isDirectory()){
            folder = folder.getParentFile();
        }
        
        FilenameFilter filter = new FilenameFilter() {
            public boolean accept(File dir, String name) {
                for (String extension : extensions) {
                    if(name.toLowerCase().endsWith(extension.toLowerCase())){
                        return true;
                    }
                }
                return false;
            }
        };

        files = Arrays.asList(folder.listFiles(filter));
      
        
        return files;
        
    }
    
    public static int getFolderLength(String folderPath){
        
        File folder = new File(folderPath);
        if(!folder.exists()){
            return 0;
        }
        if(!folder.isDirectory()){
            folder = folder.getParentFile();
        }
        int length = 0;
        List<File> files = Arrays.asList(folder.listFiles());
        for(File file : files){
            if(file.isDirectory()){
                continue;
            }
            length+=file.length();
        }
        return length;
        
    }
    
    public static void writeStringToFile(String dataString, File file) throws IOException {
        
        PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(file)));
        out.print(dataString);
        out.flush();
        out.close();
        
    }
    
    public static File ensureFolderExists(String completeFolderName) {
        File mainFolder = new File(completeFolderName);
        if(!mainFolder.exists()){
            mainFolder.mkdirs();
        }
        return mainFolder;
    }

    @SuppressWarnings("serial")
    public static class InvalidImageFileException extends Exception {
        private String messageToUser;
        InvalidImageFileException(String userMsg) {
            this.messageToUser = userMsg;
        }
        public String getMessageToUser() {
            return messageToUser;
        }
    }
}
