package reformyourcountry.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import reformyourcountry.model.GoodExample;
import reformyourcountry.repository.GoodExampleRepository;

@Controller
public class GoodExampleListController {

	 @Autowired
	 GoodExampleRepository goodExampleRepository;
	
	@RequestMapping("/goodexample")
	public ModelAndView goodExampleList(){
		
		ModelAndView mv = new ModelAndView("goodexamplelist");
		List<GoodExample> goodExamples = goodExampleRepository.findAllByDate();
		List<GoodExample>hundredGoodExamples = goodExamples.subList(0, (goodExamples.size()>100)?100:goodExamples.size());
		mv.addObject("goodExamples",hundredGoodExamples);
		return mv;
		
	}
	
}
