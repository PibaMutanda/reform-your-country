package blackbelt.parser;


public class Main {
	public static void main(String[] args){
		String text="";
		MyTagHandler tg = new MyTagHandler();
		BlackBeltTagParser parser = new BlackBeltTagParser(tg, "test blabla [code]ceci est du code[/code]");
		
		text = tg.getOutputString(parser.parse());
		System.out.println(text);
	}
}
