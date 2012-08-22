package reformyourcountry.repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.transaction.annotation.Transactional;

import reformyourcountry.misc.ClassUtil;
import reformyourcountry.model.BaseEntity;
import reformyourcountry.model.User;


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

    public void persist(E entity) {
      
        em.persist(entity);
    
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
    
    
    public User findUserWhoCreate(E entity){
        
         
        E entity1 = find(entity.getId());
          
          return entity1.getCreatedBy();
        
    }
    
    public User findUserWhoUpdate(E entity){
        
        
        E entity1 = find(entity.getId());
          
          return entity1.getUpdatedBy();
        
    }

}
