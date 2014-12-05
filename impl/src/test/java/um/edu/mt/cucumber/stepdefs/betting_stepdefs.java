package um.edu.mt.cucumber.stepdefs;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import um.edu.mt.cucumber.pageobjects.BettingPage;
import um.edu.mt.cucumber.pageobjects.LoginPage;
import cucumber.api.PendingException;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import static org.junit.Assert.*;

public class betting_stepdefs {

	WebDriver browser;
	LoginPage loginpage;
	BettingPage bettingpage;
	
	String FreeUsername = "freeuser01";
	String PremiumUsername = "premiumuser01";
	
	@Before
	public void before()
	{
		try {
			addnewFreeUser(FreeUsername);
			addnewPremiumUser(PremiumUsername);
		    Thread.sleep(500);
		}catch(Exception ex) {}
	}
	
	@After
	public void after()
	{
		try {
			deleteUser(FreeUsername);
			deleteUser(PremiumUsername);
			if(browser!=null)
			browser.quit();
			Thread.sleep(500);
		}catch(Exception ex) {}
	}
	
	@Given("^I am a user with a free account$")
	public void i_am_a_user_with_username_with_a_free_account() throws Throwable {
		System.setProperty("webdriver.chrome.driver", "C:\\Users\\Nick.Nick-PC\\chromedriver.exe");
		browser = new ChromeDriver();
		loginpage = new LoginPage(browser);
		loginpage.setUsername(FreeUsername);
		loginpage.setPassword("12345678");
		try {
		    Thread.sleep(2000);
		} catch(InterruptedException ex) {
		    Thread.currentThread().interrupt();
		}
		loginpage.clickLogin();
		try {
		    Thread.sleep(2000);
		} catch(InterruptedException ex) {
		    Thread.currentThread().interrupt();
		}
	}
	
	@When("^I try to place a bet of (\\d+) euros$")
	public void i_try_to_place_a_bet_of_euros(int arg1) throws Throwable {
		bettingpage = new BettingPage(browser);
		bettingpage.setRiskLevel("l");
		bettingpage.setAmount(arg1+"");
		bettingpage.clickPlaceBet();try {
		    Thread.sleep(500);
		} catch(InterruptedException ex) {
		    Thread.currentThread().interrupt();
		}
	}

	@Then("^I should be told the bet was successfully placed$")
	public void i_should_be_told_the_bet_was_successfully_placed() throws Throwable {
		assertTrue(bettingpage.isBetPlaced());
	}
	
	@Then("^I should be told that I have reached the maximum number of bets$")
	public void i_should_be_told_that_I_have_reached_the_maximum_number_of_bets() throws Throwable {
		assertTrue(bettingpage.is3Bets());
	}
	
	@Given("^I am a user with a premium account$")
	public void i_am_a_user_with_a_premium_account() throws Throwable {
		System.setProperty("webdriver.chrome.driver", "C:\\Users\\Nick.Nick-PC\\chromedriver.exe");
		browser = new ChromeDriver();
		loginpage = new LoginPage(browser);
		loginpage.setUsername(PremiumUsername);
		loginpage.setPassword("12345678");
		try {
		    Thread.sleep(2000);
		} catch(InterruptedException ex) {
		    Thread.currentThread().interrupt();
		}
		loginpage.clickLogin();
		try {
		    Thread.sleep(2000);
		} catch(InterruptedException ex) {
		    Thread.currentThread().interrupt();
		}
	}

	@Then("^I should be told that I have reached the maximum cumulative betting amount$")
	public void i_should_be_told_that_I_have_reached_the_maximum_cumulative_betting_amount() throws Throwable {
		assertTrue(bettingpage.isCumulativeReached());
	}
	
	@When("^I try to place a Low bet of (\\d+) euros$")
	public void i_try_to_place_a_Low_bet_of_euros(int arg1) throws Throwable {
		bettingpage = new BettingPage(browser);
		bettingpage.setRiskLevel("l");
		bettingpage.setAmount(arg1+"");
		bettingpage.clickPlaceBet();
		try {
		    Thread.sleep(500);
		} catch(InterruptedException ex) {
		    Thread.currentThread().interrupt();
		}
	}

	@Then("^I should be Allowed to bet$")
	public void i_should_be_Allowed_to_bet() throws Throwable {
	   assertTrue(bettingpage.isBetPlaced());
	}

