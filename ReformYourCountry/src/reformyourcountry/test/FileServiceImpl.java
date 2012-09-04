package reformyourcountry.test;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.imageio.ImageIO;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.commons.logging.Log;

import reformyourcountry.utils.FileUtils;
import blackbelt.util.Logger;
import blackbelt.util.image.ImageInfo;

public class FileServiceImpl  {

	@Logger Log logger;
	
	public String generatedContentFolder;

	
	public enum ImageSaveFormat {
		PNG, JPEG
	}
	
	public void saveAndScaleImage(File image, String imageName, String folderName, boolean saveOriginal,
			String originalFolderName, boolean withDecoration, int[] widths, int[] heights, String[] scaleFolderNames,
			ImageSaveFormat imageSaveFormat) throws IOException {
		this.saveAndScaleImage(new FileInputStream(image), imageName, folderName, saveOriginal, originalFolderName,
				withDecoration, widths, heights, scaleFolderNames, imageSaveFormat);
	}
	public void saveAndScaleImage(File image, String imageName, String folderName, boolean saveOriginal,
			String originalFolderName, boolean withDecoration, int widths, int heights, String scaleFolderNames,
			ImageSaveFormat imageSaveFormat) throws IOException {
		this.saveAndScaleImage(new FileInputStream(image), imageName, folderName, saveOriginal, originalFolderName,
				withDecoration, widths, heights, scaleFolderNames, imageSaveFormat);
	}

