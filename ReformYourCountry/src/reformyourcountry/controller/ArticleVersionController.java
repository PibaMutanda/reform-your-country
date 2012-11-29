package reformyourcountry.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import reformyourcountry.model.Article;
import reformyourcountry.model.ArticleVersion;
import reformyourcountry.repository.ArticleRepository;
import reformyourcountry.repository.ArticleVersionRepository;
import reformyourcountry.security.Privilege;
import reformyourcountry.security.SecurityContext;

@Controller
@RequestMapping(value={"/article/version"})
public class ArticleVersionController {
    
	@Autowired ArticleRepository articleRepository;
    @Autowired ArticleVersionRepository articleVersionRepository;
    
    // All the version of a given article
    @RequestMapping(value={"/{articleUrl}"})
    public ModelAndView displayArticleVersion(@PathVariable("articleUrl") String articleUrl){
        
    	SecurityContext.assertUserHasPrivilege(Privilege.MANAGE_ARTICLE);
        ModelAndView mv = new ModelAndView("articleversionlist");
        Article article = articleRepository.findByUrl(articleUrl);
        List<ArticleVersion> versionList = articleVersionRepository.findAllVersionForAnArticle(article);
        //Collections.reverse(versionList);//we must begin by the last element of the list
        mv.addObject("versionList", versionList);
        mv.addObject("parentsPath", article.getPath()); // For the breadcrumb
        return mv;
    }
    
    // The last versions of all articles together;
    @RequestMapping("/changelog")
    public ModelAndView displayArticleVersion(){
          	
        SecurityContext.assertUserHasPrivilege(Privilege.MANAGE_ARTICLE);
    	ModelAndView mv = new ModelAndView("articleversionlist");
        List<ArticleVersion> versionList = articleVersionRepository.findAll(500);
        mv.addObject("versionList",versionList);
        mv.addObject("changeLog", true); //to say to the articleversionlist.jsp that we are in changeLog mode
        return mv;
    }

}
