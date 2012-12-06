package reformyourcountry.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import reformyourcountry.model.Article;
import reformyourcountry.model.Comment;
import reformyourcountry.model.GoodExample;
import reformyourcountry.model.User;
import reformyourcountry.model.VoteGoodExample;
import reformyourcountry.repository.ArticleRepository;
import reformyourcountry.repository.CommentRepository;
import reformyourcountry.repository.GoodExampleRepository;
import reformyourcountry.repository.VoteGoodExampleRepository;
import reformyourcountry.security.SecurityContext;
@Service
public class GoodExampleService {
    @Autowired
    VoteGoodExampleRepository voteGoodExampleRepository; 
    @Autowired
    GoodExampleRepository goodExampleRepository;
    @Autowired 
    CommentRepository commentRepository;
    @Autowired
    ArticleRepository articleRepository;
    
    public void vote(GoodExample goodExample){
        VoteGoodExample vote = new VoteGoodExample();
        
        vote.setGoodExample(goodExample);
        User user = SecurityContext.getUser();
        
        //check if user has already voted
        if (voteGoodExampleRepository.getVoteGoodExampleForAnUser(user, goodExample) != null) {
            throw new RuntimeException("user cannot vote twice for a goodExample " + user.getUserName() + " for example " + goodExample.getId());
        }
        
        vote.setUser(SecurityContext.getUser());
        voteGoodExampleRepository.persist(vote);
        
        goodExample.setVoteCount(voteGoodExampleRepository.getVoteGoodExampleCountForAGoodExample(goodExample));
        goodExampleRepository.merge(goodExample);
    }
    
    public void unVote(GoodExample goodExample){
        User user = SecurityContext.getUser();

        VoteGoodExample vote = voteGoodExampleRepository.getVoteGoodExampleForAnUser(user, goodExample);
        
        voteGoodExampleRepository.remove(vote);
        
        goodExample.setVoteCount(voteGoodExampleRepository.getVoteGoodExampleCountForAGoodExample(goodExample));
        goodExampleRepository.merge(goodExample);
    }
    
    /**
     * delete a goodExample and delete all linked ressources
     * assuming all privileges already checked
     *
     */
    @Transactional
	public void deleteGoodExample(GoodExample goodExample) {
	    for( Comment com :goodExample.getCommentList()){
	        commentRepository.remove(com);
	    }
	    
	    for( VoteGoodExample vote :goodExample.getVoteGoodExample() ){
	        voteGoodExampleRepository.remove(vote);
	    }
	    
		for ( Article article : goodExample.getArticles()) {
		    article.getGoodExamples().remove(goodExample);
		    articleRepository.merge(article);
		}
		
		goodExampleRepository.remove(goodExample);
	}

}
