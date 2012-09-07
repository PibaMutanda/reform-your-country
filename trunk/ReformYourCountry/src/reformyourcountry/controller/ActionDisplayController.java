package reformyourcountry.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import reformyourcountry.model.Action;

@Controller
public class ActionDisplayController extends BaseController<Action> {

    @RequestMapping("action")
    public ModelAndView displayAction(@RequestParam("id") long actionId) {
        Action action = getRequiredEntity(actionId);
        ModelAndView mv = new ModelAndView("actiondisplay");
        mv.addObject("action", action);
        return mv;
    }

}
