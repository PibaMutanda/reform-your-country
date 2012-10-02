package reformyourcountry.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
public class FeaturesController {
    
    @RequestMapping("/fonctionnalites")
    public String features(){
        
        return "features";
    }

}
