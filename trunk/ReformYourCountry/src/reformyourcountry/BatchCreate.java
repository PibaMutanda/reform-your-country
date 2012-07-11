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

import reformyourcountry.exceptions.InvalidPasswordException;
import reformyourcountry.exceptions.UserAlreadyExistsException;
import reformyourcountry.exceptions.UserLockedException;
import reformyourcountry.exceptions.UserNotFoundException;
import reformyourcountry.exceptions.UserNotValidatedException;
import reformyourcountry.mail.MailingDelayType;
import reformyourcountry.model.Action;
import reformyourcountry.model.Argument;
import reformyourcountry.model.Article;
import reformyourcountry.model.Comment;
import reformyourcountry.model.Group;
import reformyourcountry.model.GroupReg;
import reformyourcountry.model.User;
import reformyourcountry.model.User.AccountStatus;
import reformyourcountry.model.User.Gender;
import reformyourcountry.model.User.Role;
import reformyourcountry.model.VoteAction;
import reformyourcountry.model.VoteArgument;
import reformyourcountry.service.LoginService;
import reformyourcountry.service.LoginService.WaitDelayNotReachedException;
import reformyourcountry.service.UserService;
import reformyourcountry.web.ContextUtil;


@Service

public class BatchCreate {


    @PersistenceContext
    EntityManager em;

    @Autowired   UserService userService;
    @Autowired   LoginService loginService;

    
    public void run() {
        BatchCreate proxy = ContextUtil.getSpringBean(BatchCreate.class);
        User user =  proxy.populateUsers();

        proxy.loginUser(user);

        Article article = proxy.populateArticle();
        Action action = proxy.populateAction(article);
        proxy.populateComment(action , user);

        Group group = proxy.populateGroup();
        proxy.populateGroupReg(user,group);
        proxy.populateVoteAction(action,user,group);
        Argument argument = proxy.populatedArgument(action,user);
        proxy.populateVoteArgument(argument,user);


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


    @Transactional 
    public User populateUsers(){


        
        
       User user = null;
    try {
        user = userService.registerUser(true,"Bob","Bob",Gender.MALE,"bobo","secret","bob0@mail.com");
 
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
        user.setMailDelayType(MailingDelayType.IMMEDIATELY);
        user.setNlSubscriber(true);
        user.setPicture(true);
        user.setRegistrationDate(new Date());
        user.setRole(Role.ADMIN);
        user.setSpammer(false);
        user.setSpamReporter(null);
        user.setAccountStatus(AccountStatus.ACTIVE);
        user.setValidationCode("123456789");

        em.persist(user);
    } catch (UserAlreadyExistsException e1) {
        // TODO Auto-generated catch block
        e1.printStackTrace();
    }
        return user;

    }
    
    @Transactional
    public Article populateArticle(){

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

        return article;


    }
    @Transactional
    public Action populateAction(Article article){

        Action action = new Action("Donner des Ipad 3 à tous les élèves","l'interaction virtuelle dynamique dans l'aprentissage.....");

        action.setContent("this is the content of the action");
        action.setUrl("http://www.cahiers-pedagogiques.com/Le-Web-2-0-et-les-profs.html");

        action.addArticle(article);

        em.persist(action);
        article.addAction(action);
        em.merge(article);
        return action;

    }
    @Transactional
    public Comment populateComment(Action action,User user){

        Comment comment = new Comment("oui mais non","je ne suis pas d'accord ",action,user);
        action.addComment(comment);

        em.persist(comment);
        em.merge(action);

        return comment;

    }

    @Transactional
    public VoteAction populateVoteAction(Action action,User user,Group group){

        VoteAction voteAction = new VoteAction(1,action,user,group);
        em.persist(voteAction);
        group.addVoteAction(voteAction);
        em.merge(group);

        return voteAction;
    }
    @Transactional
    public Group populateGroup(){

        Group group = new  Group();
        group.setDescription("une description");
        group.setName("Groupe");
        group.setUrl("url@unsite.com");

        em.persist(group);

        return group;
    }
    @Transactional
    public void populateGroupReg(User user,Group group){

        GroupReg groupReg = new GroupReg(group,user,new Date(),user);
        groupReg.setConfirmedBy(user);
        groupReg.setConfirmed(true);

        em.persist(groupReg);

    }
    @Transactional
    public Argument populatedArgument(Action action,User user){

        Argument argument = new Argument("non","il est demontré que....",action,user);
        action.addArgument(argument);

        em.persist(argument);
        em.merge(action);

        return argument;
    }
    @Transactional
    public VoteArgument populateVoteArgument(Argument argument,User user){

        VoteArgument voteArgument = new VoteArgument(2,argument,user);
        em.persist(voteArgument);
        argument.addVoteArgument(voteArgument);
        em.merge(argument);

        return voteArgument;

    }

}
