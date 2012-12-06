package reformyourcountry.model;

import java.util.Date;

import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.MappedSuperclass;
import javax.persistence.OneToOne;
import javax.persistence.PostPersist;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Transient;

import org.apache.log4j.Logger;
import org.hibernate.proxy.HibernateProxyHelper;

import reformyourcountry.search.Searchable;
import reformyourcountry.security.SecurityContext;
import reformyourcountry.util.DateUtil;

@MappedSuperclass
public class BaseEntity  {

    private static final boolean ENFORCE_CONSISTENT_HASHCODE = false;

	@Transient
    private Logger logger = Logger.getLogger(this.getClass());
    
    @Id   @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;
    @OneToOne(fetch=FetchType.LAZY)
    @JoinColumn(nullable=true) //when we are not in a thread web request, no user will be associated to entity creation/update (no logged in user)
    User createdBy;
    Date createdOn;
    @OneToOne(fetch=FetchType.LAZY)  
    @JoinColumn(nullable=true) //when we are not in a thread web request, no user will be associated to entity creation/update (no logged in user)
    User updatedBy;
    Date updatedOn;

    transient @Transient boolean isBeingPersisted = false;  // true if we are between @PerPersist and @PostPersist calls (= if we are persisting).
	transient @Transient boolean setUpdateOnWhenCreating;  // true if the updateOn date field should not be null in the DB just after creation (INSERT, persist);

    
    @PrePersist
    public void prePersist(){
    	createdBy = SecurityContext.getUser();
    	createdOn = new Date();
    	if (setUpdateOnWhenCreating) {
    		updatedOn = createdOn;
    	}
    	isBeingPersisted = true;
    }

    @PostPersist
    public void postPersist(){
    	isBeingPersisted = false;
    }
    @PreUpdate
    public void onUpdate(){
    	updatedBy=SecurityContext.getUser();
    	updatedOn=new Date();
    }

	public void updateOnNotNull() {
		setUpdateOnWhenCreating = true;
	}

    
    public Long getId() {
    	return id;
    }

   
    
    @Override
    public int hashCode() {
    	if (getId() != null) {
            return this.getId().hashCode();
    	} else if(isBeingPersisted){/*Prevents errors during Hibernate validation.
    	 						Hibernate calls HashCode() for no reason during em.persist() while doing the validation, causing problems when entities are created.*/
    		return 0;  // I guess that Hibernate validation does not really use that value anyway. It's a hack.
    	} else {  // No id yet and not being persisted.
    		if (ENFORCE_CONSISTENT_HASHCODE) {
		      throw new RuntimeException("Bug: We should not call hashCode on an entity that have not been persisted yet " +
		            "because they have a null id. Because the equals is based on id, hashCode must be based on id too. " +
		            "If hashCode is called before the id is assigned, then the hashCode will change if called later when " +
		            "the id will have been assigned. But hashCode never can change (must always return the same value). " +
		            "This exceptin may happen because you have put an entity in a collection " +
		            "(an HashSet, maybe) that calls hashCode, before you have persisted it. " +
		            "It's typically the case when you put an entity in a " +
		            "-toMany relationship before you call EntityManager.persist on that entity. " +
		            "Using this BaseEntity class constraints you not doing that (else subtle bugs may arrive by the back door).");
    		} else {//Security disabled. Can typically the case when Spring MVC calls Hibernate validation which calls HashCode() (not related to persist call)
    			return 0;
    		}
    	}
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
    
    
    
    
    public User getUpdatedOrCreatedBy() {
		return updatedBy != null ? updatedBy : createdBy;
	}

    public Date getUpdatedOrCreatedOn() {
		return updatedOn != null ? updatedOn : createdOn;
	}

    public User getCreatedBy() {
        return createdBy;
    }


    public Date getCreatedOn() {
        return createdOn;
    }
    public String getFormatedCreatedOn(){
        if (createdOn != null){
            return DateUtil.formatyyyyMMddHHmm(createdOn);
        }
            return "pas de date communiquée";
    }
    public User getUpdatedBy() {
        return updatedBy;
    }

    public Date getUpdatedOn() {
        return updatedOn;
    }
    public String getFormatedUpdatedOn(){
        if (updatedOn != null){
            return DateUtil.formatyyyyMMddHHmm(updatedOn);
        }
            return "pas de date communiquée";
    }
  
   
}
