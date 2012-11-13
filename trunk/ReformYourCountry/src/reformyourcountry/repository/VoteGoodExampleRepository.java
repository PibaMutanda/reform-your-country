package reformyourcountry.repository;

import org.springframework.stereotype.Repository;

import reformyourcountry.model.GoodExample;
import reformyourcountry.model.VoteGoodExample;

@Repository
public class VoteGoodExampleRepository extends BaseRepository<VoteGoodExample>{

    public int getVoteGoodExampleCountForAGoodExample(GoodExample goodExample){
        return getCountForAGoodExample(goodExample.getId());
    }

    public int getCountForAGoodExample(Long goodExampleId) {
        return (int) ((Number) em.createQuery("select count(vga) from VoteGoodExample vga where vga.goodExample.id =:id ")
                .setParameter("id", goodExampleId)
                .getSingleResult())
                .intValue();
    }
}
