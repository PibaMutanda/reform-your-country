package reformyourcountry.repository;

import java.util.List;


import org.springframework.stereotype.Repository;

import reformyourcountry.model.Badge;
import reformyourcountry.model.BadgeType;


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
	       
	@SuppressWarnings("unchecked")
    public List<Badge> findTypeBadge(BadgeType badgeType){
	    List<Badge> badges= em.createQuery("select b from Badge b where b.badgeType=:badgeT order by b.createdOn DESC").setParameter("badgeT",badgeType).getResultList();
	    return badges;
	    
	}
	
}
