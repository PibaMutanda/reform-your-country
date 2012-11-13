package reformyourcountry.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import reformyourcountry.model.GoodExample;
import reformyourcountry.model.VoteGoodExample;
import reformyourcountry.repository.GoodExampleRepository;
import reformyourcountry.repository.VoteGoodExampleRepository;
import reformyourcountry.security.SecurityContext;
@Service
public class GoodExampleService {
    @Autowired
    VoteGoodExampleRepository voteGoodExampleRepository; 
    @Autowired
    GoodExampleRepository goodExampleRepository;
    
    public void addVote(GoodExample goodExample){
        VoteGoodExample vote = new VoteGoodExample();
        
        vote.setGoodExample(goodExample);
        vote.setUser(SecurityContext.getUser());
        voteGoodExampleRepository.persist(vote);
        
        goodExample.setVoteCount(voteGoodExampleRepository.getVoteGoodExampleCountForAGoodExample(goodExample));
        goodExampleRepository.merge(goodExample);
    }

}
