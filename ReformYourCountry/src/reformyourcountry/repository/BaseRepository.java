package reformyourcountry.Repository;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.transaction.annotation.Transactional;

import reformyourcountry.model.BaseEntity;
import reformyourcountry.model.User;


@Transactional
@SuppressWarnings("unchecked")
public abstract class BaseRepository<E extends BaseEntity> {
    Class<?> entityClass;

    @PersistenceContext
    EntityManager em;

   
    
    
    public BaseRepository(){
        //// 1. find someEntityRepositoryClass
        Class<?> someEntityRepositoryClass;// Your repository class, i.e. UserRepository (extending BaseRepository<User>)

        if (this.getClass().getSuperclass() == BaseRepository.class) { // the class is instanced with new (i.e. new UserRepository())
            someEntityRepositoryClass = this.getClass();

        } else { // the class is instanced with Spring as CGLIB class (i.e. UserRepository$$EnhancedByCGLIB$$de100650 extends UserRepository) 
            Class<?> cglibRepositoryClass = this.getClass();
            someEntityRepositoryClass = cglibRepositoryClass.getSuperclass();
        }

        //// 2. find the ancestor of the class, which is BaseRepository<E>.class
        ParameterizedType baseRepositoryType = (ParameterizedType) someEntityRepositoryClass.getGenericSuperclass();

        //// 3. Extract the type of E (from BaseRepository<E>.class)
        Type entityTypeOne = baseRepositoryType.getActualTypeArguments()[0];
        this.entityClass = (Class< E >) entityTypeOne;

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
