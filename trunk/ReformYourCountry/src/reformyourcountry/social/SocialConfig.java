package reformyourcountry.social;

import javax.inject.Inject;

import org.apache.commons.dbcp.datasources.SharedPoolDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.security.crypto.encrypt.Encryptors;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionFactoryLocator;
import org.springframework.social.connect.ConnectionRepository;
import org.springframework.social.connect.UsersConnectionRepository;
import org.springframework.social.connect.jdbc.JdbcUsersConnectionRepository;
import org.springframework.social.connect.support.ConnectionFactoryRegistry;
import org.springframework.social.connect.web.ConnectController;
import org.springframework.social.connect.web.ProviderSignInController;
import org.springframework.social.facebook.api.Facebook;
import org.springframework.social.facebook.api.impl.FacebookTemplate;
import org.springframework.social.facebook.connect.FacebookConnectionFactory;
import org.springframework.social.google.api.Google;
import org.springframework.social.google.api.impl.GoogleTemplate;
import org.springframework.social.google.connect.GoogleConnectionFactory;
import org.springframework.social.linkedin.api.LinkedIn;
import org.springframework.social.linkedin.api.impl.LinkedInTemplate;
import org.springframework.social.linkedin.connect.LinkedInConnectionFactory;
import org.springframework.social.twitter.api.Twitter;
import org.springframework.social.twitter.api.impl.TwitterTemplate;
import org.springframework.social.twitter.connect.TwitterConnectionFactory;

import reformyourcountry.model.User;
import reformyourcountry.security.SecurityContext;
import reformyourcountry.util.CurrentEnvironment;
import reformyourcountry.web.UrlUtil;
import reformyourcountry.web.UrlUtil.Mode;

@Configuration
public class SocialConfig {
    
    @Inject  private SharedPoolDataSource dataSource;
    
    @Inject  private CurrentEnvironment environment;

    
    @Bean
    public ConnectionFactoryLocator connectionFactoryLocator() {
        ConnectionFactoryRegistry registry = new ConnectionFactoryRegistry();
        
        registry.addConnectionFactory(new FacebookConnectionFactory(
            environment.getFacebookClientId(),
            environment.getFacebookClientSecret()));
        
        
        registry.addConnectionFactory(new TwitterConnectionFactory(
                environment.getTweeterClientId(),
                environment.getTweeterClientSecret()));
        // add here new oauth provider such as twitter,linked in ...
           
        registry.addConnectionFactory(new GoogleConnectionFactory(
                environment.getGoogleClientId(),
                environment.getGoogleClientSecret()));
       
        return registry;
    }
    
    
    
    @Bean
    @Scope(value="request", proxyMode=ScopedProxyMode.INTERFACES)
    public ConnectionRepository connectionRepository(){
        User user = SecurityContext.getUser();
        
        if (user == null) {
            throw new IllegalStateException("Unable to get a ConnectionRepository: no user signed in");
        }
        return usersConnectionRepository().createConnectionRepository(user.getId()+"");
    }
    
    /**
     * Singleton data access object providing access to connections across all users.
     */
    @Bean
    public UsersConnectionRepository usersConnectionRepository() {
        JdbcUsersConnectionRepository repository = new JdbcUsersConnectionRepository(dataSource,
                connectionFactoryLocator(), Encryptors.noOpText());
        
       repository.setConnectionSignUp(new SimpleConnectionSignUp());
        return repository;
    }
    
    /** Instantiate and configure the controller of SpringSocial that will handle the login with facebook. */
    @Bean
    public ProviderSignInController providerSignInController() {
        ProviderSignInController controller = new ProviderSignInController(connectionFactoryLocator(), 
            usersConnectionRepository(), new RycSignInAdapter());
        
     
               
        controller.setSignUpUrl("/confirmaccount");  // Used by Spring Social to enable the user to confirm it's RYC user creation.
        controller.setSignInUrl("/login");
        return controller;
        
        // !!!!!!!!!!!!! Initialization is finalized in SpringSocialInitFinalizerServletContextListener
    }



    @Bean
    public ConnectController connectController() {
        ConnectController controller = new ConnectController(connectionFactoryLocator(), 
                connectionRepository());
        String url = UrlUtil.getAbsoluteUrl("",Mode.DEV);
        String prepareurl = url.substring(0,url.lastIndexOf("/"));
        controller.setApplicationUrl(prepareurl); 
       
        
        return controller;
    }
    
    @Bean
    @Scope(value="request", proxyMode=ScopedProxyMode.INTERFACES)   
    public Facebook facebook() {
        Connection<Facebook> facebook = connectionRepository().findPrimaryConnection(Facebook.class);
        return facebook != null ? facebook.getApi() : new FacebookTemplate();
    }
    
    @Bean
    @Scope(value="request", proxyMode=ScopedProxyMode.INTERFACES)   
    public Twitter tweeter() {
        Connection<Twitter> twitter = connectionRepository().findPrimaryConnection(Twitter.class);
        return twitter != null ? twitter.getApi() : new TwitterTemplate();
    }
    
    @Bean
    @Scope(value="request", proxyMode=ScopedProxyMode.INTERFACES)   
    public Google google() {
        Connection<Google> google = connectionRepository().findPrimaryConnection(Google.class);
        return google != null ? google.getApi() : new GoogleTemplate();
    }
   
    
}