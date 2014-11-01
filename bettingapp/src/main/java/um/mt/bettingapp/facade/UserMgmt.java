package um.mt.bettingapp.facade;

import java.util.Calendar;

public interface UserMgmt {

	public void registerUser(String username, String password, String name,
			String surname, Calendar date, boolean isPremium, String ccNumber,
			Calendar ccExpiry, String cvv) throws IllegalArgumentException;
	
	public boolean login(String username, String password);
	
//	public void placeBet();
	
//	public void logout();
	
}
