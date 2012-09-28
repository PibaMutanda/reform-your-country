package mainClass;

import reformyourcountry.model.Action;
import reformyourcountry.model.Argument;
import reformyourcountry.model.Article;
import reformyourcountry.model.Comment;
import reformyourcountry.model.Group;
import reformyourcountry.model.GroupReg;
import reformyourcountry.model.User;
import reformyourcountry.model.VoteAction;
import reformyourcountry.model.VoteArgument;



public class ReformYourCountryTester {

    
    
    public static void main(String[] args) {
        Action action1  = new Action ("Title: Obligation de moins de 16 ans","description: Ãªtes-vous ok pour que les enfants ne restent pas juqu'a 18 ans?");
          
          //create articles
          Article article1 = new Article ("l'obligation scolaire","la législation oblige les enfants à  aller à  l'école jusqu'a 18 ans");
          Article article2 = new Article ("les enfants a l'ecole", "les enfants ont droit à  une éducation");
          Article article3 = new Article ("les enfants doivent avoir une scolarité des plus poussée", "les enfants devrait aller a l'ecole au moins jusqu'a 21 ans pour pouvoir apprendre le plus possible sur les matières générales");
          
          //add article to an action
          action1.getArticles().add(article1);
          action1.getArticles().add(article2);
          
          //link parent-children
          article1.getChildren().add(article2); 
          article1.getChildren().add(article3);
          
          //link children - parent
          article2.setParent(article1);
          article3.setParent(article1);
          
          //create users
          User user1 = new User();
          User user2 = new User();
          User user3 = new User();
          
        //create arguments    
          Argument argument1 = new Argument("d'accord pour l'Ã©cole obligatoire avant 16 ans", action1, user1);
          Argument argument2 = new Argument("pas d'accord pour l'ecole obligatoire jusqu'a 16 ans, 18 c'est mieux", action1, user2);
          Argument argument3 = new Argument ("je suis contre l'obligation jusqu'a 16 ans, au boulot au plus vite!!!!!!!!", action1, user3);
          
          //add argument to an action
          action1.getArguments().add(argument1);
          action1.getArguments().add(argument2);
          action1.getArguments().add(argument3);
          
        //create comments
          Comment comment1 = new Comment ("l'obligation scolaire est une bonne chose", action1, user1);
          Comment comment2 = new Comment ("l'obligation scolaire est une mauvaise chose", action1, user2);
          
          //add comment to an article
          action1.getComments().add(comment1);
          action1.getComments().add(comment2);
              
          //create GroupReg
          GroupReg groupreg1 = new GroupReg();
          GroupReg groupreg2 = new GroupReg();
          GroupReg groupreg3 = new GroupReg();
          
          //add  date to a user
          user1.getGroupRegs().add(groupreg1);
          user1.getGroupRegs().add(groupreg2);
          user2.getGroupRegs().add(groupreg3);
          
          //create group
          Group group1 = new Group();
          Group group2 = new Group();
          Group group3 = new Group();
          Group group4 = new Group();
          
          //link parent-children
          group1.getChildren().add(group4); 
          group2.getChildren().add(group3);
          
          //link children - parent
          group3.setParent(group2);
          group4.setParent(group1);
          
          //add group to groupReg 
          groupreg1.setGroup(group1);
          groupreg2.setGroup(group2);
          groupreg3.setGroup(group1);
          
          //create voteArguments
          VoteArgument voteArguments1 = new VoteArgument(1,2,argument1,user1);
          VoteArgument voteArguments2 = new VoteArgument(2, 2,argument1,user2);
          VoteArgument voteArguments3 = new VoteArgument(3, -2,argument1,user3);
          
          //add voteArguments to an Argument
          argument1.getVoteArguments().add(voteArguments1);
          argument1.getVoteArguments().add(voteArguments2);
          argument1.getVoteArguments().add(voteArguments3);
          
          //create voteActions
          VoteAction voteAction1 = new VoteAction(1,-2,action1,user2,null);
          VoteAction voteAction2 = new VoteAction(2,0,action1,null,group1);
              
          //add voteAction to an action
          action1.getVoteActions().add(voteAction1);
          action1.getVoteActions().add(voteAction2);
              
          //add voteAction to an user or a group
          user2.getVoteActions().add(voteAction1);
          group1.getVoteActions().add(voteAction2);
          
    
    }


}
