package reformyourcountry.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import reformyourcountry.mail.MailCategory;
import reformyourcountry.mail.MailType;
import reformyourcountry.model.Badge;
import reformyourcountry.model.BadgeType;
import reformyourcountry.model.User;
import reformyourcountry.repository.ActionRepository;
import reformyourcountry.repository.BadgeRepository;
import reformyourcountry.util.NotificationUtil;
import reformyourcountry.web.UrlUtil;

@Service
@Transactional
public class BadgeService {

	@Autowired BadgeRepository badgeRepository;
	@Autowired ActionRepository actionRepository;
	@Autowired MailService mailService;
	
    public void saveBadgeTypeForUser (BadgeType badgeType, User user){
		Badge badge = new Badge();
		badge.setBadgeType(badgeType);
		badge.setUser(user);
		badgeRepository.persist(badge);
		user.getBadges().add(badge);
		NotificationUtil.addNotificationMessage(
				"Félicitations vous avez obtenu un badge de niveau : " + badgeType.getBadgeTypeLevel().getName()  
				+ " " + badgeType.getName());
		
		
	System.out.println("send badge");	
		if(badgeType.isMailConfirm()){
		    String htmlMessage = "Félicitation, vous venez de recevoir votre badge "+badgeType.getName() +  
		            " de niveau " + badgeType.getBadgeTypeLevel().getName();
		    mailService.sendMail(user, "Vous avez reçu un nouveau badge!", htmlMessage, MailType.SLOW_NOT_GROUPABLE, MailCategory.USER);
        
		}
		
    }

        
    
	public void grantBadgeForGroups(User user) {
        // 1. User a déjà le badge -> exit.
		if(!user.isHasBadgeType(BadgeType.STATISTICIAN))
			saveBadgeTypeForUser(BadgeType.STATISTICIAN, user);
		// 2. On lui donne le badge

		
	}
	
    
    public void grantBadgeForVoteAction(User user) {
        int count = user.getVoteActions().size();
        
        // Does the user deserve a RUBBERNECK badge ?
        if(count>=1){
            if(!user.isHasBadgeType(BadgeType.RUBBERNECK))
                saveBadgeTypeForUser(BadgeType.RUBBERNECK, user);
            
        }
        
        if(count>=10){
            if(!user.isHasBadgeType(BadgeType.ELECTOR))
                saveBadgeTypeForUser(BadgeType.ELECTOR, user);
        }
        
        
        if(count >= actionRepository.findAll().size()){
            if(!user.isHasBadgeType(BadgeType.CITIZEN))
                saveBadgeTypeForUser(BadgeType.CITIZEN, user);
        }
    }
    
    
    public void grandBadgeForArgument(User user){
        int count=user.getVoteArguments().size();
        
        if(count>=10){
            if(!user.isHasBadgeType(BadgeType.REFEREE))
                saveBadgeTypeForUser(BadgeType.REFEREE, user);
        }
        
        if(count>=50){
            if(!user.isHasBadgeType(BadgeType.JUDGE))
                saveBadgeTypeForUser(BadgeType.JUDGE, user);
        }
        if(count>=200){
            if(!user.isHasBadgeType(BadgeType.INQUISITOR))
                saveBadgeTypeForUser(BadgeType.INQUISITOR, user);
        }
    }
}
