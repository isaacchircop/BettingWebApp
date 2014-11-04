package um.mt.bettingapp.facade;

import um.mt.bettingapp.pojos.UserAccount;

public interface LoginManager {

	public boolean login(UserAccount account, String password);
	public boolean isAccountExistent(UserAccount account);
	public boolean isAccountAccessible(UserAccount account);
	public boolean isPasswordCorrect(UserAccount account, String password);
	
}
