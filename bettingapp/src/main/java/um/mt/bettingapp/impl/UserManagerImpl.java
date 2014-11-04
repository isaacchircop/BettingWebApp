package um.mt.bettingapp.impl;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import um.mt.bettingapp.facade.LoginManager;
import um.mt.bettingapp.facade.UserManager;
import um.mt.bettingapp.facade.UserValidator;
import um.mt.bettingapp.pojos.UserAccount;

public class UserManagerImpl implements UserManager {
	
	private UserValidator validator;
	private LoginManager loginManager;
	private static Map<String, UserAccount> users = new HashMap<String, UserAccount>();
	
	// Setters
	public void setUserValidator(UserValidator validator) {
		this.validator = validator;
	}
	
	public void setLoginManager(LoginManager manager) {
		this.loginManager = manager;
	}
	
	// Class Operations
	public void registerUser(String username, String password,
			String name, String surname, Calendar date, boolean isPremium,
			String ccNumber, Calendar ccExpiry, String cvv) throws IllegalArgumentException {
		
		UserAccount newAccount = new UserAccount(username, password, name, surname, date, 
												 isPremium, ccNumber, ccExpiry, cvv);
		
		if (validator.validateAccount(newAccount)) {
			users.put(username, newAccount);
		} else {
			throw new IllegalArgumentException(validator.getErrorMessage());
		}
	}
	
	public boolean login(String username, String password) {
		
		UserAccount account = users.get(username);
		return loginManager.login(account, password);
		
	}
	
//	public void placeBet();
	
//	public void logout();
	
	public UserAccount getUserAccount(String username) {
		return users.get(username);
	}
	
	public int getNumberOfUsers() {
		return users.size();
	}
	
}
