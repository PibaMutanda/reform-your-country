package reformyourcountry.controller;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.web.servlet.ModelAndView;

import reformyourcountry.exception.InvalidUrlException;
import reformyourcountry.misc.ClassUtil;
import reformyourcountry.model.BaseEntity;


public class BaseController<E extends BaseEntity> {
    Class<?> entityClass;
    
    @PersistenceContext
    EntityManager em;
    
    public BaseController(){
        entityClass = ClassUtil.getParameterizedType(this, BaseController.class);
    }

    @SuppressWarnings("unchecked")
    protected E getRequiredEntity(long id) {
        Object obj =  em.find( entityClass, id );
        if (obj == null) {
            throw new InvalidUrlException(entityClass.getName() + " having the id '"+id+"' is not found.");
        }
        return ( E )obj;
    } 
    public void setMessage(ModelAndView mv, String message){
        mv.addObject("message",message);
    }

}
