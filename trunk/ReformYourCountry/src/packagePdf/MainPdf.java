package packagePdf;

import java.io.*;

public class MainPdf {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		File filetest = new File("S:\\FichiersSorties(ne pas sup)\\pdf4ml\\pd4ml.pdf");
		try {
			FileOutputStream fos =new FileOutputStream(filetest);
			CoursePdfGenerator cPdf = new CoursePdfGenerator();
			cPdf.generatePdf(fos, true, true);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
