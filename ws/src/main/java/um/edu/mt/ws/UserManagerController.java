package um.edu.mt.ws;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class UserManagerController {

	@RequestMapping (value = "/registerUser", method = RequestMethod.POST)
	public ResponseEntity<HttpStatus> regiserUser(@RequestParam(value = "username") final String username,
												  @RequestParam(value = "password") final String password,
												  @RequestParam(value = "name") final String name,
												  @RequestParam(value = "surname") final String surname){
		System.out.println(username);
		System.out.println(password);
		System.out.println(name);
		System.out.println(surname);
		return new ResponseEntity<HttpStatus>(HttpStatus.OK);
	}
	
}
