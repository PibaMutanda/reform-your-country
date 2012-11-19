package reformyourcountry.repository;

import java.util.List;

import org.springframework.stereotype.Repository;

import reformyourcountry.model.User;

@Repository
@SuppressWarnings("unchecked")
public class UserRepository extends BaseRepository<User>{

    public  User getUserByEmail(String identifier)  {
        return getSingleOrNullResult( em.createQuery("select u from User u where lower(u.mail) =:mail")
                .setParameter("mail", identifier.toLowerCase())
                );
        }

    public  User getUserByUserName(String identifier) {
        return getSingleOrNullResult( 
                em.createQuery("select u from User u where lower(u.userName) =:userName")
                .setParameter("userName", identifier.toLowerCase())
                );

    }

    public User getUserByValidationCode(String code){
        return getSingleOrNullResult( em.createQuery("select u from User u where lower(u.validationCode) =:validationCode")
                .setParameter("validationCode", code.toLowerCase())
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
    
    public List<User> getUserWithRoleNotNull(){
    	List<User> results = (List<User>) em.createQuery("select u from User u where u.role != null").getResultList();
		return results;
    }
    
    public List<User> getUserWithPrivilegeNotEmpty(){
    	List<User> results = (List<User>) em.createQuery("select u from User u where size(u.privileges) != 0").getResultList();
		return results;
    }
    
    public List<User> getUsersWithSpecialType(){
        List<User> results = (List<User>) em.createQuery("select u from User u where u.specialType != 'PRIVATE' order by u.specialType").getResultList();
        return results;
        
    }
    
    public Long findMaxIdValue(){
    	return (Long)em.createQuery("select max(u.id) from User u").getSingleResult();
    }
    
    
}
