package blackbelt.parser;


public class Main {
	public static void main(String[] args){
		MyTagHandler tg = new MyTagHandler();
		BlackBeltTagParser parser = new BlackBeltTagParser(tg, "test blabla [code]ceci est du code[/code]");
		
		System.out.println(tg.getOutputString(parser.parse()));
		
	}
}
