package um.edu.mt.bd;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public interface UserManager {
	
	public static Map<String, UserAccount> users = new HashMap<String, UserAccount>();
	public static List<Bet> bets = new ArrayList<Bet>();

	// Getters
	public int getNumberOfUsers();
	public List<Bet> getBetsForUser(String username);
	public UserAccount getUserAccount(String username);
	
	// Setters
	public void setUserValidator(UserValidator validator);
	public void setBetValidator(BetValidator validator);
	public void setLoginManager(LoginManager loginmanager);
	
	// Class Operators
	public boolean registerUser(String username, String password, String name,
			String surname, Calendar date, boolean isPremium, String ccNumber,
			Calendar ccExpiry, String cvv) throws IllegalArgumentException;
	
	public boolean login(String username, String password);
	
	public boolean placeBet(String username, RiskLevel riskLevel, double amount);
	
	public void logout(String username);
	
	public void saveBetsToFile(String filename);
	public void saveUsersToFile(String filename);
	
}
