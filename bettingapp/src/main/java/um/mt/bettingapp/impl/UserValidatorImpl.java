package um.mt.bettingapp.impl;

import java.util.Calendar;

import um.mt.bettingapp.facade.UserManager;
import um.mt.bettingapp.facade.UserValidator;
import um.mt.bettingapp.pojos.UserAccount;

public class UserValidatorImpl implements UserValidator {
	
	private UserManager userManager;
	private String errorMessage;
	
	public UserValidatorImpl() {
		this.errorMessage = "";
	}
	
	public void setUserManager(UserManager manager) {
		userManager = manager;
	}
	
	public String getErrorMessage() {
		return this.errorMessage;
	}
	
	public boolean validateAccount(UserAccount account) {
		
		boolean isAccountValid = isUserNameValid(account.getUsername()) &&
								isPasswordValid(account.getPassword()) &&
								isNameSurnameValid(account.getName()) &&
								isNameSurnameValid(account.getSurname()) &&
								isDOBValid(account.getDate()) &&
								isCCNumberValid(account.getCcNumber()) &&
								isCCExpiryValid(account.getCcExpiry()) &&
								isCVVValid(account.getCvv());
		
		if (isAccountValid) {
			this.errorMessage = "";
		}
		
		return isAccountValid;
		
	}

	public boolean isUserNameValid(String username) {
		if (userManager.getUserAccount(username) != null) {
			errorMessage = "Username already exists";
			return false;
		} else return true;
	}
	
	public boolean isPasswordValid(String password) {
		if (password.length() < 8) {
			errorMessage = "Password must be at least 8 characters long";
			return false;
		} else return true;
	}
	
	public boolean isNameSurnameValid(String string) {
		if (string.length() == 0 || !string.matches("^[ A-z]+$")) {
			errorMessage = "Name and Surname must be non empty and contain alphabetical and whitespace characters";
			return false;
		} else return true;
	}
	
	public boolean isDOBValid(Calendar date) {
		Calendar today = Calendar.getInstance();
		
		if (today.get(Calendar.YEAR) - date.get(Calendar.YEAR) < 18) {
			errorMessage = "User must be at least 18 years of age";
			return false;
		} else return true;
	}
	
	public boolean isCCNumberValid(String ccNumber) {
		if (ccNumber.matches("^[0-9]+$")){
			
			if (ccNumber.startsWith("4")) {
				
				// Visa
				if (ccNumber.length() == 13 || ccNumber.length() == 16) {
					return isLuhnValid(ccNumber);
				} else {
					errorMessage = "Credit Card Number is of invalid length";
					return false;
				}
				
			} else {
				
				int firstTwo = Integer.parseInt(ccNumber.substring(0, 2));
				
				switch(firstTwo) {
				
				// American Express
				case 34:
				case 37:
					if (ccNumber.length() == 15) {
						return isLuhnValid(ccNumber);
					} else {
						errorMessage = "Credit Card Number is of invalid length";
						return false;
					}
					
				// MasterCard
				case 51:
				case 52:
				case 53:
				case 54:
				case 55:
					if (ccNumber.length() == 16) {
						return isLuhnValid(ccNumber);
					} else {
						errorMessage = "Credit Card Number is of invalid length";
						return false;
					}
					
				default:
					errorMessage = "Credit Card type not accepted";
					return false;
				}
				
			}
			
		} else {
			errorMessage = "Credit Card Number must contain only numeric characters";
			return false;
		}
	}
	
	public boolean isLuhnValid(String ccNumber) {
		
		int checksum = 0;
		
		// Reverse String
		ccNumber = new StringBuilder(ccNumber).reverse().toString();
		
		for (int i = 0; i < ccNumber.length(); i++) {
			int digit = Character.getNumericValue(ccNumber.charAt(i));
			
			if ((i+1) % 2 == 0) {
				// Even Index
				digit = digit * 2;
				
				if (digit > 9) {
					digit -= 9;
				}
				
			}
			
			checksum += digit;
			
		}
		
		if (checksum == 0 || checksum % 10 != 0) {
			errorMessage = "Credit Card Number does not match Luhn Algorithm rules";
			return false;
		} else return true;
	}
	
	public boolean isCCExpiryValid(Calendar ccExpiry) {
		Calendar today = Calendar.getInstance();
		
		if(today.after(ccExpiry)) {
			errorMessage = "Credit Card Expiry date must be in the future";
			return false;
		} else return true;
	}
	
	public boolean isCVVValid(String cvv) {
		if (cvv.length() != 3 || !cvv.matches("^[0-9]+$")) {
			errorMessage = "CVV must be a 3 digit numeric code";
			return false;
		} else return true;
	}

	
}
