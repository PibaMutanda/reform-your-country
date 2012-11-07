package reformyourcountry.repository;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Repository;

import reformyourcountry.model.Argument;
import reformyourcountry.model.Article;
import reformyourcountry.model.User;


@Repository
@SuppressWarnings("unchecked")
public class ArgumentRepository extends BaseRepository<Argument>{
    
    
    public List<Argument> findByUser(User user){
        if (user!=null){
            return (List<Argument>)em.createQuery("select a from Argument a where a.user = :user").setParameter("user",user);
        }
        return null;
    }

    public List<Argument> findAll(Long idAction){
        List<Argument> list = em.createQuery("select a from Argument a where a.action.id = :idAction")
                .setParameter("idAction", idAction)
                .getResultList();
        return list;
    }
    
   
}
