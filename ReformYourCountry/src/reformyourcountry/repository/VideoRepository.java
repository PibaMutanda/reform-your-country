package reformyourcountry.repository;

import java.util.List;

import org.springframework.stereotype.Repository;

import reformyourcountry.model.Article;
import reformyourcountry.model.Video;


@Repository
@SuppressWarnings("unchecked")
public class VideoRepository extends BaseRepository<Video> {

    public List<Video> findAllVideoForAnArticle(Article article){
        return findAllByArticle(article.getId());
    }
    
    
    public List<Video> findAllByArticle(Long articleId) {
        return em.createQuery("select v from Video v where v.article.id =:id order by v.createdOn DESC")
                .setParameter("id", articleId)
                .getResultList();
    }


	public List<Video>  findAllVideoByDate() {
        return em.createQuery("select v from Video v order by v.createdOn DESC")
                .getResultList();
	}

	
	
}
