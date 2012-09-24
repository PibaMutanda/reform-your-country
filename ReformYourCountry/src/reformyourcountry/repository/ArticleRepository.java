package reformyourcountry.repository;
import java.util.List;

import org.springframework.stereotype.Repository;

import reformyourcountry.model.Article;

@Repository
public class ArticleRepository extends BaseRepository<Article>{
    
    @SuppressWarnings("unchecked")
    public List<Article> findAll(){
        return    em.createQuery("select a from Article a order by a.releaseDate").getResultList();
    }
    
    @SuppressWarnings("unchecked")
    public List<Article> findAllWithoutParent(){
        return    em.createQuery("select a from Article a where a.parent is null order by a.releaseDate").getResultList();
    }
    
   

}