	public void saveAndScaleImage(InputStream imageInputStream, String imageName, String folderName,
			boolean saveOriginal, String originalFolderName, boolean withDecoration, int[] widths, int[] heights,
			String[] scaleFolderNames, ImageSaveFormat imageSaveFormat) throws IOException {
		if (imageInputStream == null) {
			throw new IllegalArgumentException("image stream cannot be null");
		}
		if (imageName == null || "".equals(imageName)) {
			throw new IllegalArgumentException("imageName cannot be null or empty");
		}
		if (folderName == null || "".equals(folderName)) {
			throw new IllegalArgumentException("folderName cannot be null or empty");
		}
		if (saveOriginal && (originalFolderName == null || "".equals(originalFolderName))) {
			throw new IllegalArgumentException("originalFolderName cannot be null or empty when saving original image");
		}
		if (widths == null || heights == null || scaleFolderNames == null) {
			throw new IllegalArgumentException("widths, heights and scaleFolderNames cannot be null");
		}
		if (widths.length != heights.length || heights.length != scaleFolderNames.length) {
			throw new IllegalArgumentException("widths, heights and scaleFolderNames must have the same size");
		}

		byte[] imageBytes = StreamUtils.getByteArrayFromInputStream(imageInputStream);
		ByteArrayInputStream imageByteInputStream = new ByteArrayInputStream(imageBytes);

		if (!this.checkImageSupport(imageByteInputStream)) {
			throw new IllegalArgumentException("The given file has not a supported image format");
		}

		imageByteInputStream.reset();

		//String completeFolderName = this.getGeneratedContentFolder() + File.separator + folderName + File.separator;
		String completeFolderName = ""; 
		//FileUtils.ensureFolderExists(completeFolderName);

		if (saveOriginal) {
			if (imageSaveFormat == ImageSaveFormat.PNG) {
				ImageUtil
						.saveImageToFileAsPNG(imageByteInputStream, completeFolderName + originalFolderName, imageName);
			} else {
				ImageUtil.saveImageToFileAsJPEG(imageByteInputStream, completeFolderName + originalFolderName,
						imageName, 1.0f);
			}
			imageByteInputStream.reset();
		}

		for (int i = 0; i < heights.length; i++) {
			BufferedImage scaledImage;
			
			if (widths[i] == 0 || heights[i] == 0) { // means no scaling
				scaledImage = ImageIO.read(imageByteInputStream);
			} else {
				scaledImage = ImageUtil.scale(imageByteInputStream, widths[i], heights[i], true);
			}

			if (withDecoration) {
				scaledImage = ImageUtil.decoratePictureWithGreyRoundedRectangle(scaledImage);
			}
			String currentScaleFolder = completeFolderName + scaleFolderNames[i];
			if (imageSaveFormat == ImageSaveFormat.PNG) {
				ImageUtil.saveImageToFileAsPNG(scaledImage, currentScaleFolder, imageName);
			} else {
				ImageUtil.saveImageToFileAsJPEG(scaledImage, currentScaleFolder, imageName, 1.0f);
			}
			imageByteInputStream.reset();
		}
		imageByteInputStream.close();
	}
	
	
	public void saveAndScaleImage(InputStream imageInputStream, String imageName, String folderName,
			boolean saveOriginal, String originalFolderName, boolean withDecoration, int widths, int heights,
			String scaleFolderNames, ImageSaveFormat imageSaveFormat) throws IOException {
		if (imageInputStream == null) {
			throw new IllegalArgumentException("image stream cannot be null");
		}
		if (imageName == null || "".equals(imageName)) {
			throw new IllegalArgumentException("imageName cannot be null or empty");
		}
		if (folderName == null || "".equals(folderName)) {
			throw new IllegalArgumentException("folderName cannot be null or empty");
		}
		if (saveOriginal && (originalFolderName == null || "".equals(originalFolderName))) {
			throw new IllegalArgumentException("originalFolderName cannot be null or empty when saving original image");
		}
		if (widths == 0 || heights == 0|| scaleFolderNames == null) {
			throw new IllegalArgumentException("widths, heights and scaleFolderNames cannot be null");
		}
		

		byte[] imageBytes = StreamUtils.getByteArrayFromInputStream(imageInputStream);
		ByteArrayInputStream imageByteInputStream = new ByteArrayInputStream(imageBytes);

		if (!this.checkImageSupport(imageByteInputStream)) {
			throw new IllegalArgumentException("The given file has not a supported image format");
		}

		imageByteInputStream.reset();

		//String completeFolderName = this.getGeneratedContentFolder() + File.separator + folderName + File.separator;
		String completeFolderName = ""; 
		//FileUtils.ensureFolderExists(completeFolderName);

		if (saveOriginal) {
			if (imageSaveFormat == ImageSaveFormat.PNG) {
				ImageUtil
						.saveImageToFileAsPNG(imageByteInputStream, completeFolderName + originalFolderName, imageName);
			} else {
				ImageUtil.saveImageToFileAsJPEG(imageByteInputStream, completeFolderName + originalFolderName,
						imageName, 1.0f);
			}
			imageByteInputStream.reset();
		}

		//for (int i = 0; i < heights.length; i++) {
			BufferedImage scaledImage;
			
			if (widths == 0 || heights == 0) { // means no scaling
				scaledImage = ImageIO.read(imageByteInputStream);
			} else {
				scaledImage = ImageUtil.scale(imageByteInputStream, widths, heights, true);
			}

			if (withDecoration) {
				scaledImage = ImageUtil.decoratePictureWithGreyRoundedRectangle(scaledImage);
			}
			String currentScaleFolder = completeFolderName + scaleFolderNames;
			if (imageSaveFormat == ImageSaveFormat.PNG) {
				ImageUtil.saveImageToFileAsPNG(scaledImage, currentScaleFolder, imageName);
			} else {
				ImageUtil.saveImageToFileAsJPEG(scaledImage, currentScaleFolder, imageName, 1.0f);
			}
			imageByteInputStream.reset();
		//}
		imageByteInputStream.close();
	}
	
