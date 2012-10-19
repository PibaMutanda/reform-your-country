package reformyourcountry.repository;

import java.util.List;

import org.springframework.stereotype.Repository;

import reformyourcountry.model.Article;
import reformyourcountry.model.ArticleVersion;
@Repository
@SuppressWarnings("unchecked")
public class ArticleVersionRepository extends BaseRepository<ArticleVersion>{

    //FIXME useless method ? --maxime 16/10/2012
    public ArticleVersion findLast(Article article){
        return (ArticleVersion) em.createQuery("select av from ArticleVersion av order by av.createdOn where av.article = :article")
                .setParameter("article", article)
                .setMaxResults(1).getResultList().get(0);
    }
    
    public List<ArticleVersion> findAll(Article article){
        return findAll(article.getId());
    }
    
    public List<ArticleVersion> findAll(Long articleId){
        return em.createQuery("select av from ArticleVersion av where av.article.id =:id")
                .setParameter("id", articleId)
                .getResultList();
    }
}
