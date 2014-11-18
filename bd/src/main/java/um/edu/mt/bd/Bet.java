package um.edu.mt.bd;

public interface Bet {

	public String getUsername();
	public void setUsername(String username);

	public RiskLevel getRiskLevel();
	public void setRiskLevel(RiskLevel riskLevel);

	public double getAmount();
	public void setAmount(double amount);
	
}
