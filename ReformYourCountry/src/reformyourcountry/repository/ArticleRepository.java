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
        return    em.createQuery("select a from Article a where a.parent is null order by a.publishDate").getResultList();
    }
    
    public Article findByShortName(String shortName){
    	return getSingleOrNullResult(em.createQuery("select a from Article a where lower(a.shortName) = :shortname").setParameter("shortname",shortName.toLowerCase()));
    }
    
    public Article findByTitle(String title){
        return getSingleOrNullResult(em.createQuery("select a from Article a where lower(a.title) = :title").setParameter("title",title.toLowerCase()));
    }
    
    public List<Article> findByDate(Date publishDate, int maxAmount){
    	return em.createQuery("select a from Article a where a.publishDate != null order by a.publishDate DESC")
    			.setMaxResults(maxAmount)
    			.getResultList();
    }
}
