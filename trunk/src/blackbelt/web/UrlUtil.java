package blackbelt.web;

import java.net.URI;
import java.net.URISyntaxException;

import org.vaadin.navigator7.PageResource;

import blackbelt.model.Organization;
import blackbelt.security.SecurityContext;
import blackbelt.web.CurrentEnvironment.Environment;

public class UrlUtil {

    public final static String TEST_ABSOLUTE_DOMAIN_NAME = "logicblackbelt.com"; 
    public final static String PROD_ABSOLUTE_DOMAIN_NAME = "knowledgeblackbelt.com"; 
    public final static String DEV_ABSOLUTE_DOMAIN_NAME = "localhost:8080";


    public static String getAbsoluteUrl(PageResource pageResource){
        return getAbsoluteUrl(pageResource.getURL(), false, null, true);  // Shouln't "/ui" be in the pageResource.getURL() already?
    }

    // Usually for images.
    public static String getAbsoluteUrl(String path){
        if(path.startsWith("/")){ //We need to normalize the incoming string. we remove the first / in the path if there is one.
            path = path.substring(1); 
        }
        return getAbsoluteUrl(path, false);
    }

    // Usually for images.
    public static String getAbsoluteUrl(String path, boolean forceProdUrl){
        return getAbsoluteUrl(path, forceProdUrl, null, false);
    }
    
	public static String getPortalDomain(Organization org) {
		return org.getUrlFragment() + "." + getDomainName();
	}
    
	
	public static String getDomainName() {
		switch(ContextUtil.getEnvironment()) {
			case DEV : return "localhost";
			case TEST : return TEST_ABSOLUTE_DOMAIN_NAME;
			case PROD : return PROD_ABSOLUTE_DOMAIN_NAME;
		}
		throw new RuntimeException("Unknown Environement");
	}
	
    public static String getAbsoluteUrl(String path, boolean forceProdUrl, Organization forcedOrganization,  boolean addVaadinPrefix){
        // Organization
        Organization organization;
        if (forcedOrganization != null) {
            organization = forcedOrganization;
        } else {
            organization = SecurityContext.getOrganization();
        }
        
        String result;
        if (!ContextUtil.devMode || forceProdUrl || ContextUtil.isInBatchNonWebMode()) {
            // In dev, it should be "http://KnowledgeBlackBelt.com/"  (because images are not loaded on developers machines).
        	
            String domainName = PROD_ABSOLUTE_DOMAIN_NAME;
            
            // Organization
            String organizationPortalUrlFrag = organization != null ? organization.getUrlFragment()+"." : "";
            
            // All together    
            result = "http://" + organizationPortalUrlFrag + domainName              + "/"; // The "/" is crucial at the end of the project name. Without it, you loose the session.
            // i.e.:  http://  + myCompany.                + KnowledgeBlackBelt.com  +  /
            result += path;
            //        #MyPage/param
        } else {  // We are in dev and we don't force prod urls.
            // Organization
            String organizationParam = organization != null ? "?organization="+organization.getUrlFragment() : "";
            result = "http://" + DEV_ABSOLUTE_DOMAIN_NAME +  ContextUtil.getRealContextPath() + "/";  // The "/" is crucial at the end of the project name. Without it, you loose the session.
            // i.e.:  http://    localhost:8080           +  /JavaBlackBelt                   +  /

            result +=  organizationParam       + path; 
            //         ?organization=myCompany + #MyPage/param

        }
        return result;
    }

    /** root url of the organization.
     * In prod: "http://myCompany.KnowldgeBlackBelt.com
     * In dev:  "http://localhot:8080/KnowledgeBlackBelt/?organization=myCompany" */
    public static String getAbsoluteUrl(Organization organization){
        return getAbsoluteUrl("", false, organization, true);
    }
    
    public static String getRelativeUrl(String path){
        return  ContextUtil.getRealContextPath() + path;
    }
    
    //return a valid url, ex  http://test for image.png    to    http://test%20for%20image.png
    public static String getUrlWithNoBlank(String url){
    	try {
			String validUrl = new URI(null, null, url).toString();
			return validUrl.substring(1);// remove the '#' before the string (that is automatically added by URI
		} catch (URISyntaxException e) {
			throw new RuntimeException("invalid URL : "+e);
		}
    }
    
}
