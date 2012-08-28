package reformyourcountry.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import org.springframework.web.servlet.ModelAndView;

import reformyourcountry.model.User;
import reformyourcountry.repository.UserRepository;

@Controller
public class UserListDisplayController {

	@Autowired
	UserRepository userrepository;
	
	@RequestMapping("/userlistdisplay")
	public ModelAndView userListDisplay(){
		List<User> userlist=userrepository.findAll();
		ModelAndView mv=new ModelAndView("userlistdisplay");
		mv.addObject("userlist",userlist);
		return mv;
	}
	
	@RequestMapping("/userbynamedisplay")
	public ModelAndView userByNameDisplay(@RequestParam("username") String username){
		
		String errorMsg = null;
		ModelAndView mv=new ModelAndView("userlistdisplay");
		
		if (userrepository.getUserByUserName(username)!=null) {
			List<User> usersList=userrepository.findAllUsersByName(username);
			
			mv.addObject("userlist",usersList);
			
		} else {
			errorMsg="L'utilisateur  avec le nom "+username+"  n'existe pas.";
			List<User> userlist=userrepository.findAll();
		
			mv.addObject("userlist",userlist);
			mv.addObject("errorMsg",errorMsg);
		}
		return mv;
	}
		
}
