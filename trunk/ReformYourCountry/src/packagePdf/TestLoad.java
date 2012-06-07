package packagePdf;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.Scanner;



public class TestLoad {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		URL source;
		URL css;

		try {
			//source = new URL("http://fr.wikipedia.org/wiki/Scrum_(m%C3%A9thode)");
			source = new URL("http://www.rtbf.be/");
			URLConnection yc;
			yc = source.openConnection();
			BufferedReader in;

			in = new BufferedReader(new InputStreamReader(yc.getInputStream()));

			String inputLine;
			
			//css = new URL("http://fr.wikipedia.org/w/index.php?title=MediaWiki:Common.css/taxobox_v3.css&action=raw&ctype=text/css");
			css = new URL("http://sgc.static.rtbf.be/css/7/3/73dd36d745a86b671aa358b9c5550530.css");
			URLConnection cssc = css.openConnection();
			
			BufferedReader incss =  new BufferedReader(new InputStreamReader(cssc.getInputStream()));
			
			
			CoursePdfGenerator pdfgen = new CoursePdfGenerator();
			
			File filetest = new File("S:\\FichiersSorties(ne pas sup)\\pdf4ml\\pd4mlrtbf.pdf");
			try {
				FileOutputStream fos =new FileOutputStream(filetest);
			
					
				
					String input="";
				while ((inputLine = in.readLine()) != null){
					
			    			
					 input = input + inputLine;
				}
				
				pdfgen.generatePdf(input,fos,incss);
				
				fos.close();
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
			
             incss.close();
			 in.close();

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
