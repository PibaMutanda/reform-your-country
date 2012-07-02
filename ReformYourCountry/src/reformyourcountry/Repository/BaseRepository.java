package reformyourcountry.Repository;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.transaction.annotation.Transactional;

import reformyourcountry.model.BaseEntity;


@Transactional
@SuppressWarnings("unchecked")
public class BaseRepository<E extends BaseEntity> {
    Class<?> entityClass;

    @PersistenceContext
    EntityManager em;

    public BaseRepository(){
        // 1. find someEntityRepositoryClass
        Class<?> someEntityRepositoryClass;// Your repository class, i.e. UserRepository (extending BaseRepository<User>)

        if(this.getClass().getSuperclass() == BaseRepository.class) { // the class is instanced with new
            someEntityRepositoryClass = this.getClass();

        } else { // the class is instanced with Spring as CGLIB class (i.e. UserRepository$$EnhancedByCGLIB$$de100650 extends UserRepository: new BankRepository$$EnhancedByCGLIB$$de100650()) 
            Class<?> cglibRepositoryClass = this.getClass();
            someEntityRepositoryClass = cglibRepositoryClass.getSuperclass();
        }

        // 2. find the ancestor of the class, which is BaseRepository<E>.class
        ParameterizedType baseRepositoryType = (ParameterizedType) someEntityRepositoryClass.getGenericSuperclass();

        // 3. Extract the type of E (from BaseRepository<E>.class)
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
