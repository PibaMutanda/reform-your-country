package test;
import java.io.StringReader;
import java.util.HashMap;
import java.util.Map;

import BBParser.*;
import blackbelt.parser.*;
public class MainTestParser {
	public static void main(String[] args){
		BBDomParser dp = new BBDomParser();
		BBTag tg = null;
		try {
			tg = dp.parse(new StringReader("[h1 \"123\"]Article n°128[/h1][/a][quote][a href=\"www.google.com\"]<br>[Quote bib=\"dixit CF\"]ceci est un paragraphe de test avec du [code]code <br> <p>test de paragraphe</p>[/code][/Quote]"));
		    
			System.out.println(tg.toString());

		} catch (BBParserException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