	public void saveScaleAndFillWithWhiteImage(InputStream imageInputStream, String imageName, String folderName, boolean saveOriginal,
			String originalFolderName, boolean withDecoration, int[] widths, int[] heights, String[] scaleFolderNames,
			ImageSaveFormat imageSaveFormat) throws IOException {
		if (imageInputStream == null) {
			throw new IllegalArgumentException("image stream cannot be null");
		}
		if (imageName == null || "".equals(imageName)) {
			throw new IllegalArgumentException("imageName cannot be null or empty");
		}
		if (folderName == null || "".equals(folderName)) {
			throw new IllegalArgumentException("folderName cannot be null or empty");
		}
		if (saveOriginal && (originalFolderName == null || "".equals(originalFolderName))) {
			throw new IllegalArgumentException("originalFolderName cannot be null or empty when saving original image");
		}
		if (widths == null || heights == null || scaleFolderNames == null) {
			throw new IllegalArgumentException("widths, heights and scaleFolderNames cannot be null");
		}
		if (widths.length != heights.length || heights.length != scaleFolderNames.length) {
			throw new IllegalArgumentException("widths, heights and scaleFolderNames must have the same size");
		}

		byte[] imageBytes = StreamUtils.getByteArrayFromInputStream(imageInputStream);
		ByteArrayInputStream imageByteInputStream = new ByteArrayInputStream(imageBytes);

		if (!this.checkImageSupport(imageByteInputStream)) {
			throw new IllegalArgumentException("The given file has not a supported image format");
		}

		imageByteInputStream.reset();

		String completeFolderName = this.getGeneratedContentFolder() + File.separator + folderName + File.separator;

		reformyourcountry.utils.FileUtils.ensureFolderExists(completeFolderName);

		if (saveOriginal) {
			if (imageSaveFormat == ImageSaveFormat.PNG) {
				ImageUtil
						.saveImageToFileAsPNG(imageByteInputStream, completeFolderName + originalFolderName, imageName);
			} else {
				ImageUtil.saveImageToFileAsJPEG(imageByteInputStream, completeFolderName + originalFolderName,
						imageName, 1.0f);
			}
			imageByteInputStream.reset();
		}

		for (int i = 0; i < heights.length; i++) {
			BufferedImage scaledImage;
			if (widths[i] == 0 || heights[i] == 0) { // means no scaling
				scaledImage = ImageIO.read(imageByteInputStream);
			} else {
				scaledImage = ImageUtil.scale(imageByteInputStream, widths[i], heights[i], true);
			}

			scaledImage = ImageUtil.fillImageWithTransparentPixelsToMatchSize(widths[i], heights[i], scaledImage);

			String currentScaleFolder = completeFolderName + scaleFolderNames[i];
			if (imageSaveFormat == ImageSaveFormat.PNG) {
				ImageUtil.saveImageToFileAsPNG(scaledImage, currentScaleFolder, imageName);
			} else {
				ImageUtil.saveImageToFileAsJPEG(scaledImage, currentScaleFolder, imageName, 1.0f);
			}
			imageByteInputStream.reset();
		}
		imageByteInputStream.close();
	}

	public boolean checkImageSupport(InputStream imageInputStream) {
		ImageInfo imageInfo = new ImageInfo();
		imageInfo.setInput(imageInputStream);
		if (imageInfo.check()) {
			int imageFormat = imageInfo.getFormat();
			switch (imageFormat) {
			case (ImageInfo.FORMAT_BMP):
			case (ImageInfo.FORMAT_GIF):
			case (ImageInfo.FORMAT_JPEG):
			case (ImageInfo.FORMAT_PNG):
				return true;
			}
		}
		return false;
	}

	public File saveFile(File file, String fileName, String... subFoldersNames) throws IOException {
		if (subFoldersNames == null) {
			throw new IllegalArgumentException("foldernames cannot be empty");
		}
		StringBuffer folder = new StringBuffer();
		for (String subFolderName : subFoldersNames) {
			folder.append(subFolderName);
			folder.append(File.separator);
		}

		FileUtils.ensureFolderExists(this.getGeneratedContentFolder() + File.separator + folder.toString());
		File newFile = new File(this.getGeneratedContentFolder(), folder.toString()	+ File.separator + fileName);
		org.apache.commons.io.FileUtils.copyFile(file, newFile);
		return newFile;
	}

