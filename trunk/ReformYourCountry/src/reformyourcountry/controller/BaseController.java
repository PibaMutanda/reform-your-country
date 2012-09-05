package reformyourcountry.controller;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.ModelAndView;

import reformyourcountry.exception.InvalidUrlException;
import reformyourcountry.misc.ClassUtil;
import reformyourcountry.model.BaseEntity;

@Transactional
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
    
    /** Useful for SpringMVC @ModelAttribute method.
     * When a form is submitted, our @ModelAttribute method returns a detached entity.
     * Then Spring injects the parameters values in that entity, then the @RequestMapping method is called.
     * Our requestMapping controller method may refuse to validate the changes and if the entity is still attached,
     * the Hibernate's dirty checking might save the entity (which we do not want). 
     * @param id
     * @return
     */
    protected E getRequiredDetachedEntity(long id) {
    	E entity = getRequiredEntity(id);
    	em.detach(entity);
    	return entity;
    }
    
    /** Sets a message string to be displayed by the next JSP in the sitemesh template */
    public void setMessage(ModelAndView mv, String message){
        mv.addObject("message",message);
    }

}
