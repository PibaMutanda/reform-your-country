package reformyourcountry.maintest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import reformyourcountry.batch.BatchUtil;
import reformyourcountry.converter.BBConverter;
import reformyourcountry.repository.ArticleRepository;
import reformyourcountry.repository.BookRepository;

@Service
public class ArticleParserDebug implements Runnable {
	
	@Autowired	private BookRepository bookRepository;
	@Autowired 	private ArticleRepository articleRepository;
	
	
	public static void main(String[]args) {
		BatchUtil.startSpringBatch(ArticleParserDebug.class);
	}
	
	public void run() {
		String example = "machin [link article=\"auto\"]mens[/link] chose\n\n [quote inline=\"true\"]jesuis un quote inline[/quote] [quote inline=\"false\"]non c'est faux[/quote]";
		String example2 = " Des situations comme celles-là, j’en ai rencontré bon nombre, tout comme mes collègues, d’ailleurs.[link article=\"effort\"]Professeur, envers et contre tout[/link]\n\n[quote bib=\"mens\"]L’école pourrait recruter elle-même les enseignants, en suivant une procédure déterminée par le Ministère. Etant donné l’objectif de l’école défini ci-dessus, celle-ci aurait tout intérêt à recruter les meilleurs candidats; ceux qui sont retenus seraient ainsi plus motivés à collaborer avec l’équipe et seraient mieux soutenus par elle.[/quote]";
		String example3 = "L'affectation actuelle des fonctionnaires à leur école est digne des plus obscures économies planifiées.\n\"Comment, c’est à 100 km de votre domicile ? Ce n’est pas notre affaire, Monsieur ! Vous dites qu’un autre candidat qui n’a pas postulé dans la région de votre choix a obtenu l’emploi que vous souhaitiez ? Mais de quoi vous mêlez-vous ?\" Voilà le style de réponse que l’on obtient après huit ans de services. Des situations comme celles-là, j’en ai rencontré bon nombre, tout comme mes collègues, d’ailleurs.[link article=\"effort\"]Professeur, envers et contre tout[/link]";
		BBConverter converter = new BBConverter(bookRepository, articleRepository);
		System.out.println(converter.transformBBCodeToHtmlCode(example3));
	}

}
