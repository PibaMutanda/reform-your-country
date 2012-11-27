package reformyourcountry.repository;


import java.util.List;

import org.springframework.stereotype.Repository;

import reformyourcountry.model.Article;
import reformyourcountry.model.GoodExample;


@Repository
public class GoodExampleRepository extends BaseRepository<GoodExample>{

	@SuppressWarnings("unchecked")
	public List<GoodExample> findAllByDate(){
		
		List<GoodExample> goodExamples = em.createQuery("select g from GoodExample g order by g.createdOn").getResultList();
		return goodExamples;
	
	}
	@SuppressWarnings("unchecked")
	public List<GoodExample> findAll(){
        return    em.createQuery("select g from GoodExample g order by upper(g.title)").getResultList();
    }
	@SuppressWarnings("unchecked")
	public List<GoodExample> findLastGoodExample(Article article, int amount){
		return em.createQuery("select ge from GoodExample ge join ge.articles a where a = :article order by ge.publishDate DESC")
				.setParameter("article", article)
				.setMaxResults(amount)
    			.getResultList();
	}
}
