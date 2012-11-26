package reformyourcountry.util;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.springframework.web.context.request.RequestAttributes;

import reformyourcountry.web.ContextUtil;

public class NotificationUtil {

	/** Sets a message string to be displayed by the next JSP in the Notification bar */
	public static void addNotificationMessage(String notification){
	    
	    HttpSession httpSession = ContextUtil.getHttpSession();
	       
	    @SuppressWarnings("unchecked")
		List<Notification> notifications=(List<Notification>) httpSession.getAttribute("notifications");
	    
	    if(notifications==null){
	    	notifications = new ArrayList<Notification>();
	    }
    	notifications.add(new Notification(notification,Status.NOTICE));
	    
		httpSession.setAttribute("notifications",notifications);
	}
	
	
	public enum Status{
		
		ERROR("Error"),
		NOTICE("Notice");
	
		String name;
		Status(String name){
			this.name=name;
		}

		public String getName(){
			return name;
		}
		
	}
	
	
	static public class  Notification{
		
		String text;
		Status status;
		
		public Notification (String text,Status status){
			this.text=text;
			this.status=status;
		}
		public String getText() {
			return text;
		}
		public void setText(String text) {
			this.text = text;
		}
		public Status getStatus() {
			return status;
		}
		public void setStatus(Status status) {
			this.status = status;
		}
		@Override
		public String toString() {
			return "Notification [text=" + text + ", status=" + status + "]";
		}
		
		
	}
	
	
}
