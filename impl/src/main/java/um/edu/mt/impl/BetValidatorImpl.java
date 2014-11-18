package um.edu.mt.impl;

import java.util.List;

import um.edu.mt.bd.Bet;
import um.edu.mt.bd.BetValidator;
import um.edu.mt.bd.RiskLevel;
import um.edu.mt.bd.UserAccount;
import um.edu.mt.bd.UserManager;

public class BetValidatorImpl implements BetValidator{
	
	private final double TOTAL_AMOUNT_LIMIT = 5000;
	private final double TOTAL_BET_LIMIT = 3;
	private UserManager userManager;
	
	// Setters
	public void setUserManager(UserManager userManager) {
		this.userManager = userManager;
	}
	
	// Class Operations
	public boolean validateBet(UserAccount account, RiskLevel riskLevel, double amount) {
		return validateRisk(account, riskLevel) && 
				validateAmount(account, amount) && 
				account.isPremium() ? validateCumulative(account, amount) 
									: validateNumberOfBets(account);
		
	}
	
	public boolean validateCumulative(UserAccount account, double amount) {
		List<Bet> bets = userManager.getBetsForUser(account.getUsername());
		
		double total = 0;
		for (Bet bet : bets) {
			total += bet.getAmount();
		}
		
		return (total + amount) <= TOTAL_AMOUNT_LIMIT;
	}
	
	public boolean validateNumberOfBets(UserAccount account) {
		
		List<Bet> bets = userManager.getBetsForUser(account.getUsername());
		return bets.size() < TOTAL_BET_LIMIT;
		
	}
	
	public boolean validateRisk(UserAccount account, RiskLevel riskLevel) {
		//Free users can only place low-risk bets
		return !account.isPremium() ? riskLevel == RiskLevel.LOW : riskLevel != null;
		
	}
	
	public boolean validateAmount(UserAccount account, double amount) {
		//Free users are capped to EUR 5 per bet
		return !account.isPremium() ? amount <= 5 && amount > 0: amount > 0;
		
	}
	
}
