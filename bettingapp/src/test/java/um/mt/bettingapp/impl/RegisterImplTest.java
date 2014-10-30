package um.mt.bettingapp.impl;

import java.util.Calendar;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import um.mt.bettingapp.facade.Register;

public class RegisterImplTest {

	Register reg;
	
	@Before
	public void setup(){
		reg = new RegisterImpl();
	};
	
	@After
	public void teardown(){
		reg = null;
	};
	
	@Test
	public void validUsernameTest() {
		
	}
	
	@Test
	public void invalidUsernameTest() {
		
	}
	
	@Test
	public void validPasswordTest() {
		reg.validatePassword("Password123");
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void invalidPasswordTest() {
		reg.validatePassword("Pass123");
	}
	
	@Test
	public void validNameSurnameTest_withWS() {
		reg.validateNameSurname("Mr Bean");
	}
	
	@Test
	public void validNameSurnameTest_AlphaOnly() {
		reg.validateNameSurname("Joseph");
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void invalidNameSurnameTest_ContainsNumeric() {
		reg.validateNameSurname("Joseph1");
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void invalidNameSurnameTest_ContainsSymbol() {
		reg.validateNameSurname("Mr. Bean");
	}
	
	@Test
	public void validDOB() {
		Calendar dob = Calendar.getInstance();
		dob.set(1994, 11, 14);
		reg.validateDOB(dob);
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void invalidDOB() {
		Calendar dob = Calendar.getInstance();
		dob.set(2000, 11, 14);
		reg.validateDOB(dob);
	}
	
	@Test
	public void validCreditCardNumberTest_AmericanExpress() {
		String cardNumber = "346026622135281";
		reg.validateCCNumber(cardNumber);
	}
	
	@Test
	public void validCreditCardNumberTest_Visa16Digit() {
		String cardNumber = "4929373633541769";
		reg.validateCCNumber(cardNumber);
	}
	
	@Test
	public void validCreditCardNumberTest_Visa13Digit() {
		String cardNumber = "4261336955079";
		reg.validateCCNumber(cardNumber);
	}
	
	@Test
	public void validCreditCardNumberTest_MasterCard() {
		String cardNumber = "5468610960992758";
		reg.validateCCNumber(cardNumber);
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void invalidCreditCardNumberTest_UnrecognisedCardType() {
		String cardNumber = "2468610960992758";
		reg.validateCCNumber(cardNumber);
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void invalidCreditCardNumberTest_InvalidAmericanExpressLength() {
		String cardNumber = "3460262135281";
		reg.validateCCNumber(cardNumber);
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void invalidCreditCardNumberTest_InvalidVisaLength() {
		String cardNumber = "468610960992758";
		reg.validateCCNumber(cardNumber);
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void invalidCreditCardNumberTest_InvalidMasterCardLength() {
		String cardNumber = "54686109609927";
		reg.validateCCNumber(cardNumber);
	}
	
	@Test
	public void luhnPass() {
		String cardNumber = "5468610960992758";
		reg.isLuhnValid(cardNumber);
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void luhnFail() {
		String cardNumber = "5468610960092758";
		reg.isLuhnValid(cardNumber);
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void invalidCreditCardNumberTest_NotNumeric() {
		String cardNumber = "54686L0960092758";
		reg.validateCCNumber(cardNumber);
	}
	
	@Test 
	public void validCreditCardExpiryTest() {
		Calendar expiry = Calendar.getInstance();
		expiry.set(2024, 11, 1);
		reg.validateCCExpiry(expiry);
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void invalidCreditCardExpiryTest() {
		Calendar expiry = Calendar.getInstance();
		expiry.set(2014, 9, 1);
		reg.validateCCExpiry(expiry);
	}
	
	@Test
	public void validCVVTest() {
		String cvv = "159";
		reg.validateCVV(cvv);
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void invalidCVVTest_InvalidLength() {
		String cvv = "1597";
		reg.validateCVV(cvv);
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void invalidCVVTest_NotNumeric() {
		String cvv = "15G";
		reg.validateCVV(cvv);
	}
	
}
