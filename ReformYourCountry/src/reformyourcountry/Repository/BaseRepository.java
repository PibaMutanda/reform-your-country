package reformyourcountry.Repository;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import javax.persistence.*;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import reformyourcountry.model.BaseEntity;


@Transactional
@SuppressWarnings("unchecked")
public class BaseRepository<E extends BaseEntity> {
    Class<?> entityClass;
    
    @PersistenceContext
    EntityManager em;
    
    public BaseRepository(){
        Class<?> someEntityRepositoryClass;
        
        if(this.getClass().getSuperclass() == BaseRepository.class) {
            someEntityRepositoryClass = this.getClass();
            
        } else {
            Class<?> cglibRepositoryClass = this.getClass();
            someEntityRepositoryClass = cglibRepositoryClass.getSuperclass();
        }

        ParameterizedType baseRepositoryType = (ParameterizedType) someEntityRepositoryClass.getGenericSuperclass();
        
        Type entityTypeOne = baseRepositoryType.getActualTypeArguments()[0];
        this.entityClass = (Class< E >) entityTypeOne;

    }
    
    public E find(Long id) {
        Object obj =  em.find( entityClass, id );
        if (obj == null)
            return null;
        return ( E )obj;
    }
    
    public void persist(E entity) {
        em.persist(entity);
    }
    
    public E merge(E entity) {
        return em.merge( entity );
    }
    
    public void remove(E entity) {
        em.merge(entity);
        em.remove(entity);
    }

}
