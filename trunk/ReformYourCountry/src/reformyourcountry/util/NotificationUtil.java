package reformyourcountry.util;

import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.springframework.web.context.request.RequestAttributes;

import reformyourcountry.web.ContextUtil;

public class NotificationUtil {

	/** Sets a message string to be displayed by the next JSP in the Notification bar */
	public static void addNotificationMessage(String notification){
	    
	    HttpSession httpSession = ContextUtil.getHttpSession();

		String existingNotif = (String)httpSession.getAttribute("notification");

		if(!StringUtils.isBlank(existingNotif)){
			notification = existingNotif + "<br/>"+ notification; 
		}

		httpSession.setAttribute("notification", notification);
	}
}
