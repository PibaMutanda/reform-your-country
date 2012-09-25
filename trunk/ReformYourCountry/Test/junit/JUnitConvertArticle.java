package junit;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import reformyourcountry.converter.BBConverter;
import reformyourcountry.converter.PTagsGenerator;
import reformyourcountry.repository.ArticleRepository;
import reformyourcountry.repository.BookRepository;

public class JUnitConvertArticle {

	private BookRepository bookRepository;
	private ArticleRepository articleRepository;
	
	private BBConverter converter = new BBConverter(bookRepository, articleRepository);
		
	@Test
	public void newStringGen() {
		String example = "je m'appelle jordy\n\nj'ai quatre ans et jsuis petit\n ouwéwé baby\n\n dur dur d'être bébé\n\n";
        PTagsGenerator pTagsGenerator = new PTagsGenerator();
		String result = pTagsGenerator.transformTextBlocksIntoStringWithPTags(example);
		System.out.println(result);
	}

	@Test
	public void lists() {
		String example = "Trois points:\n<ul><li>le premier,</li>\n<li>Le deuxième</li>\n\n\n<li>Le troisième</li></ul>Voilà.";
        PTagsGenerator pTagsGenerator = new PTagsGenerator();
		String result = pTagsGenerator.transformTextBlocksIntoStringWithPTags(example);
		System.out.println(result);
	}
	
	@Test
	public void manuelBR() {
		String example = "je retourne à la ligne<br/>\n ou pas\n\n";
        PTagsGenerator pTagsGenerator = new PTagsGenerator();
		String result = pTagsGenerator.transformTextBlocksIntoStringWithPTags(example);
		System.out.println(result);
	}
	

	
//	@Test
//	public void addP() {
//		String example = "je m'appelle jordy\n\nj'ai quatre ans et jsuis petit\r\n ouwéwé baby\n\n dur dur d'être bébé\n\n";
//		String result = "<p>je m'appelle jordy</p><p>j'ai quatre ans et jsuis petit</p><p> ouwéwé baby</p><p> durdur d'être bébé</p>";
//		assertEquals(result,converter.transformBBCodeToHtmlCode(example));
//		System.out.println("what i have"+result);
//	}
//	@Test
//	public void addBr() {
//		String example  ="i'm the mad commiter\n i'm the number one\n I'm an expert";
//		String expected ="i'm the mad commiter<br />\n i'm the number one<br/>\n I'm an expert";
//		String result = converter.transformBBCodeToHtmlCode(example);
//		assertEquals(expected, result);
//		System.out.println("what i have"+result);
//	}
//	@Test
//	public void addPandBr() {
//		String example = "je m'appelle jordy\n j'ai quatre ans et jsuis petit\r\nouwéwé baby\n\ndur dur d'être bébé\n\nIt's hard to be a baby\n\nouin ouin";
//		String result = "je m'appelle jordy<br /> j'ai quatre ans et jsuis petit<p>ouwéwé baby</p>dur dur d'être bébé<p>It's hard to be a baby</p>ouin ouin";
//		assertEquals(result,converter.transformBBCodeToHtmlCode(example));
//		System.out.println("what i have"+result);
//	}
}