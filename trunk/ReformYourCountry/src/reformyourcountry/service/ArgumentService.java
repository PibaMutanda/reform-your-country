package reformyourcountry.service;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.jsoup.helper.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import reformyourcountry.mail.MailCategory;
import reformyourcountry.mail.MailType;
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
import reformyourcountry.security.SecurityContext;
import reformyourcountry.util.DateUtil;
import reformyourcountry.util.HTMLUtil;

@Service
@Transactional
public class ArgumentService {
    
    @Autowired ArgumentRepository argumentRepository;
    @Autowired ActionRepository actionRepository;
    @Autowired UserRepository userRepository;
    @Autowired VoteActionRepository voteActionRepository;
    @Autowired VoteArgumentRepository voteArgumentRepository;
    @Autowired CommentRepository commentRepository;
    @Autowired MailService mailService;
    
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
    

    public void notifyByEmailNewCommentPosted(Argument argument,Comment comment){
        User userPostComment = SecurityContext.getUser();
        User ownerArg = argument.getUser();
        Date date = comment.getCreatedOn();
        String dateString = DateUtil.formatddMMyyyyHHmm(date);
        
        String htmlMessage = HTMLUtil.getUserPageLink(userPostComment)+
                " vient de publier un commentaire sur votre argument \""+argument.getTitle()+"\" :<br/><br/>"+
                "Le "+dateString +":<br/>"+
                comment.getContent();
               ;
        mailService.sendMail(ownerArg, "Un utilisateur a commenté votre argument "+argument.getTitle(), htmlMessage, MailType.GROUPABLE, MailCategory.USER);
        
    }
    
    
    
}
