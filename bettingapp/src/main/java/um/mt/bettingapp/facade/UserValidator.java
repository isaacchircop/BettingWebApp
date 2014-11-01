package um.mt.bettingapp.facade;

import java.util.Calendar;

import um.mt.bettingapp.pojos.UserAccount;

public interface UserValidator {
	
	public boolean validateAccount(UserAccount account);
	
	public String getErrorMessage();
	
	public boolean isUserNameValid(String username);
	public boolean isPasswordValid(String password);
	public boolean isNameSurnameValid(String string);
	public boolean isDOBValid(Calendar date);
	public boolean isCCNumberValid(String ccNumber);
	public boolean isCCExpiryValid(Calendar ccExpiry);
	public boolean isCVVValid(String cvv);
	public boolean isLuhnValid(String cardNumber);

}
