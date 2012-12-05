package reformyourcountry.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import reformyourcountry.mail.MailCategory;
import reformyourcountry.mail.MailType;
import reformyourcountry.model.Argument;
import reformyourcountry.model.Badge;
import reformyourcountry.model.BadgeType;
import reformyourcountry.model.User;
import reformyourcountry.repository.ActionRepository;
import reformyourcountry.repository.ArgumentRepository;
import reformyourcountry.repository.BadgeRepository;
import reformyourcountry.repository.CommentRepository;
import reformyourcountry.repository.GoodExampleRepository;
import reformyourcountry.repository.VoteArgumentRepository;
import reformyourcountry.repository.VoteGoodExampleRepository;
import reformyourcountry.util.NotificationUtil;

@Service
@Transactional
public class BadgeService {

    @Autowired
    BadgeRepository badgeRepository;
    @Autowired
    ActionRepository actionRepository;
    @Autowired
    MailService mailService;
    @Autowired
    ArgumentRepository argumentRepository;
    @Autowired
    VoteArgumentRepository voteArgumentRepository;
    @Autowired
    CommentRepository commentRepository;
    @Autowired
    GoodExampleRepository goodExampleRepository;
    @Autowired
    VoteGoodExampleRepository voteGoodExampleRepository;
    
    public void createBadgeIfNotAlreadyExist(BadgeType badgeType, User user) {
        if (user.isHasBadgeType(badgeType)) {
            return; // He already has that badge.
        }

        Badge badge = new Badge();
        badge.setBadgeType(badgeType);
        badge.setUser(user);
        badgeRepository.persist(badge);
        user.getBadges().add(badge);
        String htmlMessage = "Félicitations vous avez obtenu un badge de niveau "
                + badgeType.getBadgeTypeLevel().getName()
                + ": "
                + "<a href='/badge/'>"+badgeType.getName()+"</a>";
        NotificationUtil
                .addNotificationMessage(htmlMessage);

        // Mail
        if (badgeType.isMailConfirm()) {
            mailService.sendMail(user, "Vous avez reçu un nouveau badge!",
                    htmlMessage, MailType.GROUPABLE, MailCategory.USER);

        }

    }

    public void grantBadgeForGroups(User user) {
        createBadgeIfNotAlreadyExist(BadgeType.STATISTICIAN, user);
    }

    public void grantBadgeForVoteAction(User user) {
        int count = user.getVoteActions().size();

        // Does the user deserve a RUBBERNECK badge ?
        if (count >= 1) {
            createBadgeIfNotAlreadyExist(BadgeType.RUBBERNECK, user);

        }

        if (count >= 10) {
            createBadgeIfNotAlreadyExist(BadgeType.ELECTOR, user);

        }
        if (count >= actionRepository.findAll().size()) {
            createBadgeIfNotAlreadyExist(BadgeType.CITIZEN, user);

        }
    }

    public void grandBadgeForArgumentVoter(User voter) {
        int count = voter.getVoteArguments().size();

        if (count >= 10) {
            createBadgeIfNotAlreadyExist(BadgeType.REFEREE, voter);
        }

        if (count >= 50) {
            createBadgeIfNotAlreadyExist(BadgeType.JUDGE, voter);
        }
        if (count >= 200) {
            createBadgeIfNotAlreadyExist(BadgeType.JUDGE, voter);
        }
    }

    public void grandBadgeForArgumentAuthor(User author) {
        List<Argument> argumentsForUser = argumentRepository.findByUser(author);
        int argHaving10Votes = 0;
        int argHaving20Votes = 0;
        int argHaving100Votes = 0;

        for (Argument arg : argumentsForUser) {
            if (arg.getTotal() >= 1) {
                argHaving10Votes++;
            }
            if (arg.getTotal() >= 5) {
                argHaving20Votes++;
            }
            if (arg.getTotal() >= 20) {
                argHaving100Votes++;
            }
        }

        if (argHaving10Votes >= 10) {
            // donner badge bronze
            createBadgeIfNotAlreadyExist(BadgeType.LAWYER, author);
        }
        if (argHaving20Votes >= 20) {
            // argent
            createBadgeIfNotAlreadyExist(BadgeType.TENOR, author);
        }
        if (argHaving100Votes >= 100) {
            createBadgeIfNotAlreadyExist(BadgeType.LUMINARY, author);
        }
    }

    /**
     * give an AUTOBIOGRAPHER badge only if the user has fully completed his
     * profile
     */
    public void grantIfUserIsComplete(User user) {

        // test if the user has completed his profile
        if (user.getBirthDate() != null && user.getFirstName() != null
                && user.getGender() != null && user.getLastName() != null
                && user.getMail() != null && user.isPicture() == true
                && user.getTitle() != null && user.getUserName() != null) {
            createBadgeIfNotAlreadyExist(BadgeType.AUTOBIOGRAPHER, user);
        }

    }

    /** verify if the user has already the BadgeType passed in parameter */
    public boolean hasAlreadyBadgeAffected(BadgeType badgeType, User user) {

        for (Badge badge : user.getBadges()) {
            if (badge.getBadgeType().equals(badgeType)) {
                return true;
            }
        }
        return false;
    }

    public void recomputeBadges(User user) {
        grantBadgeForGroups(user);
        grantBadgeForVoteAction(user);
        grandBadgeForArgumentVoter(user);
    }

    public void grandBadgeForComment(User author) {
        long countComment = commentRepository.countCommentsForUser(author);

        if (countComment >= 10) {
            createBadgeIfNotAlreadyExist(BadgeType.COMMENTATOR, author);
        }
        if (countComment >= 100) {
            createBadgeIfNotAlreadyExist(BadgeType.BLABBERMOUTH, author);
        }
    }

    // TODO call this method from GoodExampleController when the controller has been programmed.
    public void grandBadgeForGoodExample(User author){
        long countVotesOnGoodExample = voteGoodExampleRepository.countVotesForAuthor(author);
        
        if(goodExampleRepository.countGoodExampleForUser(author)>=1){
            createBadgeIfNotAlreadyExist(BadgeType.EXAMPLE, author);
        }
        if(countVotesOnGoodExample >= 10){
            createBadgeIfNotAlreadyExist(BadgeType.GOODEXAMPLE, author);
        } 
        if(countVotesOnGoodExample >= 100){
            createBadgeIfNotAlreadyExist(BadgeType.MODEL, author);
        }
    }
}
