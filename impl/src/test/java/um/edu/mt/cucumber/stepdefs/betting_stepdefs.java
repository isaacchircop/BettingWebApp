package um.edu.mt.cucumber.stepdefs;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.util.Random;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import um.edu.mt.cucumber.pageobjects.BettingPage;
import um.edu.mt.cucumber.pageobjects.LoginPage;
import cucumber.api.java.After;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import static org.junit.Assert.*;

public class betting_stepdefs {

	private WebDriver browser;
	private LoginPage loginpage;
	private BettingPage bettingpage;
	
	private String username = "";
	private String password = "";

	@After
	public void teardown() {
		username = "";
		password = "";
		if(browser!=null) {
			browser.quit();
		}
	}

	@Given("^I am a user who has not yet logged on$")
	public void i_am_a_user_who_has_not_yet_logged_on() throws Throwable {
		// Register User
		System.setProperty("webdriver.chrome.driver", "impl\\src\\test\\resources\\chromedriver.exe");
		browser = new ChromeDriver();
		addUser(true);
	}

	@Given("^I am a user with a free account$")
	public void i_am_a_user_with_username_with_a_free_account() throws Throwable {
		// Register user
		System.setProperty("webdriver.chrome.driver", "impl\\src\\test\\resources\\chromedriver.exe");
		browser = new ChromeDriver();
		addUser(false);

		// Fill in login details
		loginpage = new LoginPage(browser);
		loginpage.setUsername(username);
		loginpage.setPassword(password);

		// Attempt login
		loginpage.clickLogin();
	}

	@Given("^I am a user with a premium account$")
	public void i_am_a_user_with_a_premium_account() throws Throwable {
		// Register
		System.setProperty("webdriver.chrome.driver", "impl\\src\\test\\resources\\chromedriver.exe");
		browser = new ChromeDriver();
		addUser(true);

		// Fill in login details
		loginpage = new LoginPage(browser);
		loginpage.setUsername(username);
		loginpage.setPassword(password);

		// Attempt Login
		loginpage.clickLogin();
	}

	@When("^I try to place a bet of (\\d+) euros$")
	public void i_try_to_place_a_bet_of_euros(int arg1) throws Throwable {
		// Fill in bet details
		bettingpage = new BettingPage(browser);
		bettingpage.setRiskLevel("l");
		bettingpage.setAmount(arg1+"");

		// Submit bet
		bettingpage.clickPlaceBet();
		try {
		    Thread.sleep(500);
		}
		catch(InterruptedException ex) {
		    Thread.currentThread().interrupt();
		}
	}

	@When("^I try to place a Low bet of (\\d+) euros$")
	public void i_try_to_place_a_Low_bet_of_euros(int arg1) throws Throwable {
		// Fill in bet details
		bettingpage = new BettingPage(browser);
		bettingpage.setRiskLevel("l");
		bettingpage.setAmount(arg1+"");

		// Submit bet
		bettingpage.clickPlaceBet();
		try {
			Thread.sleep(1000);
		} catch(InterruptedException ex) {
			Thread.currentThread().interrupt();
		}
	}

	@When("^I try to place a Medium bet of (\\d+) euros$")
	public void i_try_to_place_a_Medium_bet_of_euros(int arg1) throws Throwable {
		// Fill in bet details
		bettingpage = new BettingPage(browser);
		bettingpage.setRiskLevel("m");
		bettingpage.setAmount(arg1+"");

		// Submit bet
		bettingpage.clickPlaceBet();
		try {
			Thread.sleep(1000);
		} catch(InterruptedException ex) {
			Thread.currentThread().interrupt();
		}
	}

	@When("^I try to place a High bet of (\\d+) euros$")
	public void i_try_to_place_a_High_bet_of_euros(int arg1) throws Throwable {
		// Fill in bet details
		bettingpage = new BettingPage(browser);
		bettingpage.setRiskLevel("h");
		bettingpage.setAmount(arg1+"");

		// Submit bet
		bettingpage.clickPlaceBet();
		try {
			Thread.sleep(1000);
		} catch(InterruptedException ex) {
			Thread.currentThread().interrupt();
		}
	}

	@When("^I try to access the betting screen$")
	public void i_try_to_access_the_betting_screen() throws Throwable {
		browser.get("http://localhost:8080/bettingapp_frontend/betting_screen.html");
		try {
			Thread.sleep(1000);
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

	@Then("^I should be told that I have reached the maximum cumulative betting amount$")
	public void i_should_be_told_that_I_have_reached_the_maximum_cumulative_betting_amount() throws Throwable {
		assertTrue(bettingpage.isCumulativeReached());
	}

	@Then("^I should be Allowed to bet$")
	public void i_should_be_Allowed_to_bet() throws Throwable {
	   assertTrue(bettingpage.isBetPlaced());
	}

	@Then("^I should be Not Allowed to bet$")
	public void i_should_be_Not_Allowed_to_bet() throws Throwable {
	    assertTrue(bettingpage.isRiskTooHigh());
	}

	@Then("^I should be refused access$")
	public void i_should_be_refused_access() throws Throwable {
		WebElement msg_header = browser.findElement(By.id("message_header"));
		assertEquals("Need to Login", msg_header.getText());
	}

	private void addUser(boolean isPremium) {
		Random r = new Random();
		final int random = r.nextInt(9999);

		username = "username" + random;
		password = "password123";

		String freeParams = "username="+username+"&password="+password+"&name=Free&surname=User&dob=1994-03-17&user_type=user_free&ccNumber=5555555555554444&ccExpiry=2017-06&cvv=123";
		String premiumParams = "username="+username+"&password="+password+"&name=Premium&surname=User&dob=1994-03-17&user_type=user_premium&ccNumber=5555555555554444&ccExpiry=2017-06&cvv=123";

		try{
			URL url = new URL("http://localhost:8080/bettingapp/registerUser");
			URLConnection conn = url.openConnection();
			conn.setDoOutput(true);

			OutputStreamWriter writer = new OutputStreamWriter(conn.getOutputStream());
			writer.write((isPremium) ? premiumParams : freeParams);
			writer.flush();

			BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			reader.close();
			writer.close();
		}
		catch(IOException e){}

	}

}