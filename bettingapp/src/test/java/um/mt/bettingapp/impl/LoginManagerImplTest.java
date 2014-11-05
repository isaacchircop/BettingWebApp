package um.mt.bettingapp.impl;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import um.mt.bettingapp.facade.LoginManager;
import um.mt.bettingapp.pojos.UserAccount;

public class LoginManagerImplTest {

	LoginManager manager;
	
	@Before
	public void setup(){
		manager = new LoginManagerImpl();
	}
	
	@After
	public void teardown(){
		manager = null;
	}
	
	@Test
	public void isAccountExistent_TestNonExistentAccount(){
		assertFalse("Account should not exist", manager.isAccountExistent(null));
	}
	
	@Test
	public void isAccountExistent_TestExistentAccount(){
		UserAccount account = mock(UserAccount.class);
		assertTrue("Account should exist", manager.isAccountExistent(account));
	}
	
	@Test
	public void isAccountAccessible_TestInvalidAccount(){
		assertFalse("Account does not exist", manager.isAccountAccessible(null));
	}
	
	@Test
	public void isAccountAccessible_TestBlockedAccount(){
		UserAccount account = mock(UserAccount.class);
		when(account.isBlocked()).thenReturn(true);
		when(account.unblkTimeLeft()).thenReturn((long)1000);
		
		assertFalse("Blocked account cannot be accessed", manager.isAccountAccessible(account));
	}
	
	@Test
	public void isAccountAccessible_TestUnblockedAccount(){
		UserAccount account = mock(UserAccount.class);
		when(account.isBlocked()).thenReturn(false);
		
		assertTrue("Unblocked account should be accessed", manager.isAccountAccessible(account));
	}
	
	@Test
	public void isAccountAccessible_TestExpiredBlockedTime(){
		UserAccount account = mock(UserAccount.class);
		when(account.isBlocked()).thenReturn(true);
		when(account.unblkTimeLeft()).thenReturn((long)300000);
		
		assertTrue("Account should be unblocked and accessed", manager.isAccountAccessible(account));
		verify(account).unblock();
	}
	
	@Test
	public void isPasswordCorrect_TestNonExistingAccount(){
		assertFalse("Account does not exist", manager.isPasswordCorrect(null, ""));
	}
	
	@Test
	public void isPasswordCorrect_TestIncorrectPassword(){
		UserAccount account = mock(UserAccount.class);
		when(account.getPassword()).thenReturn("Password123");
		
		assertFalse("Passwords do not match", manager.isPasswordCorrect(account, ""));
	}
	
	@Test
	public void isPasswordCorrect_TestCorrectPassword(){
		UserAccount account = mock(UserAccount.class);
		when(account.getPassword()).thenReturn("Password123");
		
		assertTrue("Passwords should match", manager.isPasswordCorrect(account, "Password123"));
	}
	
	@Test
	public void isPasswordCorrect_TestLoginTriesIncrementation(){
		UserAccount account = mock(UserAccount.class);
		when(account.getPassword()).thenReturn("Password123");
		
		manager.isPasswordCorrect(account, "");
		verify(account).incrLoginTries();
	}
	
	@Test
	public void isPasswordCorrect_TestBlockAccount(){
		UserAccount account = mock(UserAccount.class);
		when(account.getPassword()).thenReturn("Password123");
		when(account.getLoginTries()).thenReturn(4);
		
		manager.isPasswordCorrect(account, "");
		verify(account, times(1)).block();
	}
	
	@Test
	public void login_TestInvalidCredentials() {
		assertFalse(manager.login(null, ""));
	}
	
	@Test
	public void login_TestCorrectCredentials(){
		UserAccount account = mock(UserAccount.class);
		when(account.getPassword()).thenReturn("Password123");
		when(account.isBlocked()).thenReturn(false);
		
		assertTrue(manager.login(account, "Password123"));
	}
	
}
