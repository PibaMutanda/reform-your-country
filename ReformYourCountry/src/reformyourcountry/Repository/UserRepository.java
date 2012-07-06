package reformyourcountry.Repository;

import java.util.List;

import org.springframework.stereotype.Repository;

import reformyourcountry.model.User;

@Repository
@SuppressWarnings("unchecked")
public class UserRepository extends BaseRepository<User>{

    public  User getUserByEmail(String identifier)  {
        List<User> results= em.createQuery("select u from User u where u.mail =:mail")
                .setParameter("mail", identifier)
                .getResultList();
        if(results.isEmpty()) {
            return null;
        }
        return results.get(0);
    }

    public  User getUserByUserName(String identifier) {
        List<User> results= em.createQuery("select u from User u where u.userName =:userName")
                .setParameter("userName", identifier)
                .getResultList();
        if(results.isEmpty()) {
            return null;
        }
        return results.get(0);
    }
    
    public User getUserById(Long identifier){
    	List<User> results= em.createQuery("select u from User u where u.id =:id")
                .setParameter("id", identifier)
                .getResultList();
        if(results.isEmpty()) {
            return null;
        }
        return results.get(0);
    }
}
