package reformyourcountry.controller;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionFactory;
import org.springframework.social.connect.ConnectionFactoryLocator;
import org.springframework.social.connect.UsersConnectionRepository;
import org.springframework.social.connect.support.OAuth1ConnectionFactory;
import org.springframework.social.connect.support.OAuth2ConnectionFactory;
import org.springframework.social.connect.web.ConnectSupport;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import reformyourcountry.model.User;
import reformyourcountry.model.User.AccountStatus;
import reformyourcountry.repository.UserRepository;
import reformyourcountry.security.SecurityContext;
import reformyourcountry.service.LoginService;
import reformyourcountry.service.UserService;
import reformyourcountry.util.CurrentEnvironment;
import reformyourcountry.util.NotificationUtil;
import reformyourcountry.web.UrlUtil;

@Controller
public class SocialAccountManageController extends BaseController<User>{

    private ConnectSupport webSupport;

    @Autowired UserRepository userRepository;
    @Autowired LoginService loginService;
    @Autowired UserService userService;
    @Autowired ConnectionFactoryLocator connectionFactoryLocator;
    @Autowired UsersConnectionRepository usersConnectionRepository;

 
    /** Getter to lazy init the webSupport var and call UrlUtil as late as possible (else it's called before tomcat initialization, and our ServletcontextListeners initialize needed values). */
    private synchronized ConnectSupport getWebSupport() {
        if (webSupport == null) {
            this.webSupport = new ConnectSupport();
            
            /**
             * Configures the base secure URL for the application this controller is being used in e.g. <code>https://myapp.com</code>. Defaults to null.
             * If specified, will be used to generate OAuth callback URLs.
             * If not specified, OAuth callback URLs are generated from web request info.
             * You may wish to set this property if requests into your application flow through a proxy to your application server.
             * In this case, the request URI may contain a scheme, host, and/or port value that points to an internal server not appropriate for an external callback URL.
             * If you have this problem, you can set this property to the base external URL for your application and it will be used to construct the callback URL instead.
             * @param applicationUrl the application URL value
             */
            String url = UrlUtil.getAbsoluteUrl("");
            String prepareurl = url.substring(0,url.lastIndexOf("/"));
            // 1st part of the URL, where "/addconnection/facebook" will be appended by Spring Social. Then the full URL is given to facebook who will redirect there when facebook login done.
            webSupport.setApplicationUrl(prepareurl);

            this.webSupport.setUseAuthenticateUrl(true);
        }
        return webSupport;
    }
    


    @RequestMapping("/socialaccountmanage")
    public ModelAndView socialAccountManage(@RequestParam(value="id", required=true) long userId) {
        User user = getRequiredEntity(userId); 
        SecurityContext.assertCurrentUserMayEditThisUser(user);
        boolean fb = true;
        boolean tw =true;
        boolean go = true;
        boolean li = true;
        List<Connection<?>> connections = userService.getAllConnections(user);
        for(Connection<?> connection : connections){
            switch(connection.getKey().getProviderId()){
            case "facebook" : fb = false;break;
            case "twitter"  : tw = false;break;
            case "google"   : go = false;break;
            case "linkedin" : li = false;break;
            }
            
        }
    
        ModelAndView mv ;
        if(connections.isEmpty() && user.getAccountStatus().equals(AccountStatus.ACTIVE) && !user.isPasswordKnownByTheUser()) { // We are probably here if the user removed his last social connection but does not know it's password (=> could not login).
            mv = new ModelAndView("socialaccountconvert");  // Asks to provide a new password.
            return mv;

        } else {     
            mv = new ModelAndView("socialaccountmanage");
            mv.addObject("connections", connections);
            mv.addObject("user", user);
            mv.addObject("fb",fb);
            mv.addObject("tw",tw);
            mv.addObject("go",go);
            mv.addObject("li",li);
            return mv;
        }
    }


