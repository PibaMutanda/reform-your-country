package reformyourcountry.Repository;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceUnit;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import reformyourcountry.model.User;
import reformyourcountry.exceptions.*;
@Repository
@Transactional
public class UserRepository extends BaseRepository<User>{

    // TODO: remove this when we'll use a DB
//    private  Set<User> listeUser = new HashSet<User>();

    // TODO: remove this when we'll use Spring.
//    private static UserRepository uniqueInstance = new UserRepository();
//    private UserRepository() {};  // To disable new UserDao() outside this singleton class.
//    public static UserRepository getInstance() {
//        return uniqueInstance;
//    }

//    @SuppressWarnings("unchecked")
//    public  List<User> getListeUser() {
//        return (List<User>) listeUser;
//    }
    
    @PersistenceUnit
    EntityManager em;

    public  User getUserByEmail(String identifier)  {
        List<User> results= em.createNamedQuery("findUserByEmail").setParameter("mail", identifier).getResultList();
        return results.get(0);
    }

    public  User getUserByUserName(String identifier) {
        List<User> results= em.createNamedQuery("findUserByUserName").setParameter("userName", identifier).getResultList();
        return results.get(0);
    }
}
