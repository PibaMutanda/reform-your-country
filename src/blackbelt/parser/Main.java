package blackbelt.parser;


public class Main {
	public static void main(String[] args){
		MyTagHandler tg = new MyTagHandler();
		BlackBeltTagParser parser = new BlackBeltTagParser(tg, "[/quote]test [/code]double balise[/code]"+
																"[/quote]blabla [code]ceci [quote]est[/quote] du code[/code]");
		
		System.out.println(tg.addParagraph(tg.getOutputString(parser.parse())));
		
	}
}
