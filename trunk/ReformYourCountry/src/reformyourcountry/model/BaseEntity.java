package reformyourcountry.model;

import java.util.Date;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.OneToOne;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

import org.hibernate.proxy.HibernateProxyHelper;
import org.springframework.beans.factory.annotation.Autowire;
import org.springframework.beans.factory.annotation.Configurable;

import reformyourcountry.security.SecurityContext;

@MappedSuperclass
@Configurable(autowire = Autowire.BY_TYPE, dependencyCheck = true)
public class BaseEntity {



    @Id   @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;
    @OneToOne
    User createdBy;
    Date createdOn;
    @OneToOne
    User updatedBy;
    Date updatedOn;
  

    
 @PrePersist
  public void onPersist(){
     System.out.println("onPersist");
    
         if (createdBy==null) {
          
             createdBy = SecurityContext.getUser();
             createdOn = new Date();
     
         }else{
             updatedBy=SecurityContext.getUser();
             updatedOn=new Date();
         }
         System.out.println("onPersist");
      
  }
   @PreUpdate
    public void onUpdate(){
       
       System.out.println("onUpdate");
    //   SecurityContext security = SecurityContextUtil.getSecurityContext();
       
      // SecurityContext security = (SecurityContext) ContextUtil.getSpringBean("secrityContext");
        if (createdBy==null) {
         
            createdBy = SecurityContext.getUser();
            createdOn = new Date();
    
        }else{
            updatedBy=SecurityContext.getUser();
            updatedOn=new Date();
        }
    }
   
    public Long getId() {
        return id;
    }
    
   
    
    @Override
    public int hashCode(){
        if (this.getId()==null){
            throw new RuntimeException("Bug: We should not call hashCode on an entity that have not been persisted yet " +
                    "because they have a null id. Because the equals is based on id, hashCode must be based on id too. " +
                    "If hashCode is called before the id is assigned, then the hashCode will change if called later when " +
                    "the id will have been assigned. But hashCode never can change (must always return the same value). " +
                    "This exceptin may happen because you have put an entity in a collection " +
                    "(an HashSet, maybe) that calls hashCode, before you have persisted it. " +
                    "It's typically the case when you put an entity in a " +
                    "-toMany relationship before you call EntityManager.persist on that entity. " +
                    "Using this BaseEntity class constraints you not doing that (else subtle bugs may arrive by the back door).");
        }
        return this.getId().hashCode();
      
    }
    
    @Override
    public boolean equals(Object other){
        if (other==null)
            return false;
        if (this == other)
            return true;
        
        //We need the real Class not the proxy's class
        Class<?> otherClassNoProxy = HibernateProxyHelper.getClassWithoutInitializingProxy(other); 
        Class<?> thisClassNoProxy = HibernateProxyHelper.getClassWithoutInitializingProxy(this);
        
        // Is one of the two class assignable from the other.
        if (!otherClassNoProxy.isAssignableFrom(thisClassNoProxy)&& ! thisClassNoProxy.isAssignableFrom(otherClassNoProxy) )
            return false;
        //Is it an entity? 
        if (!  (other instanceof BaseEntity)) {
            throw new RuntimeException("Probably Bug: how can other be assignableFrom us (or vis versa) " +
                    "and not being a BaseEntity? other=["+other+"] - this=["+this+"]"); 
        }
        BaseEntity otherEntity = (BaseEntity) other;
        
        if (this.getId()==null || otherEntity.getId()==null) {
            return false;
        }
        return this.getId().equals(otherEntity.getId());
    }
    
    public User getCreatedBy() {
        return createdBy;
    }


    public Date getCreatedOn() {
        return createdOn;
    }

    public User getUpdatedBy() {
        return updatedBy;
    }

    public Date getUpdatedOn() {
        return updatedOn;
    }
  
   
}
