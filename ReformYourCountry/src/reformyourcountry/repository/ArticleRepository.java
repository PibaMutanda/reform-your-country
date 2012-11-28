package reformyourcountry.repository;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Repository;

import reformyourcountry.model.Article;


@Repository
@SuppressWarnings("unchecked")
public class ArticleRepository extends BaseRepository<Article>{
    
    public List<Article> findAll(){
        return    em.createQuery("select a from Article a order by a.publishDate").getResultList();
    }
    
    public List<Article> findAllWithoutParent(){
        return    em.createQuery("select a from Article a where a.parent is null order by a.title").getResultList();
    }
    
    public Article findByShortName(String shortName){
        if (shortName!=null){
            return getSingleOrNullResult(em.createQuery("select a from Article a where lower(a.shortName) = :shortname").setParameter("shortname",shortName.toLowerCase()));
        }
        return null;
    }
    
    public Article findByTitle(String title){
        return getSingleOrNullResult(em.createQuery("select a from Article a where lower(a.title) = :title").setParameter("title",title.toLowerCase()));
    }
    
    public List<Article> findByDate(Date publishDate, int maxAmount){
    	return em.createQuery("select a from Article a where a.publicView = true and (a.publishDate is null or a.publishDate < :now)  order by a.publishDate DESC")
    	        .setParameter("now", new Date())
    			.setMaxResults(maxAmount)
    			.getResultList();
    }
    
    public List<Article> findbyUpdateDate(){
    	return em.createQuery("select a from Article a order by a.updatedOn DESC").getResultList();
    }
    
    public Article findArticleBySummary(String summary){
        return getSingleOrNullResult( em.createQuery("select a from Article a where lower(a.arcticleVersion.summary) = :summary").setParameter("summary",summary.toLowerCase()) );
    }
    
   
}
