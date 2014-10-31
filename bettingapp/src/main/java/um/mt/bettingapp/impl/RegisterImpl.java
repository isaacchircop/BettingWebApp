package um.mt.bettingapp.impl;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import um.mt.bettingapp.facade.Register;
import um.mt.bettingapp.pojos.UserAccount;

public class RegisterImpl implements Register {
	
	/**
	 * The List of registered users
	 */
	private Map<String, UserAccount> users = new HashMap<String, UserAccount>();
	
	/**
	 * Adds a new user to list on successful validation of user details
	 * 
	 * @return A boolean value stating whether the account was successfully created or not
	 */
	public boolean registerUser(String username, String password,
			String name, String surname, Calendar date, boolean isPremium,
			String ccNumber, Calendar ccExpiry, String cvv) throws IllegalArgumentException {
		
		validateUsername(username);
		validatePassword(password);
		validateNameSurname(name);
		validateNameSurname(surname);
		validateDOB(date);
		validateCCNumber(ccNumber);
		validateCCExpiry(ccExpiry);
		validateCVV(cvv);
		
		UserAccount previous = users.put(username, new UserAccount(username, password, name, surname, date, isPremium, ccNumber, ccExpiry, cvv));
		
		return previous == null;
		
	}
	
	public void validateUsername(String username) {
		if (users.get(username) != null) {
			throw new IllegalArgumentException("Username already exists");
		}
	}
	
	public void validatePassword(String password) {
		if (password.length() < 8) {
			throw new IllegalArgumentException("Password must be at least 8 characters long");
		}
	}
	
	public void validateNameSurname(String string) {
		if (string.length() == 0 || !string.matches("^[ A-z]+$")) {
			throw new IllegalArgumentException("Name and Surname must be non empty and contain alphabetical and whitespace characters");
		}
	}
	
	public void validateDOB(Calendar date) {
		Calendar today = Calendar.getInstance();
		
		if (today.get(Calendar.YEAR) - date.get(Calendar.YEAR) < 18) {
			throw new IllegalArgumentException("User must be at least 18 years of age");
		}
	}
	
	public void validateCCNumber(String ccNumber) {
		if (ccNumber.matches("^[0-9]+$")){
			
			if (ccNumber.startsWith("4")) {
				
				// Visa
				if (ccNumber.length() == 13 || ccNumber.length() == 16) {
					isLuhnValid(ccNumber);
				} else {
					throw new IllegalArgumentException("Credit Card Number is of invalid length");
				}
				
			} else {
				
				int firstTwo = Integer.parseInt(ccNumber.substring(0, 2));
				
				switch(firstTwo) {
				
				// American Express
				case 34:
				case 37:
					if (ccNumber.length() == 15) {
						isLuhnValid(ccNumber);
					} else {
						throw new IllegalArgumentException("Credit Card Number is of invalid length");
					}
					break;
					
				// MasterCard
				case 51:
				case 52:
				case 53:
				case 54:
				case 55:
					if (ccNumber.length() == 16) {
						isLuhnValid(ccNumber);
					} else {
						throw new IllegalArgumentException("Credit Card Number is of invalid length");
					}
					break;
					
				default:
					throw new IllegalArgumentException("Credit Card type not accepted");
				}
				
			}
			
		} else {
			throw new IllegalArgumentException("Credit Card Number must contain only numeric characters");
		}
	}
	
	public void isLuhnValid(String ccNumber) {
		
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
			throw new IllegalArgumentException("Credit Card Number does not match Luhn Algorithm rules");
		}
	}
	
	public void validateCCExpiry(Calendar ccExpiry) {
		Calendar today = Calendar.getInstance();
		
		if(today.after(ccExpiry)) {
			throw new IllegalArgumentException("Credit Card Expiry date must be in the future");
		}
	}
	
	public void validateCVV(String cvv) {
		if (cvv.length() != 3 || !cvv.matches("^[0-9]+$")) {
			throw new IllegalArgumentException("CVV must be a 3 digit numeric code");
		}
	}

}
