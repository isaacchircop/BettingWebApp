package um.mt.bettingapp.impl;

import java.util.List;

import um.mt.bettingapp.facade.BetValidator;
import um.mt.bettingapp.facade.UserManager;
import um.mt.bettingapp.pojos.Bet;
import um.mt.bettingapp.pojos.RiskLevel;
import um.mt.bettingapp.pojos.UserAccount;

public class BetValidatorImpl implements BetValidator{
	
	private final double TOTAL_AMOUNT_LIMIT = 5000;
	private final double TOTAL_BET_LIMIT = 3;
	private UserManager userManager;
	
	public void setUserManager(UserManager userManager) {
		this.userManager = userManager;
	}
	
	public boolean validateBet(UserAccount account, RiskLevel riskLevel, double amount) {
		
		return validateRisk(account, riskLevel) && 
				validateAmount(account, amount) && 
				validateCumulative(account, amount);
		
	}
	
	public boolean validateCumulative(UserAccount account, double amount) {
		
		List<Bet> bets = userManager.getBetsForUser(account.getUsername());
		
		if (account.isPremium()) {
			double total = 0;
			for (Bet bet : bets) {
				total += bet.getAmount();
			}
			return (total + amount) <= TOTAL_AMOUNT_LIMIT;
		} else {
			return bets.size() < TOTAL_BET_LIMIT;
		}
		
	}
	
	public boolean validateRisk(UserAccount account, RiskLevel riskLevel) {
		
		if (!account.isPremium()) {
			return riskLevel == RiskLevel.LOW;
		} else return true;
		
	}
	
	public boolean validateAmount(UserAccount account, double amount) {
		
		if (!account.isPremium()) {
			return amount < 5;
		} else {
			return true;
		}
		
	}
	
}
