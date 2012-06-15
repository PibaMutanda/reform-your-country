package test.security;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import reformyourcountry.model.User;

import blackbelt.security.Privilege;

public class PrivilegeTest {

  
    
    @Test
    public void testIsPrivilegeOfUser() {
        
        assertNotNull(Privilege.EDIT_CORPORATE_USERS_PRIVILEGES);
 
    }

}
