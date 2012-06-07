package blackbelt.impl;

import static org.junit.Assert.*;

import org.apache.commons.collections.map.StaticBucketMap;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import reformyourcountry.service.UserService;


import blackbelt.model.User;

public class UserServiceTest {

	private UserService userImp;

	
	@Before
	public void setUp() throws Exception {
		userImp=new UserService();
	}

	@After
	public void tearDown() throws Exception {
		userImp=null;
	}

	@Test
	public void testRegisterUserBooleanStringStringGenderStringStringString() {
		//fail("Not yet implemented");
		
		assertNotNull("Object is created",userImp);
	}

}
