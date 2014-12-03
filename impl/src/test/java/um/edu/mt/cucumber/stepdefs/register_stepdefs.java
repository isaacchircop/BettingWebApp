package um.edu.mt.cucumber.stepdefs;



import org.junit.After;
import org.junit.Before;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import um.edu.mt.cucumber.pageobjects.RegisterPage;
import cucumber.api.PendingException;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import static org.junit.Assert.*;

public class register_stepdefs {

	WebDriver browser;
	RegisterPage registerpage;
	
	@Given("^I am a user trying to register")
	public void Testing() throws Throwable {
		System.setProperty("webdriver.chrome.driver", "C:\\Users\\Nick.Nick-PC\\chromedriver.exe");
		browser = new ChromeDriver();
		registerpage = new RegisterPage(browser);
	}
	
	@When("^I register providing correct information$")
	public void i_register_providing_correct_information() throws Throwable {
		registerpage.setUsername("NickGalea1");
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
		    Thread.sleep(1000);
		} catch(InterruptedException ex) {
		    Thread.currentThread().interrupt();
		}
	}

	@Then("^I should be told that the registration was successful$")
	public void i_should_be_told_that_the_registration_was_successful() throws Throwable {
		WebElement msg_header = browser.findElement(By.id("message_header"));
		assertEquals("Successful Registration", msg_header.getText());
		browser.quit();
	}
	
}
