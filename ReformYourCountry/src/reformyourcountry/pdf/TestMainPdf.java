package reformyourcountry.pdf;


import java.awt.Dimension;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

import blackbelt.web.UrlUtil;


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
			// file will be create in os temp directory  C:\Users\forma*\AppData\Local\Temp
			File filetest = new File(System.getProperty("java.io.tmpdir")+"pd4ml.pdf");
			FileOutputStream fos =new FileOutputStream(filetest);
			ArticlePdfGenerator cPdf = new ArticlePdfGenerator(true);

			// html input file 
			//source file read from workspace location
			URL sourceHtml = new URL("file:///"+System.getProperty("user.dir")+"/src/reformyourcountry/pdf/"+"wiki.html");
			
			URLConnection co = sourceHtml.openConnection();
			BufferedReader in = new BufferedReader(new InputStreamReader(co.getInputStream()));
			 
			// Read the input file into a string.
			String input="";
			String inputLine ;
			while ((inputLine = in.readLine()) != null){		
				 input = input + inputLine;
			}
			
			String headerHtmlTemplate = "<table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\">"
					+ "<tr>"
					+ "<td><span class =\"titre\">${title}</span></td>"
					+ "<td>page ${page}</td>" + "</tr>" + "</table>";
			
			String footerHtmlTemplate = "<table width='100%'><tr><td></td><td class='licence'>"+"wikipedia 2012"+
		        "</td><td align='right' class='valignBottom'>${page}<span class='grey'> /${total}</span></td></tr></table>";
				
			cPdf.generatePDF(input, fos, headerHtmlTemplate, footerHtmlTemplate, true); 
			fos.close();
		} catch (Exception e) {
	
			System.err.println("An exception was raised : " + e.getMessage());
		}
		
	}

}



