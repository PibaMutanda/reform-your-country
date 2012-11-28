package reformyourcountry.maintest;



import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;

import reformyourcountry.pdf.ArticlePdfGenerator;




/** Used during development to test PD4ML, and PDF generation.
 * 
 * @author Lionel
 *
 */
public class TestMainPdf {


	public static void main(String[] args) {
		

		try {
		
			
			ArticlePdfGenerator cPdf = new ArticlePdfGenerator(true);

			ByteArrayOutputStream bos = new ByteArrayOutputStream();
		 
			// html input file 
			//source file read from workspace location
			URL sourceHtml = new URL("file:///"+System.getProperty("user.dir")+"/src/reformyourcountry/pdf/"+"wiki1.html");
			
			URLConnection co = sourceHtml.openConnection();
			BufferedReader in = new BufferedReader(new InputStreamReader(co.getInputStream()));
			 
			// Read the input file into a string.
			String input="";
			String inputLine ;
			while ((inputLine = in.readLine()) != null){		
				 input = input + inputLine;
			}
			
	
			
			
		// pdf generation html input , binary output stream,coverpage ? 
			cPdf.generatePDF(input, bos, true); 
			
			// pdf output file
			// file will be create in os temp directory  C:\Users\forma*\AppData\Local\Temp
			File filetest = new File(System.getProperty("java.io.tmpdir")+"pd4ml.pdf");
			FileOutputStream fos =new FileOutputStream(filetest);
			
			bos.writeTo(fos);
			
		
			bos.close();
			fos.close();
			
		} catch (Exception e) {
	
			throw new RuntimeException(e);
		}
		
	}

}



