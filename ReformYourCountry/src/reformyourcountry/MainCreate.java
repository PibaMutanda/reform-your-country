package reformyourcountry;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class MainCreate {
    
    
    public static void main(String [] args){
    EntityManagerFactory emf = Persistence.createEntityManagerFactory("ConnectionPostgres");
    
    EntityManager em = emf.createEntityManager();
    }

}
