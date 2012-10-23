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
    
    public List<ArticleVersion> findAllVersionForAnArticle (Article article){
        return findAllByArticle(article.getId());
    }
    
    public List<ArticleVersion> findAllByArticle(Long articleId){
        return em.createQuery("select av from ArticleVersion av where av.article.id =:id order by av.createdOn DESC")
                .setParameter("id", articleId)
                .getResultList();
    }
    
    public List<ArticleVersion> findAll(int rowAmount){
    	return em.createQuery("select av from ArticleVersion av order by av.updatedOn DESC")
        		.setMaxResults(rowAmount)
                .getResultList();
    }
}
