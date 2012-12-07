package reformyourcountry.repository;


import java.util.Date;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;


import reformyourcountry.model.Article;
import reformyourcountry.model.GoodExample;
import reformyourcountry.model.User;


@Repository
@SuppressWarnings("unchecked")
public class GoodExampleRepository extends BaseRepository<GoodExample>{

	
	public List<GoodExample> findAllByDate(){
		
		List<GoodExample> goodExamples = em.createQuery("select g from GoodExample g order by g.createdOn").getResultList();
		return goodExamples;
	
	}
	
	public List<GoodExample> findGoodExampleByUpdateDate(){
		return em.createQuery("select ge from GoodExample ge order by ge.updatedOn DESC").getResultList();
	}
	
	public List<GoodExample> findByDate(Date publishDate, int maxAmount){
    	return em.createQuery("select ge from GoodExample ge where (ge.createdOn < :now) order by ge.createdOn DESC")
    			.setParameter("now", new Date())
    			.setMaxResults(maxAmount)
    			.getResultList();
    }
	
	public GoodExample findByTitle(String title){
    	return getSingleOrNullResult(em.createQuery("select ge from GoodExample ge where ge.title=:title")
    			.setParameter("title", title));
	}
	
	public List<GoodExample> findAll(){
        return    em.createQuery("select g from GoodExample g order by upper(g.title)").getResultList();
    }

	public List<GoodExample> findLastGoodExample(Article article, int amount){
		 List <Article> articleList = new ArrayList <Article>();
		 articleList.addAll (article.getChildren());
		 articleList.add( article);
		return em.createQuery("select DISTINCT ge from GoodExample ge join ge.articles a where a in (:articleList) order by ge.createdOn DESC")
				.setParameter("articleList",articleList)
				.setMaxResults(amount)
    			.getResultList();
	}
	
	public long countGoodExampleForUser(User user){
	    return (Long)em.createQuery("select count(goodexample) from GoodExample goodexample where goodexample.createdBy=:user")
                .setParameter("user", user).getSingleResult();
	}
}
