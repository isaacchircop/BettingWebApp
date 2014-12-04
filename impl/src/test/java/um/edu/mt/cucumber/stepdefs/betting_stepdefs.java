package um.edu.mt.cucumber.stepdefs;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import um.edu.mt.cucumber.pageobjects.BettingPage;
import um.edu.mt.cucumber.pageobjects.LoginPage;
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
	
	@Before
	public void before()
	{
		System.out.println("Hello Before");
		try {
			try {
				addnewFreeUser("freeuser01");
			} catch (Exception e) {

				e.printStackTrace();
			}
		    Thread.sleep(500);
		} catch(InterruptedException ex) {
		    Thread.currentThread().interrupt();
		}
	}
	
	@After
	public void after()
	{
		System.out.println("Hello After");
		try {
			try {
				deleteUser("freeuser01");
			} catch (Exception e) {

				e.printStackTrace();
			}
		    Thread.sleep(500);
		} catch(InterruptedException ex) {
		    Thread.currentThread().interrupt();
		}
	}
	
	@Given("^I am a user with a free account$")
	public void i_am_a_user_with_username_with_a_free_account() throws Throwable {
		System.setProperty("webdriver.chrome.driver", "C:\\Users\\Nick.Nick-PC\\chromedriver.exe");
		browser = new ChromeDriver();
		loginpage = new LoginPage(browser);
		loginpage.setUsername("freeuser01");
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
		browser.quit();
	}
	
	private void addnewFreeUser(String username) throws Exception {
		System.out.println("Trying to Add");
		String urlParameters = "username="+username+"&password=12345678&name=Free&surname=User&dob=1994-03-17&user_type=user_free&ccNumber=5555555555554444&ccExpiry=2017-06&cvv=123";
		URL url = new URL("http://localhost:8080/bettingapp/registerUser");
		URLConnection conn = url.openConnection();
		conn.setDoOutput(true);

		OutputStreamWriter writer = new OutputStreamWriter(conn.getOutputStream());

		writer.write(urlParameters);
		writer.flush();

		String line;
		BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));

		while ((line = reader.readLine()) != null) {
		    System.out.println(line);
		}
		writer.close();
		reader.close(); 
	}
	
	private void deleteUser(String username) throws Exception {
		System.out.println("Trying to Delete");
		String urlParameters = "username="+username;
		URL url = new URL("http://localhost:8080/bettingapp/deleteUser");
		URLConnection conn = url.openConnection();
		conn.setDoOutput(true);

		OutputStreamWriter writer = new OutputStreamWriter(conn.getOutputStream());

		writer.write(urlParameters);
		writer.flush();

		String line;
		BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));

		while ((line = reader.readLine()) != null) {
		    System.out.println(line);
		}
		writer.close();
		reader.close(); 
	}
	
	
	
}