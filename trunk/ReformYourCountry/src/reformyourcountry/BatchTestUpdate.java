package reformyourcountry;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import reformyourcountry.exceptions.InvalidPasswordException;
import reformyourcountry.exceptions.UserLockedException;
import reformyourcountry.exceptions.UserNotFoundException;
import reformyourcountry.exceptions.UserNotValidatedException;
import reformyourcountry.model.Comment;
import reformyourcountry.model.User;
import reformyourcountry.service.LoginService;
import reformyourcountry.service.LoginService.WaitDelayNotReachedException;
import reformyourcountry.service.UserService;
import reformyourcountry.web.ContextUtil;
@Service
public class BatchTestUpdate {
    
    @PersistenceContext
    EntityManager em;

    @Autowired   UserService userService;
    @Autowired   LoginService loginService;

    public void run(){
        BatchTestUpdate proxy = ContextUtil.getSpringBean(BatchTestUpdate.class);
        User user = em.find(User.class,1L);
        
       loginUser(user);
       proxy.updateComment(user);
        
        
    }
    
    @Transactional
    public void updateComment(User user){
        
        Comment result = (Comment) em.createQuery("select c from Comment c where c.user = :user")
                            .setParameter("user", user)
                            .getSingleResult();
       result.setTitle("pourquoi non non");
       result = em.merge(result);
        System.out.println(result.getTitle());
        
    }
    
    public void loginUser(User user){
        try {
             loginService.login(user.getUserName(), "secret", false);
        } catch (UserNotFoundException | InvalidPasswordException
                | UserNotValidatedException | UserLockedException
                | WaitDelayNotReachedException e) {
            throw new RuntimeException (e);
        }
       

    }

}
