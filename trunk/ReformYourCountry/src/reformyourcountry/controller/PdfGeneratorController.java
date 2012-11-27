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
import reformyourcountry.pdf.ArticlePdfGenerator;
import reformyourcountry.repository.ArticleRepository;
import reformyourcountry.security.Privilege;
import reformyourcountry.security.SecurityContext;
import reformyourcountry.service.ArticleService;
import reformyourcountry.util.ArticleTreePdfVisitor;
import reformyourcountry.util.ArticleTreeWalker;

@Controller
public class PdfGeneratorController extends BaseController<Article>{
    
    @Autowired ArticleService articleService;
    @Autowired ArticleRepository articleRepository;

    @RequestMapping("/ajax/pdfgeneration")
    public ModelAndView pdfGeneration(@RequestParam("hassubarticle") boolean hasSubArticle,@RequestParam(value="id",required=false) Long id){
        ModelAndView mv = new ModelAndView("pdfgeneration");
        mv.addObject("hasSubArticle",hasSubArticle);
      
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
       
        if(!article.isPublished() && !SecurityContext.isUserHasPrivilege(Privilege.EDIT_ARTICLE)){
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
            if(article == null){ // if article is null that mean we re trying to generate the pdf from the whole article list page.

                ArticleTreePdfVisitor atv = new ArticleTreePdfVisitor();
                ArticleTreeWalker atw = new ArticleTreeWalker(atv, articleRepository);
                atw.walk();

                cPdf = new ArticlePdfGenerator(atv.getListResult(),cover,toc,onlysummary,includeNotPublished,true);
            }else{
                cPdf = new ArticlePdfGenerator(article,cover,toc,onlysummary,subarticles,includeNotPublished,true);
            }
            ByteArrayOutputStream baos = null;
            if(cPdf != null)
              baos = cPdf.generatePdf();

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
