package reformyourcountry.repository;

import java.util.List;


import org.springframework.stereotype.Repository;

import reformyourcountry.model.Badge;

@Repository
public class BadgeRepositorty extends BaseRepository<Badge> {

	@SuppressWarnings("unchecked")
	public List<Badge> findAllBadges(){
		
		List<Badge>badges= em.createQuery("select b from Badge b order by b.badgeType").getResultList();
		
		return  badges;
	}
	
	
}
