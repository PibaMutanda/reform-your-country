package reformyourcountry.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import reformyourcountry.model.Action;
import reformyourcountry.model.Video;
import reformyourcountry.repository.VideoRepository;
import reformyourcountry.security.Privilege;
import reformyourcountry.security.SecurityContext;


@Controller
@RequestMapping("/video")
public class VideoEditController extends BaseController<Video> {
    
    
   @Autowired VideoRepository videoRepository;



   @RequestMapping(value={"/edit","/create"})
   public ModelAndView videoCreate(@ModelAttribute Video video){
       SecurityContext.assertUserHasPrivilege(Privilege.EDIT_ARTICLE);
       return new ModelAndView("videoedit", "video", video);
   }

    
    @RequestMapping("/editsubmit")
    public ModelAndView videoEditSubmit(@Valid @ModelAttribute Video video, Errors errors){
        
          if(errors.hasErrors()){
              return new ModelAndView("videoedit","video",video);
          }
          
          if(video.getId()==null){
              videoRepository.persist(video);   
          } else {
              videoRepository.merge(video);
          }
          return new ModelAndView("redirect:/video","id", video.getId());
    }
    
    @ModelAttribute
    public Video findVideo(@RequestParam(value="id", required=false) Long id){
        if (id == null){ //create
            return new Video();
        } else { //edit
            return getRequiredDetachedEntity(id);
        }
    }
}
