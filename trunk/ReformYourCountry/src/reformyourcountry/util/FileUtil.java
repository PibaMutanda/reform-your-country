package reformyourcountry.util;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.multipart.MultipartFile;

import reformyourcountry.util.CurrentEnvironment.Environment;
import reformyourcountry.web.ContextUtil;

public abstract class FileUtil {
	
	final static public String BOOK_SUB_FOLDER = "/book";
	final static public String BOOK_ORIGINAL_SUB_FOLDER = "/original";
	final static public String BOOK_RESIZED_SUB_FOLDER = "/resized";
	final static public String ARTICLE_SUB_FOLDER = "/article";
	final static public String USER_SUB_FOLDER = "/user";
	final static public String USER_ORIGINAL_SUB_FOLDER = "/original";
	final static public String USER_RESIZED_SUB_FOLDER = "/resized";
	final static public String USER_RESIZED_LARGE_SUB_FOLDER = "/large";
	final static public String USER_RESIZED_SMALL_SUB_FOLDER = "/small";
	final static public String GROUP_SUB_FOLDER = "/group";
	final static public String GROUP_ORIGINAL_SUB_FOLDER = "/original";
	final static public String GROUP_RESIZED_SUB_FOLDER = "/resized";
	final static public String LUCENE_INDEX_FOLDER = "/indexLucene";
	final static public String PDF_FOLDER ="/pdf";

