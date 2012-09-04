package reformyourcountry.test;

import java.io.File;
import java.io.IOException;
import java.math.*;

import reformyourcountry.test.FileServiceImpl.ImageSaveFormat;

public class MainTest {

	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		String imageName = "sonic.gif";
		String folderName = "C:/";
		boolean saveOriginal = true;
		String originalFolderName = "C:/original_image";
		float surface = 120*200;
		double width
		double width = (Math.sqrt((2000.0/290.0) * surface));
		double heigth = surface / width;
		int widths = (int) Math.round(width);
		int heights = (int) Math.round(heigth);
		if(widths>200){
			widths = 200;
		}
		if(heights>200){
			heights = 200;
		}
		System.out.println(surface + " " + (int) Math.round(width)+ " " + (int) Math.round(heigth));
		String scaleFolderNames = "C:/resized";	
		ImageSaveFormat imageFormat = ImageSaveFormat.PNG;
		File file = new File ("C:/sonic.gif");
		FileServiceImpl fsi = new FileServiceImpl();
		fsi.saveAndScaleImage(file, imageName, folderName, saveOriginal, originalFolderName, false, widths, heights, scaleFolderNames, imageFormat);
	}
}

