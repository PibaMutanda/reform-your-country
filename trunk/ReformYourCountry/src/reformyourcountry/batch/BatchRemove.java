package reformyourcountry.batch;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import reformyourcountry.model.GroupReg;
import reformyourcountry.repository.GroupRegRepository;

@Service
@Transactional
public class BatchRemove implements Runnable{

    /**
     * @param args
     */
    
    @PersistenceContext 
    EntityManager em;
    
    @Autowired    GroupRegRepository groupRegRepository;
    
    public static void main(String[] args) {
        // TODO Auto-generated method stub
        BatchUtil.startSpringBatch(BatchRemove.class);

    }

    public void run(){
       GroupReg groupReg=groupRegRepository.find(24l);
       System.out.println(groupReg);
       em.remove(groupReg);
       
    }
    
    
}