    static private Log log = LogFactory.getLog(FileUtil.class);
    public static String getGenFolderPath(CurrentEnvironment currentEnvironment) {
    	if (currentEnvironment.getEnvironment() == Environment.PROD) {
    		return currentEnvironment.getGenFolderOnProd();  // probably something like "/var/www/html/gen".
    	} else if (currentEnvironment.getEnvironment() == Environment.DEV) {
    		// In dev mode, returns something like C:\Users\forma308\Documents\workspace\.metadata\.plugins\org.eclipse.wst.server.core\tmp0\wtpwebapps\ReformYourCountry\gen 
    		return ContextUtil.getServletContext().getRealPath("/gen");
    	} else {  // Defensive coding.
    		throw new IllegalStateException("Bug: unexpected enum value " + currentEnvironment.getEnvironment());
    	}
    }

    
    /** If an uploaded MultipartFile has the name "toto.jpeg", and we give "123" as newNamePrefixFileName, this method will return "123.jpg".
     * @throws InvalidImageFileException 
     */
    public static String assembleImageFileNameWithCorrectExtention(MultipartFile multipartFile, String newPrefixFileName) throws InvalidImageFileException {
        String type = multipartFile.getContentType();
        
        if (!type.contains("image")) {
            if(log.isDebugEnabled()){
                log.debug("someone try to upload this fille but this isn't an image : "+multipartFile.getOriginalFilename());}
            throw new InvalidImageFileException("File is not an image.  Detected type = '"+type+"'");
        }
        
        ////// Get the right extension
        String extension;
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
        
        // Compute the new file name
        return newPrefixFileName + "." + extension;
    }
    
    
    /**
     * write the picture in the right folder    
     * @param path
     * @param multipartFile
     * @return
     * @throws InvalidImageFileException 
     * @throws IOException 
     * @throws Exception 
     */
    public static File uploadFile(MultipartFile multipartFile, String path, String fileName) throws InvalidImageFileException, IOException {
        if (multipartFile.getSize()>1500000)  {
            throw new InvalidImageFileException("file is too large 1.5Mo maximum");
        }
        if (path == null) {
            throw new IllegalArgumentException("File path(image) can't be null");
        }
        
        if (fileName == null) {
            throw new IllegalArgumentException("File name(image) can't be null");
        }
        File folder = FileUtil.ensureFolderExists(path);
        if(log.isDebugEnabled()){
            log.debug("genFolder : "+folder.getAbsolutePath());
            log.debug("file type is :"+multipartFile.getContentType());
            log.debug("file original name is "+multipartFile.getOriginalFilename());
        }
        
        
        if (multipartFile.isEmpty()){
            if(log.isDebugEnabled()){
                log.debug("someone try to submit an empty file : "+multipartFile.getOriginalFilename());}
            throw new InvalidImageFileException("No file to transfer. File is empty.");

        }

        File file = new File(folder, fileName);
         

        FileOutputStream fos = null;
        fos = new FileOutputStream(file);
        fos.write(multipartFile.getBytes());
        fos.close();

        log.debug("file succesfull uploaded : "+file.getCanonicalPath());
        return file;
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
    
    public void saveDataToFile(byte[] data, String fileName, String absoluteFolderPath) throws IOException {
		if (StringUtils.isEmpty(absoluteFolderPath)) {
			throw new IllegalArgumentException("foldernames cannot be empty");
		}
		FileUtil.ensureFolderExists(absoluteFolderPath);
		org.apache.commons.io.FileUtils.writeByteArrayToFile(new File(absoluteFolderPath + File.separator + fileName), data);
	}


	private static int deleteContentOlderThan(File directory, int numberOfDays){
		int count = 0;
		for (File file : directory.listFiles()) {
			if(file.isDirectory()){
				count+= deleteContentOlderThan(file, numberOfDays);
				deleteFileOlderThan(file, numberOfDays);
			} else {
				deleteFileOlderThan(file, numberOfDays);
				count += 1;
			}
		}
		return count;
	}

	private static boolean deleteFileOlderThan(File file, int numberOfDays){
		boolean deleted = false;
		Date lastModified = new Date(file.lastModified());
		if(lastModified.before(DateUtils.addDays(new Date(), -numberOfDays))){
			deleted = file.delete();
			if(!deleted){
			log.warn("Cannot delete file : " + file.getAbsolutePath());
			}
		}
		return deleted;
	}
	
	/**
	 * 
	 * @param path  example: "c:/mydirectory"
	 * @param filePattern example: "12345.*"
	 */
	public static void deleteFilesWithPattern(String pathStr, String filePattern) {  
	    
	    try {
	        Path dir = Paths.get(pathStr);
	        DirectoryStream<Path> dirStream;
	        dirStream = Files.newDirectoryStream(dir, filePattern);
	        for (Path filePath: dirStream) {
	            Files.delete(filePath);
	        }
	    } catch (IOException e) {
	        new RuntimeException("Problem while deleting files "+filePattern+" in path "+pathStr, e);
	    } 
	}



	public static String createTemporaryFolder() {
		String destinationDirectory = System.getProperty("java.io.tmpdir") + UUID.randomUUID().toString() + "/";
		FileUtil.ensureFolderExists(destinationDirectory); 
		return destinationDirectory ;
	}


	private static void copyFiles(String sourcePath, String destinationPath, String ... filterExtensions){
		// If source does not exists returns
		File sourceFolder = new File(sourcePath);
		if(!sourceFolder.exists()){
			log.warn("Copy Files source directory does not exists " + sourcePath);
			return;
		}

		// Create destination directory if needed
		File destinationDir = FileUtil.ensureFolderExists(destinationPath);

		for (File file : FileUtil.getFilesFromFolder(sourcePath, filterExtensions)) {
			String name = file.getName();
			file.renameTo(new File(destinationDir + "/" + name));
		}
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
    
    @SuppressWarnings("serial")
    public static class deleteFileException extends Exception {
        private String messageToUser;
        deleteFileException(String userMsg) {
            this.messageToUser = userMsg;
        }
        public String getMessageToUser() {
            return messageToUser;
        }
    }

	public static String getLuceneIndexDirectory(CurrentEnvironment currentEnvironment) {
		return getGenFolderPath(currentEnvironment)+LUCENE_INDEX_FOLDER+"/";
	}
    
    
}
