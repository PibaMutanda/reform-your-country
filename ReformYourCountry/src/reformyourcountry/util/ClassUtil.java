package reformyourcountry.util;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public class ClassUtil {

    /** Finds the parameter type out of an object. For example, finds User.class of out BaseRepository<User>
     * @Param springBean the object in which we are looking for the parameter type. For example, it's an instance of UserRepository (extends BaseRepository<User>). 
     * @Param baseSpringClass the class at the top of the hierarchy, where the parameter type is declared. For example BaseRepository.class*/    
    public static Class getParameterizedType(Object springBean, Class baseSpringClass) {
        //// 1. find someEntityRepositoryClass
        Class<?> someEntityRepositoryClass;// Your repository class, i.e. UserRepository (extending BaseRepository<User>)

        if (springBean.getClass().getSuperclass() == baseSpringClass) { // the class is instanced with new (i.e. new UserRepository())
            someEntityRepositoryClass = springBean.getClass();

        } else { // the class is instanced with Spring as CGLIB class (i.e. UserRepository$$EnhancedByCGLIB$$de100650 extends UserRepository) 
            Class<?> cglibRepositoryClass = springBean.getClass();
            someEntityRepositoryClass = cglibRepositoryClass.getSuperclass();
        }

        //// 2. find the ancestor of the class, which is BaseRepository<E>.class
        ParameterizedType baseRepositoryType = (ParameterizedType) someEntityRepositoryClass.getGenericSuperclass();

        //// 3. Extract the type of E (from BaseRepository<E>.class)
        Type entityTypeOne = baseRepositoryType.getActualTypeArguments()[0];
        return (Class)entityTypeOne;

    }
}