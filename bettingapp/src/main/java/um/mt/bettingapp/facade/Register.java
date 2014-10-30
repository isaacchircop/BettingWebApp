package um.mt.bettingapp.facade;

import java.util.Date;

import um.mt.bettingapp.pojos.UserAccount;

public interface Register {

	public boolean registerUser(String username, String password, String name,
			String surname, Date date, boolean isPremium, String ccNumber,
			Date ccExpiry, String cvv) throws IllegalArgumentException;
	
}
