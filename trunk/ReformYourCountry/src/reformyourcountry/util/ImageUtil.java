package reformyourcountry.util;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.Area;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.AreaAveragingScaleFilter;
import java.awt.image.BufferedImage;
import java.awt.image.FilteredImageSource;
import java.awt.image.ImageProducer;
import java.awt.image.RenderedImage;
import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriter;
import javax.imageio.plugins.jpeg.JPEGImageWriteParam;
import javax.imageio.stream.FileImageOutputStream;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;




public class ImageUtil {


    static Log log = LogFactory.getLog(FileUtil.class);
	public static void saveImageToFileAsJPEG(InputStream imageInputStream, String folderPath,
			String imageName, float quality) throws FileNotFoundException, IOException {
		saveImageToFileAsJPEG(ImageIO
				.read(new BufferedInputStream(imageInputStream)), folderPath, imageName, quality);

	}

	/** fullUrl must be a full url like "http://www.knowledgeblackbelt.com/img/...", not just like "/img/..."
	 * Use BlackBeltUriAnalyzer.getFullUrl() if you start from a Resource */
	public static BufferedImage readImage(String fullUrl) {
		BufferedImage bImg;
		try {
			bImg = ImageIO.read(new URL(fullUrl));
		} catch (MalformedURLException e) {
			throw new RuntimeException(e);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		return bImg;
	}


	public static void saveImageToFileAsJPEG(RenderedImage image,
			String folderPath, String fileName, float quality)
					throws FileNotFoundException, IOException {

		JPEGImageWriteParam param = new JPEGImageWriteParam(null);
		param.setCompressionMode(JPEGImageWriteParam.MODE_EXPLICIT);
		param.setCompressionQuality(quality);
		java.util.Iterator<ImageWriter> it = ImageIO.getImageWritersBySuffix("jpg");
		ImageWriter writer = (ImageWriter) it.next();
		// We create folders if necessary
		File folderFile = new File(folderPath);
		if(!folderFile.exists()){
			folderFile.mkdirs();
		}
		FileImageOutputStream fileImageOutputStream = new FileImageOutputStream(
				new File(folderPath, fileName));
		writer.setOutput(fileImageOutputStream);
		writer.write(null, new IIOImage(image, null, null), param);
		fileImageOutputStream.close();
		writer.dispose();
	}

	public static void saveImageToFileAsGIF(RenderedImage image,
			String folderPath, String fileName) throws FileNotFoundException,
			IOException {

		java.util.Iterator<ImageWriter> it = ImageIO.getImageWritersBySuffix("gif");
		ImageWriter writer = (ImageWriter) it.next();
		// We create folders if necessary
		File folderFile = new File(folderPath);
		if(!folderFile.exists()){
			folderFile.mkdirs();
		}
		FileImageOutputStream fileImageOutputStream = new FileImageOutputStream(
				new File(folderPath, fileName));
		writer.setOutput(fileImageOutputStream);
		writer.write(null, new IIOImage(image, null, null), null);
		fileImageOutputStream.close();
		writer.dispose();
	}

	public static void saveImageToFileAsPNG(InputStream imageInputStream, String folderPath,
			String imageName) throws FileNotFoundException, IOException {
		saveImageToFileAsPNG(ImageIO
				.read(new BufferedInputStream(imageInputStream)), folderPath, imageName);

	}

	public static void saveImageToFileAsPNG(RenderedImage image,
			String folderPath, String fileName) throws FileNotFoundException,
			IOException {
		java.util.Iterator<ImageWriter> it = ImageIO.getImageWritersBySuffix("png");
		ImageWriter writer = (ImageWriter) it.next();
		// We create folders if necessary
		File folderFile = new File(folderPath);
		if(!folderFile.exists()){
			folderFile.mkdirs();
		}
		FileImageOutputStream fileImageOutputStream = new FileImageOutputStream(
				new File(folderPath, fileName));
		writer.setOutput(fileImageOutputStream);
		writer.write(null, new IIOImage(image, null, null), null);
		fileImageOutputStream.close();
		writer.dispose();
	}

	public static BufferedImage scale(InputStream in, float factorX, float factorY,
			boolean keepAspect) throws Exception {

		BufferedImage original = ImageIO.read(in);
		if (original == null) {
			throw new Exception("Unsupported file format!");
		}

		int originalWidth = original.getWidth();
		int originalHeight = original.getHeight();
		if (keepAspect) {
			factorX = Math.max(factorX, factorY);
			factorY = factorX;
		}

		// The scaling will be nice smooth with this filter
		AreaAveragingScaleFilter scaleFilter = new AreaAveragingScaleFilter(
				Math.round(originalWidth / factorX), Math.round(originalHeight
						/ factorY));
		ImageProducer producer = new FilteredImageSource(original.getSource(),
				scaleFilter);
		ImageGenerator generator = new ImageGenerator();
		producer.startProduction(generator);
		BufferedImage scaled = generator.getImage();

		return scaled;

	}

	/**
	 * Downscale (if necessary) the image, so it's not within the limits of maxWidth and maxHeight and if it's surface is bigger than maxSurfaceInPixels
	 */
	public static BufferedImage scale(InputStream in, int maxSurfaceInPixels, int maxWidth, int maxHeight) throws IOException {

		BufferedImage original = ImageIO.read(in);
		if (original == null) {
			throw new IllegalArgumentException("Unsupported file format!");
		}
		
		int newWidth = original.getWidth();
		int newHeight = original.getHeight();

		/// 1. We downsize if too big surface.
		double surfaceOrigin = newWidth * newHeight; //surface variable with origin parameter
		if (surfaceOrigin > maxSurfaceInPixels) {  // Image too big
			log.debug("width or height > 200");
			newWidth = (int)(Math.sqrt(((double)newWidth/(double)newHeight) * maxSurfaceInPixels));
			newHeight = (int) (maxSurfaceInPixels / newWidth);
			log.debug(newWidth + " " + newHeight);
		}
		
		/// 2. We downsize if image still overlaps the boundaries. Surface is right, but the image is thin and one side is too big.
		if(newWidth > 200||newHeight > 200){
			double factorX = (double)  original.getWidth() / maxWidth;
			double factorY = (double) original.getHeight() / maxHeight;
			log.debug(" factor x = " + factorX +" factor y = " + factorY);
		    factorY = factorX = Math.max(factorX, factorY);  // We take the maximal reduction to both fit the maxWidth and maxHeight requirements.
			newWidth = (int)(original.getWidth()  / factorX);
			newHeight = (int)(original.getHeight() / factorY);
		}

		// The scaling will be nice smooth with this filter
		AreaAveragingScaleFilter scaleFilter = new AreaAveragingScaleFilter(newWidth, newHeight);
		ImageProducer producer = new FilteredImageSource(original.getSource(), scaleFilter);
		ImageGenerator generator = new ImageGenerator();
		producer.startProduction(generator);
		BufferedImage scaled = generator.getImage();
		return scaled;
	}


	public static BufferedImage decoratePictureWithGreyRoundedRectangle(
			InputStream imageInputStream) throws IOException {
		return decoratePictureWithGreyRoundedRectangle(ImageIO
				.read(new BufferedInputStream(imageInputStream)));
	}

	/**
	 * Create a new image of a given size, fill it with white pixels, center the given image on top of the white background.
	 */
	public static BufferedImage fillImageWithTransparentPixelsToMatchSize(int resultImageWidth, int resultImageHeight, BufferedImage srcImage) {

		if(resultImageWidth < srcImage.getWidth() || resultImageHeight < srcImage.getHeight()){
			throw new IllegalArgumentException("Result image size must be bigger than original image size"); // No scaling in this method
		}

		// Create image result
		BufferedImage image = new BufferedImage(resultImageWidth, resultImageHeight, BufferedImage.TYPE_INT_ARGB);

		// determines where to place the image
		int posX = (resultImageWidth - srcImage.getWidth()) / 2; 
		int posY = (resultImageHeight - srcImage.getHeight()) / 2;

		Graphics2D g2D = image.createGraphics();
		g2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);
		g2D.setRenderingHint(RenderingHints.KEY_ALPHA_INTERPOLATION,
				RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY);

		/* Draw white background
        Shape s = new Rectangle(0, 0, resultImageWidth, resultImageHeight);
        g2D.setColor(Color.WHITE); // White
        g2D.draw(s);
		 */

		// Superpose original image
		g2D.drawImage(srcImage, null, posX, posY);

		g2D.dispose();

		return image;

	}



