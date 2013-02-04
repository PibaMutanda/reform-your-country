package reformyourcountry.controller;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import reformyourcountry.exception.UnauthorizedAccessException;
import reformyourcountry.model.Article;
import reformyourcountry.pdf.ArticlePdfGenerator;
import reformyourcountry.repository.ActionRepository;
import reformyourcountry.repository.ArticleRepository;
import reformyourcountry.repository.BookRepository;
import reformyourcountry.security.Privilege;
import reformyourcountry.security.SecurityContext;
import reformyourcountry.service.ArticleService;

@Controller
public class PdfGeneratorController extends BaseController<Article>{
    
    @Autowired ArticleService articleService;
    @Autowired ArticleRepository articleRepository;
    @Autowired ActionRepository actionRepository;
    @Autowired BookRepository bookRepository;

    @RequestMapping("/ajax/pdfgeneration")
    public ModelAndView pdfGeneration(@RequestParam("hassubarticle") boolean hasSubArticle,@RequestParam(value="id",required=false) Long id,@RequestParam("isfromgenerallist") boolean isfromGeneralList){
        ModelAndView mv = new ModelAndView("pdfgeneration");
        mv.addObject("hasSubArticle",hasSubArticle);
        if(isfromGeneralList){
          
            mv.addObject("isfromgenerallist", isfromGeneralList);
        }
        if(id != null){
         mv.addObject("id", id);
        }
        return mv;
    }
    
    @RequestMapping("/ajax/pdfgenerationsubmit")
    public void pdfGenerationSubmit(@RequestParam(value="iscover",required = false) Boolean isCover,
                                                      @RequestParam(value = "istoc",required = false) Boolean isToc,
                                                      @RequestParam(value = "isnotpublished", required = false) Boolean isNotPublished,
                                                      @RequestParam(value = "isonlysummary", required = false) Boolean isOnlysummary,
                                                      @RequestParam(value = "issubarticles", required = false) Boolean isSubarticles,
                                                      @RequestParam(value="idarticle",required = false) Long idArticle,
                                                      HttpServletResponse response){
        Article article = null;
        if(idArticle != null){ //if id article is null that mean we re trying to generate the pdf from the whole article list page.
            article = this.getRequiredEntity(idArticle);

            if(!article.isPublished() && !SecurityContext.isUserHasPrivilege(Privilege.VIEW_UNPUBLISHED_ARTICLE)){
                throw new UnauthorizedAccessException("You're trying to access pdf by url hacking");
            }
        }
        
        boolean includeNotPublished = isNotPublished != null ? isNotPublished : false;
        if (includeNotPublished){
            SecurityContext.assertUserHasPrivilege(Privilege.VIEW_UNPUBLISHED_ARTICLE);
        }
        boolean cover = isCover != null ? isCover : false;
        boolean toc = isToc != null ? isToc : false;
        
        boolean onlysummary = isOnlysummary != null ? isOnlysummary: false;
        boolean subarticles = isSubarticles != null ? isSubarticles : false;
        
        ArticlePdfGenerator cPdf = null;
        try {
            
            cPdf = new ArticlePdfGenerator(article,articleRepository,actionRepository,bookRepository,cover,toc,onlysummary,subarticles,includeNotPublished,true);
          
            ByteArrayOutputStream baos = null;
            if(cPdf != null)  {
              baos = cPdf.generatePdf();
            }
            
            // Put the outpustream in the response.
            response.setHeader("Content-Disposition", "attachment; filename=" + (article != null ? article.getUrl() : "Enseignement2_be")+".pdf");
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
