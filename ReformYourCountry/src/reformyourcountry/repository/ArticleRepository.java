package reformyourcountry.repository;
import java.util.List;

import org.springframework.stereotype.Repository;

import reformyourcountry.model.Article;

@Repository
@SuppressWarnings("unchecked")
public class ArticleRepository extends BaseRepository<Article>{
    
    public List<Article> findAll(){
        return    em.createQuery("select a from Article a order by a.releaseDate").getResultList();
    }
    
    public List<Article> findAllWithoutParent(){
        return    em.createQuery("select a from Article a where a.parent is null order by a.releaseDate").getResultList();
    }
    
    public Article findByShortName(String shortName){
    	return getSingleOrNullResult(em.createQuery("select a from Article a where a.shortName = :shortname").setParameter("shortname",shortName));
    }
}
