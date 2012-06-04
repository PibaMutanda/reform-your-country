package blackbelt.web;

//TODO maxime uncomment

//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.stereotype.Component;
//
//import blackbelt.paypal.PayPalProperties.PaypalEnvironment;

/**
 * Spring Bean that wraps the current environment
 */
// TODO maxime uncomment

// @Component
public class CurrentEnvironment {
	// TODO maxime uncomment

	// @Value("${app.environment}") // comes from the jbbconfig.properties file.
	// Value is modified by ant at build time
//	private Environment environment;
//
//	public Environment getEnvironment() {
//		return environment;
//	}

	public enum MailBehavior {
		NOT_STARTED, // The mail thread is not started
		STARTED_NOT_SENT, // The mail thread is started, the mails are consumed
							// but not sent (logged only). Used for debug/test
							// purpose
		SENT; // Mails are sent. Production Behaviour.
	}

	/**
	 * Describes the different deployment environments Injected by spring in
	 * ContextUtil, the value used is based on an Ant property in the build
	 * script
	 */
	// TODO maxime uncomment

	// public enum Environment {
	//
	// // XXX(Domain Name, Mail Deamon Started,Social NW Connected, Paypal Env.)
	// DEV("127.0.0.1", MailBehavior.NOT_STARTED, true, PaypalEnvironment.DEV),
	// TEST("logicblackbelt.com", MailBehavior.NOT_STARTED, false,
	// PaypalEnvironment.DEV),
	// PROD("knowledgeblackbelt.com", MailBehavior.SENT, true,
	// PaypalEnvironment.LIVE);
	//
	// // String containing the domain name of the environment (useful to
	// // create absolute path to images, ...)
	// private String domainName;
	//
	// // Flag that tells wether the mail demon thread must be started
	// private MailBehavior mailBehavior;
	//
	// // Flag that tells wether the social things must be posted
	// private boolean socialNetworkToBeConnected;
	//
	// // Which Paypal environment should we use
	// private PaypalEnvironment paypalEnvironment;
	//
	// private Environment(String domainName, MailBehavior mailBehavior,
	// boolean socialNetworkToBeConnected,
	// PaypalEnvironment paypalEnvironment) {
	// this.domainName = domainName;
	// this.mailBehavior = mailBehavior;
	// this.socialNetworkToBeConnected = socialNetworkToBeConnected;
	// this.paypalEnvironment = paypalEnvironment;
	// }
	//
	// public String getDomainName() {
	// return domainName;
	// }
	//
	// public MailBehavior getMailBehavior() {
	// return mailBehavior;
	// }
	//
	// public boolean isSocialNetworkToBeConnected() {
	// return socialNetworkToBeConnected;
	// }
	//
	// public PaypalEnvironment getPaypalEnvironment() {
	// return paypalEnvironment;
	// }
	//
	// }
}
