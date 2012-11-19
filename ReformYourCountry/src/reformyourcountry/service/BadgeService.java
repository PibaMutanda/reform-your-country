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
		NotificationUtil.addNotificationMessage(
				"Félicitations vous avez obtenu le badge " + badgeType.getName() +  
				" de niveau " + badgeType.getBadgeTypeLevel().getName() );
    }
	
}
