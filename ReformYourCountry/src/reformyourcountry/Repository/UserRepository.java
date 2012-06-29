package reformyourcountry.Repository;

import java.util.List;


import javax.persistence.EntityManager;
import javax.persistence.PersistenceUnit;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import reformyourcountry.model.User;

@Repository
@Transactional
@SuppressWarnings("unchecked")
public class UserRepository extends BaseRepository<User>{

    public  User getUserByEmail(String identifier)  {
        List<User> results= em.createNamedQuery("findUserByEmail").setParameter("mail", identifier).getResultList();
        return results.get(0);
    }

    public  User getUserByUserName(String identifier) {
        List<User> results= em.createNamedQuery("findUserByUserName").setParameter("userName", identifier).getResultList();
        return results.get(0);
    }
}
