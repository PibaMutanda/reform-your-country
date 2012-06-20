package reformyourcountry.security;

import java.util.EnumSet;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;



import blackbelt.security.Privilege;
import blackbelt.security.SecurityContext;

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
	     //user can only try a certain time to log in. If he writes the wrong password or lastname 3 times, he can't go to the application.
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
                                 //we save the user, his privileges and id in the 3 threadlocal.                               
                                userIdentify.setLastName(user.getLastName());
                                userIdentify.setPassword(user.getPassword());
                                userIdentify.setPrivileges(user.getPrivileges());
                                userIdentify.setId(user.getId());
                                //TODO : replace the id with an id which is generated when the user log in.
                                //it's an id for test
                              
                                userIdentify.setId(user.getId());
                                SecurityContext.setUser(userIdentify);
                                SecurityContext.setUserId(userIdentify.getId());
                                SecurityContext.setThreadPrivilege(userIdentify.getPrivileges());
                            }
                        }
                    } while (!flag && NUMBEROFtRYPASS !=0);
                    System.out.println("ID: "+userIdentify.getId()+"\n"+"Name: "+userIdentify.getLastName()+"\n"+"Password: "+userIdentify.getPassword()+"\n"+"Privilege(s): "+userIdentify.getPrivileges());
                    try {
						System.out.println( SecurityContext.getUser());
						System.out.println( SecurityContext.getThreadPrivilege().get());
						System.out.println( SecurityContext.getUserId().toString());
					} catch (UserNotFoundException | InvalidPasswordException
							| UserNotValidatedException | UserLockedException
							| WaitDelayNotReachedException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
                   
                    
                   //TODO : uncomment when the recognition of id is implemented. 
                   /*  try {0
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
	         userP.setId(130l);
	              privilegeP.add(Privilege.CREATE_QUESTIONS);
	              privilegeP.add(Privilege.EDIT_COMMUNITY_USERS_PRIVILEGES);
	              privilegeP.add(Privilege.EDIT_INFLUENCE_OF_USERS);
	              
	              privilegeP.add(Privilege.GET_NOTIFIED_WHEN_USERS_REACH_MANY_POINTS);
	         userP.setPrivileges(privilegeP);
	         list.add(userP);
	         
	         
	  User userJ=new User();   
	       userJ.setId(163l);
	       userJ.setLastName("Jerome");
	       userJ.setPassword("jerome");
	            privilegeJ.add(Privilege.EDIT_WIKIS);
	            privilegeJ.add(Privilege.EDIT_REF_MANUAL);
	            privilegeJ.add(Privilege.MANAGE_COACH_OFFERINGS);
	       userJ.setPrivileges(privilegeJ);
	       list.add(userJ);
	       
	       
	    return  list;
	}
	private static LoginService loginService=new LoginService();
    private static Scanner scanKeyBoard = new Scanner(System.in);
    static Integer NUMBEROFTRYID = 3;
    static Integer NUMBEROFtRYPASS =3;

}	
