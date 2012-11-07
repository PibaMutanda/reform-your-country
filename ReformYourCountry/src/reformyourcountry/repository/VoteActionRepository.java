package reformyourcountry.repository;

import java.util.List;

import org.springframework.stereotype.Repository;

import reformyourcountry.model.Argument;
import reformyourcountry.model.User;
import reformyourcountry.model.VoteAction;

@Repository
@SuppressWarnings("unchecked")
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
}