	/**
	 * Decorate an image with rounded and orange rectangle. 
	 */
	public static BufferedImage decoratePictureWithGreyRoundedRectangle(
			BufferedImage srcImage) {

		float stroke = (((float) srcImage.getWidth() + (float) srcImage.getHeight()) / 2.0f)
				* (3.0f / 100.0f);
		stroke = new Float(Math.ceil(new Double(stroke).doubleValue()))
		.floatValue();
		if (stroke < 2.0f)
			stroke = 2.0f;

		BufferedImage image = new BufferedImage(srcImage.getWidth()+2*(int)stroke, srcImage
				.getHeight()+2*(int)stroke, BufferedImage.TYPE_INT_ARGB);

		Graphics2D g2D = image.createGraphics();
		g2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);
		g2D.setRenderingHint(RenderingHints.KEY_ALPHA_INTERPOLATION,
				RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY);

		g2D.drawImage(srcImage, null, Math.round(stroke), Math.round(stroke));

		// Creation of a normal rectangle that covers all the surface of the
		// picture
		Rectangle2D.Float rect = new Rectangle2D.Float(0, 0, image.getWidth(),
				image.getHeight());
		// Creation of the rounded rectangle
		RoundRectangle2D.Float roundedRect = new RoundRectangle2D.Float(
				(float) stroke / 2.0f, (float) stroke / 2.0f, (float) image
				.getWidth()
				- ((float) stroke *1.5f), (float) image.getHeight()
				- ((float) stroke *1.5f), (float) image.getWidth() / 4.0f,
				(float) image.getWidth() / 4.0f);

		// We determine the corner area by subtracting the two above shapes area
		Area cornerArea = new Area(rect);
		cornerArea.subtract(new Area(roundedRect));

		// for each pixel in the corners we set transparency
		for (int y = 0; y < image.getHeight(); y++) {
			for (int x = 0; x < image.getWidth(); x++) {
				int pixel = 0, alpha = 0;
				if (cornerArea.contains(x, y)) {
					pixel = image.getRGB(x, y);
					alpha = ((pixel & 0xff000000) >>> 24) - 255;
					alpha = alpha < 0 ? 0 : alpha;
					alpha = alpha > 255 ? 255 : alpha;
					image.setRGB(x, y, (pixel & 0x00ffffff) | (alpha << 24));
				}
			}
		}

		// Draw the rounded rectangle
		g2D.setColor(new Color(200, 200, 200));
		g2D.setStroke(new BasicStroke(stroke));
		g2D.draw(roundedRect);
		g2D.dispose();

		return image;

	}



	public static boolean checkImageSupport(InputStream imageInputStream) {
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
	
	public static ByteArrayInputStream convertIntoByteArrayInputStream(BufferedImage image){
	    
	    ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {
            ImageIO.write( image, "jpg", baos );
            baos.flush();
            baos.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
          return bais;
	    
	}

}