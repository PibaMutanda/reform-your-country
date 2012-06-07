package packagePdf;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

public class MainCoverPage {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		File filetest = new File("S:\\coverTest.pdf");
		try {
			FileOutputStream fos =new FileOutputStream(filetest);
			TestCoverPage cPdf = new TestCoverPage();
			cPdf.generatePdf(fos, true);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