    @RequestMapping("/socialaccountdissociate")  
    public ModelAndView dissociate(@RequestParam(value="id", required=true) long userId,@RequestParam("providerUserId") String providerUserId, @RequestParam("providerId")String providerId , WebRequest request){
        User user = getRequiredEntity(userId); 
        SecurityContext.assertCurrentUserMayEditThisUser(user);

        List<Connection<?>> connections = userService.getAllConnections(user);

        if (connections.size()<=1) { // We try to remove the last social connection
            // => We need to convert this social RYC account into a normal RYC account => we need to ask for a password.
            return new ModelAndView("socialaccountconvert","id",userId);
        }
        
        // Normal case: we remove a social connection.
        // We know the providerId (param) and we look for the corresponding connnection.
        Connection<?> connection = null;
        for(Connection<?> con : connections){
            if(con.getKey().getProviderId().equals(providerId) && con.getKey().getProviderUserId().equals(providerUserId)){
                connection = con;                
            }
        }
        if (connection == null)  {  // not found => PROBLEM (user edits connections from 2 pages ?) 
            NotificationUtil.addNotificationMessage("Association avec "+ providerId + " NON effacée (problème de consistance).");
        }

        // We remove the connection from the DB now.
        userService.removeSocialConnection(user, connection);
  
        return new ModelAndView("redirect:socialaccountmanage");
    }

    
    /** If the user removes its last social connection, he needs a password for the remaining RYC user.
     * He will input such a password here, from socialaccountconvert.jsp. */
    @RequestMapping("/socialaccountdefinepasswordsubmit")
    public ModelAndView definepassword(@RequestParam("id") long id,
            @RequestParam("password") String password , 
            @RequestParam("confirmpassword") String confirmPassword ,WebRequest request){

        User user = getRequiredEntity(id);
        SecurityContext.assertCurrentUserMayEditThisUser(user);
        

        boolean hasError = false;
        ModelAndView mv = new ModelAndView("socialaccountconvert");
        mv.addObject("id",id);
        if (StringUtils.isBlank(password) || StringUtils.isBlank(confirmPassword)) {
            mv.addObject("errorEmpty", "Le champ password ne peut être vide");
            hasError = true;
        } else {  // Both passwords not null
            if(!password.equals(confirmPassword)){
                mv.addObject("errorDiff", "La confirmation du password et le nouveau password ne correspondent pas");
                hasError = true;
            }
        }
            
        if (hasError) {
            return mv;

        } else { // Ok, here we actually change the password, and account status (and remove any remaining social connection)
                       
            // Normally there is only one or no connection left, but ... who knows. Let's remove them all.
            userService.unsocialiseUser(user,confirmPassword);

            NotificationUtil.addNotificationMessage("Votre mot de passe a été définis avec succès,vous pouvez dorénavant l'utiliser pour vous connecter au site.");
            
            return new ModelAndView("redirect:login");
        }
    }

    /**
     * Process a sign-in form submission by commencing the process of establishing a connection to the provider on behalf of the user.
     * For OAuth1, fetches a new request token from the provider, temporarily stores it in the session, then redirects the user to the provider's site for authentication authorization.
     * For OAuth2, redirects the user to the provider's site for authentication authorization.
     */
    @RequestMapping(value="addconnection/{providerId}", method=RequestMethod.POST)
    public RedirectView addconnection(@PathVariable String providerId, NativeWebRequest request) {
        ConnectionFactory<?> connectionFactory = connectionFactoryLocator.getConnectionFactory(providerId);
        
        // unfortunately we cannot set the call back url manually to another path "/addconnection" than the method we are in.
        // Will put the token in the HttpSession.
        return new RedirectView(getWebSupport().buildOAuthUrl(connectionFactory, request));
    }
    

    @RequestMapping(value="addconnection/{providerId}", method=RequestMethod.GET)
    public ModelAndView oauthCallback(@PathVariable String providerId,  @RequestParam(value="code",required = false) String code,NativeWebRequest request) {
        Connection<?> connection = null;
        if(code==null){
            OAuth1ConnectionFactory<?> connectionFactory = (OAuth1ConnectionFactory<?>) connectionFactoryLocator.getConnectionFactory(providerId);
            // Will look for the token put in the session during the addconnection() method.
            connection = getWebSupport().completeConnection(connectionFactory, request);
        }else{
            OAuth2ConnectionFactory<?> connectionFactory = (OAuth2ConnectionFactory<?>) connectionFactoryLocator.getConnectionFactory(providerId);
            connection = getWebSupport().completeConnection(connectionFactory, request);
        }
        
        User user = SecurityContext.getUser();
               
        
       List<String> idsUsers =  usersConnectionRepository.findUserIdsWithConnection(connection);
        
        if(idsUsers.isEmpty()){
        // This will persist the connection (query for it, not find it, then persist it).
           usersConnectionRepository.createConnectionRepository(user.getId()+"").addConnection(connection);
           NotificationUtil.addNotificationMessage("Connection "+connection.getKey().getProviderId()+" ajoutée avec succès. Vous pouvez maintenant utiliser le bouton "+connection.getKey().getProviderId()+" pour vous connecter sur "+UrlUtil.getWebSiteName()+".");
        }else{
           User userfound =  userRepository.find(Long.parseLong(idsUsers.get(0)));
           NotificationUtil.addNotificationMessage("Un utilisateur( "+userfound.getUserName()+" ) à déjà utilisé ce compte "+ connection.getKey().getProviderId()+" pour s'enregeistrer sur "+UrlUtil.getWebSiteName() +".Merci d'utiliser ce compte.");
            
        }
     
       
        return new ModelAndView("redirect:/socialaccountmanage","id",user.getId());
    }

    
    
}
