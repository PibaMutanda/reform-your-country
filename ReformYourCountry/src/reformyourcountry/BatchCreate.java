package reformyourcountry;

import java.io.File;
import java.io.FileNotFoundException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import reformyourcountry.Repository.UserRepository;
import reformyourcountry.mail.MailingDelayType;
import reformyourcountry.model.*;
import reformyourcountry.model.User.*;

@Service
@Transactional
public class BatchCreate {
    

    @PersistenceContext
    EntityManager em;
    
    @Autowired
    UserRepository userRepository;
    
    
    
    public void run() {
        
        populateUsers();
        populateArticle();
        populateGroup();
        populateGroupReg();
        
    }
    
    
    
    
    
    private void populateUsers(){
        
        User user = new User();
        user.setFirstName("Bill");
        user.setLastName("Smith");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date bithdate;
       try {
           bithdate = sdf.parse("1982-02-23");
           user.setBirthDate(bithdate);
           user.setLastAccess(sdf.parse("2012-07-05"));
           user.setLastFailedLoginDate(sdf.parse("2012-07-10"));
           user.setLastMailSentDate(sdf.parse("2012-07-01"));
    } catch (ParseException e) {
       
        throw new RuntimeException(e);
    }
       user.setGender(Gender.MALE);
       user.setLastLoginIp("192.168.1.6");
       user.setLockReason("this is a lock reason");
       user.setMail("reformyourcountrytest@gmail.com");
       user.setMailDelayType(MailingDelayType.IMMEDIATELY);
       user.setNlSubscriber(true);
       user.setPassword("password");
       user.setPicture(true);
       user.setRegistrationDate(new Date());
       user.setRole(Role.ADMIN);
       user.setSpammer(false);
       user.setSpamReporter(null);
       user.setAccountStatus(AccountStatus.NOTVALIDATED);
       user.setValidationCode("123456789");
      
       em.persist(user);
       
        
    }
    
    private void populateArticle(){
        
        Article article = new Article();
        article.setTitle("Le Web 2.0 et les profs");
        
        Scanner scan;
        String str = "";
        
        try {
            

            scan = new Scanner(new File(System.getProperty("user.dir")+"/src/reformyourcountry/"+"article.txt"));
      
       
        while(scan.hasNext()){
            str = str + scan.next();
            
        }
        scan.close();
        } catch (FileNotFoundException e) {
        
            throw new RuntimeException(e);
        }
        
        article.setContent(str);
        article.setParent(null);
        
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            article.setReleaseDate(sdf.parse("2012-07-06"));
        } catch (ParseException e) {
      
            throw new RuntimeException(e);
        }
        
        article.setSummary("1.Échanger pour se former/2.Construire ensemble/3.Du plaisir de la mise en réseau");
        article.setUrl("http://www.cahiers-pedagogiques.com/Le-Web-2-0-et-les-profs.html");
        
        
        em.persist(article);
        
    }
    private void populateGroup(){
    	Group group = new  Group();
    	group.setDescription("une description");
    	group.setName("Groupe");
    	group.setUrl("url@unsite.com");
    	
    	em.persist(group);
    	
    }
    
    private void populateGroupReg(){
    	User u =userRepository.find(1L);
    	GroupReg groupReg = new GroupReg();
    	groupReg.setUser(u);
    	groupReg.setConfirmedBy(u);
    	groupReg.setConfirmed(true);
    }

}
