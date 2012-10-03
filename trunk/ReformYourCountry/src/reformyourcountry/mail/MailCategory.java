package reformyourcountry.mail;

/**
 * 
 * Each grouped mail has a category, we go through the categories of grouped mails to be send and we build the subject. 
 * 
 * Example of a common subject for grouped mails: 
 * 
 *          ------------------------------------------------------
 *          |        Subject         |   Category    |    User   | 
 *          ------------------------------------------------------
 *          |   //mail's subject     |Belt           |Faton      |
 *          ------------------------------------------------------
 *          |   //mail's subject     |Belt           |Faton      |
 *          ------------------------------------------------------
 *          |   //mail's subject     |Course         |Faton      |
 *          ------------------------------------------------------
 *          |   //mail's subject     |Question       |Faton      |
 *          ------------------------------------------------------
 *          
 *          subject   ====>    3 - Belt, 1 - Course access, 1 - Question update(s)
 *          
 * 
 */
public enum MailCategory {
    USER("User"),
    CONTACT("Contact"),
    NEWSLETTER("NewsLetter");
//    QUESTION("Question update(s)"),
//    BELT("Belt"),
//    COACH("Coach"),
//    COURSE("Course access"),
//    USER("User management"),
//    AUCTION("Auction"),
//    GROUP("Group"),
//    DISCUSSION("Discussion"),
//    NEWSLETTER("NewsLetter"),
//    SPAM("Spam"),
//    BUG("Bug"),
//    INVENTORY("Inventory"),    
//    OBJECTIVE("Objective");

    private String text;
    
    private MailCategory(String text){
        this.text=text;
    }
    
    public String getText(){
        return this.text;
    }

}


 