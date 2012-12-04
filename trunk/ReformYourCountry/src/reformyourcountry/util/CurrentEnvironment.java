package reformyourcountry.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;


import reformyourcountry.web.UrlUtil;


/**
 * Spring Bean that wraps the current environment
 */
@Component
public class CurrentEnvironment {

	@Value("${app.environment}") // comes from the secret.properties file. Value is modified by ant at build time
	private Environment environment;
	@Value("${facebook.clientId}") //comes from secret.properties files.used to identify the app on facebook 
	private String facebookClientId;
	@Value("${facebook.clientSecret}") // needed by facebook when user token requested
	private String facebookClientSecret;
	@Value("${tweeter.clientId}")
	private String tweeterClientId;
	@Value("${tweeter.clientSecret}")
	private String tweeterClientSecret;
	@Value("${google.clientId}")
	private String googleClientId;
	@Value("${google.clientSecret}")
	private String googleClientSecret;
    @Value("${site.name}")
    private String siteName;
    @Value("${site.address}")
    private String siteAddress;
    @Value("${version}")
    private String version;
	@Value("${genFolderOnProd}")
	private String genFolderOnProd;
	
	
	public String getGoogleClientId() {
        return googleClientId;
    }

    public String getGoogleClientSecret() {
        return googleClientSecret;
    }

    public String getTweeterClientId() {
        return tweeterClientId;
    }

    public String getTweeterClientSecret() {
        return tweeterClientSecret;
    }

    public Environment getEnvironment() {
		return environment;
	}

	public enum MailBehavior {
		NOT_STARTED, // The mail thread is not started 
		STARTED_NOT_SENT, // The mail thread is started, the mails are consumed but not sent (logged only). Used for debug/test purpose
		SENT; // Mails are sent. Production Behaviour.
	}
	
	/**
	 * Describes the different deployment environments Injected by spring in
	 * ContextUtil, the value used is based on an Ant property in the build
	 * script
	 */
	public enum Environment {

		DEV(MailBehavior.NOT_STARTED, true),
		PROD(MailBehavior.SENT, true);

		// Flag that tells wether the mail demon thread must be started
		private MailBehavior mailBehavior;

		// Flag that tells wether the social things must be posted
		private boolean socialNetworkToBeConnected;

		private Environment(MailBehavior mailBehavior,
				boolean socialNetworkToBeConnected) {
			this.mailBehavior = mailBehavior;
			this.socialNetworkToBeConnected = socialNetworkToBeConnected;

		}

		public String getDomainName() {  // Cannot pre-load the value because it's not ready at class loading time.
		    if (this == PROD) {
		        return UrlUtil.getProdAbsoluteDomainName();
		    } else if (this == DEV) {
		        return "127.0.0.1";
		    } else {
		        throw new IllegalStateException("Bug: unexpected enum value");
		    }
		}

		public MailBehavior getMailBehavior() {
			return mailBehavior;
		}

		public boolean isSocialNetworkToBeConnected() {
			return socialNetworkToBeConnected;
		}

	}

    public String getFacebookClientId() {
        return facebookClientId;
    }

    public String getFacebookClientSecret() {
        return facebookClientSecret;
    }

    public String getSiteName() {
        return siteName;
    }


    public String getSiteAddress() {
        return siteAddress;
    }
    public String getVersion() {
        return version;
    }

   public String getGenFolderOnProd() {
		return genFolderOnProd;
	}
   


	
	
	
}
