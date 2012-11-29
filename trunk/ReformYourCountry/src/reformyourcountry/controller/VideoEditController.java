package reformyourcountry.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;


import reformyourcountry.model.Video;
import reformyourcountry.repository.ArticleRepository;
import reformyourcountry.repository.VideoRepository;
import reformyourcountry.security.Privilege;
import reformyourcountry.security.SecurityContext;


@Controller
@RequestMapping("/video")
public class VideoEditController extends BaseController<Video> {
    
    
   @Autowired VideoRepository videoRepository;
   @Autowired ArticleRepository articleRepository;



   @RequestMapping(value={"/edit","/create"})
   public ModelAndView videoCreate(@ModelAttribute Video video){
       SecurityContext.assertUserHasPrivilege(Privilege.MANAGE_ARTICLE);
       ModelAndView mv = new ModelAndView("videoedit");
       mv.addObject("video",video);
       mv.addObject("article",video.getArticle());
       return mv;
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
          return new ModelAndView("redirect:/video/manager","id", video.getArticle().getId());
    }
    
    @ModelAttribute
    public Video findVideo(@RequestParam(value="idVideo", required=false) Long idVideo,
                           @RequestParam(value="idArticle", required=false) Long idArticle)  // In case of idVideo is null (creation), we need to know for which article; 
    {
    	if (idVideo == null){ //create
    		Video video =  new Video();
            video.setArticle(articleRepository.find(idArticle));
            return video;
        } else { //edit
            return getRequiredDetachedEntity(idVideo);
        }
    }
}
