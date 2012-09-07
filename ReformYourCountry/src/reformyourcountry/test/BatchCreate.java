package reformyourcountry.test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
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
        Article article2 = proxy.populateArticle2WithParent(null);
        Article article3 = proxy.populateArticle3WithParent(article2);
        Article article4 = proxy.populateArticle4WithParent(article3);
        Action action = proxy.populateAction(article);
        proxy.populateComment(action , user);
        Group group = proxy.populateGroup();
        proxy.populateGroupReg(user,group);
        proxy.populateVoteAction(action,user,group);
        Argument argument = proxy.populatedArgument(action,user);
        proxy.populateVoteArgument(argument,user);
        proxy.populateBook();
        proxy.polulateAction();

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
            user.setMailDelayType(MailingDelayType.IMMEDIATELY);
            user.setNlSubscriber(true);
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


    @SuppressWarnings("resource")
    @Transactional
    public Article populateArticle2WithParent(Article parent){

        Article article2 = new Article();
        article2.setTitle("Autonomie des écoles");

      
        int ch;
        StringBuffer strContent = new StringBuffer("");
        File f = new File(System.getProperty("user.dir")+"/src/reformyourcountry/"+"article2.txt");
        
        
        FileInputStream fis = null;
        try {
                           
            fis = new FileInputStream(f);
            InputStreamReader isr = new InputStreamReader(fis, "UTF8");
           
            while( (ch = isr.read()) != -1)
                                
                strContent.append((char)ch);
                    
              fis.close();
             
            
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        catch(IOException e1){
            e1.printStackTrace();
        }
       
 
      

        article2.setContent(strContent.toString());
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

    

        int ch;
        StringBuffer strContent = new StringBuffer("");
        File f = new File(System.getProperty("user.dir")+"/src/reformyourcountry/"+"article3.txt");
        
        
        FileInputStream fis = null;
        try {
                           
            fis = new FileInputStream(f);
            InputStreamReader isr = new InputStreamReader(fis, "UTF8");
           
            while( (ch = isr.read()) != -1)
                                
                strContent.append((char)ch);
                    
            
              fis.close();
             
            
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        catch(IOException e1){
            e1.printStackTrace();
        }
        
        

        article3.setContent(strContent.toString());
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

        int ch;
        StringBuffer strContent = new StringBuffer("");
        File f = new File(System.getProperty("user.dir")+"/src/reformyourcountry/"+"article4.txt");
        
        
        FileInputStream fis = null;
        try {
                           
            fis = new FileInputStream(f);
            InputStreamReader isr = new InputStreamReader(fis, "UTF8");
           
            while( (ch = isr.read()) != -1)
                                
                strContent.append((char)ch);
                    
              fis.close();
             
            
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        catch(IOException e1){
            e1.printStackTrace();
        }

        article4.setContent(strContent.toString());
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
    public void populateBook(){
        
        Book book1 = new Book("abcd","Les clés du succès des systèmes scolaires les plus performants","Excellent rapport, agréable à lire par tous, pour comprendre les différences entre systèmes scolaires dans le monde et ce qui fait que certains s'améliorent.","McKinsey","2007",true,"http://mckinseyonsociety.com/how-the-worlds-best-performing-schools-come-out-on-top/");
        Book book2 = new Book("mens","Un meilleur enseignement en Communauté française; nous le pouvons si nous le voulons","Facultés Universitaires Notre Dame de la Paix - Namur","Robert Deschamps","2010",false,"http://www.fundp.ac.be/pdf/publications/70749.pdf");
        Book book3 = new Book("cede","Réflexions en vue d'un système éducatif plus performant pour tous les enfants","Centre d'étude de de défense de l'école publique.","","",false,"http://www.cedep.be/default.asp?contentID=31");
        Book book4 = new Book("ecol"," Ecole de l'échec: comment la réformer?","Du pédagogisme à la gouvernance","Alain Destexhe, Vincent Vandenberghe, Guy Vlaeminck","2004",false,"http://www.bookfinder.com/dir/i/Lecole_De_Lechec-Comment_La_Reformer-Du_Pedagogisme_a_La_Gouvernance/2804019322/");
        Book book5 = new Book("muta"," La mutation de l'école secondaire","Questions de sens - Propositions d’action","Francis Tilman, Dominique Grootaers, Barbara Dufour","2011",true,"http://www.couleurlivres.be/html/nouveautes/mutation-ecol-sec.html");
 
        em.persist(book1);
        em.persist(book2);
        em.persist(book3);
        em.persist(book4);
        em.persist(book5);
        
       
    }

    
    @Transactional
    public void polulateAction(){
        Action action1=new Action();
        Action action2=new Action();
        Action action3=new Action();

        action1.setTitle("Statut du Directeur");
        action1.setShortDescription("Sanction contre un directeur d'école!");
        action1.setContent("L’Inspection académique de l’Isère a décidé de démettre de ses fonctions un directeur d’école primaire, tout en lui conservant sa fonction d’instituteur. Jean-Yves Le Gall, directeur de l’école primaire de Notre-Dame-de-Vaulx, refusait d’enregistrer des informations dans la banque de données “base élèves”.");
        action1.setUrl("http://blogs.mediapart.fr/blog/velveth/040209/base-eleves-sanction-contre-un-directeur-d-ecole-2");

        action2.setTitle("Les droits et obligations des enseignants");
        action2.setContent("Les enseignants bénéficient de droits liés aux missions qu'ils exercent, mais aussi d'un certain nombre d'obligations. Chaque membre de la communauté éducative doit avoir un comportement et une conduite irréprochable vis-à-vis des élèves, de ses collègues et de l'environnement scolaire dans lequel il se trouve. ");
        action2.setUrl("http://www.cap-concours.fr/enseignement/preparer-les-concours/concours-de-cpe/les-droits-et-obligations-des-enseignants-mas_educ_09");

        action3.setTitle("Les obligations enseignants");
        action3.setShortDescription("L'obligation d'obéissance hiérarchique");
        action3.setContent("L'enseignant doit toujours se conformer aux instructions de son supérieur hiérarchique, sauf dans le cas où l'ordre donné est manifestement illégal et de nature à compromettre gravement un intérêt public. Le refus d'obéissance est considéré comme une faute professionnelle. En outre, l'enseignant se doit de respecter les lois et règlements de toute nature.");
        action3.setUrl("http://www.cap-concours.fr/enseignement/preparer-les-concours/concours-de-cpe/les-droits-et-obligations-des-enseignants-mas_educ_09#/page3");

        em.persist(action1);
        em.persist(action2);
        em.persist(action3);
    }
}
