package um.mt.bettingapp.pojos;

import java.util.Date;

public class UserAccount {
	
	private String username;
	private String password;
	private String name;
	private String surname;
	private Date date;
	private boolean isPremium;
	private String ccNumber;
	private Date ccExpiry;
	private String cvv;
	
	public UserAccount(String username, String password, String name,
			String surname, Date date, boolean isPremium, String ccNumber,
			Date ccExpiry, String cvv) {
		this.username = username;
		this.password = password;
		this.name = name;
		this.surname = surname;
		this.date = date;
		this.isPremium = isPremium;
		this.ccNumber = ccNumber;
		this.ccExpiry = ccExpiry;
		this.cvv = cvv;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public boolean isPremium() {
		return isPremium;
	}

	public void setPremium(boolean isPremium) {
		this.isPremium = isPremium;
	}

	public String getCcNumber() {
		return ccNumber;
	}

	public void setCcNumber(String ccNumber) {
		this.ccNumber = ccNumber;
	}

	public Date getCcExpiry() {
		return ccExpiry;
	}

	public void setCcExpiry(Date ccExpiry) {
		this.ccExpiry = ccExpiry;
	}

	public String getCvv() {
		return cvv;
	}

	public void setCvv(String cvv) {
		this.cvv = cvv;
	}
	
	
	
}