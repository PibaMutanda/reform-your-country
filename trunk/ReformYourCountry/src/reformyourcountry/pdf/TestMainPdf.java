package reformyourcountry.pdf;


import java.awt.Dimension;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;


/** Used during development to test PD4ML, and PDF generation.
 * 
 * @author Lionel
 *
 */
public class TestMainPdf {


	public static void main(String[] args) {
		// TODO Auto-generated method stub

		try {
			// pdf output file
			File filetest = new File("S:\\FichiersSorties(ne pas sup)\\pdf4ml\\pd4ml.pdf");
			FileOutputStream fos =new FileOutputStream(filetest);
			ArticlePdfGenerator cPdf = new ArticlePdfGenerator();

			// html input file
			URL sourceHtml = new URL("file:///S:/FichiersSorties%28ne%20pas%20sup%29/pdf4ml/wiki.html");
			URLConnection co = sourceHtml.openConnection();
			BufferedReader in = new BufferedReader(new InputStreamReader(co.getInputStream()));
			 
			// Read the input file into a string.
			String input="";
			String inputLine ;
			while ((inputLine = in.readLine()) != null){		
				 input = input + inputLine;
			}
				
			cPdf.generatePDF(input, fos, new Dimension(500,500), null, null); 
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}



