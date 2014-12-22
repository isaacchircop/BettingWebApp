package um.edu.mt.ws;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

@RestController
public class UserManagerController {
	
	private UserManager myuser = new UserManagerImpl("bets.dat", "users.dat");
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
		
		System.out.println(username + " Register");
		
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
		
		boolean isPremium = user_type.equals("user_premium");
		
		UserValidator validator = new UserValidatorImpl();
		validator.setUserManager(myuser);
		myuser.setUserValidator(validator);

		boolean regSuccess = myuser.registerUser(username, password, name, surname, dobCal, isPremium, ccNumber, expCal, cvv);
		return new ResponseEntity<HttpStatus>((regSuccess) ? HttpStatus.OK : HttpStatus.NOT_ACCEPTABLE);
	}

	
	@RequestMapping (value = "/loginUser", method = RequestMethod.POST)
	public ResponseEntity<String> loginUser(@RequestParam(value = "username") final String username,
							@RequestParam(value = "password") final String password) {
		
		System.out.println(username + " Login");

		myuser.setLoginManager(myloginmanager);

		if (myuser.login(username, password)) {
			return new ResponseEntity<String>(HttpStatus.OK);
		} else {
			UserAccount account = myuser.getUserAccount(username);
			if(!myloginmanager.isAccountAccessible(account)) {
				return new ResponseEntity<String>(myloginmanager.getErrorMessage(), HttpStatus.FORBIDDEN);
			} else {
				return new ResponseEntity<String>(myloginmanager.getErrorMessage(), HttpStatus.FORBIDDEN);
			}
		}

	}
	
	@RequestMapping(value = "/placeBet", method = RequestMethod.POST)
	public ResponseEntity<String> placeBet(@RequestParam(value="username") final String username,
										   @RequestParam(value="risk_level") final String risk_level,
										   @RequestParam(value="amount") final String strAmount){
		
		System.out.println(username + " Place Bet");

		Double amount = Double.parseDouble(strAmount);
		RiskLevel risk = RiskLevel.valueOf(risk_level.toUpperCase());

		BetValidator validator = new BetValidatorImpl();
		validator.setUserManager(myuser);
		myuser.setBetValidator(validator);

		UserAccount useraccount = myuser.getUserAccount(username);

		// Can be improved by getting most recent error message from bet validator
		if(myuser.placeBet(username, risk, amount)) {
			return new ResponseEntity<String>(HttpStatus.ACCEPTED);
		} else {
			if(!validator.validateAmount(useraccount, amount)){
				return new ResponseEntity<String>("Bet amount exceeds maximum limit", HttpStatus.FORBIDDEN);
			}

			if(!validator.validateRisk(useraccount, risk)) {
				return new ResponseEntity<String>("Invalid Risk Level for user account type", HttpStatus.FORBIDDEN);
			}

			if(!validator.validateNumberOfBets(useraccount)) {
				return new ResponseEntity<String>("Maximum number of bets reached", HttpStatus.FORBIDDEN);
			}

			if (useraccount.isPremium() && !validator.validateCumulative(useraccount, amount)) {
				return new ResponseEntity<String>("Maximum number of cumulative bets amount reached", HttpStatus.FORBIDDEN);
			}

			throw new IllegalStateException("Bet not placed for no reason at all!");
		}
	}
	
	@RequestMapping(value = "/getBets", method = RequestMethod.GET)
	public List<Bet> getBets(@RequestParam(value="username") final String username)	{
		return myuser.getBetsForUser(username);
	}
	
	@RequestMapping(value = "/logOut", method = RequestMethod.POST)
	public ResponseEntity<String> placeBet(@RequestParam(value="username") final String username){
			System.out.println(username + " LogOut");
			myuser.logout(username);
			return new ResponseEntity<String>(HttpStatus.OK);
		
	}

}
