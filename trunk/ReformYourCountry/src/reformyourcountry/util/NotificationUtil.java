package reformyourcountry.util;

import org.apache.commons.lang3.StringUtils;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.WebRequest;

public class NotificationUtil {

	/** Sets a message string to be displayed by the next JSP in the Notification bar */
	public static void addNotificationMessage(String notification, WebRequest request){

		String notif = (String)request.getAttribute("notification", RequestAttributes.SCOPE_SESSION);

		if(!StringUtils.isBlank(notif)){
			notification = notif + notification; 
		}

		request.setAttribute("notification", notification, RequestAttributes.SCOPE_SESSION);
	}
}
