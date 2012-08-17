package reformyourcountry.utils;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class FileUtils {
    
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
    
}
