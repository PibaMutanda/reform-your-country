
package reformyourcountry.web;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.servlet.http.HttpSession;


/**
 * Class used to keep track of all the urser's sessions.
 * Used by SessionListener to keep track of incoming users.
 * UserOnLineList uses this information to display the on-line users.
 * 
 * If the servlet specification proposed a mechanism to list the existing sessions, this mechanism would be useless. Unfortunatly it's not the case.
 * 
 * @see be.loop.jbb.web.listeners.SessionListener
 * @author mrik
 *
 */
public class HttpSessionTracker {
    
    //List of active session
    private List<HttpSession> activeSessions = new ArrayList<HttpSession>();
    
    private static HttpSessionTracker instance = new HttpSessionTracker();
    
    private HttpSessionTracker(){
        
    }
    
    public static HttpSessionTracker getInstance(){
        return instance;
    }
    
    public void addSession(HttpSession session){
    	this.activeSessions.add(session);
    }

    public void removeSession(HttpSession session){    	
    	this.activeSessions.remove(session);
    }   
    
    public List<HttpSession> getActiveSessions() {
        return Collections.unmodifiableList(new ArrayList<HttpSession>(activeSessions));
    }

}
