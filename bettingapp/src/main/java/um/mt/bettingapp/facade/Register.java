package um.mt.bettingapp.facade;

import java.util.Calendar;

public interface Register {

	public boolean registerUser(String username, String password, String name,
			String surname, Calendar date, boolean isPremium, String ccNumber,
			Calendar ccExpiry, String cvv) throws IllegalArgumentException;
	
	public void validateUsername(String username);
	public void validatePassword(String password);
	public void validateNameSurname(String string);
	public void validateDOB(Calendar date);
	public void validateCCNumber(String ccNumber);
	public void validateCCExpiry(Calendar ccExpiry);
	public void validateCVV(String cvv);
	public void isLuhnValid(String cardNumber);
}
