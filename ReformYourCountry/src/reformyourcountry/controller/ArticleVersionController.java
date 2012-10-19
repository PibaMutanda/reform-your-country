package reformyourcountry.controller;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

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
    
    @RequestMapping(value={"/{articleUrl}"})
    public ModelAndView displayArticleVersion(@PathVariable("articleUrl") String articleUrl){
        SecurityContext.assertUserHasPrivilege(Privilege.EDIT_ARTICLE);
        ModelAndView mv = new ModelAndView("articleversionlist");
        
        List<ArticleVersion> versionList = articleVersionRepository.findAll(articleRepository.findByUrl(articleUrl));
        
        Collections.reverse(versionList);//we must begin by the last element of the list
        mv.addObject("versionList",versionList);
        return mv;
    }

}
