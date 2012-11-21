package reformyourcountry.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import reformyourcountry.badge.BadgeType;
import reformyourcountry.model.Badge;
import reformyourcountry.model.User;
import reformyourcountry.repository.BadgeRepository;
import reformyourcountry.util.NotificationUtil;

@Service
@Transactional
public class BadgeService {

	@Autowired BadgeRepository badgeRepository;
	
    public void saveBadgeTypeForUser (BadgeType badgeType, User user){
		Badge badge = new Badge();
		badge.setBadgeType(badgeType);
		badge.setUser(user);
		badgeRepository.persist(badge);
		user.getBadges().add(badge);
		NotificationUtil.addNotificationMessage(
				"Félicitations vous avez obtenu le badge " + badgeType.getName() +  
				" de niveau " + badgeType.getBadgeTypeLevel().getName() );
    }

	public void grantBadgeForGroups(User user) {
        // 1. User a déjà le badge -> exit.
		if(!user.isHasBadgeType(BadgeType.STATISTICIAN))
			saveBadgeTypeForUser(BadgeType.STATISTICIAN, user);
		// 2. On lui donne le badge

		
	}
	
}
