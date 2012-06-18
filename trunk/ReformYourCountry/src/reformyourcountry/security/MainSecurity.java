package reformyourcountry.security;

import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;



import blackbelt.security.Privilege;

import reformyourcountry.exceptions.InvalidPasswordException;
import reformyourcountry.exceptions.UserLockedException;
import reformyourcountry.exceptions.UserNotFoundException;
import reformyourcountry.exceptions.UserNotValidatedException;
import reformyourcountry.model.User;
import reformyourcountry.service.LoginService;
import reformyourcountry.service.LoginService.WaitDelayNotReachedException;



public class MainSecurity {

	
	public static void main(String[] args) {
	   
	     String identifier=null;
	     String password=null;
	     Set<User>list=listUser();
	     User userIdentify=new User();
	     boolean flag=true;
	     
	     if(list==null)
	        System.out.println("there isn't privilege list");
	     else{
	         do {
	             
	             System.out.println("Give your Last name please!\n");
	             identifier=consoleForm();
                for (User user : list) {
                    if(!identifier.equals(user.getLastName())){
                        System.out.println("Your last name isn't correct\n\n "+--NUMBEROFTRYID+"  you still attempt to log in\n\n");
                        flag=false;
                }
                    else
                        flag=true;
                }      
                             
            } while (!flag && NUMBEROFTRYID!=0);
	         
            if(flag){
                    do {
                        System.out.println("Give your Password please\n\n");
                        password=consoleForm();
                        for (User user : list) {
                            if(!password.equals(user.getPassword())){
                                System.out.println("Your Password isn't correct\n\n "+--NUMBEROFtRYPASS+" you still attempt to log in\n\n");
                                flag=false;
                            }
                            else{
                                flag=true;
                                
                                userIdentify.setLastName(user.getLastName());
                                userIdentify.setPassword(user.getPassword());
                                userIdentify.setPrivileges(user.getPrivileges());
                            }
                        }
                    } while (!flag && NUMBEROFtRYPASS !=0);
                    System.out.println("Name: "+userIdentify.getLastName()+"\n"+"Password: "+userIdentify.getPassword()+"\n"+"Privilege(s): "+userIdentify.getPrivileges());
                   /* try {
                        userIdentify=loginService.login(identifier, password, false);
                        System.out.println(userIdentify);
                    } catch (UserNotFoundException | InvalidPasswordException
                            | UserNotValidatedException | UserLockedException
                            | WaitDelayNotReachedException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }*/
            }
            else
                System.out.println("I am sorry, you can not connect");
	    
	    
	 
	 }
}

	
	
	
	
	public static String consoleForm(){
	     return   scanKeyBoard.next();
	}
	
	
	public static Set<User> listUser(){
	    
	    Set<User> list=new HashSet<User>();
	    Set<Privilege>privilegeP=new HashSet<Privilege>();
	    Set<Privilege>privilegeJ=new HashSet<Privilege>();
	    
	    User userP=new User();
	         userP.setLastName("Piba");
	         userP.setPassword("passe");
	              privilegeP.add(Privilege.CREATE_QUESTIONS);
	              privilegeP.add(Privilege.EDIT_COMMUNITY_USERS_PRIVILEGES);
	              privilegeP.add(Privilege.EDIT_INFLUENCE_OF_USERS);
	              
	              privilegeP.add(Privilege.GET_NOTIFIED_WHEN_USERS_REACH_MANY_POINTS);
	         userP.setPrivileges(privilegeP);
	         list.add(userP);
	         
	         
	  User userJ=new User();    
	       userJ.setLastName("Jerome");
	       userJ.setPassword("jerome");
	            privilegeJ.add(Privilege.EDIT_WIKIS);
	            privilegeJ.add(Privilege.EDIT_REF_MANUAL);
	            privilegeJ.add(Privilege.MANAGE_COACH_OFFERINGS);
	       userJ.setPrivileges(privilegeJ);
	       
	    return  list;
	}
	private static LoginService loginService=new LoginService();
    private static Scanner scanKeyBoard = new Scanner(System.in);
    static Integer NUMBEROFTRYID = 3;
    static Integer NUMBEROFtRYPASS =3;

}	
