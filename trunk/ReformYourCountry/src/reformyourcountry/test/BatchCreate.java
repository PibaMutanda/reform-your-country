package reformyourcountry.test;

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

import reformyourcountry.batch.BatchUtil;
import reformyourcountry.exception.InvalidPasswordException;
import reformyourcountry.exception.UserAlreadyExistsException;
import reformyourcountry.exception.UserLockedException;
import reformyourcountry.exception.UserNotFoundException;
import reformyourcountry.exception.UserNotValidatedException;
import reformyourcountry.mail.MailingDelayType;
import reformyourcountry.model.Action;
import reformyourcountry.model.Argument;
import reformyourcountry.model.Article;
import reformyourcountry.model.Book;
import reformyourcountry.model.Comment;
import reformyourcountry.model.Group;
import reformyourcountry.model.GroupReg;
import reformyourcountry.model.User;
import reformyourcountry.model.User.AccountStatus;
import reformyourcountry.model.User.Gender;
import reformyourcountry.model.User.Role;
import reformyourcountry.model.VoteAction;
import reformyourcountry.model.VoteArgument;
import reformyourcountry.repository.UserRepository;
import reformyourcountry.security.Privilege;
import reformyourcountry.service.LoginService;
import reformyourcountry.service.LoginService.WaitDelayNotReachedException;
import reformyourcountry.service.UserService;
import reformyourcountry.web.ContextUtil;


@Service()
public class BatchCreate implements Runnable {


    @PersistenceContext
    EntityManager em;

    @Autowired   UserService userService;
    @Autowired   LoginService loginService;
    @Autowired	 UserRepository userRepository;	
    public static void main(String[] args){
        BatchUtil.startSpringBatch(BatchCreate.class);
    }

    public void run() {
        BatchCreate proxy = ContextUtil.getSpringBean(BatchCreate.class);


        User user =  proxy.populateUsers();
        proxy.populateUsersWithOneModerator();
        proxy.populateUsersWithOneUser();
      //  proxy.loginUser(user);

        Article article = proxy.populateArticle();
        Article article2 = proxy.populateArticle2WithParent(article);
        Article article3 = proxy.populateArticle3WithParent(article2);
        Article article4 = proxy.populateArticle4WithParent(article3);
        Action action = proxy.populateAction(article);
        proxy.populateComment(action , user);
        Group group = proxy.populateGroup();
        proxy.populateGroupReg(user,group);
        proxy.populateVoteAction(action,user,group);
        Argument argument = proxy.populatedArgument(action,user);
        proxy.populateVoteArgument(argument,user);


    }

