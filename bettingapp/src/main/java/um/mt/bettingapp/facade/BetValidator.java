package um.mt.bettingapp.facade;

import um.mt.bettingapp.pojos.RiskLevel;
import um.mt.bettingapp.pojos.UserAccount;

public interface BetValidator {
	
	public void setUserManager(UserManager manager);
	public boolean validateBet(UserAccount account, RiskLevel riskLevel, double amount);
	public boolean validateNumberOfBets(UserAccount account);
	public boolean validateCumulative(UserAccount account, double amount);
	public boolean validateRisk(UserAccount account, RiskLevel riskLevel);
	public boolean validateAmount(UserAccount account, double amount);
	
}