	public void saveBytesToFile(byte[] data, String fileName, String... subFoldersNames) throws IOException {
		if (subFoldersNames == null) {
			throw new IllegalArgumentException("foldernames cannot be empty");
		}
		StringBuffer folder = new StringBuffer();
		for (String subFolderName : subFoldersNames) {
			folder.append(subFolderName);
			folder.append(File.separator);
		}
		FileUtils.ensureFolderExists(this.getGeneratedContentFolder() + File.separator + folder.toString());
		org.apache.commons.io.FileUtils.writeByteArrayToFile(new File(this.getGeneratedContentFolder(), folder
				.toString()
				+ File.separator + fileName), data);
	}

	public void saveDataToFile(byte[] data, String fileName, String absoluteFolderPath) throws IOException {
		if (StringUtils.isEmpty(absoluteFolderPath)) {
			throw new IllegalArgumentException("foldernames cannot be empty");
		}
		FileUtils.ensureFolderExists(absoluteFolderPath);
		org.apache.commons.io.FileUtils.writeByteArrayToFile(new File(absoluteFolderPath + File.separator + fileName), data);
	}
	
	public void deleteFolder(String... subFoldersNames) throws IOException {
		if (subFoldersNames == null) {
			throw new IllegalArgumentException("foldernames cannot be empty");
		}

		if (subFoldersNames.length <= 1) {
			throw new IllegalArgumentException("Cannot delete a main gen folder");
		}

		StringBuffer folder = new StringBuffer();
		for (String subFolderName : subFoldersNames) {
			folder.append(subFolderName);
			folder.append(File.separator);
		}

		org.apache.commons.io.FileUtils.deleteDirectory(new File(this.getGeneratedContentFolder(), folder.toString()));
	}

	public void setGeneratedContentFolder(String generatedFolderContent) {
		this.generatedContentFolder = generatedFolderContent;
	}

	public String getGeneratedContentFolder() {
		return this.generatedContentFolder;
	}
	
    

	private int deleteContentOlderThan(File directory, int numberOfDays){
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

	private boolean deleteFileOlderThan(File file, int numberOfDays){
		boolean deleted = false;
		Date lastModified = new Date(file.lastModified());
		if(lastModified.before(DateUtils.addDays(new Date(), -numberOfDays))){
			deleted = file.delete();
			if(!deleted){
				logger.warn("Cannot delete file : " + file.getAbsolutePath());
			}
		}
		return deleted;
	}
	
	public String createFolder(String basePath, String privateFolder) {
    	String destinationDirectory = basePath + privateFolder; 
    	FileUtils.ensureFolderExists(destinationDirectory); 
    	System.out.println("created " + destinationDirectory); // TODO REMOVE
    	return destinationDirectory ;
	}

	public String createTemporaryFolder() {
    	String destinationDirectory = System.getProperty("java.io.tmpdir") + UUID.randomUUID().toString() + "/";
    	FileUtils.ensureFolderExists(destinationDirectory); 
    	System.out.println("created " + destinationDirectory); // TODO REMOVE
    	return destinationDirectory ;
	}
    

    
    private void copyFiles(String sourcePath, String destinationPath, String ... filterExtensions){
    	// If source does not exists returns
    	File sourceFolder = new File(sourcePath);
    	if(!sourceFolder.exists()){
    		logger.warn("Copy Files source directory does not exists " + sourcePath);
    		return;
    	}
    	
    	// Create destination directory if needed
    	File destinationDir = FileUtils.ensureFolderExists(destinationPath);

    	for (File file : FileUtils.getFilesFromFolder(sourcePath, filterExtensions)) {
    		String name = file.getName();
    		file.renameTo(new File(destinationDir + "/" + name));
		}
    }
    
	public List<File> getImageAttachments(String folderPath) {
		return FileUtils.getFilesFromFolder(folderPath, "png", "jpg", "jpeg", "gif");
	}

}
