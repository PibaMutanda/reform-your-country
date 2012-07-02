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
    Class entityClass;
    
    @PersistenceContext
    EntityManager em;
    public BaseRepository(){
        Class someEntityRepositoryClass;
        if(this.getClass().getSuperclass() == BaseRepository.class)
        {
            someEntityRepositoryClass = this.getClass();
        }else
        {
            Class cglibRepositoryClass = this.getClass();
            someEntityRepositoryClass = cglibRepositoryClass.getSuperclass();
        }

        ParameterizedType baseRepositoryType = (ParameterizedType) someEntityRepositoryClass.getGenericSuperclass();
        
        Type entityTypeOne = baseRepositoryType.getActualTypeArguments()[0];
        this.entityClass = (Class< E >) entityTypeOne;

    }
    
    public E find(Long id){
        return ( E ) em.find( entityClass, id );
    }
    
    public void create(E entity){
        em.persist(entity);
    }
    
    public E update(E entity){
        return em.merge( entity );
    }
    
    public void remove(E entity){
        em.merge(entity);
        em.remove(entity);
    }
    protected EntityManager getEntityManager() {
        return em;
    }

    public void setEntityManager(final EntityManager entityManager) {
        this.em = entityManager;
    }
}
