package reformyourcountry.model;

import java.util.Date;

//import java.util.ArrayList;
//import java.util.List;

public class ReformYourCountryTester {

	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		/*Group_Reg c = new Group_Reg(); 
		*/
		
	  Action action1  = new Action ("Title: Obligation de moins de 16 ans","description: êtes-vous ok pour que les enfants ne restent pas juqu'a 18 ans?");
	  //Action x2 = new Action ("Title: le soleil est beau", "description: mais il est pas la ajd");
	  
	  //create articles
	  Article article1 = new Article ("l'obligation scolaire","la législation oblige les enfants à aller à l'école jusqu'a 18 ans",null);
	  Article article2 = new Article ("les enfants a l'ecole", "les enfants ont droit à une éducation",null);
	  //add article to an action
	  action1.getArticles().add(article1);
	  action1.getArticles().add(article2);
	  //create comments
	  Comment comment1 = new Comment ("l'obligation scolaire est une bonne chose",null);
	  Comment comment2 = new Comment ("l'obligation scolaire est une mauvaise chose",null);
	  //add comment to an article
	  action1.getComments().add(comment1);
	  action1.getComments().add(comment2);
	  //create arguments	  
	  Argument argument1 = new Argument("d'accord pour l'école obligatoire avant 16 ans",null,null);
	  Argument argument2 = new Argument("pas d'accord pour l'ecole obligatoire jusqu'a 16 ans, 18 c'est mieux",null,null);
	  Argument argument3 = new Argument ("je suis contre l'obligation jusqu'a 16 ans, au boulot au plus vite!!!!!!!!",null,null);
	  //add argument to an action
	  action1.getArguments().add(argument1);
	  action1.getArguments().add(argument2);
	  action1.getArguments().add(argument3);
	
	  //create users
	  User user1 = new User();
	  User user2 = new User();
	  User user3 = new User();
	  	  
	  //create voteArguments
	  VoteArguments voteArguments1 = new VoteArguments(2,argument1,user1,null);
	  VoteArguments voteArguments2 = new VoteArguments(2,argument1,user2,null);
	  VoteArguments voteArguments3 = new VoteArguments(-2,argument1,user3,null);
	  //add voteArguments to an Argument
	  argument1.getVoteArguments().add(voteArguments1);
	  argument1.getVoteArguments().add(voteArguments2);
	  argument1.getVoteArguments().add(voteArguments3);
	  //create voteActions
	  VoteAction voteAction1 = new VoteAction(-2,null,null,null);
	  VoteAction voteAction2 = new VoteAction(0,null,null,null);
	  VoteAction voteAction3 = new VoteAction(2,null,null,null);
	  //add voteAction to an action
	  action1.getVoteActions().add(voteAction1);
	  action1.getVoteActions().add(voteAction2);
	  action1.getVoteActions().add(voteAction3);
	  	
	  user1.getVoteArguments().add(voteArguments1);
	  
		

	}
	
	



}
