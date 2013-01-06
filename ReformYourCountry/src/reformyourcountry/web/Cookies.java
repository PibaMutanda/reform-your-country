package reformyourcountry.web;

import javax.servlet.http.Cookie;

import reformyourcountry.model.User;


public class Cookies {

    
	public static final String SSO_HASH_SALT = "jbbssosec021";
	public final static String PASSCOOKIE_KEY = "pmark";
	public final static String LOGINCOOKIE_KEY = "login";



	static public void createCookie(String name, String value) {
        Cookie cookie = new Cookie(name, value);
        cookie.setMaxAge(60 * 60 * 24 * 180);
        cookie.setPath("/");
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
//            cookie.setDomain(UrlUtil.getCookieDomainName());
            cookie.setPath("/");
            ContextUtil.getHttpServletResponse().addCookie(cookie);
        }
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
