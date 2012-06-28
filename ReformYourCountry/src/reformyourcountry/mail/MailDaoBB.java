package reformyourcountry.mail;



import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import reformyourcountry.model.User;

import blackbelt.model.Mail;
import blackbelt.model.MailType;
import blackbelt.model.MailingDelayType;

@Repository
public class MailDaoBB extends BaseRepository<Mail> {

    
    /**
     * returns a user containing immediate mails
     * @return a user
     */

    @SuppressWarnings("unchecked")
    public User userHavingImmediateMails(){

        List<User> list = getSession().createQuery("SELECT m.user FROM Mail m WHERE m.mailType=:MailType")
        .setParameter("MailType", MailType.IMMEDIATE)
        .setMaxResults(1)  // Because we don't need all the users, just one.
        .list();

        if (list.size() == 0) {
            return null;    
        } else {
            return list.get(0);  // We just want one user, we'll execute this query again later to get the next user.
        }
    }

    /**
     * returns a user (the next one) having grouped mails that must be sent now.
     * @return a user
     */
    @SuppressWarnings("unchecked")
    public User userHavingGroupedMails(){
        ///// We compute the date/time of yesterday (for users having grouped daily), and the date/time of one week ago (for users having grouped weekly)
        Date yesterday;
        Date lastWeek;      
        Date now;
        now = new Date();
        GregorianCalendar cal= new GregorianCalendar();
        cal.setTime(now);

        cal.add(Calendar.DAY_OF_WEEK, -1);   // test  cal.add(Calendar.SECOND, -1);   
        yesterday = cal.getTime();
        
        cal.add(Calendar.DAY_OF_WEEK, -7);   // test  cal.add(Calendar.SECOND, -30);
        lastWeek = cal.getTime();   
        /************************/

        //request to get a user having at least one old pending grouped mails (so old that we must send it now). 
        List<User> list = getSession().createQuery("SELECT m.user "
                + "FROM Mail m "
                + " WHERE ("
                + "         m.mailType=:MailType"
                + "     AND"
                + "          ("
                + "               m.user.lastMailSentDate IS NULL "   // Not sent yet
                + "           OR  (     m.user.lastMailSentDate IS NOT NULL"
                + "                AND (    (m.user.mailingDelayType = :dailytype AND :yesterday > m.user.lastMailSentDate)"
                + "                      OR (m.user.mailingDelayType = :weeklytype AND :lastWeek > m.user.lastMailSentDate)"
                + "                      OR  m.user.mailingDelayType = :immediatetype"
                + "                    )"
                + "              )"
                + "          )"
                + "     ) ")
                .setParameter("MailType", MailType.GROUPABLE)
                .setParameter("yesterday", yesterday)
                .setParameter("lastWeek", lastWeek)
                .setParameter("dailytype", MailingDelayType.DAILY)
                .setParameter("weeklytype", MailingDelayType.WEEKLY)
                .setParameter("immediatetype", MailingDelayType.IMMEDIATELY)
                .setMaxResults(1)
                .list();
        if (list.size() == 0) {
            return null;    
        } else {
            return list.get(0);  // We just want one user, we'll execute this query again later to get the next user.
        }   
    }   

    /**
     * Get mails for not blackbelt user
     */
    @SuppressWarnings("unchecked")
    public List<Mail> getMailsEmailTarget(){
        return (List<Mail>) getSession().createQuery("select m from Mail m where m.user is null order by m.creationDate").setMaxResults(5).list(); 
    }


    /**
     * returns a user containing slow not groupable mails
     * @return a user
     */
    @SuppressWarnings("unchecked")
    public User userHavingSlowMails(){
        List<User> list = getSession().createQuery("SELECT m.user FROM Mail m WHERE m.mailType=:MailType")
        .setParameter("MailType", MailType.SLOW_NOT_GROUPABLE)
        .setMaxResults(1)  // Because we don't need all the users, just one.
        .list();

        if (list.size() == 0) {
            return null;    
        } else {
            return list.get(0);  // We just want one user, we'll execute this query again later to get the next user.
        }
    }

    /**
     * Get mails, immediate or groupable  from a given user.
     * @param isImmediate indicates if we want immediate mails or grouped mails.
     * @param user the user who we want the mail.
     * @return a list of mails 
     */
    @SuppressWarnings("unchecked")
    public List<Mail> getMailsFromUser(MailType mailType, User user){
        return (List<Mail>) getSession().createQuery("SELECT m FROM Mail m WHERE m.user =:user AND m.mailType =:mailType" )
                .setParameter("user",user)
                .setParamet0er("mailType",mailType).list();
    }

    /**
     * remove a list of mails
     * @param mails
     */
    public void removeMails(List<Mail> mails) {
        if(!mails.isEmpty()){
            Query update = (Query) getSession().createQuery("DELETE FROM Mail m WHERE m IN (:mails)").setParameterList("mails",mails);
            update.executeUpdate();
        }
    }

    // TODO: remove at integration (use inherited)
    /**
     * saves a mail in the database
     * @param mail
     * @param idUser
     */
    public void save(Mail mail, Long idUser) {
        if(idUser !=null){
            User user = (User) getSession().get(User.class, idUser);
            mail.setUser(user);
            
        }
        // TODO : change it to JhibernateSupport method
        getSession().save(mail);
    }
    
    //Remove at integration. This methode it is only used for testing the application
    public User getUser(Long id){
        return (User) getSession().get(User.class, id);
    }
}

