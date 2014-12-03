package um.edu.mt.cucumber.pageobjects;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import net.thucydides.core.annotations.DefaultUrl;
import net.thucydides.core.annotations.findby.By;
import net.thucydides.core.annotations.findby.FindBy;
import net.thucydides.core.pages.PageObject;


	@DefaultUrl("http://localhost:8080/bettingapp_frontend/register.html")
	public class RegisterPage extends PageObject {
		
		public RegisterPage(WebDriver driver) {
	        super(driver); 
	        driver.get("http://localhost:8080/bettingapp_frontend/register.html");
	    }
		

	    @FindBy(id = "inputUsername")
	    WebElement username_input;
	    
	    @FindBy(id = "inputPassword")
	    WebElement password_input;
	    
	    @FindBy(id = "inputName")
	    WebElement name_input;
	    
	    @FindBy(id = "inputSurname")
	    WebElement surname_input;
	    
	    @FindBy(id = "inputDate")
	    WebElement dob_input;
	    
	    @FindBy(id = "optionsRadios2")
	    WebElement Premium_radioBtn;
	    
	    @FindBy(id = "optionsRadios1")
	    WebElement Free_radioBtn;
	    
	    @FindBy(id = "inputCreditCard")
	    WebElement card_input;
	    
	    @FindBy(id = "inputExpiry")
	    WebElement expiry_input;
	    
	    @FindBy(id = "inputCVV")
	    WebElement cvv_input;
	    
	    @FindBy(id = "register_button")
	    WebElement registerBtn;

	    public void setUsername(String input)
	    {
	    	username_input.sendKeys(input);
	    }
	    
	    public void setPassword(String input)
	    {
	    	password_input.sendKeys(input);
	    }
	    
	    public void setName(String input)
	    {
	    	name_input.sendKeys(input);
	    }
	    
	    public void setSurname(String input)
	    {
	    	surname_input.sendKeys(input);
	    }

	    public void setDOB(String input)
	    {
	    	dob_input.sendKeys(input);
	    }
	    
	    public void selectFree()
	    {
	    	Free_radioBtn.click();
	    }
	    
	    public void selectPremium()
	    {
	    	Premium_radioBtn.click();
	    }
	    
	    public void setCreditCard(String input)
	    {
	    	card_input.sendKeys(input);
	    }
	    
	    public void setExpiry(String input)
	    {
	    	expiry_input.sendKeys(input);
	    }
	    
	    public void setCVV(String input)
	    {
	    	cvv_input.sendKeys(input);
	    }
	    
	    public void clickRegister()
	    {
	    	registerBtn.click();
	    }

		public void setExpiry(Keys input) {
			expiry_input.sendKeys(input);
		}
	    
	}


