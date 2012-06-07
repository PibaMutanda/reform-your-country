package reformyourcountry.test;
import java.io.StringReader;

import reformyourcountry.converter.BBConverter;
import reformyourcountry.parser.*;
public class MainTestParser {
	public static void main(String[] args){
		ExportToHtml("[quote]Je suis une citation avec [link article=\"the-great-article-inside\"]un hyperlien[/link] "
					+"vers un autre article dedans, ainsi qu�un [link out=\"http://lesoir.be/toto\" label=\"Journal le soir, article du 11 mai 2012\"]" +
					"hyperlien[/link] vers un site web.[/quote]");
	}
	public static void ExportToHtml(String txt){
		BBDomParser dp = new BBDomParser();
		BBTag tg = null;
		tg = dp.parse(new StringReader(txt));
		DefaultBBTag tag = (DefaultBBTag)tg;
		
		BBConverter.IsTagValid(tag);
		
	    
		System.out.println(BBConverter.BBTagToHtml(tag));
	}
}
