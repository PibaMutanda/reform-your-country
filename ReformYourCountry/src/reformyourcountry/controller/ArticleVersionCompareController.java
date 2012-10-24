package reformyourcountry.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import reformyourcountry.maintest.DiffGenerator;
import reformyourcountry.model.Action;
import reformyourcountry.model.Article;
import reformyourcountry.model.ArticleVersion;
import reformyourcountry.repository.ArticleRepository;
import reformyourcountry.repository.ArticleVersionRepository;

@Controller

public class ArticleVersionCompareController extends BaseController<ArticleVersion>{

	@Autowired ArticleRepository articleRepository;
	@Autowired ArticleVersionRepository articleVersionRepository;
	
	
	@RequestMapping("/article/versioncompare")
	public ModelAndView articleVersionCompareDisplay(@RequestParam("id") long id, @RequestParam(value="id2",required=false) Long id2){
		ModelAndView mv = new ModelAndView("articleversioncompare");
	
		ArticleVersion av = getRequiredEntity(id);
		Article article = av.getArticle();
		
		mv.addObject("article",article);	
		List<ArticleVersion> versionList = articleVersionRepository.findAllVersionForAnArticle(articleRepository.findByUrl(article.getUrl()));
		        
		Collections.reverse(versionList);
        mv.addObject("versionList",versionList);
        
        /// Add for the JSP an array with both articles.
        List<ArticleVersionAndText> twoArticleVersionAndTexts = new ArrayList<ArticleVersionAndText>();  // 0 = left, 1 = right
        mv.addObject("twoArticleVersionAndTexts", twoArticleVersionAndTexts);
        twoArticleVersionAndTexts.add(new ArticleVersionAndText());
        twoArticleVersionAndTexts.add(new ArticleVersionAndText());
        
        twoArticleVersionAndTexts.get(0).articleVersion = av;
        if(id2!=null){
            twoArticleVersionAndTexts.get(1).articleVersion = getRequiredEntity(id2);
		} else {
            twoArticleVersionAndTexts.get(1).articleVersion = article.getLastVersion();
		}
		
        DiffGenerator diffGenerator = new DiffGenerator(twoArticleVersionAndTexts.get(0).articleVersion.getContent(),
        		                                        twoArticleVersionAndTexts.get(1).articleVersion.getContent());
        twoArticleVersionAndTexts.get(0).text = diffGenerator.getTextOriginalHtmlDiff(true);
        twoArticleVersionAndTexts.get(1).text = diffGenerator.getTextModifiedHtmlDiff(true);
        
		return mv;
	}
	
	public static class ArticleVersionAndText {
		ArticleVersion articleVersion;
		String text;
		// Need getters for EL.
		public String getText() {
			return text;
		}
		public ArticleVersion getArticleVersion() {
			return articleVersion;
		}
	}

	
}
