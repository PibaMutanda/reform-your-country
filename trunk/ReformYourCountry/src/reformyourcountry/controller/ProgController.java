package reformyourcountry.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
public class ProgController {
    
    @RequestMapping("/prog")
    public String prog(){
        
        return "prog";
    }

}
