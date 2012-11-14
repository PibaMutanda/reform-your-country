package reformyourcountry.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import reformyourcountry.model.Action;
import reformyourcountry.repository.VoteActionRepository;
@Service
@Transactional
public class VoteActionService {
    
    @Autowired VoteActionRepository voteActionRepository;
    
    
    public List<Long> getResultNumbersForAction( Action action){
        
        
        List<Long> resultNumbers = new ArrayList<Long>();
        for(int i=-2;i<=2;i++){
            resultNumbers.add(voteActionRepository.getNumberOfVotesByValue(action.getId(), i));
        }
        
        return resultNumbers;
    }

}
