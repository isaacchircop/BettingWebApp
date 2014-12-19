package um.edu.mt.bd;

import java.util.Calendar;

public interface UserAccount {

	public String getUsername();
	public void setUsername(String username);

	public String getPassword();
	public void setPassword(String password);

	public String getName();
	public void setName(String name);

	public String getSurname();
	public void setSurname(String surname);

	public Calendar getDate();
	public void setDate(Calendar date);

	public boolean isPremium();
	public void setPremium(boolean isPremium);

	public String getCcNumber();
	public void setCcNumber(String ccNumber);

	public Calendar getCcExpiry();
	public void setCcExpiry(Calendar ccExpiry);

	public String getCvv();
	public void setCvv(String cvv);
	
	public boolean isLoggedIn();
	public void setLoggedIn(boolean logged);

	public boolean isBlocked();
	public void block();
	public void unblock();
	
	public void incrLoginTries();
	public int getLoginTries();
	public void resetLoginTries();
	
	public long unblkTimeLeft();
	
}
