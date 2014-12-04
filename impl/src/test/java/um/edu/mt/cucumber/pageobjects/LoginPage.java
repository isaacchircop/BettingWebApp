package um.edu.mt.cucumber.pageobjects;

import net.thucydides.core.annotations.DefaultUrl;
import net.thucydides.core.annotations.findby.FindBy;
import net.thucydides.core.pages.PageObject;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

@DefaultUrl("http://localhost:8080/bettingapp_frontend/login.html")
public class LoginPage extends PageObject {
	
	public LoginPage(WebDriver driver) {
        super(driver); 
        driver.get("http://localhost:8080/bettingapp_frontend/login.html");
    }
	
	@FindBy(id = "inputUsername")
    WebElement inputUsername;
    
    @FindBy(id = "inputPassword")
    WebElement inputPassword;
    
    @FindBy(id = "login_button")
    WebElement login_button;
    
    public void setUsername(String input)
    {
    	inputUsername.sendKeys(input);
    }
    
    public void setPassword(String input)
    {
    	inputPassword.sendKeys(input);
    }
    
    public void clickLogin()
    {
    	login_button.click();
    }

}
