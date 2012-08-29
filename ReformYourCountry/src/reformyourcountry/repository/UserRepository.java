package reformyourcountry.repository;

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
    
    public User getUserByValidationCode(String code){
        List<User> results = em.createQuery("select u from User u where u.validationCode =:validationCode")
                .setParameter("validationCode", code)
                .getResultList();
        if(results.isEmpty()){
            return null;
        }
        return results.get(0);
    }
 
    public List<User> findAll(){
        return  em.createQuery("select u from User u").getResultList();
    }
    
    public List<User> findAllUsersByName(String identifier){
    	//TODO: to update this query when more users in database
    	List<User> results= em.createQuery("select u from User u where u.userName like :userName or u.firstName like 'T%' or u.lastName like 'T%' ")
                .setParameter("userName", identifier)
                .getResultList();
    	return results;
    }
}
