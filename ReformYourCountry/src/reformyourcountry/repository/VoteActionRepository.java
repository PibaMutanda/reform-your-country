package reformyourcountry.repository;


import java.util.List;

import org.springframework.stereotype.Repository;

import reformyourcountry.model.Action;
import reformyourcountry.model.User;
import reformyourcountry.model.VoteAction;

@Repository
public class VoteActionRepository extends BaseRepository<VoteAction> {

    public VoteAction findVoteActionForUser(User user, Long idAction){
        return getSingleOrNullResult( 
                em.createQuery("select v from VoteAction v where v.user = :user and v.action.id = :idAction")
                .setParameter("user", user)
                .setParameter("idAction", idAction));
    }
    
    public Long getTotalVoteValue( Long idAction){
        return (Long)em.createQuery("select sum(v.value) from VoteAction v where v.action.id = :idAction")
                .setParameter("idAction", idAction).getSingleResult();
    }
    
    public Long getNumberOfVotesByValue(Long idAction, int voteValue) {  // TODO John 2013-01 : this will fire a query for each vote value. Should be replaced by a single call returning a list of Long, each entry being a count for a specific value (done with a group by v.value ?)
    	Long result = (Long)em.createQuery("select count(v) from VoteAction v where v.action.id = :idAction and v.value = :voteValue")
    			.setParameter("idAction", idAction).setParameter("voteValue", voteValue).getSingleResult();
    	return result!=null ? result : 0l;
    }
    
    @SuppressWarnings("unchecked")
    public List<VoteAction> findVotesActionForUser(User user , List<Action> actions){
        
        return (List<VoteAction>) em.createQuery("select v from VoteAction v where v.user = :user and v.action in (:actions)")
                .setParameter("user",user)
                .setParameter("actions",actions).getResultList();
        
    }
    	
}
