package mainClass;

import reformyourcountry.repository.ArticleRepository;
import reformyourcountry.repository.BookRepository;

public class ArticleParserDebug {
	
	private static BookRepository bookRepository;
	private static ArticleRepository articleRepository;
	
	
	public static void main(String[]args)
	{
		String example = "je m'appelle jordy\nj'ai quatre ans et jsuis petit\r\nouwéwé baby\n\ndur dur d'être bébé\n\nIt's hard to be a baby\n\nouin ouin";
		String result = "je m'appelle jordy<br/>j'ai quatre ans et jsuis petit<p>ouwéwé baby</p>dur dur d'être bébé<p>It's hard to be a baby</p>ouin ouin";
		
		
		System.out.println("what i expected :"+result);
//		System.out.println("what i have : "+pTag.transformTextBlocksIntoStringWithPTags(example));
	}

}
