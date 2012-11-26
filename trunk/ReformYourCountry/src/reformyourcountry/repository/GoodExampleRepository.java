package reformyourcountry.repository;


import java.util.List;
import org.springframework.stereotype.Repository;
import reformyourcountry.model.GoodExample;


@Repository
public class GoodExampleRepository extends BaseRepository<GoodExample>{

	@SuppressWarnings("unchecked")
	public List<GoodExample> findAllByDate(){
		
		List<GoodExample> goodExamples = em.createQuery("select g from GoodExample g order by g.createdOn").getResultList();
		return goodExamples;
	
	}
	
}
