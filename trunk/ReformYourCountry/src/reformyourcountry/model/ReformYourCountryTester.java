package reformyourcountry.model;

//import java.util.ArrayList;
//import java.util.List;

public class ReformYourCountryTester {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
	  Action x  = new Action ("Title: Obligation de moins de 16 ans","      description: êtes-vous ok pour que les enfants ne restent pas juqu'a 18 ans?");
	  Action x2 = new Action ("Title: le soleil est beau", "    description: mais il est pas la ajd");
	  
	  // crée un article
	  Articles art = new Articles ("l'obligation scolaire","    la législation oblige les enfants a aller a l'ecole jusqu'a 18 ans");
	  Articles art2 = new Articles ("les enfants a l'ecole", "   les enfants ont droit a une education");
	  
	  Comment co1 = new Comment ("d'accord pour l'ecole obligatoire avant 16 ans");
	  Comment co2 = new Comment ("pas d'accord pour l'ecole obligatoire jusqu'a 16 ans, 18 c'est mieux");
	  Comment co3 = new Comment ("je suis contre l'obligation jusqu'a 16 ans, au boulot au plus vite!!!!!!!!");
	  
	  x.getArticle().add(art);
	  art.getActions().add(x);
	  
	  x.getArticle().add(art2);
	  x2.getArticle().add(art2);
	  
	  art.getActions().add(x);
	  art.getActions().add(x2);
	  art2.getActions().add(x);
	  
	  x.getComment().add(co1);
	  x.getComment().add(co2);
	  
	  x2.getComment().add(co3);
	  
	 
	  x.displayArticles();
	  x2.displayArticles();
	  art.displayAction();
	  x.displayComment();
	  x2.displayComment();
	  
	  
		

	}
	

}
