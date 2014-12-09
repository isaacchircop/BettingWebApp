package um.edu.mt.ws;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import um.edu.mt.bd.Bet;
import um.edu.mt.bd.BetValidator;
import um.edu.mt.bd.LoginManager;
import um.edu.mt.bd.RiskLevel;
import um.edu.mt.bd.UserAccount;
import um.edu.mt.bd.UserManager;
import um.edu.mt.impl.LoginManagerImpl;
import um.edu.mt.bd.UserValidator;
import um.edu.mt.impl.BetValidatorImpl;
import um.edu.mt.impl.UserManagerImpl;
import um.edu.mt.impl.UserValidatorImpl;

@Controller
public class UserManagerController {
	
	private UserManager myuser = new UserManagerImpl();
	private LoginManager myloginmanager = new LoginManagerImpl();

	@RequestMapping (value = "/registerUser", method = RequestMethod.POST)
	public ResponseEntity<HttpStatus> regiserUser(@RequestParam(value = "username") final String username,
							  @RequestParam(value = "password") final String password,
							  @RequestParam(value = "name") final String name,
							  @RequestParam(value = "surname") final String surname,
							  @RequestParam(value = "dob") final String dob,
							  @RequestParam(value = "user_type") final String user_type,
							  @RequestParam(value = "ccNumber") final String ccNumber,
							  @RequestParam(value = "ccExpiry") final String ccExpiry,
							  @RequestParam(value = "cvv") final String cvv) {
		
		DateFormat dobFormat = new SimpleDateFormat("yyyy-MM-DD");
		DateFormat expiryFormat = new SimpleDateFormat("yyyy-MM");
		
		Calendar dobCal = Calendar.getInstance();
		Calendar expCal = Calendar.getInstance();
		try {
			dobCal.setTime(dobFormat.parse(dob));
			expCal.setTime(expiryFormat.parse(ccExpiry));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		System.out.println(username);
		System.out.println(password);
		System.out.println(name);
		System.out.println(surname);
		System.out.println(dobCal.toString());
		System.out.println(user_type);
		System.out.println(ccNumber);
		System.out.println(ccExpiry.toString());
		System.out.println(cvv);
		
		boolean isPremium = (user_type.equals("user_premium")) ? true : false;
		
		UserValidator validator = new UserValidatorImpl();
		validator.setUserManager(myuser);
		myuser.setUserValidator(validator);
		boolean regSuccess = myuser.registerUser(username, password, name, surname, dobCal, isPremium, ccNumber, expCal, cvv);
		System.out.println(myuser.getUserAccount(username).isPremium());
		return new ResponseEntity<HttpStatus>((regSuccess) ? HttpStatus.OK : HttpStatus.NOT_ACCEPTABLE);

	}

	
	@RequestMapping (value = "/loginUser", method = RequestMethod.POST)
	public @ResponseBody String loginUser(@RequestParam(value = "username") final String username,
												@RequestParam(value = "password") final String password)
	{
		System.out.println(username);
		System.out.println(password);
		myuser.setLoginManager(myloginmanager);
		
		if (myuser.login(username, password))
			return "LOGIN_SUCCESS";
		else if(!myloginmanager.isAccountAccessible(myuser.getUserAccount(username)))
			return "ACCOUNT_BLOCKED"+myuser.getUserAccount(username).unblkTimeLeft()/1000;
		else 
			return "USER_NOT_FOUND";	
	}
	
	@RequestMapping(value = "/placeBet", method = RequestMethod.POST)
	public @ResponseBody String placeBet(@RequestParam(value="username") final String username,
										 @RequestParam(value="risk_level") final String risk_level,
										 @RequestParam(value="amount") final String amount){
		Double dblamount = Double.parseDouble(amount);	
		
		RiskLevel rlevel = null;		
		if(risk_level.equals("Low"))
			rlevel= RiskLevel.LOW;
		else if(risk_level.equals("Medium"))
			rlevel= RiskLevel.MEDIUM;
		else if(risk_level.equals("High"))
			rlevel= RiskLevel.HIGH;				
		
		BetValidator validator = new BetValidatorImpl();
		validator.setUserManager(myuser);
		myuser.setBetValidator(validator);		
		
		UserAccount useraccount = myuser.getUserAccount(username);	
		
		if(myuser.placeBet(username, rlevel,dblamount ))
		return "BET_PLACED";
		else if(!useraccount.isPremium())
		{
			if(!validator.validateAmount(useraccount, dblamount))
				return "AMOUNT_TOO_HIGH";
			else if(!validator.validateRisk(useraccount, rlevel))
				return "RISK_TOO_HIGH";
			else if(!validator.validateNumberOfBets(useraccount))
				return "3_BETS_ALREADY";	
			return "BET_NOT_PLACED";
		}
		else
		{
			if(!validator.validateCumulative(useraccount, dblamount))
				return "CUMULATIVE_REACHED";	
			return "BET_NOT_PLACED";
		}
	}
	
	@RequestMapping(value = "/getBets", method = RequestMethod.GET)
	public @ResponseBody String getBets(@RequestParam(value="username") final String username)
	{
		List<Bet> bets = myuser.getBetsForUser(username);
		String response ="";
		for(Bet b : bets)
		{
			if(response=="")
			response = b.toString();
			else
			response = response + "|" + b.toString();
		}
		
		if(response=="")
		return "0_BETS";
		return response;
	}
	
	@RequestMapping(value= "/deleteUser", method = RequestMethod.POST)
	public String deleteUser(@RequestParam(value="username") final String username)
	{
		
		if(myuser.getNumberOfUsers()!=0)
		{
			System.out.println("Deleting User "+ username);
			myuser.deleteUser(username);
			System.out.println("No of Users: " + myuser.getNumberOfUsers());
		}
		return "OK";
	}
}
