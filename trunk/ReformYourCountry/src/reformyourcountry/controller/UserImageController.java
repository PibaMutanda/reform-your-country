package reformyourcountry.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class UserImageController {

	
	@RequestMapping("userimage")
	public ModelAndView userImage(){
		
		return new ModelAndView("userimage");
	}
}
