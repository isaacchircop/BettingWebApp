package um.edu.mt.impl;

import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.anyDouble;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Calendar;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import um.edu.mt.bd.BetValidator;
import um.edu.mt.bd.LoginManager;
import um.edu.mt.bd.RiskLevel;
import um.edu.mt.bd.UserAccount;
import um.edu.mt.bd.UserManager;
import um.edu.mt.bd.UserValidator;
import um.edu.mt.impl.UserManagerImpl;

public class UserManagerImplTest {

	UserManager manager;

	@Before
	public void setup() {
		manager = new UserManagerImpl();
	}

	@After
	public void teardown() {
		manager = null;
	}

	@Test
	public void userRegistrationTest_Successful() {
		// Create mock user validator
		UserValidator validator = mock(UserValidator.class);
		when(validator.validateAccount(any(UserAccount.class)))
				.thenReturn(true);
		manager.setUserValidator(validator);

		// Setup necessary parameter values
		Calendar date = Calendar.getInstance();
		Calendar ccExpiry = Calendar.getInstance();
		date.set(1990, 5, 30);
		ccExpiry.set(2016, 12, 12);

		// Start Test
		int usersBefore = manager.getNumberOfUsers();
		manager.registerUser("Username", "Password123", "Name", "Surname",
				date, false, "346026622135281", ccExpiry, "123");
		int usersAfter = manager.getNumberOfUsers();

		assertTrue("Size of mapping should be incremented by 1",
				usersAfter == usersBefore + 1);
	}

	@Test
	public void userRegistrationTest_Unsuccessful() {
		// Create mock user validator
		UserValidator validator = mock(UserValidator.class);
		when(validator.validateAccount(any(UserAccount.class))).thenReturn(
				false);
		manager.setUserValidator(validator);

		// Setup necessary parameter values
		Calendar date = Calendar.getInstance();
		Calendar ccExpiry = Calendar.getInstance();
		date.set(1990, 5, 30);
		ccExpiry.set(2016, 12, 12);

		// Start Test
		assertFalse(manager.registerUser("Username", "Password123", "Name",
				"Surname", date, false, "346026622135281", ccExpiry, "123"));
	}

	@Test
	public void LoginTest_Successful() {
		// Create mock login manager
		LoginManager loginmanager = mock(LoginManager.class);
		when(loginmanager.login(any(UserAccount.class), anyString()))
				.thenReturn(true);
		when(
				loginmanager.isPasswordCorrect(any(UserAccount.class),
						anyString())).thenReturn(true);
		manager.setLoginManager(loginmanager);

		assertTrue(manager.login("joseph", "1010101010101"));

	}

	@Test
	public void LoginTest_UnSuccessful() {
		// Create mock login manager
		LoginManager loginmanager = mock(LoginManager.class);
		when(loginmanager.login(any(UserAccount.class), anyString()))
				.thenReturn(false);
		when(
				loginmanager.isPasswordCorrect(any(UserAccount.class),
						anyString())).thenReturn(false);
		manager.setLoginManager(loginmanager);

		assertFalse(manager.login("joseph", "1010101010101"));
	}

	@Test
	public void LogOutTest_Successful() {
		// Setup of values
		Calendar date = Calendar.getInstance();
		Calendar ccExpiry = Calendar.getInstance();
		date.set(1990, 5, 30);
		ccExpiry.set(2016, 12, 12);

		UserAccount account = new UserAccountImpl("Username", "Password123",
				"Name", "Surname", date, false, "346026622135281", ccExpiry,
				"123");
		manager.setLoggedinUser(account);
		assertTrue(manager.logout());
	}

	@Test(expected = IllegalStateException.class)
	public void LogOutTest_UnSuccessful() {
		// Setup of values
		manager.logout();
	}

	@Test
	public void placeBetTest_Successful() {
		// Create mock user validator
		BetValidator validator = mock(BetValidator.class);
		when(validator.validateBet(any(UserAccount.class),any(RiskLevel.class), anyDouble())).thenReturn(true);
		manager.setBetValidator(validator);

		assertTrue(manager.placeBet("joseph", RiskLevel.LOW, 10.5));
	}
	
	@Test
	public void placeBetTest_UnSuccessful() {
		// Create mock user validator
		BetValidator validator = mock(BetValidator.class);
		when(validator.validateBet(any(UserAccount.class),any(RiskLevel.class), anyDouble())).thenReturn(false);
		manager.setBetValidator(validator);

		assertFalse(manager.placeBet("joseph", RiskLevel.LOW, 10.5));
	}

}
