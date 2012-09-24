package reformyourcountry.repository;

import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import reformyourcountry.model.Book;
import reformyourcountry.model.User;

@Repository
@SuppressWarnings("unchecked")
public class UserRepository extends BaseRepository<User>{

    public  User getUserByEmail(String identifier)  {
        return getSingleOrNullResult( em.createQuery("select u from User u where u.mail =:mail")
                .setParameter("mail", identifier)
                );
        }

    public  User getUserByUserName(String identifier) {
        return getSingleOrNullResult( 
                em.createQuery("select u from User u where u.userName =:userName")
                .setParameter("userName", identifier)
                );

    }

    public User getUserByValidationCode(String code){
        return getSingleOrNullResult( em.createQuery("select u from User u where u.validationCode =:validationCode")
                .setParameter("validationCode", code)
                );
    }
 
    public List<User> findAll(){
        return  em.createQuery("select u from User u").getResultList();
    }
    
    public List<User> searchUsers(String identifier){
        String name = "\\" +identifier.toUpperCase()+ "%";
        List<User> results= em.createQuery("select u from User u where upper(u.userName) like :name or upper(u.firstName) like :name or upper(u.lastName) like :name ")
                .setParameter("name", name)
                .getResultList();
        return results;
    }
        
}
