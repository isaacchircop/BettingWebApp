package um.mt.bettingapp.facade;

import um.mt.bettingapp.pojos.RiskLevel;
import um.mt.bettingapp.pojos.UserAccount;

public interface BetValidator {
	
	public boolean validateBet(UserAccount account, RiskLevel riskLevel, double amount);
	public boolean validateRisk(UserAccount account, RiskLevel riskLevel);
	public boolean validateAmount(UserAccount account, double amount);
	
}
