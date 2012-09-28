package reformyourcountry.maintest;

import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import reformyourcountry.model.BaseEntity;
import reformyourcountry.model.User;

public class TestValidator {

    /**
     * @param args
     */
    public static void main(String[] args) {
//     
//        EntityManagerFactory emf = Persistence.createEntityManagerFactory("ConnectionPostgres");
//        
//        EntityManager em = emf.createEntityManager();
        
        
        User user = new User();
        user.setFirstName("");
        user.setLastName("");
        user.setMail("aaa@fffffoooocom");
        user.setUserName("rob");
        user.setPassword("x");
//        
//        em.getTransaction().begin();
//        
//        em.persist(user);
        
        
        
       // ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        //Validator validator = factory.getValidator();
      //  Set<ConstraintViolation<BaseEntity>> constraintViolations = user.validateBean();
        
        for(ConstraintViolation<BaseEntity> constraint : constraintViolations){
            
            System.out.println(constraint.getMessage()+ " "+constraint.getInvalidValue().toString());
            
        }

    }

}
