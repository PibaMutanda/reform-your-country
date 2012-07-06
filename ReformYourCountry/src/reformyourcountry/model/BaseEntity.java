package reformyourcountry.model;

import java.util.Date;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.PreUpdate;

import org.hibernate.proxy.HibernateProxyHelper;

import reformyourcountry.security.SecurityContext;

@MappedSuperclass
public class BaseEntity {

    @Id   @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;
    User createdBy;
    Date creationDate;
    User lastModifiedBy;
    Date lastModificationDate;
    
 
   
    public Long getId() {
        return id;
    }
    
    @PreUpdate
    public void onUpdate(){
    	if (createdBy==null) {
			createdBy = SecurityContext.getUser();
			creationDate = new Date();
		    
		}else{
			lastModifiedBy=SecurityContext.getUser();
			lastModificationDate=new Date();
		}
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
    
    
   
}
