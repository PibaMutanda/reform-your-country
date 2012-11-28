package reformyourcountry.repository;

import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Repository;
import reformyourcountry.model.Action;

@Repository
@SuppressWarnings("unchecked")
public class ActionRepository extends BaseRepository<Action> {

    public List<Action> findAll(){
        return    em.createQuery("select a from Action a order by upper(a.title)").getResultList();
    }
    
    public Action findByShortName(String desc){
        return getSingleOrNullResult(em.createQuery("select a from Action a where lower(a.shortName) = :shortname").setParameter("shortname",desc.toLowerCase()));
    }
    
    public List<Action> findByDate(Date publishDate, int maxAmount){
    	return em.createQuery("select a from Action a where (a.createdOn < :now) order by a.createdOn DESC")
    			.setParameter("now", new Date())
    			.setMaxResults(maxAmount)
    			.getResultList();
    }
    public List<Action> findByUpdateDate(){
    	return em.createQuery("select a from Action a where (a.updatedOn < :now) order by a.updatedOn DESC")
    			.setParameter("now", new Date())
    			.getResultList();
    }
}
