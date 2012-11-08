package reformyourcountry.model;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import reformyourcountry.badge.BadgeType;

@Entity
public class Badge extends BaseEntity {

	@Enumerated(EnumType.STRING)
	private BadgeType badgeType;

	@ManyToOne
	@JoinColumn(nullable = false)
	private User user;

	public BadgeType getBadgeType() {
		return badgeType;
	}

	public void setBadgeType(BadgeType badgeType) {
		this.badgeType = badgeType;
	}
		
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
	
}