    //    public void run() {
    //        
    //         
    //         
    //         User user = populateUsers();
    //
    //        loginUser(user);
    //
    //         Article article = populateArticle();
    //         Action action = populateAction(article);
    //         populateComment(action , user);
    //         Group group = populateGroup();
    //         populateGroupReg(user,group);
    //         populateVoteAction(action,user,group);
    //         Argument argument = populatedArgument(action,user);
    //         populateVoteArgument(argument,user);
    //
    //
    //     }
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
            user = userService.registerUser(true,"test","secret","test@mail.com");

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
            user.setFirstName("Testname");
            user.setLastName("Testlastname");
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

          
        } catch (UserAlreadyExistsException e) {
            throw new RuntimeException(e);
        }
        return user;

    }

    @Transactional 
    public User populateUsersWithOneModerator(){




        User user = null;
        try {
            user = userService.registerUser(true,"moder","secret","moder@mail.com");

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date bithdate;
            try {
                bithdate = sdf.parse("1982-01-29");
                user.setBirthDate(bithdate);
                user.setLastAccess(sdf.parse("2012-07-05"));
                user.setLastFailedLoginDate(sdf.parse("2012-07-10"));
                user.setLastMailSentDate(sdf.parse("2012-07-01"));
            } catch (ParseException e) {

                throw new RuntimeException(e);
            }
            user.setFirstName("Testname");
            user.setLastName("Testlastname");
            user.setGender(Gender.MALE);
            user.setLastLoginIp("192.168.1.7");
            user.setLockReason("this is a lock reason");
            user.setMailDelayType(MailingDelayType.IMMEDIATELY);
            user.setNlSubscriber(true);
            user.setPicture(true);
            user.setRegistrationDate(new Date());
            user.setRole(Role.MODERATOR);
            user.getPrivileges().add(Privilege.EDIT_ARTICLE);
            user.getPrivileges().add(Privilege.MANAGE_USERS);

            user.setSpammer(false);
            user.setSpamReporter(null);
            user.setAccountStatus(AccountStatus.ACTIVE);
            user.setValidationCode("123456789");

            //em.persist(user);
        } catch (UserAlreadyExistsException e) {
            throw new RuntimeException(e);
        }
        return user;

    }


    @Transactional 
    public User populateUsersWithOneUser(){




        User user = null;
        try {
            user = userService.registerUser(true,"user","secret","user@mail.com");

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date bithdate;
            try {
                bithdate = sdf.parse("1982-01-30");
                user.setBirthDate(bithdate);
                user.setLastAccess(sdf.parse("2012-07-05"));
                user.setLastFailedLoginDate(sdf.parse("2012-07-10"));
                user.setLastMailSentDate(sdf.parse("2012-07-01"));
            } catch (ParseException e) {

                throw new RuntimeException(e);
            }
            user.setFirstName("Testname");
            user.setLastName("Testlastname");
            user.setGender(Gender.MALE);
            user.setLastLoginIp("192.168.1.8");
            user.setLockReason("this is a lock reason");
            user.setMailDelayType(MailingDelayType.IMMEDIATELY);
            user.setNlSubscriber(true);
            user.setPicture(true);
            user.setRegistrationDate(new Date());
            user.setRole(Role.USER);


            user.setSpammer(false);
            user.setSpamReporter(null);
            user.setAccountStatus(AccountStatus.ACTIVE);
            user.setValidationCode("123456788");

            //em.persist(user);
        } catch (UserAlreadyExistsException e) {
            throw new RuntimeException(e);
        }
        return user;

    }

    @Transactional
    public Article populateArticle(){
        // article.
        Article article = new Article();
        article.setTitle("Le Web 2.0 et les profs");

        Scanner scan;
        String str = "";

        try {


            scan = new Scanner(new File(System.getProperty("user.dir")+"/src/reformyourcountry/"+"article.txt"));


            while(scan.hasNext()){
                str = str + scan.nextLine();

            }
            scan.close();
        } catch (FileNotFoundException e) {

            throw new RuntimeException(e);
        }

        article.setContent(str);
        article.setParent(null);
        article.setPublicView(true);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            article.setReleaseDate(sdf.parse("2012-07-06"));
            article.setPublishDate(sdf.parse("2012-09-09"));
        } catch (ParseException e) {

            throw new RuntimeException(e);
        }

        article.setSummary("1.Échanger pour se former/2.Construire ensemble/3.Du plaisir de la mise en réseau");
        article.setUrl("http://www.cahiers-pedagogiques.com/Le-Web-2-0-et-les-profs.html");


        em.persist(article);

        return article;


    }


    @Transactional
    public Article populateArticle2WithParent(Article parent){

        Article article2 = new Article();
        article2.setTitle("Autonomie des écoles");

        Scanner scan;
        String str = "";

        try {


            scan = new Scanner(new File(System.getProperty("user.dir")+"/src/reformyourcountry/"+"article2.txt"));


            while(scan.hasNext()){
                str = str + scan.nextLine();

            }
            scan.close();
        } catch (FileNotFoundException e) {

            throw new RuntimeException(e);
        }

        article2.setContent(str);
        article2.setParent(parent);
        article2.setPublicView(true);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            article2.setReleaseDate(sdf.parse("2012-08-10"));
            article2.setPublishDate(sdf.parse("2012-08-08"));
        } catch (ParseException e) {

            throw new RuntimeException(e);
        }

        article2.setSummary("1.Règles actuelles/2.Décrets pédagogiques/3.Contrôle des résultats/4.Recrutement/Licenciement/5.Libre plus autonome");
        article2.setUrl("https://sites.google.com/site/enseignement2be/ecoles/autonomie-des-ecoles");

        em.persist(article2);
        return article2;

    }

    @Transactional
    public Article populateArticle3WithParent(Article parent){

        //Article 3
        Article article3 = new Article();
        article3.setTitle("Directeurs d'école");

        Scanner scan;
        String str = "";

        try {


            scan = new Scanner(new File(System.getProperty("user.dir")+"/src/reformyourcountry/"+"article3.txt"));


            while(scan.hasNext()){
                str = str + scan.nextLine();

            }
            scan.close();
        } catch (FileNotFoundException e) {

            throw new RuntimeException(e);
        }

        article3.setContent(str);
        article3.setParent(parent);
        article3.setPublicView(false);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        try {
            article3.setReleaseDate(sdf.parse("2012-08-10"));
        } catch (ParseException e) {

            throw new RuntimeException(e);
        }

        article3.setSummary("1.Règles actuelles/2.Décrets pédagogiques/3.Contrôle des résultats/4.Recrutement/Licenciement/5.Libre plus autonome");
        article3.setUrl("https://sites.google.com/site/enseignement2be/ecoles/auto");


        em.persist(article3);

        return article3;

    }

    @Transactional
    public Article populateArticle4WithParent(Article parent){

        //Article 4
        Article article4 = new Article();
        article4.setTitle("Ecole de l'avenir");

        Scanner scan;
        String str = "";

        try {


            scan = new Scanner(new File(System.getProperty("user.dir")+"/src/reformyourcountry/"+"article4.txt"));


            while(scan.hasNext()){
                str = str + scan.nextLine();

            }
            scan.close();
        } catch (FileNotFoundException e) {

            throw new RuntimeException(e);
        }

        article4.setContent(str);
        article4.setParent(parent);
        article4.setPublicView(true);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        try {
            article4.setReleaseDate(sdf.parse("2012-08-10"));
        } catch (ParseException e) {

            throw new RuntimeException(e);
        }

        article4.setSummary("1.Règles actuelles/2.Décrets pédagogiques/3.Contrôle des résultats/4.Recrutement/Licenciement/5.Libre plus autonome");
        article4.setUrl("https://sites.google.com/site/enseignement2be/ecoles/auto");


        em.persist(article4);

        return article4;


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
    
    @Transactional
    public Book populateBook(){
        
        Book book = new Book("abcd","Les clés du succès des systèmes scolaires les plus performants","Excellent rapport, agréable à lire par tous, pour comprendre les différences entre systèmes scolaires dans le monde et ce qui fait que certains s'améliorent.","McKinsey","2007",true,"http://mckinseyonsociety.com/how-the-worlds-best-performing-schools-come-out-on-top/");
        em.persist(book);
        
        return book;
    }

}
