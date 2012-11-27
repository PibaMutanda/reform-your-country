package reformyourcountry.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import reformyourcountry.model.Argument;
import reformyourcountry.model.Comment;
import reformyourcountry.model.User;
import reformyourcountry.model.VoteArgument;
import reformyourcountry.repository.ActionRepository;
import reformyourcountry.repository.ArgumentRepository;
import reformyourcountry.repository.CommentRepository;
import reformyourcountry.repository.UserRepository;
import reformyourcountry.repository.VoteActionRepository;
import reformyourcountry.repository.VoteArgumentRepository;

@Service
@Transactional
public class ArgumentService {
    
    @Autowired ArgumentRepository argumentRepository;
    @Autowired ActionRepository actionRepository;
    @Autowired UserRepository userRepository;
    @Autowired VoteActionRepository voteActionRepository;
    @Autowired VoteArgumentRepository voteArgumentRepository;
    @Autowired CommentRepository commentRepository;
    
    // A user is voting
    public void updateVoteArgument(Long idArg, int value, User user, Argument arg) {
        VoteArgument vote = getVoteArgument(user, arg);
        
        if (vote==null) {  // A new vote entity
            vote = new VoteArgument(value, arg, user);
            vote.setArgument(arg);
            voteArgumentRepository.persist(vote);
            arg.addVoteArgument(vote);
            argumentRepository.merge(arg);
        } else {
            vote.setValue(value);
            voteArgumentRepository.merge(vote);
            arg.recalculate();
            argumentRepository.merge(arg);
        }
    }   

    // Finds the voteArguemnt for the given user on the given argument.
    public VoteArgument getVoteArgument(User user, Argument arg) {
        for (VoteArgument vt :arg.getVoteArguments()){
            if(vt.getUser().equals(user)){
                return vt;
            }
        }
        return null;  // Not found
    }
    
    public void deleteArgument(Argument arg){
       for( Comment com :arg.getCommentList()){
           commentRepository.remove(com);
       }
       for( VoteArgument vote :arg.getVoteArguments()){
           voteArgumentRepository.remove(vote);
       }
       argumentRepository.remove(arg);
    }
    

    
    
    
}
