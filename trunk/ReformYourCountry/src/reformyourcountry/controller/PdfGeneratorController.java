package reformyourcountry.controller;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import reformyourcountry.exception.UnauthorizedAccessException;
import reformyourcountry.model.Article;
import reformyourcountry.model.User;
import reformyourcountry.pdf.ArticlePdfGenerator;
import reformyourcountry.security.Privilege;
import reformyourcountry.security.SecurityContext;
import reformyourcountry.service.ArticleService;

@Controller
public class PdfGeneratorController extends BaseController<Article>{
    
    @Autowired ArticleService articleService;

    @RequestMapping("/ajax/pdfgeneration")
    public ModelAndView pdfGeneration(@RequestParam("hassubarticle") boolean hasSubArticle,@RequestParam("id") long id){
        ModelAndView mv = new ModelAndView("pdfgeneration");
        mv.addObject("hasSubArticle",hasSubArticle);
        mv.addObject("id", id);
        return mv;
    }
    
    @RequestMapping("/ajax/pdfgenerationsubmit")
    public void pdfGenerationSubmit(@RequestParam(value="iscover",required = false) Boolean isCover,
                                                      @RequestParam(value = "istoc",required = false) Boolean isToc,
                                                      @RequestParam(value = "isnotpublished", required = false) Boolean isNotPublished,
                                                      @RequestParam(value = "isonlysummary", required = false) Boolean isOnlysummary,
                                                      @RequestParam(value = "issubarticles", required = false) Boolean isSubarticles,
                                                      @RequestParam("idarticle") long idArticle,
                                                      HttpServletResponse response){
   
        
        Article article = this.getRequiredEntity(idArticle);
       
        if(!article.isPublished() && !SecurityContext.isUserHasPrivilege(Privilege.EDIT_ARTICLE)){
            throw new UnauthorizedAccessException("You're trying to access pdf by url hacking");
        }
        boolean notpublished = isNotPublished != null;
        if (notpublished){
            SecurityContext.assertUserHasPrivilege(Privilege.VIEW_UNPUBLISHED_ARTICLE);
        }
        boolean cover = isCover != null;
        boolean toc = isToc != null;
        
        boolean onlysummary = isOnlysummary != null ;
        boolean subarticles = isSubarticles != null;
        
        ArticlePdfGenerator cPdf = new ArticlePdfGenerator(article,true,cover,toc,onlysummary);
        
        

        ByteArrayOutputStream baos = cPdf.generatePDF();

      //  ByteArrayOutputStream baos = articleService.makePdf(article, cover, toc,onlysummary,notpublished);
        try {
            response.setHeader("Content-Disposition", "attachment; filename=" + article.getUrl()+".pdf");

            response.setContentType("application/pdf");

            if (baos != null){
                response.getOutputStream().write(baos.toByteArray());
            }

        } catch (IOException e) {
          throw new RuntimeException(e);
        }

        // That version of the code created a file in a temp directory to be downloaded by the uer;
        //return new ResponseEntity<String>(UrlUtil.getAbsoluteUrl("")+"gen"+FileUtil.PDF_FOLDER+"/"+article.getUrl()+".pdf",HttpStatus.OK);
    }

}
