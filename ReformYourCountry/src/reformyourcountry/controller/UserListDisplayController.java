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
	public ModelAndView userByNameDisplay(@RequestParam("name") String name){
		
		String errorMsg = null;
		ModelAndView mv=new ModelAndView("userlistdisplay");
		List<User> usersList=userrepository.searchUsers(name);
		if(usersList.isEmpty()){
			errorMsg="Il n'existe aucun utilisateur ayant "+name+" comme username,firstname ou lastname.";
			usersList=userrepository.findAll();
		}
		mv.addObject("userlist",usersList);
		mv.addObject("errorMsg",errorMsg);
		
		return mv;
	}
		
}
