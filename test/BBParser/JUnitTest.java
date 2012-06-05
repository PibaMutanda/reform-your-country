package BBParser;

import static org.junit.Assert.*;

import java.io.StringReader;

import org.junit.Test;

public class JUnitTest {

	@Test
	public void testQuoteBib() {
		BBDomParser dp = new BBDomParser();
		BBTag tg = dp.parse(new StringReader("Un dicton célèbre dit: [quote inline=\"true\" bib=\"Gargamel66\"]La valeur n’attend pas le nombre des années.[/quote]"));
		assertEquals("dunno", tg.toString(), "Un dicton célèbre dit: <span class=\"quote-inline\">La valeur n’attend pas le nombre des années.</span>&nbsp;<span class=\"bibref\">"+
														"<a href=\"/Bibliography#Gargamel666\">[Gargamel666]</a></span>");
	}

}
