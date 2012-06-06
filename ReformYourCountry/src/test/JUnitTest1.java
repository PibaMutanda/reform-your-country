package test;

import static org.junit.Assert.*;

import java.io.StringReader;

import org.junit.Before;
import org.junit.Test;

import BBParser.BBDomParser;

public class JUnitTest1 {

	@Before
	public void setUp() throws Exception {
		
	}

	@Test
	public void test1() {
		assertEquals(new BBDomParser().parse(new StringReader("Un dicton célèbre dit: [quote inline=\"true\" bib=\"Gargamel66\"]La valeur n’attend pas le nombre des années.[/quote]")),"Un dicton célèbre dit: <span class=\"quote-inline\">La valeur n’attend pas le nombre des années.</span>&nbsp;<span class=\"bibref\"><a href=\"/Bibliography#Gargamel666\">[Gargamel666]</a></span>");
	}
	@Test
	public void test2() {
		assertEquals(new BBDomParser().parse(new StringReader("[quote]Je suis une citation avec [link article=\"the-great-article-inside\"]un hyperlien[/link] vers un autre article dedans, ainsi qu’un [link out=\"http://lesoir.be/toto\" label=\"Journal le soir, article du 11 mai 2012\"]hyperlien[/link] vers un site web.[/quote]")),"<div class=\"quote-block\">Je suis une citation avec <a href=\"/Article/the-great-article-inside\">un hyperlien</a> vers un autre article dedans, ainsi qu’un <a href=\"http://lesoir.be/toto\">hyperlien</a> vers un site web.</div>");
	}
	@Test
	public void test3(){
		assertEquals(new BBDomParser().parse(new StringReader("[ActionPoint id=\"34\"/]")),"<div class=\"actionpoint-title\">Coca gratuit</div><div class=\"actionpoint-body\">Il faut que le coca-cola soit gratuit chez TechnofuturTIC</div>");
	}
	@Test
	public void test4(){
		assertEquals(new BBDomParser().parse(new StringReader("[quote bib=\"Gargamel66\"]Il était un fois [...] fin[/quote]")),"<div class=\"quote-block\">Il était un fois [...] fin</div><div class=\"bibref-after-block\"><a href=\"/Bibliography#Gargamel666\">[Gargamel66]</a></div>");

	}
	@Test
	public void test5(){
		assertEquals(new BBDomParser().parse(new StringReader("Allez voir [Link article=\"abcd\"]cette page[/link]")),"Allez voir <a href=\"Article/abcd\">cette page</a>");
	}
	@Test
	public void test6(){
		assertEquals(new BBDomParser().parse(new StringReader("[quote]Je suis une citation écrite par une dame.[bib out=\"http://lesoir.be/toto\"]une femme - Le Soir[/bib][/quote]")),"<div class=\"quote-block\">Je suis une citation écrite par une dame.</div><div class=\"bibref-after-block\">(<a href=\"http://lesoir.be/toto\">une femme - Le Soir</a>)</div>");
	}
	@Test
	public void test7(){
		assertEquals(new BBDomParser().parse(new StringReader("[quote bib=\"Gargamel66\" addbib=\"page 145-147\"]Je suis une citation d’un livre.[/quote]")),"<div class=\"quote-block\">Je suis une citation d’un livre.</div><div class=\"bibref-after-block\"><a href=\"/Bibliography#Gargamel666\">[Gargamel66]</a> page 145-147</div>");
	}
	@Test
	public void test8(){
		assertEquals(new BBDomParser().parse(new StringReader("[quote]Il y en a 2 [unquote](roues)[/unquote] devant et 2 derrière.[/quote]")),"<div class=\"quote-block\">Il y en a 2 <span class=\"unquote\">(roues)</span> devant et 2 derrière.</div>");
	}
	@Test
	public void test9(){
		assertEquals(new BBDomParser().parse(new StringReader("[escape]Je met des crochets[[[] et des <<< dans mon texte. [/escape]")),"Je met des crochets[[[] et des <<< dans mon texte.");
	}
	@Test
	public void test10(){
		assertEquals(new BBDomParser().parse(new StringReader("[quote]Je suis une citation [untranslated]I’m a quote[/untranslated][/quote]")),"<div class=\"quote-block\">Je suis une citation<div class=\"quote-untranslated\">I’m a quote</div></div>");
	}
	@Test
	public void test11(){
		assertEquals(new BBDomParser().parse(new StringReader("[quote][untranslated]I’m a quote[/untranslated]Je suis une citation[/quote]")),"<div class=\"quote-block\">Je suis une citation<div class=\"quote-untranslated\">I’m a quote</div></div>");
	}
}
