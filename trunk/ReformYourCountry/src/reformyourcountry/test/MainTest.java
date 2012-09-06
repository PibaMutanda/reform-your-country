package reformyourcountry.test;

import java.io.File;
import java.io.IOException;

import reformyourcountry.util.ImageUtil;
import reformyourcountry.util.ImageUtil.ImageSaveFormat;



public class MainTest {

	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		String imageName = "sonic.jpg";
		String folderName = "C:/";
		boolean saveOriginal = true;
		String originalFolderName = "C:/original_image";		
		boolean noRezise = false;//boolean for allowing resize
		boolean withDecoration = false;
		int WIDTH_MAX = 200;
		int HEIGTH_MAX = 200;
		int SURFACE_MAX = WIDTH_MAX * HEIGTH_MAX;
		String scaleFolderNames = "C:/resized";	
		ImageSaveFormat imageFormat = ImageSaveFormat.PNG;
		File file = new File ("C:/sonic.jpg");
		System.out.println(file.getCanonicalPath());
		System.out.println(file.getAbsolutePath());
		System.out.println(file.getName());
		ImageUtil.saveAndScaleImage(file, imageName, folderName, saveOriginal, originalFolderName, false, false, WIDTH_MAX, HEIGTH_MAX, SURFACE_MAX, scaleFolderNames, imageFormat);
	}
}

