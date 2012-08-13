package reformyourcountry;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;


/**
 * Spring Bean that wraps the current environment
 */
@Component
public class CurrentEnvironment {

	@Value("${environment}") // comes from the config.properties file. Value is modified by ant at build time
	private Environment environment;
	
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

		// XXX(Domain Name, Mail Deamon Started,Social NW Connected, Paypal Env.)
		DEV("127.0.0.1", MailBehavior.NOT_STARTED, true),
		TEST("logicblackbelt.com", MailBehavior.NOT_STARTED, false),
		PROD("knowledgeblackbelt.com", MailBehavior.SENT, true);

		// String containing the domain name of the environment (useful to
		// create absolute path to images, ...)
		private String domainName;

		// Flag that tells wether the mail demon thread must be started
		private MailBehavior mailBehavior;

		// Flag that tells wether the social things must be posted
		private boolean socialNetworkToBeConnected;

	

		private Environment(String domainName, MailBehavior mailBehavior,
				boolean socialNetworkToBeConnected) {
			this.domainName = domainName;
			this.mailBehavior = mailBehavior;
			this.socialNetworkToBeConnected = socialNetworkToBeConnected;

		}

		public String getDomainName() {
			return domainName;
		}

		public MailBehavior getMailBehavior() {
			return mailBehavior;
		}

		public boolean isSocialNetworkToBeConnected() {
			return socialNetworkToBeConnected;
		}

	}
}
