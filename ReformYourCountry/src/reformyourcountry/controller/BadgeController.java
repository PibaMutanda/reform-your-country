package reformyourcountry.controller;


//import org.springframework.beans.factory.annotation.Autowired;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import reformyourcountry.model.Badge;
import reformyourcountry.model.BadgeType;
import reformyourcountry.model.BadgeTypeLevel;
//import reformyourcountry.repository.BadgeRepositorty;
import reformyourcountry.repository.BadgeRepository;


@Controller
@RequestMapping("/badge")
public class BadgeController extends BaseController<Badge> {

	@Autowired BadgeRepository badgeRepository;

	@RequestMapping("/")
	public ModelAndView badgeDisplayAll() {

	    ModelAndView mv = new ModelAndView("badge");
	    
		////// Builds a map with badges and count for each badge.
		Map<BadgeType,Long> badgeCountMap = new EnumMap<BadgeType,Long>(BadgeType.class);	// An EnumMap keeps the natural order of the enum for the keys (countrary to an HashMap).
		for(BadgeType badgeType : BadgeType.values()){
			long count = badgeRepository.countBadges(badgeType);
			badgeCountMap.put(badgeType, count);
		}
		mv.addObject("badgeCountMap",badgeCountMap );
		
        ArrayList<BadgeTypeLevel> badgeTypeLevelList=new ArrayList<>();
        
		for(BadgeTypeLevel badgeTypeLevel:BadgeTypeLevel.values()){
		    
		    badgeTypeLevelList.add(badgeTypeLevel);
		}
		
		mv.addObject("badgeTypeLevelList",badgeTypeLevelList);
		///// Builds a list of BadgeTypeLevel to display the legend.
		
		
		return mv;

	}	
}
