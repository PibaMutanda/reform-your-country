package reformyourcountry.controller;


//import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import reformyourcountry.badge.BadgeType;
import reformyourcountry.model.Badge;
//import reformyourcountry.repository.BadgeRepositorty;


@Controller
@RequestMapping("/badge")
public class BadgeController extends BaseController<Badge> {

	// @Autowired BadgeRepositorty badgeRepositorty;

	@RequestMapping("/")
	public ModelAndView badgeDisplayAll() {
		return new ModelAndView("badge", "badges", BadgeType.values());

	}

}
