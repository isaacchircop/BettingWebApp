package um.edu.mt.impl;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
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
	
	public static Map<String, UserAccount> users = new HashMap<String, UserAccount>();
	public static List<BetImpl> bets = new ArrayList<BetImpl>();
	
	public static String betsfile;
	public static String usersfile;
	
	public UserManagerImpl(String betsf, String usersf){
		
		betsfile = betsf;
		usersfile = usersf;
		
		try
		{
		ObjectInputStream inpbets = new ObjectInputStream(new FileInputStream(betsfile));
		bets = (List<BetImpl>)inpbets.readObject();
		inpbets.close();
		
		ObjectInputStream inpusrs = new ObjectInputStream(new FileInputStream(usersfile));
		users = (Map<String, UserAccount>)inpusrs.readObject();
		inpusrs.close();
		}
		catch(Exception E){}
		
	}
	
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
			saveUsersToFile(usersfile);
			return true;
		} else {
			return false;
		}
	}
	
	public boolean login(String username, String password) {
		UserAccount account = users.get(username);
		if(account!=null)
		{
			boolean loginAttempt = loginManager.login(account, password);
			account.setLoggedIn(loginAttempt);
			return loginAttempt;
		}
		else return false;
	}
	
	public boolean placeBet(String username, RiskLevel riskLevel, double amount) {
		
		if (betValidator.validateBet(users.get(username), riskLevel, amount)) {
			BetImpl new_bet = new BetImpl(username, riskLevel, amount);
			bets.add(new_bet);
			saveBetsToFile(betsfile);
			return true;
		} else return false;
		
	}
	
	public void logout(String username) {
		UserAccount account = users.get(username);
		account.setLoggedIn(false);
	}
	
	public void saveBetsToFile(String filename) 
	{
		try
		{
		ObjectOutputStream oout = new ObjectOutputStream(new FileOutputStream(filename));
		oout.writeObject(bets);
		oout.close();
		}
		catch(Exception E){}
	}
	
	public void saveUsersToFile(String filename) 
	{
		try
		{
		ObjectOutputStream oout = new ObjectOutputStream(new FileOutputStream(filename));
		oout.writeObject(users);
		oout.close();
		}
		catch(Exception E){}
	}

}
