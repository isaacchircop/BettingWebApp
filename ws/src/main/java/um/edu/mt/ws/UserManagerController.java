package um.edu.mt.ws;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import um.edu.mt.bd.LoginManager;
import um.edu.mt.bd.UserManager;
import um.edu.mt.impl.LoginManagerImpl;
import um.edu.mt.bd.UserValidator;
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
		
		boolean isPremium = (user_type == null) ? true : false;
		
		UserValidator validator = new UserValidatorImpl();
		validator.setUserManager(myuser);
		myuser.setUserValidator(validator);
		boolean regSuccess = myuser.registerUser(username, password, name, surname, dobCal, isPremium, ccNumber, expCal, cvv);
		
		return new ResponseEntity<HttpStatus>((regSuccess) ? HttpStatus.OK : HttpStatus.NOT_ACCEPTABLE);

	}

	
	@RequestMapping (value = "/loginUser", method = RequestMethod.POST)
	public ResponseEntity<HttpStatus> loginUser(@RequestParam(value = "username") final String username,
												@RequestParam(value = "password") final String password)
	{
		System.out.println(username);
		System.out.println(password);

		myuser.setLoginManager(myloginmanager);
		return new ResponseEntity<HttpStatus>((myuser.login(username, password)) ? HttpStatus.OK : HttpStatus.NOT_ACCEPTABLE);
	}
	
}
