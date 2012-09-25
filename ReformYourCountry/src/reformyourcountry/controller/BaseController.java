package reformyourcountry.controller;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.servlet.ModelAndView;

import reformyourcountry.exception.InvalidUrlException;
import reformyourcountry.model.BaseEntity;
import reformyourcountry.util.ClassUtil;

@Transactional
public class BaseController<E extends BaseEntity> {
    Class<?> entityClass;
    
    @PersistenceContext
    EntityManager em;
    
    public BaseController(){
        entityClass = ClassUtil.getParameterizedType(this, BaseController.class);
    }
    /**
     * Used for trimming Strings handled by Spring
     * @param binder
     */
    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
    }
    
    @SuppressWarnings("unchecked")
    protected E getRequiredEntity(long id) {
        Object obj =  em.find( entityClass, id );
        if (obj == null) {
            throw new InvalidUrlException(entityClass.getName() + " ayant l'id '"+id+"' est introuvable.");
        }
        return ( E )obj;
    }
    
    @SuppressWarnings("unchecked")
    public E getRequiredEntityByUrl(String url){
    	Object obj;
		try {
			obj = em.createQuery("select e from "+entityClass.getName()+" e where e.url = :url").setParameter("url",url).getSingleResult();
		} catch (Exception e) {
			throw new InvalidUrlException(entityClass.getName() + " ayant l'url '"+url+"' est introuvable.", e);
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