package reformyourcountry.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ConfirmAccountController {
	//when a user try to log in, for example with a facebook account, he is invited to confirm the creation of a new account on enseignement2
	@RequestMapping("/confirmaccount")  
	public String confirmaccount(){
		return "confirmaccount";
	}
}
