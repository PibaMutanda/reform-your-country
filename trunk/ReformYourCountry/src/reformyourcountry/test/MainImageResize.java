package reformyourcountry.test;

import java.io.File;
import java.io.IOException;

import reformyourcountry.test.FileServiceImpl.ImageSaveFormat;

public class MainImageResize {

	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		String imageName = "McKinsey2007.png";
		String folderName = "C:/";
		boolean saveOriginal = true;
		String originalFolderName = "C:/original_image";
		boolean withDecoration = false;
		int[] widths =  {150, 66, 33 };
		int[] heights = {150, 66, 33};
		String[] scaleFolderNames = new String[] { "C:/high", "C:/medium", "C:/small" };	
		ImageSaveFormat imageFormat = ImageSaveFormat.PNG;
		File file = new File ("C:/McKinsey2007.png");
		FileServiceImpl fsi = new FileServiceImpl();
		fsi.saveAndScaleImage(file, imageName, folderName, saveOriginal, originalFolderName, withDecoration, widths, heights, scaleFolderNames, imageFormat);
		}

}