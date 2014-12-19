package um.edu.mt.impl;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Calendar;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import um.edu.mt.bd.UserAccount;
import um.edu.mt.bd.UserManager;
import um.edu.mt.bd.UserValidator;

public class UserValidatorImplTest {

	UserValidator validator;
	
	@Before
	public void setup(){
		validator = new UserValidatorImpl();
	};
	
	@After
	public void teardown(){
		validator = null;
	};
	
	@Test
	public void errormsg_empty() {
		assertTrue(validator.getErrorMessage().equals(""));
	}
	
	@Test
	public void validUsernameTest() {
		UserManager userManager = mock(UserManager.class);
		when(userManager.getUserAccount(anyString())).thenReturn(null);
		
		validator.setUserManager(userManager);
		
		String userName = "Joe123";
		assertTrue(validator.isUserNameValid(userName));
	}
	
	@Test
	public void invalidUsernameTest() {
		UserAccount account = mock(UserAccount.class);
		UserManager userManager = mock(UserManager.class);
		when(userManager.getUserAccount(anyString())).thenReturn(account);
		
		validator.setUserManager(userManager);
		
		String userName = "Joe123";
		assertFalse(validator.isUserNameValid(userName));
	}
	
	@Test
	public void validPasswordTest() {
		String password = "Password123";
		assertTrue(validator.isPasswordValid(password));
	}
	
	@Test
	public void invalidPasswordTest() {
		String password = "Pass123";
		assertFalse(validator.isPasswordValid(password));
	}
	
	@Test
	public void validNameSurnameTest_withWS() {
		String name = "Mr Bean";
		assertTrue(validator.isNameSurnameValid(name));
	}
	
	@Test
	public void validNameSurnameTest_AlphaOnly() {
		String name = "Joseph";
		assertTrue(validator.isNameSurnameValid(name));
	}
	
	@Test
	public void invalidNameSurnameTest_EmptyString() {
		String name = "";
		assertFalse(validator.isNameSurnameValid(name));
	}
	
	@Test
	public void invalidNameSurnameTest_ContainsNumeric() {
		String name = "Joseph1";
		assertFalse(validator.isNameSurnameValid(name));
	}
	
	@Test
	public void invalidNameSurnameTest_ContainsSymbol() {
		String name = "Mr. Bean";
		assertFalse(validator.isNameSurnameValid(name));
	}
	
	@Test
	public void validDOB() {
		//January is month 0
		Calendar dob = Calendar.getInstance();
		dob.set(1994, 11, 14);
		assertTrue(validator.isDOBValid(dob));
	}
	
	@Test
	public void invalidDOBSameYearUpcomingMonth() {
		Calendar dob = Calendar.getInstance();
		dob.set(1996, dob.get(Calendar.MONTH)+1, 01);
		assertFalse(validator.isDOBValid(dob));
	}
	
	@Test
	public void invalidDOBSameYearSameMonthUpcomingDay() {
		Calendar dob = Calendar.getInstance();
		dob.set(1996, dob.get(Calendar.MONTH), dob.get(Calendar.DAY_OF_MONTH)+1);
		assertFalse(validator.isDOBValid(dob));
	}
	
	@Test
	public void validDOBSameYearSameMonth() {
		Calendar dob = Calendar.getInstance();
		dob.set(Calendar.YEAR, 1996);
		dob.set(Calendar.DAY_OF_MONTH, 1);
		assertTrue(validator.isDOBValid(dob));
	}
	
	@Test
	public void validDOBSameYearSameMonthSameDay() {
		Calendar dob = Calendar.getInstance();
		dob.set(Calendar.YEAR, 1996);
		assertTrue(validator.isDOBValid(dob));
	}
	
	@Test
	public void invalidDOB() {
		Calendar dob = Calendar.getInstance();
		dob.set(2000, 11, 14);
		assertFalse(validator.isDOBValid(dob));
	}
	
	@Test
	public void validCreditCardNumberTest_AmericanExpress() {
		String cardNumber = "346026622135281";
		assertTrue(validator.isCCNumberValid(cardNumber));
	}
	
	@Test
	public void validCreditCardNumberTest_Visa16Digit() {
		String cardNumber = "4929373633541769";
		assertTrue(validator.isCCNumberValid(cardNumber));
	}
	
