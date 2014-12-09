package um.edu.mt.impl;

import um.edu.mt.bd.LoginManager;
import um.edu.mt.bd.UserAccount;

public class LoginManagerImpl implements LoginManager {
	
	private final long UNBLOCKTIME = 300000;
	private String errorMessage;
	
	public boolean login(UserAccount account, String password) {
		return isAccountAccessible(account) &&
				isPasswordCorrect(account, password);
	}

	public boolean isAccountAccessible(UserAccount account) {
		if (isAccountExistent(account)) {
			if (account.isBlocked()) {
				if (account.unblkTimeLeft() >= UNBLOCKTIME) {
					account.unblock();
					account.resetLoginTries();
					return true;
				} else {
					errorMessage = "Account is blocked.  Try again within " + account.unblkTimeLeft() / 1000 + " seconds";
					return false;
				}
			} else {
				return true;
			}
		} else {
			errorMessage = "Account does not exist";
			return false;
		}
	}

	public boolean isPasswordCorrect(UserAccount account, String password) {
		if (isAccountExistent(account)) {
			if (!account.getPassword().equals(password)) {
				account.incrLoginTries();
				int tries = account.getLoginTries();
				if (tries > 3) {
					account.block();
					errorMessage = "Account blocked.  Try again within 5 minutes";
					return false;
				} else {
					errorMessage = "Invalid Password.  Number of tries left: " + (3 - tries);
					return false;
				}
			} else return true;
		} else return false;
	}

	public boolean isAccountExistent(UserAccount account) {
		if (account == null) {
			errorMessage = "Account with specified username does not exist";
			return false;
		} else return true;
	}
	
	public String getErrorMessage() {
		return this.errorMessage;
	}
	
}
