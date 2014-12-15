package um.edu.mt.cucumber.stepdefs;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.util.Random;

import cucumber.api.java.Before;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import um.edu.mt.cucumber.pageobjects.RegisterPage;
import cucumber.api.java.After;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import static org.junit.Assert.*;

public class register_stepdefs {

	WebDriver browser;
	RegisterPage registerpage;
	
	String username = "";

	@Before
	public void before() {
		Random r = new Random();
		username = "username"+r.nextInt(9999);
	}

	@After
	public void after()
	{	
		if(browser!=null){
			browser.quit();
		}
	}
	
	@Given("^I am a user trying to register")
	public void Testing() throws Throwable {
		System.setProperty("webdriver.chrome.driver", "src\\test\\resources\\chromedriver.exe");
		browser = new ChromeDriver();
		registerpage = new RegisterPage(browser);
	}
	
	@When("^I register providing correct information$")
	public void i_register_providing_correct_information() throws Throwable {

		registerpage.setUsername(username);
		registerpage.setPassword("12345678");
		registerpage.setName("Leo");
		registerpage.setSurname("Messi");
		registerpage.setDOB("03/17/1994");
		registerpage.selectFree();
		registerpage.setCreditCard("4012888888881881");
		registerpage.setExpiry("June");
		registerpage.setExpiry(Keys.TAB);
		registerpage.setExpiry("2016");
		registerpage.setCVV("123");

		registerpage.clickRegister();
		try {
		    Thread.sleep(2000);
		} catch(InterruptedException ex) {
		    Thread.currentThread().interrupt();
		}
	}

	@Then("^I should be told that the registration was successful$")
	public void i_should_be_told_that_the_registration_was_successful() throws Throwable {
		WebElement msg_header = browser.findElement(By.id("message_header"));
		assertEquals("Successful Registration", msg_header.getText());
	}
	
	@When("^I fill in a form with correct data$")
	public void i_fill_in_a_form_with_correct_data() throws Throwable {
		registerpage.setUsername(username);
		registerpage.setPassword("12345678");
		registerpage.setName("Leo");
		registerpage.setSurname("Messi");
		registerpage.setDOB("03/17/1994");
		registerpage.selectFree();
		registerpage.setCreditCard("4012888888881881");
		registerpage.setExpiry("June");
		registerpage.setExpiry(Keys.TAB);
		registerpage.setExpiry("2016");
		registerpage.setCVV("123");
	}

	@When("^I change the Name field to have incorrect input$")
	public void i_change_the_Name_field_to_have_incorrect_input() throws Throwable {
		registerpage.setName("1");
		try {
		    Thread.sleep(500);
		} catch(InterruptedException ex) {
		    Thread.currentThread().interrupt();
		}
	}

	@Then("^I should be told that the data in Name is incorrect$")
	public void i_should_be_told_that_the_data_in_Name_is_incorrect() throws Throwable {
	    assertFalse(registerpage.isFirstNameCorrect());
	}
	
	@When("^I change the Surname field to have incorrect input$")
	public void i_change_the_Surname_field_to_have_incorrect_input() throws Throwable {
		registerpage.setSurname("1");
		try {
		    Thread.sleep(500);
		} catch(InterruptedException ex) {
		    Thread.currentThread().interrupt();
		}
	}

	@Then("^I should be told that the data in Surname is incorrect$")
	public void i_should_be_told_that_the_data_in_Surname_is_incorrect() throws Throwable {
	    assertFalse(registerpage.isSurnameCorrect());
	}
	
	@When("^I change the DOB field to have incorrect input$")
	public void i_change_the_DOB_field_to_have_incorrect_input() throws Throwable {
		registerpage.setDOB("03/17/1997");
		try {
		    Thread.sleep(500);
		} catch(InterruptedException ex) {
		    Thread.currentThread().interrupt();
		}
	}

	@Then("^I should be told that the data in DOB is incorrect$")
	public void i_should_be_told_that_the_data_in_DOB_is_incorrect() throws Throwable {
		assertFalse(registerpage.isDOBCorrect());
	}
	
	@When("^I change the CreditCard field to have incorrect input$")
	public void i_change_the_CreditCard_field_to_have_incorrect_input() throws Throwable {
		registerpage.setCreditCard("54");
		try {
		    Thread.sleep(500);
		} catch(InterruptedException ex) {
		    Thread.currentThread().interrupt();
		}
	}

	@Then("^I should be told that the data in CreditCard is incorrect$")
	public void i_should_be_told_that_the_data_in_CreditCard_is_incorrect() throws Throwable {
		assertFalse(registerpage.isCreditCardCorrect());
	}

	@When("^I change the ExpiryDate field to have incorrect input$")
	public void i_change_the_ExpiryDate_field_to_have_incorrect_input() throws Throwable {
		registerpage.setExpiry("June");
		registerpage.setExpiry(Keys.TAB);
		registerpage.setExpiry("2012");
		registerpage.setExpiry(Keys.TAB);
		try {
		    Thread.sleep(500);
		} catch(InterruptedException ex) {
		    Thread.currentThread().interrupt();
		}
	}

	@Then("^I should be told that the data in ExpiryDate is incorrect$")
	public void i_should_be_told_that_the_data_in_ExpiryDate_is_incorrect() throws Throwable {
		assertFalse(registerpage.isExpiryCorrect());
	}
	
}