	@Test
	public void validCreditCardNumberTest_Visa13Digit() {
		String cardNumber = "4261336955079";
		assertTrue(validator.isCCNumberValid(cardNumber));
	}
	
	@Test
	public void validCreditCardNumberTest_MasterCard() {
		String cardNumber = "5468610960992758";
		assertTrue(validator.isCCNumberValid(cardNumber));
	}
	
	@Test
	public void invalidCreditCardNumberTest_UnrecognisedCardType() {
		String cardNumber = "2468610960992758";
		assertFalse(validator.isCCNumberValid(cardNumber));
	}
	
	@Test
	public void invalidCreditCardNumberTest_InvalidAmericanExpressLength() {
		String cardNumber = "3460262135281";
		assertFalse(validator.isCCNumberValid(cardNumber));
	}
	
	@Test
	public void invalidCreditCardNumberTest_InvalidVisaLength() {
		String cardNumber = "468610960992758";
		assertFalse(validator.isCCNumberValid(cardNumber));
	}
	
	@Test
	public void invalidCreditCardNumberTest_InvalidMasterCardLength() {
		String cardNumber = "54686109609927";
		assertFalse(validator.isCCNumberValid(cardNumber));
	}
	
	@Test
	public void luhnPass() {
		String cardNumber = "5468610960992758";
		assertTrue(validator.isLuhnValid(cardNumber));
	}
	
	@Test
	public void luhnFail() {
		String cardNumber = "5468610960092758";
		assertFalse(validator.isLuhnValid(cardNumber));
	}
	
	@Test
	public void luhnFail_InvalidChecksum() {
		String cardNumber = "0000000000000000";
		assertFalse(validator.isLuhnValid(cardNumber));
	}
	
	@Test
	public void invalidCreditCardNumberTest_NotNumeric() {
		String cardNumber = "54686L0960092758";
		assertFalse(validator.isCCNumberValid(cardNumber));
	}
	
	@Test 
	public void validCreditCardExpiryTest() {
		Calendar expiry = Calendar.getInstance();
		expiry.set(2024, 11, 1);
		assertTrue(validator.isCCExpiryValid(expiry));
	}
	
	@Test
	public void invalidCreditCardExpiryTest() {
		Calendar expiry = Calendar.getInstance();
		expiry.set(2014, 9, 1);
		assertFalse(validator.isCCExpiryValid(expiry));
	}
	
	@Test
	public void validCVVTest() {
		String cvv = "159";
		assertTrue(validator.isCVVValid(cvv));
	}
	
	@Test
	public void invalidCVVTest_InvalidLength() {
		String cvv = "1597";
		assertFalse(validator.isCVVValid(cvv));
	}
	
	@Test
	public void invalidCVVTest_NotNumeric() {
		String cvv = "15G";
		assertFalse(validator.isCVVValid(cvv));
	}
	
	@Test
	public void validateAccont_TestValidAccountRegistration() {
		
		// Setup of values
		Calendar date = Calendar.getInstance();
		Calendar ccExpiry = Calendar.getInstance();
		date.set(1990, 5, 30);
		ccExpiry.set(2016, 12, 12);
		
		UserManager manager = mock(UserManager.class);
		when(manager.getUserAccount(anyString())).thenReturn(null);
		validator.setUserManager(manager);
		
		UserAccount account = new UserAccountImpl("Username", "Password123", "Name", "Surname", date, false, "346026622135281", ccExpiry, "123");
		assertTrue(validator.validateAccount(account));
	}
	
	@Test
	public void validateAccont_TestInvalidAccountRegistration() {
		// Setup of values
		Calendar date = Calendar.getInstance();
		Calendar ccExpiry = Calendar.getInstance();
		date.set(1990, 5, 30);
		ccExpiry.set(2016, 12, 12);
		
		UserManager manager = mock(UserManager.class);
		when(manager.getUserAccount(anyString())).thenReturn(any(UserAccount.class));
		validator.setUserManager(manager);
		
		UserAccount account = new UserAccountImpl("Username", "Pass123", "Name", "Surname", date, false, "346026622135281", ccExpiry, "123");
		assertFalse(validator.validateAccount(account));
	}
	
}
