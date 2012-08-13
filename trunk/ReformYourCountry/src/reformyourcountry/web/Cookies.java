package reformyourcountry.web;

import javax.servlet.http.Cookie;

import reformyourcountry.model.User;
//TODO maxime uncomment
//import be.loop.jbb.web.SessionObject;
//import be.loop.jbb.web.WebUtil;


public class Cookies {

    
	public static final String SSO_HASH_SALT = "jbbssosec021";
	public final static String PASSCOOKIE_KEY = "pmark";
	public final static String LOGINCOOKIE_KEY = "login";

	/**
	 * Stores a volatile cookie 
	 */
    static public void createCookie(String domain, String name, String value) {
        Cookie cookie = new Cookie(name, value);
        cookie.setDomain(domain);
        cookie.setPath("/test");
        ContextUtil.getHttpServletResponse().addCookie(cookie);
    }
	
    /**
     * Look in the request for a cookie with a specified name to return it.
     * 
     * @param name
     *            The name of the cookie that have to be found.
     * @param request
     *            The request in wich are the cookies.
     * @return The cookie that have just been found or null if not found
     */
    static public Cookie findCookie(String name) {
    	
        Cookie[] cookies = ContextUtil.getHttpServletRequest().getCookies();
        Cookie searchedCookie = null;
        if (cookies != null) {
            int i = 0;
            while (i < cookies.length && searchedCookie == null) {
                if (cookies[i].getName().equals(name)) {
                    searchedCookie = cookies[i];
                    break;
                }
                i++;
            }
        }
        return searchedCookie;
    }

    /**
     * Look in the given http request for cookies to destroy them using the http
     * response by setting cookie life time to 0 sec.
     * 
     * @param request
     * @param response
     */
    static public void destroyAllCookies() {
        Cookie[] cookies = ContextUtil.getHttpServletRequest().getCookies();
        for (int i = 0; i < cookies.length; i++) {
            destroyCookieByName(cookies[i].getName());
        }
    }

    /**
     * Look in the given http request for a cookie maching the given name to
     * destroy it using the http response by setting cookie life time to 0 sec.
     * 
     * @param name
     *            The name of the cookie that have to be destroyed.
     * @param request
     * @param response
     */
    static public void destroyCookieByName(String name) {
        Cookie cookie = findCookie(name);
      
        if (cookie != null) {
        	cookie.setValue(null);
            // By setting the cookie maxAge to 0 it will deleted immediately
            cookie.setMaxAge(0);
            cookie.setDomain(UrlUtil.getDomainName());
            cookie.setPath("/");
            ContextUtil.getHttpServletResponse().addCookie(cookie);
        }
    }
	
    /* 
     * Need to keep the jsessionid for the org user and other.
     * Configure the context.xml of the server
     * and define the domain of the cookie
     * check : http://stackoverflow.com/questions/7400025
     **/
    static public void createCookie(String name, String value) {
        Cookie cookie = new Cookie(name, value);
        cookie.setDomain(UrlUtil.getDomainName()); //Set the domain of the cookie to get the same cookie for the org user and other user.
        cookie.setMaxAge(15552000);// Cookie is stored for 6 month
        cookie.setPath("/");  // So it does not depends on the request path (like /KnowledgeBlackBelt/ui/UIDL) -- John 2009-07-13)
        ContextUtil.getHttpServletResponse().addCookie(cookie);
    }
    

	public static void setLoginCookies(User user) {
		createCookie(LOGINCOOKIE_KEY, user.getId().toString());
		createCookie(PASSCOOKIE_KEY, user.getPassword());  // MD5 encoded!
	}
	
	public static void clearLoginCookies() {
        // If login cookies exist they are destroyed
        destroyCookieByName(LOGINCOOKIE_KEY);
        destroyCookieByName(PASSCOOKIE_KEY);
	}

}
