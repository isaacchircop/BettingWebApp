package um.edu.mt.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import um.edu.mt.bd.Bet;
import um.edu.mt.bd.BetValidator;
import um.edu.mt.bd.LoginManager;
import um.edu.mt.bd.RiskLevel;
import um.edu.mt.bd.UserAccount;
import um.edu.mt.bd.UserManager;
import um.edu.mt.bd.UserValidator;

public class UserManagerImpl implements UserManager {
	
	private UserValidator userValidator;
	private LoginManager loginManager;
	private BetValidator betValidator;
	
	private UserAccount loggedInUser;
	private static Map<String, UserAccount> users = new HashMap<String, UserAccount>();
	private static List<BetImpl> bets = new ArrayList<BetImpl>();
	
	// Setters
	public void setUserValidator(UserValidator validator) {
		this.userValidator = validator;
	}
	
	public void setLoginManager(LoginManager manager) {
		this.loginManager = manager;
	}
	
	public void setBetValidator(BetValidator validator) {
		this.betValidator = validator;
	}
	
	public void setLoggedinUser(UserAccount useraccount) {
		this.loggedInUser = useraccount;
	}
	
	// Getters
	
	public UserAccount getUserAccount(String username) {
		return users.get(username);
	}
	
	public int getNumberOfUsers() {
		return users.size();
	}
	
	public List<Bet> getBetsForUser(String username) {
		List<Bet> queriedBets = new ArrayList<Bet>();
		
		for (Bet bet : bets) {
			if (bet.getUsername().equals(username)) {
				queriedBets.add(bet);
			}
		}
		
		return queriedBets;
	}
	
	// Class Operations
	public boolean registerUser(String username, String password,
			String name, String surname, Calendar date, boolean isPremium,
			String ccNumber, Calendar ccExpiry, String cvv) {
		
		UserAccount newAccount = new UserAccountImpl(username, password, name, surname, date, 
												 isPremium, ccNumber, ccExpiry, cvv);
		
		if (userValidator.validateAccount(newAccount)) {
			users.put(username, newAccount);
			return true;
		} else {
			return false;
		}
	}
	
	public boolean login(String username, String password) {
		
		UserAccount account = users.get(username);
		
		boolean loginAttempt = loginManager.login(account, password);
		
		this.loggedInUser = loginAttempt ? account : null;		
		return loginAttempt;
		
	}
	
	public boolean placeBet(String username, RiskLevel riskLevel, double amount) {
		
		if (betValidator.validateBet(users.get(username), riskLevel, amount)) {
			bets.add(new BetImpl(username, riskLevel, amount));
			return true;
		} else return false;
		
	}
	
	public boolean logout() {
		if (loggedInUser != null) {
			return true;
		} else {
			throw new IllegalStateException("Cannot logout from a non-existing user session");
		}
	}

}
