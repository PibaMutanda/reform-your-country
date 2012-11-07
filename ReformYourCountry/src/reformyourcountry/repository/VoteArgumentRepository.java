package reformyourcountry.repository;


import org.springframework.stereotype.Repository;


import reformyourcountry.model.User;
import reformyourcountry.model.VoteAction;
import reformyourcountry.model.VoteArgument;

@Repository
public class VoteArgumentRepository extends BaseRepository<VoteArgument> {

    public VoteArgument findVoteArgumentForUser(User user, Long idArg){
        return getSingleOrNullResult( 
                em.createQuery("select v from VoteArgument v where v.user = :user and v.argument.id = :idAction")
                .setParameter("user", user)
                .setParameter("idAction", idArg));
    }
    public Long getTotalVoteValue( Long idArg){
        return (Long)em.createQuery("select sum(v.value) from VoteArgument v where v.argument.id = :idAction")
                .setParameter("idAction", idArg).getSingleResult();
    }
}