	@When("^I try to place a Medium bet of (\\d+) euros$")
	public void i_try_to_place_a_Medium_bet_of_euros(int arg1) throws Throwable {
		bettingpage = new BettingPage(browser);
		bettingpage.setRiskLevel("m");
		bettingpage.setAmount(arg1+"");
		bettingpage.clickPlaceBet();
		try {
		    Thread.sleep(500);
		} catch(InterruptedException ex) {
		    Thread.currentThread().interrupt();
		}
	}

	@Then("^I should be Not Allowed to bet$")
	public void i_should_be_Not_Allowed_to_bet() throws Throwable {
	    assertTrue(bettingpage.isRiskTooHigh());
	}

	@When("^I try to place a High bet of (\\d+) euros$")
	public void i_try_to_place_a_High_bet_of_euros(int arg1) throws Throwable {
		bettingpage = new BettingPage(browser);
		bettingpage.setRiskLevel("h");
		bettingpage.setAmount(arg1+"");
		bettingpage.clickPlaceBet();
		try {
		    Thread.sleep(500);
		} catch(InterruptedException ex) {
		    Thread.currentThread().interrupt();
		}
	}
	
	@Given("^I am a user who has not yet logged on$")
	public void i_am_a_user_who_has_not_yet_logged_on() throws Throwable {
		System.setProperty("webdriver.chrome.driver", "C:\\Users\\Nick.Nick-PC\\chromedriver.exe");
		browser = new ChromeDriver();
		loginpage = new LoginPage(browser);
		loginpage.setUsername(PremiumUsername);
		loginpage.setPassword("12345678");
		try {
		    Thread.sleep(2000);
		} catch(InterruptedException ex) {
		    Thread.currentThread().interrupt();
		}
		loginpage.clickLogin();
		try {
		    Thread.sleep(2000);
		} catch(InterruptedException ex) {
		    Thread.currentThread().interrupt();
		}
		bettingpage = new BettingPage(browser);
		bettingpage.clickLogOut();
		try {
		    Thread.sleep(500);
		} catch(InterruptedException ex) {
		    Thread.currentThread().interrupt();
		}
	}

	@When("^I try to access the betting screen$")
	public void i_try_to_access_the_betting_screen() throws Throwable {
	    browser.get("http://localhost:8080/bettingapp_frontend/betting_screen.html");
	    try {
		    Thread.sleep(500);
		} catch(InterruptedException ex) {
		    Thread.currentThread().interrupt();
		}
	}

	@Then("^I should be refused access$")
	public void i_should_be_refused_access() throws Throwable {
		WebElement msg_header = browser.findElement(By.id("message_header"));
		assertEquals("Need to Login", msg_header.getText());
	}
	
	private void addnewFreeUser(String username) throws Exception {
		String urlParameters = "username="+username+"&password=12345678&name=Free&surname=User&dob=1994-03-17&user_type=user_free&ccNumber=5555555555554444&ccExpiry=2017-06&cvv=123";
		URL url = new URL("http://localhost:8080/bettingapp/registerUser");
		URLConnection conn = url.openConnection();
		conn.setDoOutput(true);
		OutputStreamWriter writer = new OutputStreamWriter(conn.getOutputStream());
		writer.write(urlParameters);
		writer.flush();
		try{BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream())); reader.close();}
		catch(Exception e){}
		writer.close();
	}
	
	private void addnewPremiumUser(String username) throws Exception {
		String urlParameters = "username="+username+"&password=12345678&name=Premium&surname=User&dob=1994-03-17&user_type=user_premium&ccNumber=5555555555554444&ccExpiry=2017-06&cvv=123";
		URL url = new URL("http://localhost:8080/bettingapp/registerUser");
		URLConnection conn = url.openConnection();
		conn.setDoOutput(true);
		OutputStreamWriter writer = new OutputStreamWriter(conn.getOutputStream());
		writer.write(urlParameters);
		writer.flush();
		try{BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream())); reader.close();}
		catch(Exception e){}
		writer.close();
	}
	
	private void deleteUser(String username) throws Exception {
		String urlParameters = "username="+username;
		URL url = new URL("http://localhost:8080/bettingapp/deleteUser");
		URLConnection conn = url.openConnection();
		conn.setDoOutput(true);
		OutputStreamWriter writer = new OutputStreamWriter(conn.getOutputStream());
		writer.write(urlParameters);
		writer.flush();
		try{BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream())); reader.close();}
		catch(Exception e){}
		writer.close();	
	}
	
	
	
	
	
}