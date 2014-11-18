package um.edu.mt.ws;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class UserManagerController {

	@RequestMapping (value = "/registerUser", method = RequestMethod.POST)
	public ResponseEntity<HttpStatus> addMessage(){
		System.out.println("Hello World!");
		return new ResponseEntity<HttpStatus>(HttpStatus.OK);
	}
	
}
