package um.mt.bettingapp.impl;

import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Calendar;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import um.mt.bettingapp.facade.UserManager;
import um.mt.bettingapp.facade.UserValidator;
import um.mt.bettingapp.pojos.UserAccount;

public class UserManagerImplTest {
	
	UserManager manager;

	@Before
	public void setup(){
		manager = new UserManagerImpl();
	}
	
	@After
	public void teardown(){
		manager = null;
	}
	
	@Test
	public void userRegistrationTest_Successful() {
		// Create mock user validator
		UserValidator validator = mock(UserValidator.class);
		when(validator.validateAccount(any(UserAccount.class))).thenReturn(true);
		manager.setUserValidator(validator);
		
		// Setup necessary parameter values
		Calendar date = Calendar.getInstance();
		Calendar ccExpiry = Calendar.getInstance();
		date.set(1990, 5, 30);
		ccExpiry.set(2016, 12, 12);
		
		// Start Test
		int usersBefore = manager.getNumberOfUsers();
		manager.registerUser("Username", "Password123", "Name", "Surname", date, false, "346026622135281", ccExpiry, "123");
		int usersAfter = manager.getNumberOfUsers();
		
		assertTrue("Size of mapping should be incremented by 1", usersAfter == usersBefore + 1);
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void userRegistrationTest_Unsuccessful() {
		// Create mock user validator
		UserValidator validator = mock(UserValidator.class);
		when(validator.validateAccount(any(UserAccount.class))).thenReturn(false);
		manager.setUserValidator(validator);
		
		// Setup necessary parameter values
		Calendar date = Calendar.getInstance();
		Calendar ccExpiry = Calendar.getInstance();
		date.set(1990, 5, 30);
		ccExpiry.set(2016, 12, 12);
		
		// Start Test
		manager.registerUser("Username", "Password123", "Name", "Surname", date, false, "346026622135281", ccExpiry, "123");
	}
	
}
