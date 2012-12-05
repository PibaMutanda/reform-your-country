package reformyourcountry.repository;

import javax.persistence.NoResultException;

import org.springframework.stereotype.Repository;

import reformyourcountry.model.GoodExample;
import reformyourcountry.model.User;
import reformyourcountry.model.VoteGoodExample;

@Repository
public class VoteGoodExampleRepository extends BaseRepository<VoteGoodExample>{

    public int getVoteGoodExampleCountForAGoodExample(GoodExample goodExample){
        return (int) ((Number) em.createQuery("select count(vga) from VoteGoodExample vga where vga.goodExample =:goodExample ")
                .setParameter("goodExample", goodExample)
                .getSingleResult())
                .intValue();    
    }
    
    public VoteGoodExample getVoteGoodExampleForAnUser(User user, GoodExample goodExample) {
        try {
            return(VoteGoodExample) em.createQuery("select vga from VoteGoodExample vga where vga.user =:user and vga.goodExample=:goodExample")
                    .setParameter("user", user)
                    .setParameter("goodExample", goodExample)
                    .getSingleResult();
        } catch (NoResultException nre) {
            return null;
        }
    }

    
   public long countVotesForAuthor(User author){
       return (long)em.createQuery("select count(vote) from VoteGoodExample vote where vote.goodexample.createdBy=:author")
               .setParameter("author", author).getSingleResult();
   }
}
