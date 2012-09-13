package reformyourcountry.repository;

import java.util.List;

import org.springframework.stereotype.Repository;
import reformyourcountry.model.Action;

@Repository
@SuppressWarnings("unchecked")
public class ActionRepository extends BaseRepository<Action> {

    public List<Action> findAll(){
        return    em.createQuery("select a from Action a order by upper(a.title)").getResultList();
    }
  
}
