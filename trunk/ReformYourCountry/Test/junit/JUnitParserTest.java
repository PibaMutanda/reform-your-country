package junit;


import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import reformyourcountry.converter.BBConverter;
import reformyourcountry.repository.ArticleRepository;
import reformyourcountry.repository.BookRepository;


public class JUnitParserTest {

	private BookRepository bookRepository;
	private ArticleRepository articleRepository;
	public  String ExportToHtml(String txt){
		List<String> tags = new ArrayList<String>();
		tags.add("[...]");
		BBConverter bbc = new BBConverter(bookRepository, articleRepository); 
		return bbc.transformBBCodeToHtmlCode(txt);
	}

	@Test
	public void test1() {
		assertEquals(ExportToHtml("Un dicton célèbre dit: [quote inline=\"true\" bib=\"Gargamel66\"]La valeur n’attend pas le nombre des années.[/quote]"),
				"Un dicton célèbre dit: <span class=\"quote-inline\">La valeur n’attend pas le nombre des années.</span><span class=\"bibref\"><a href=\"/Bibliography#Gargamel66\">[Gargamel66]</a></span>");
	}
	@Test
	public void test2() {
		assertEquals(ExportToHtml("[quote]Je suis une citation avec [link article=\"the-great-article-inside\"]un hyperlien[/link] vers un autre article dedans, ainsi qu’un [link out=\"http://lesoir.be/toto\" label=\"Journal le soir, article du 11 mai 2012\"]hyperlien[/link] vers un site web.[/quote]"),
		        "<div class=\"quote-block\">Je suis une citation avec <a href=\"/Article/the-great-article-inside\">un hyperlien</a> vers un autre article dedans, ainsi qu’un <a href=\"http://lesoir.be/toto\">hyperlien</a> vers un site web.</div>");
	}
	@Test
	public void test3(){
		assertEquals(ExportToHtml("[Action id=\"34\"/]"),"<div class=\"action-title\">Coca gratuit</div><div class=\"action-body\">Il faut que le coca-cola soit gratuit chez TechnofuturTic</div>");
	}
	@Test
	public void test4(){
		assertEquals(ExportToHtml("[quote bib=\"Gargamel66\"]Il était un fois [...] fin[/quote]"),"<div class=\"quote-block\">Il était un fois [...] fin</div><div class=\"bibref-after-block\"><a href=\"/Bibliography#Gargamel66\">[Gargamel66]</a></div>");
	}
	@Test
	public void test5(){
		assertEquals(ExportToHtml("Allez voir [Link article=\"abcd\"]cette page[/link]"),"Allez voir <a href=\"/Article/abcd\">cette page</a>");
	}
	@Test
	public void test6(){
		assertEquals(ExportToHtml("[quote]Je suis une citation écrite par une dame.[bib out=\"http://lesoir.be/toto\"]une femme - Le Soir[/bib][/quote]"),
		        "<div class=\"quote-block\">Je suis une citation écrite par une dame.</div><div class=\"bibref-after-block\">(<a href=\"http://lesoir.be/toto\">une femme - Le Soir</a>)</div>");
	}
	@Test
	public void test7(){
		assertEquals(ExportToHtml("[quote bib=\"Gargamel66\" addbib=\"page 145-147\"]Je suis une citation d’un livre.[/quote]"),"<div class=\"quote-block\">Je suis une citation d’un livre.</div><div class=\"bibref-after-block\"><a href=\"/Bibliography#Gargamel66\">[Gargamel66]</a> page 145-147</div>");
	}
	@Test
	public void test8(){
		assertEquals(ExportToHtml("[quote]Il y en a 2 [unquote](roues)[/unquote] devant et 2 derrière.[/quote]"),"<div class=\"quote-block\">Il y en a 2 <span class=\"unquote\">(roues)</span> devant et 2 derrière.</div>");
	}
	@Test
	public void test9(){
		assertEquals(ExportToHtml("[escape]Je met des crochets[[[] et des <<< dans mon texte.[/escape]"),"Je met des crochets[[[] et des <<< dans mon texte.");
	}
	@Test
	public void test10(){
		assertEquals(ExportToHtml("[quote]Je suis une citation[untranslated]I’m a quote[/untranslated][/quote]"),"<div class=\"quote-block\">Je suis une citation<div class=\"quote-untranslated\">I’m a quote</div></div>");
	}
	@Test
	public void test11(){
		assertEquals(ExportToHtml("[quote][untranslated]I’m a quote[/untranslated]Je suis une citation[/quote]"),"<div class=\"quote-block\">Je suis une citation<div class=\"quote-untranslated\">I’m a quote</div></div>");
	}
}
