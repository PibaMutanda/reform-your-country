package reformyourcountry.controller;

import java.io.IOException;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

import reformyourcountry.exception.AjaxExceptionVO;
import reformyourcountry.exception.AjaxValidationException;
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
    
    protected Object getRequiredEntity(long id, Class<?> clazz) {
        Object obj =  em.find(clazz, id );
        if (obj == null) {
            throw new InvalidUrlException(clazz.getName() + " ayant l'id '"+id+"' est introuvable.");
        }
        return obj;
    }    
    
    @SuppressWarnings("unchecked")
    protected E getRequiredEntity(long id) {
        return ( E )getRequiredEntity(id, entityClass);
    }
    
    protected Object getRequiredEntityByUrl(String url, Class<?> clazz){
    	Object obj;
		try {
			obj = em.createQuery("select e from "+clazz.getName()+" e where e.url = :url").setParameter("url",url).getSingleResult();
		} catch (Exception e) {
			throw new InvalidUrlException(clazz.getName() + " ayant l'url '"+url+"' est introuvable.", e);
		}
        return obj;
    }
    
    @SuppressWarnings("unchecked")
    protected E getRequiredEntityByUrl(String url){
    	Object obj;
		try {
			obj = em.createQuery("select e from "+entityClass.getName()+" e where lower(e.url) = :url")
					.setParameter("url",url.toLowerCase())
					.getSingleResult();
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

    /**
     * handle AjaxValidationException and return an AjaxExceptionVO to get message in jQuery.error() 
     * @see https://doanduyhai.wordpress.com/2012/05/06/spring-mvc-part-v-exception-handling/
     */
    @ExceptionHandler(AjaxValidationException.class)
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    protected AjaxExceptionVO handleAjaxValidationException(AjaxValidationException ex, HttpServletResponse response) throws IOException
    {
    	AjaxExceptionVO exceptionVO = new AjaxExceptionVO("handleAjaxValidationException()", this.getClass().toString(), ex.getMessage());
    	return exceptionVO;
    }
    
    /**
     * This method will return a premade model and view to go on confiorm page before the deletion of some Entities.
     * 
     * @param message 
     * @param deleteMethodAdress address of the controller method 
     * @param urlInCaseOfAbortion
     * @param id
     * @return 
     */
    protected ModelAndView getConfirmBeforeDeletePage(String message,String deleteMethodAdress,String urlInCaseOfAbortion,Long id){

        ModelAndView mv = new ModelAndView("confirm");
        mv.addObject("url",deleteMethodAdress);
        mv.addObject("info",message);
        mv.addObject("id",id);
        mv.addObject("abortUrl",urlInCaseOfAbortion);
        return mv;
    }
}
