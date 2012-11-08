package reformyourcountry.repository;

import java.util.List;

import org.springframework.stereotype.Repository;

import reformyourcountry.model.Comment;


@Repository
public class CommentRepository extends BaseRepository<Comment>{
	@SuppressWarnings("unchecked")
	public List<Comment> findAllComment(){
		return em.createQuery("select c from Comment c").getResultList();
	}
}
