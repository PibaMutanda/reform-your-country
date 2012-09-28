package reformyourcountry.maintest;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;

public class StringReaderTest {

	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		String example = "je \n\n \r\nsuis un exemple";
		StringReader reader = new StringReader(example);
		int ch=reader.read();

		while(ch != -1){
			if(ch == -1){
				System.out.println("char "+ch+"stream finished");
			}
			System.out.println("char "+ch);
			
			ch = reader.read();
		}
	}
	

	
	
	

}
