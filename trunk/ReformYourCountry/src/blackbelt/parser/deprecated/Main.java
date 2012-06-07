package blackbelt.parser.deprecated;


public class Main {
	public static void main(String[] args){
		MyTagHandler tg = new MyTagHandler();
<<<<<<< .mine
		BlackBeltTagParser parser = new BlackBeltTagParser(tg, "[quote]test [code]double balise[code]"+
																"[/quote]blabla[...] [code]ceci [quote]est[/quote] du code[/code]");
=======
		BlackBeltTagParser parser = new BlackBeltTagParser(tg, "[/quote]test [/code]double balise[/code]"+
																"[/quote]blabla[...] [code]ceci [quote]est[/quote] du code[/code]");
>>>>>>> .r70
		
		System.out.println(tg.addParagraph(tg.getOutputString(parser.parse())));
		
	}
}
