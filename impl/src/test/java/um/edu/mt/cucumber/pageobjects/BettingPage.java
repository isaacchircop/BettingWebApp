package um.edu.mt.cucumber.pageobjects;

import net.thucydides.core.annotations.DefaultUrl;
import net.thucydides.core.annotations.findby.FindBy;
import net.thucydides.core.pages.PageObject;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

@DefaultUrl("http://localhost:8080/bettingapp_frontend/betting_screen.html")
public class BettingPage extends PageObject {
	
	@FindBy(id= "LogOut_Link")
	WebElement logout_link;
		
	@FindBy(id = "input_risklevel")
    WebElement risklevel_input;
    
    @FindBy(id = "input_amount")
    WebElement amount_input;
    
    @FindBy(id = "placebet_button")
    WebElement placebet_button;
    
    @FindBy(id = "alert_errorMsg")
    WebElement alert_errorMsg;
    
    @FindBy(id = "alert_Success")
    WebElement alert_Success;

    public BettingPage(WebDriver driver) {
        super(driver);
    }

    // Input methods
    public void setRiskLevel(String input) {
    	risklevel_input.sendKeys(input);
    }
    
    public void setAmount(String input) {
    	amount_input.clear();
    	amount_input.sendKeys(input);
    }
    
    public void clickPlaceBet() {
    	placebet_button.click();
    }

    public void clickLogOut() {
        logout_link.click();
    }

    // Check display contents methods
    public boolean is3Bets(){
		return alert_errorMsg.getText().equals("Maximum number of bets reached") && alert_errorMsg.isDisplayed();
	}
    
    public boolean isCumulativeReached() {
		return alert_errorMsg.getText().equals("Maximum number of cumulative bets amount reached") && alert_errorMsg.isDisplayed();
	}
    
    public boolean isRiskTooHigh() {
    	return alert_errorMsg.getText().equals("Invalid Risk Level for user account type") && alert_errorMsg.isDisplayed();
    }
    
    public boolean isBetPlaced() {
    	return alert_Success.isDisplayed();
    }

}
