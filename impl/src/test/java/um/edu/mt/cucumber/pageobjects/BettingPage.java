package um.edu.mt.cucumber.pageobjects;

import net.thucydides.core.annotations.DefaultUrl;
import net.thucydides.core.annotations.findby.FindBy;
import net.thucydides.core.pages.PageObject;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

@DefaultUrl("http://localhost:8080/bettingapp_frontend/betting_screen.html")
public class BettingPage extends PageObject {
	
	public BettingPage(WebDriver driver) {
        super(driver); 
    }
		
	@FindBy(id = "input_risklevel")
    WebElement risklevel_input;
    
    @FindBy(id = "input_amount")
    WebElement amount_input;
    
    @FindBy(id = "placebet_button")
    WebElement placebet_button;
    
    @FindBy(id = "alert_riskhigh")
    WebElement alert_riskhigh;
    
    @FindBy(id = "alert_amounthigh")
    WebElement alert_amounthigh;
    
    @FindBy(id = "alert_3bets")
    WebElement alert_3bets;
    
    @FindBy(id = "alert_cumulative")
    WebElement alert_cumulative;
    
    @FindBy(id = "alert_success")
    WebElement alert_success;
    
    public void setRiskLevel(String input)
    {
    	risklevel_input.sendKeys(input);
    }
    
    public void setAmount(String input)
    {
    	amount_input.clear();
    	amount_input.sendKeys(input);
    }
    
    public void clickPlaceBet()
    {
    	placebet_button.click();
    }
    
    public boolean is3Bets()
	{
		return alert_3bets.isDisplayed();
	}
    
    public boolean isCumulativeReached()
	{
		return alert_cumulative.isDisplayed();
	}
    
    public boolean isRiskTooHigh()
    {
    	return alert_riskhigh.isDisplayed();
    }
    
    public boolean isBetPlaced()
    {
    	return alert_success.isDisplayed();
    }

}
