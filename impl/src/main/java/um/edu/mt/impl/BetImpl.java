package um.edu.mt.impl;

import um.edu.mt.bd.Bet;
import um.edu.mt.bd.RiskLevel;

public class BetImpl implements Bet{

	private String username;
	private RiskLevel riskLevel;
	private double amount;
	
	public BetImpl(String username, RiskLevel riskLevel, double amount) {
		this.username = username;
		this.riskLevel = riskLevel;
		this.amount = amount;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public RiskLevel getRiskLevel() {
		return riskLevel;
	}

	public void setRiskLevel(RiskLevel riskLevel) {
		this.riskLevel = riskLevel;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}
	
}
