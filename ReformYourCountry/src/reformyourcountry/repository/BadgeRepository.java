package reformyourcountry.repository;

import java.util.List;


import org.springframework.stereotype.Repository;

import reformyourcountry.badge.BadgeType;
import reformyourcountry.model.Badge;

@Repository
public class BadgeRepository extends BaseRepository<Badge> {

	@SuppressWarnings("unchecked")
	public List<Badge> findAllBadges(){
		
		List<Badge>badges= em.createQuery("select b from Badge b order by b.badgeType").getResultList();
		return  badges;
	}
	
	public Long countBadges(BadgeType badgeType){
		
		Long count;
		count = (Long) em.createQuery("select count (b.badgeType) from Badge b where b.badgeType=:bt")
				.setParameter("bt", badgeType)
				.getSingleResult();
		return count;
	}
	                   
}
