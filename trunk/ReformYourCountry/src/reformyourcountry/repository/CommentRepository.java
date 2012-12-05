package reformyourcountry.repository;

import java.util.List;

import org.springframework.stereotype.Repository;

import reformyourcountry.model.Comment;
import reformyourcountry.model.User;


@Repository
public class CommentRepository extends BaseRepository<Comment>{
	@SuppressWarnings("unchecked")
	public List<Comment> findAllComment(){
		return em.createQuery("select c from Comment c").getResultList();
	}
	
	@SuppressWarnings("unchecked")
	public List<Comment> findCommentByDate(){
		return em.createQuery("select c from Comment c order by c.updatedOn DESC").getResultList();
	}
	
	public long countCommentsForUser(User user){
	    return (Long)em.createQuery("select count(comment) from Comment comment where comment.createdBy=:user")
	             .setParameter("user", user).getSingleResult();
	}
}
