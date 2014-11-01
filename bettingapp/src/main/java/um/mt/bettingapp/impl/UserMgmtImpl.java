package um.mt.bettingapp.impl;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import um.mt.bettingapp.facade.UserMgmt;
import um.mt.bettingapp.facade.UserValidator;
import um.mt.bettingapp.pojos.UserAccount;

public class UserMgmtImpl implements UserMgmt {
	
	private final long UNBLOCKTIME = 300000;
	
	private UserValidator validator;
	private static Map<String, UserAccount> users = new HashMap<String, UserAccount>();
	
	
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
		
		if (account == null) {
			throw new IllegalArgumentException("Account with specified username does not exist");
		}
		
		if (account.isBlocked()) {
			
			if (account.unblkTimeLeft() >= UNBLOCKTIME) {
				account.unblock();
			} else {
				throw new IllegalArgumentException("Account is blocked.  Try again within " + account.unblkTimeLeft() / 1000 + " seconds");
			}
		}
		
		if (!account.getPassword().equals(password)) {
			int tries = account.incrInvalidLogin();
			if (tries > 3) {
				account.block();
				throw new IllegalArgumentException("Account blocked.  Try again within 5 minutes");
			} else {
				throw new IllegalArgumentException("Invalid Password.  Number of tries left: " + (3 - tries));
			}
		}
		
		return true;
		
	}
	
//	public void placeBet();
	
//	public void logout();
	
	public UserAccount getUserAccount(String username) {
		return users.get(username);
	}
	
}
