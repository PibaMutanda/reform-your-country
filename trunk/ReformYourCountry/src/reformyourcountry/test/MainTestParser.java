package reformyourcountry.test;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import reformyourcountry.converter.BBConverter;
import reformyourcountry.parser.*;
public class MainTestParser {
	public static void main(String[] args){
		String g ="";
		String h ="";
		
		
//		ExportToHtml("[ActionPoint id=\"34\"/]");
//		System.out.println(h="<div class=\"actionpoint-title\">Coca gratuit</div><div class=\"actionpoint-body\">Il faut que le coca-cola soit gratuit chez TechnoFuturTIC</div>");
//		System.out.println(g.equals(h));
		ExportToHtml("[quote bib=\"Gargamel66\"]Il était un fois [...] fin[/quote]");
		System.out.println(h="<div class=\"quote-block\">Il était un fois [...] fin</div><div class=\"bibref-after-block\"><a href=\"/Bibliography#Gargamel66\">[Gargamel66]</a></div>");
		System.out.println(g.equals(h));
//		ExportToHtml("[quote]Je suis une citation écrite par une dame.[bib out=\"http://lesoir.be/toto\"]une femme - Le Soir[/bib][/quote]");
//		System.out.println(h="<div class=\"quote-block\">Je suis une citation écrite par une dame.</div><div class=\"bibref-after-block\">(<a href=\"http://lesoir.be/toto\">une femme - Le Soir</a>)</div>");
//		System.out.println(g.equals(h));
//		ExportToHtml("[escape]Je met des crochets[[[] et des <<< dans mon texte.[/escape]");
//		System.out.println(h="Je met des crochets[[[] et des <<< dans mon texte.");
//		System.out.println(g.equals(h));
//		
//		ExportToHtml("[quote]Je suis une citation avec [link article=\"the-great-article-inside\"]un hyperlien[/link] "
//					+"vers un autre article dedans, ainsi qu'un [link out=\"http://lesoir.be/toto\" label=\"Journal le soir, article du 11 mai 2012\"]" +
//					"hyperlien[/link] vers un site web.[/quote]");
	}
	public static void ExportToHtml(String txt){
		BBDomParser dp = new BBDomParser();
		BBTag tg = null;
		List<String> tags = new ArrayList<String>();
		tags.add("[...]");
		tg = dp.parse(new StringReader(txt),tags);
		DefaultBBTag tag = (DefaultBBTag)tg;
		
		BBConverter.IsTagValid(tag);
		
	    
		System.out.println(BBConverter.BBTagToHtml(tag));
	}
}
