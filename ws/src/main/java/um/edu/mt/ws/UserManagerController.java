package um.edu.mt.ws;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import um.edu.mt.bd.LoginManager;
import um.edu.mt.bd.UserManager;
import um.edu.mt.impl.LoginManagerImpl;
import um.edu.mt.impl.UserManagerImpl;

@Controller
public class UserManagerController {
	
	UserManager myuser = new UserManagerImpl();
	LoginManager myloginmanager = new LoginManagerImpl();
	
	@RequestMapping (value = "/registerUser", method = RequestMethod.POST)
	public String regiserUser(@RequestParam(value = "username") final String username,
												  @RequestParam(value = "password") final String password,
												  @RequestParam(value = "name") final String name,
												  @RequestParam(value = "surname") final String surname,
												  @RequestParam(value = "dob") final String dob,
												  @RequestParam(value = "user_type") final String user_type)
												  {
		System.out.println(username);
		System.out.println(password);
		System.out.println(name);
		System.out.println(surname);
		System.out.println(dob);
		System.out.println(user_type);
		return "hello";	
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
