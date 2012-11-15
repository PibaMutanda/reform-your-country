package reformyourcountry.controller;


//import org.springframework.beans.factory.annotation.Autowired;
import java.util.EnumMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import reformyourcountry.badge.BadgeType;
import reformyourcountry.model.Badge;
//import reformyourcountry.repository.BadgeRepositorty;
import reformyourcountry.repository.BadgeRepository;


@Controller
@RequestMapping("/badge")
public class BadgeController extends BaseController<Badge> {

	@Autowired BadgeRepository badgeRepository;

	@RequestMapping("/")
	public ModelAndView badgeDisplayAll() {

		////// Builds a map with badges and count for each badge.
		Map<BadgeType,Long> badgeCount = new EnumMap<BadgeType,Long>(BadgeType.class);	// An EnumMap keeps the natural order of the enum for the keys (countrary to an HashMap).
		for(BadgeType badgeType : BadgeType.values()){
			long count = badgeRepository.countBadges(badgeType);
			badgeCount.put(badgeType, count);
		}
		
		return new ModelAndView("badge", "badges", badgeCount);

	}

}
