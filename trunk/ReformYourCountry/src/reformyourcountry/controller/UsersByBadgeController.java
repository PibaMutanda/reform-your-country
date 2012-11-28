package reformyourcountry.controller;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import reformyourcountry.model.Badge;
import reformyourcountry.model.BadgeType;
import reformyourcountry.repository.BadgeRepository;

@Controller
public class UsersByBadgeController extends BaseController<Badge> {
    @Autowired BadgeRepository badgesRepository;
    @RequestMapping("/usersbybadge")
    public ModelAndView usersByBadge(@RequestParam("badgeType")String badgeT){
                                    
        BadgeType badgeType = BadgeType.valueOf(badgeT);
        badgeType.getBadgeTypeLevel();
        
        
        ModelAndView mv=new ModelAndView("usersbybadge");
        mv.addObject("badgeType",badgeType);
        mv.addObject("badges",badgesRepository.findTypeBadge(badgeType));
        
        return mv;
    }

}
