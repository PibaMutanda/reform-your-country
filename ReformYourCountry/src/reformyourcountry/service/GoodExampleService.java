package reformyourcountry.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import reformyourcountry.model.Comment;
import reformyourcountry.model.GoodExample;
import reformyourcountry.model.User;
import reformyourcountry.model.VoteArgument;
import reformyourcountry.model.VoteGoodExample;
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
    @Autowired CommentRepository commentRepository;
    
    public void vote(GoodExample goodExample){
        VoteGoodExample vote = new VoteGoodExample();
        
        vote.setGoodExample(goodExample);
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

	public void deleteGoodExample(GoodExample goodExample) {
		for( Comment com :goodExample.getCommentList()){
	           commentRepository.remove(com);
	       }
		goodExampleRepository.remove(goodExample);
	}

}
