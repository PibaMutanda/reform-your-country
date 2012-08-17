package reformyourcountry.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ImageUploadController {

    
    @RequestMapping("/imageupload")
    public String imageUpload()
    {
        return "imageUpload";
    }
    
    @RequestMapping("/imageuploadsubmit")
    public String imageUploadSubmit()
    {
        return "imageUpload";
    }
}
