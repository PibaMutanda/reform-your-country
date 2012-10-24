package reformyourcountry.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import reformyourcountry.model.Article;
import reformyourcountry.model.Video;
import reformyourcountry.repository.VideoRepository;

@Controller
@RequestMapping("/video")
public class VideoManagerController extends BaseController<Article> {

	@Autowired VideoRepository videoRepository;
	
	@RequestMapping("/manager")
	public ModelAndView manager(@RequestParam("id") Long articleId){
		
		ModelAndView mv = new ModelAndView("videomanager");
		Article article = this.getRequiredEntity(articleId);
		mv.addObject("article", article);
		return mv;
	}
		
	@RequestMapping("/delete")
	public ModelAndView delete(@RequestParam("videoId") long videoId) {
		Video video = videoRepository.find(videoId);
		Article article = video.getArticle();
		videoRepository.remove(video);
		return new ModelAndView("redirect:/video/manager","id",article.getId());
	}
	
}
