package um.edu.mt.impl;

import java.io.Serializable;
import java.util.Calendar;

import um.edu.mt.bd.UserAccount;

public class UserAccountImpl implements UserAccount, Serializable {

	private static final long serialVersionUID = 1L;
	private String username;
	private String password;
	private String name;
	private String surname;
	private Calendar date;
	private boolean isPremium;
	private String ccNumber;
	private Calendar ccExpiry;
	private String cvv;
	
	private boolean isBlocked;
	private int loginTries;
	private long blockTime;
	
	private boolean isLoggedIn;
	
	public boolean isLoggedIn() {
		return isLoggedIn;
	}

	public void setLoggedIn(boolean isLoggedIn) {
		this.isLoggedIn = isLoggedIn;
	}

	public UserAccountImpl(String username, String password, String name,
			String surname, Calendar date, boolean isPremium, String ccNumber,
			Calendar ccExpiry, String cvv) {
		this.username = username;
		this.password = password;
		this.name = name;
		this.surname = surname;
		this.date = date;
		this.isPremium = isPremium;
		this.ccNumber = ccNumber;
		this.ccExpiry = ccExpiry;
		this.cvv = cvv;
		this.isBlocked = false;
		this.loginTries = 0;
		isLoggedIn = false;
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

	public Calendar getDate() {
		return date;
	}

	public void setDate(Calendar date) {
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

	public Calendar getCcExpiry() {
		return ccExpiry;
	}

	public void setCcExpiry(Calendar ccExpiry) {
		this.ccExpiry = ccExpiry;
	}

	public String getCvv() {
		return cvv;
	}

	public void setCvv(String cvv) {
		this.cvv = cvv;
	}

	public boolean isBlocked() {
		return isBlocked;
	}

	public void block() {
		this.isBlocked = true;
		this.blockTime = System.currentTimeMillis();
	}
	
	public void unblock() {
		this.isBlocked = false;
		this.blockTime = 0;
	}
	
	public void incrLoginTries() {
		this.loginTries++;
	}
	
	public int getLoginTries() {
		return this.loginTries;
	}
	
	public void resetLoginTries(){
		this.loginTries = 0;
	}
	
	public long unblkTimeLeft() {
		return (this.blockTime == 0) ? 0 : System.currentTimeMillis() - this.blockTime;
	}
	
}