package reformyourcountry.maintest;
import java.io.IOException;

import reformyourcountry.converter.BBConverter;

public class MainTestParser {
    public static void main(String[] args) throws IOException{
        BBConverter cvt = new BBConverter();

//        System.out.println(cvt.transformBBCodeToHtmlCode("Un dicton c�l�bre dit: [quote inline=\"true\" bib=\"Gargamel66\"]La valeur n�attend pas le nombre des ann�es.[/quote]"));
//      System.out.println(cvt.transformBBCodeToHtmlCode("[quote]Je suis une citation avec [link article=\"the-great-article-inside\"]un hyperlien[/link] "
//                  +"vers un autre article dedans, ainsi qu�un [link out=\"http://lesoir.be/toto\" label=\"Journal le soir, article du 11 mai 2012\"]" +
//                  "hyperlien[/link] vers un site web.[/quote]"));
//      System.out.println(cvt.transformBBCodeToHtmlCode("[Action id=\"34\"]")); 
//      System.out.println(cvt.transformBBCodeToHtmlCode("[quote bib=\"Gargamel66\"]Il était un fois [...] fin[/quote]")); 
//      System.out.println(cvt.transformBBCodeToHtmlCode("Allez voir [Link article=\"abcd\"]cette page[/link]. [ActionPoint id=\"34\"/]")); 
//      System.out.println(cvt.transformBBCodeToHtmlCode("[quote]Je suis une citation écrite par une dame.[bib out=\"http://www.lesoir.be/la_une/\"]une femme - Le Soir[/bib][/quote]")); 
//      System.out.println(cvt.transformBBCodeToHtmlCode("[quote bib=\"Gargamel66\" addbib=\"page 145-147\"]Je suis une citation d'un livre.[/quote]")); 
//      System.out.println(cvt.transformBBCodeToHtmlCode("[quote]Il y en a 2 [unquote](roues)[/unquote] devant et 2 derrière.[/quote]")); 

        
        System.out.println(cvt.transformBBCodeToHtmlCode("[escape]Je met des crochets[[[] et des <<< dans mon texte. [/escape]")); 
//      System.out.println(cvt.transformBBCodeToHtmlCode("[quote][untranslated]I'm a quote[/untranslated]Je suis une citation[/quote]")); 
//      System.out.println(cvt.transformBBCodeToHtmlCode("[quote]Je suis une citation[untranslated]I'm a quote[/untranslated][/quote]")); 

    }
}

