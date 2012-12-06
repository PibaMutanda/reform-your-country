package reformyourcountry.repository;



import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.transaction.annotation.Transactional;

import reformyourcountry.model.BaseEntity;
import reformyourcountry.util.ClassUtil;

@Transactional
@SuppressWarnings("unchecked")
public abstract class BaseRepository<E extends BaseEntity> {
    Class<?> entityClass;

    @PersistenceContext
    EntityManager em;

    public BaseRepository(){
        entityClass = ClassUtil.getParameterizedType(this, BaseRepository.class);
    }

    public E find(Long id) {
        Object obj =  em.find( entityClass, id );
        if (obj == null)
            return null;  // Cannot downcast null.
        return ( E )obj;
    }


    
    public E findByUrl(String url){
        Object obj = getSingleOrNullResult(em.createQuery("select e from "+entityClass.getName()+" e where lower(e.url) = :url").setParameter("url",url.toLowerCase()));
        if (obj == null)
            return null;  // Cannot downcast null.
        return ( E )obj;
    }
    
    
     public void persist(E entity) {
      
        em.persist(entity);
    
    }
     public void refresh(E entity) {
         
         em.refresh(entity);
     
     }
    public E merge(E entity) {
        return em.merge( entity );
    }

    public void remove(E entity) {
        entity = find(entity.getId());  // In case of entity is detached. We can only pass managed entities to remove. If the entity already managed, it's in the 1st level cache => no DB access.
        if (entity == null) {
            throw new IllegalArgumentException("Bug: trying to remove an entity that is not in DB anymore?");
        } else {
            em.remove(entity);
        }
    }

    // The JPA API EntityManager.getsingleResult() throws an exception if not found, which is not nice.
    protected E getSingleOrNullResult(Query query) {
        try {
            E result = (E)query.getSingleResult();
            return result;
        } catch (NoResultException nre) {
            return null;  // Normal, entity is just not found.
        }
    }
}
