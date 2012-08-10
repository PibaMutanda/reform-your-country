package reformyourcountry.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class IndexController {
	@RequestMapping(value={"/index","/INDEX","/Index","/"})
	public String index(){
		return "index";
	}
}
