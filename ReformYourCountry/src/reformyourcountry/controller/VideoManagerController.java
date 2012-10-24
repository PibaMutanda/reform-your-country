package reformyourcountry.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import reformyourcountry.model.Article;
import reformyourcountry.repository.VideoRepository;

@Controller
@RequestMapping("/video")
public class VideoManagerController extends BaseController<Article> {

	@Autowired VideoRepository videoRepository;
	
	@RequestMapping("/manager")
	public ModelAndView deleteManager(@RequestParam("id") Long articleId){
		
		ModelAndView mv = new ModelAndView("videomanager");
		Article article = this.getRequiredEntity(articleId);
		mv.addObject("article", article);
		return mv;
	}
		
	@RequestMapping("/delete")
	public ModelAndView delete(@RequestParam("videoId") Long videoId,
										@RequestParam("articleId") Long articleId){
		
		videoRepository.remove(videoRepository.find(videoId));
		return new ModelAndView("redirect:/video/manager","id",articleId);
	}
	
}
