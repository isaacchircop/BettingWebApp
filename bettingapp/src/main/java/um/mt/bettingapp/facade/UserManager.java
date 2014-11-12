package um.mt.bettingapp.facade;

import java.util.Calendar;
import java.util.List;

import um.mt.bettingapp.pojos.Bet;
import um.mt.bettingapp.pojos.RiskLevel;
import um.mt.bettingapp.pojos.UserAccount;

public interface UserManager {

	// Getters
	public int getNumberOfUsers();
	public List<Bet> getBetsForUser(String username);
	public UserAccount getUserAccount(String username);
	
	// Setters
	public void setUserValidator(UserValidator validator);
	public void setBetValidator(BetValidator validator);
	public void setLoginManager(LoginManager loginmanager);
	public void setLoggedinUser(UserAccount useraccount);
	
	// Class Operators
	public boolean registerUser(String username, String password, String name,
			String surname, Calendar date, boolean isPremium, String ccNumber,
			Calendar ccExpiry, String cvv) throws IllegalArgumentException;
	
	public boolean login(String username, String password);
	
	public boolean placeBet(String username, RiskLevel riskLevel, double amount);
	
	public boolean logout();
	
}
