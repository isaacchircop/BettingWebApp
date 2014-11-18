package um.edu.mt.impl;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import um.edu.mt.bd.Bet;
import um.edu.mt.bd.BetValidator;
import um.edu.mt.bd.RiskLevel;
import um.edu.mt.bd.UserAccount;
import um.edu.mt.bd.UserManager;

public class BetValidatorImplTest {

	private BetValidator validator;
	
	@Before
	public void setup() {
		validator = new BetValidatorImpl();
	}
	
	@After
	public void teardown() {
		validator = null;
	}
	
	@Test
	public void validateAmount_TestValidAmountForFree() {
		UserAccount account = mock(UserAccount.class);
		when(account.isPremium()).thenReturn(false);
		
		assertTrue(validator.validateAmount(account, 4.50));
	}
	
	@Test
	public void validateAmount_TestInvalidAmountForFreeUser_GreaterThanLimit() {
		UserAccount account = mock(UserAccount.class);
		when(account.isPremium()).thenReturn(false);
		
		assertFalse(validator.validateAmount(account, 6));
	}
	
	@Test
	public void validateAmount_TestInvalidAmountForFreeUser_AmountEquals0() {
		UserAccount account = mock(UserAccount.class);
		when(account.isPremium()).thenReturn(false);
		
		assertFalse(validator.validateAmount(account, 0));
	}
	
	@Test
	public void validateAmount_TestValidAmountForPremium() {
		UserAccount account = mock(UserAccount.class);
		when(account.isPremium()).thenReturn(true);
		
		assertTrue(validator.validateAmount(account, 700));
	}
	
	@Test
	public void validateAmount_TestInvalidAmountForPremium() {
		UserAccount account = mock(UserAccount.class);
		when(account.isPremium()).thenReturn(true);
		
		assertFalse(validator.validateAmount(account, 0));
	}
	
	@Test
	public void validateRisk_TestValidRiskForFree() {
		UserAccount account = mock(UserAccount.class);
		when(account.isPremium()).thenReturn(false);
		
		assertTrue(validator.validateRisk(account, RiskLevel.LOW));
	}
	
	@Test
	public void validateRisk_TestInvalidRiskForFree() {
		UserAccount account = mock(UserAccount.class);
		when(account.isPremium()).thenReturn(false);
		
		assertFalse(validator.validateRisk(account, RiskLevel.HIGH));
	}
	
	@Test
	public void validateRisk_TestNullRiskForFree() {
		UserAccount account = mock(UserAccount.class);
		when(account.isPremium()).thenReturn(false);
		
		assertFalse(validator.validateRisk(account, null));
	}
	
	@Test
	public void validateRisk_TestValidRiskForPremium() {
		UserAccount account = mock(UserAccount.class);
		when(account.isPremium()).thenReturn(true);
		
		assertTrue(validator.validateRisk(account, RiskLevel.HIGH));
	}
	
	@Test
	public void validateRisk_TestNullRiskForPremium() {
		UserAccount account = mock(UserAccount.class);
		when(account.isPremium()).thenReturn(true);
		
		assertFalse(validator.validateRisk(account, null));
	}
	
	@Test
	public void validateNumberOfRisks_TestBetsBelowLimit() {
		UserManager userManager = mock(UserManager.class);
		when(userManager.getBetsForUser(anyString())).thenReturn(new ArrayList<Bet>());
		validator.setUserManager(userManager);
		
		UserAccount account = mock(UserAccount.class);
		
		assertTrue(validator.validateNumberOfBets(account));
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void validateNumberOfRisks_TestBetsExceedingLimit() {
		
		@SuppressWarnings("rawtypes")
		ArrayList bets = mock(ArrayList.class);
		when(bets.size()).thenReturn(3);
		
		UserManager userManager = mock(UserManager.class);
		when(userManager.getBetsForUser(anyString())).thenReturn(bets);
		validator.setUserManager(userManager);
		
		UserAccount account = mock(UserAccount.class);
		
		assertFalse(validator.validateNumberOfBets(account));
	}
	
	@Test
	public void validateCumulative_TestValidAmount() {
		List<Bet> bets = new ArrayList<Bet>();
		Bet bet = mock(Bet.class);
		when(bet.getAmount()).thenReturn(100.00);
		bets.add(bet);
		bets.add(bet);
		
		UserManager userManager = mock(UserManager.class);
		when(userManager.getBetsForUser(anyString())).thenReturn(bets);
		validator.setUserManager(userManager);
		
		UserAccount account = mock(UserAccount.class);
		
		assertTrue(validator.validateCumulative(account, 500));
	}
	
	@Test
	public void validateCumulative_TestInvalidAmount() {
		List<Bet> bets = new ArrayList<Bet>();
		Bet bet = mock(Bet.class);
		when(bet.getAmount()).thenReturn(2000.00);
		bets.add(bet);
		bets.add(bet);
		
		UserManager userManager = mock(UserManager.class);
		when(userManager.getBetsForUser(anyString())).thenReturn(bets);
		validator.setUserManager(userManager);
		
		UserAccount account = mock(UserAccount.class);
		
		assertFalse(validator.validateCumulative(account, 1500));
	}
	
	@Test
	public void validateBet_TestValidBetFreeUser() {
		UserManager manager = mock(UserManager.class);
		when(manager.getBetsForUser(anyString())).thenReturn(new ArrayList<Bet>());
		
		UserAccount account = mock(UserAccount.class);
		when(account.isPremium()).thenReturn(false);
		
		validator.setUserManager(manager);
		
		assertTrue(validator.validateBet(account, RiskLevel.LOW, 5));
	}
	
	@Test
	public void validateBet_TestInvalidRiskFreeUser() {
		UserManager manager = mock(UserManager.class);
		when(manager.getBetsForUser(anyString())).thenReturn(new ArrayList<Bet>());
		
		UserAccount account = mock(UserAccount.class);
		when(account.isPremium()).thenReturn(false);
		
		validator.setUserManager(manager);
		
		assertTrue(validator.validateBet(account, RiskLevel.HIGH, 5));
	}
	
	@Test
	public void validateBet_TestInvalidAmountFreeUser() {
		UserManager manager = mock(UserManager.class);
		when(manager.getBetsForUser(anyString())).thenReturn(new ArrayList<Bet>());
		
		UserAccount account = mock(UserAccount.class);
		when(account.isPremium()).thenReturn(false);
		
		validator.setUserManager(manager);
		
		assertTrue(validator.validateBet(account, RiskLevel.LOW, 10));
	}
	
	@Test
	public void validateBet_TestValidBetPremiumUser() {
		UserManager manager = mock(UserManager.class);
		when(manager.getBetsForUser(anyString())).thenReturn(new ArrayList<Bet>());
		
		UserAccount account = mock(UserAccount.class);
		when(account.isPremium()).thenReturn(true);
		
		validator.setUserManager(manager);
		
		assertTrue(validator.validateBet(account, RiskLevel.HIGH, 500));
	}
	
}
