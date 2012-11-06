package reformyourcountry.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;

import reformyourcountry.model.BaseEntity;
import reformyourcountry.model.User.Role;
import reformyourcountry.repository.ArticleRepository;
import reformyourcountry.security.SecurityContext;
import reformyourcountry.service.IndexManagerService;
import reformyourcountry.service.SearchService;

@Controller
@RequestMapping("/admin")
public class AdminController  extends BaseController<BaseEntity> {//used for setMessage, DON'T USE TO GET ENTITIES
	
	@Autowired SearchService searchService;
	@Autowired IndexManagerService indexManagerService;
	@Autowired ArticleRepository articleRepository;
	
	@RequestMapping("")
	 public ModelAndView adminDisplay(){
		SecurityContext.assertUserHasRole(Role.ADMIN);
        ModelAndView mv = new ModelAndView("admin");
        return mv;
    }
	
	/** Used by an administrator to recreate the indexes */
	@RequestMapping("/createIndex")
	public ModelAndView createIndex(WebRequest request){
		
		try {
			indexManagerService.removeIndexes();
		} finally {
			indexManagerService.createIndexes();
		}
		addNotificationMessage("L'index est construit.", request);
		
		return new ModelAndView("redirect:/admin");
	}
	

}
