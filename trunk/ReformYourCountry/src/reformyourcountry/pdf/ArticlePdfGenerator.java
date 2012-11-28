package reformyourcountry.pdf;

import java.awt.Insets;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.StringReader;
import java.net.URL;
import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.imageio.ImageIO;

import org.zefer.pd4ml.PD4ML;
import org.zefer.pd4ml.PD4PageMark;

import reformyourcountry.model.Article;
import reformyourcountry.repository.ArticleRepository;
import reformyourcountry.security.Privilege;
import reformyourcountry.security.SecurityContext;
import reformyourcountry.util.ArticleTreePdfVisitor;
import reformyourcountry.util.ArticleTreeWalker;
import reformyourcountry.util.DateUtil;
import reformyourcountry.web.UrlUtil;



/** Generates an html String with course sections, and use PD4ML to transform the html into a pdf document */ 
public class ArticlePdfGenerator {

	//default cover page image link
	public static String COVER_PAGE_IMG = UrlUtil.getAbsoluteUrl("")+"/images/logo/enseignement2-logo-black.png";//="/VAADIN/themes/blackbelt/image/bgphoto/asianWomanSword.jpg";

	

	private PD4ML pd4ml;

	private String logoUrl;
	private BufferedImage logoImage;

	private boolean doTheUserWantACoverPage;
    private boolean doTheUserWantAToc;
    private boolean doTheUserWantOnlySummary;
    private boolean doTheUserWantUnpublishedArticles;

    private List<Article> articles;
	public boolean enableDebug ;
	private URL url;
	private  BufferedImage img;
	private String title;

	ArticleRepository articleRepository;
	
	private final String CSS = 
			"h1 { "+
			"font-size: 2.0em;"+
			"font-family: Colaborate, Arial, sans-serif;"+
			"}"+
			"h2 { "+
			"font-size: 1.9em;"+
			"font-weight: normal;"+
			"font-family: Colaborate, Arial, sans-serif;"+
			"}"+
			"h3 {"+
			"font-size: 18px;"+
			"font-family: Colaborate, Arial, sans-serif;"+
			"}"+
			"h4 {"+
			"font-size: 15px;"+
			"font-family: Colaborate, Arial, sans-serif;"+
			"}"+
			"#gardeTitle{"+
			"font-size:32;"+
			"}"+
			"body{"+
			"font-family:Georgia, \"DejaVu Serif\", Norasi, serif;"+
			"color: #444;"+
			"display: block;"+
			"font-size:15px;"+
			"}"+
			"strong, b {"+
			 "font-weight: bold;"+
			 "}"+
			"p{"+
			"line-height:17px;"+
			"}"+
			".titre{"+
			"font-size:8px;"+
			"font-family:Arial,Times New Roman;"+
			"vertical-align:top;"+ 
			"}"+
			".logo{"+
			"font-size:32;"+
			"font-family:helvetica,Aharoni,Impact;"+
			"}"+
			".grey{"+
			"color:#787878;"+
			"font-size:10px;"+
			"}"+
			"pre.contentProgramListing {"+
			"background-color: #EEE;"+
			"margin-bottom: 14px;"+
			"margin-left: 2px;"+
			"padding: 14px;"+
			"font-family: 'Courier New';"+
			"font-size: 12px; /* smaller to have longer lines */"+
			"overflow: auto; /* scroll bar if too wide */"+
			"}"+
			".contentQuote {"+
			"background-color: #EEE;"+
			"margin-bottom: 14px;"+
			"margin-left: 2px;"+
			"padding: 14px;"+
			"font-family: Times;"+
			"font-style: italic;"+
			"font-size: 12px; /* smaller to have longer lines */"+
			"overflow: auto; /* scroll bar if too wide */"+
			"}"+
			".licence {"+
			"font-size:8px;"+
			"color: #9E9E9E;"+
			"vertical-align: bottom;"+
			"padding-left: 15px;"+
			"}"+
			".valignTop{vertical-align:top;"+
			".valignBottom{"+
			"vertical-align: bottom;"+
			"}"+
			".valignMiddle{"+
			"vertical-align:middle;"+
			"}"+
			".small{"+
			"font-size:10px;"+
			"}"+
			".bibref-after-block {"+
			"color: #888;"+
			"font-family: Arial, Helvetica, \"Liberation Sans\", FreeSans, sans-serif;"+
			"font-size: 0.6em;"+
			"text-align: right;"+
			"margin-left: 50px;"+
			"margin-bottom:1em;"+
			"}"+
			".quote-block {"+
			"margin: 0;"+
			"margin-right: 40px;"+
			"margin-left: 40px;"+
			"margin-bottom:1em;"+
			"}"+
			".quote {"+
			"color: #666;"+
			"font-family: \"Times New Roman\", Times, \"Liberation Serif\", FreeSerif, serif;"+
			"font-size: 1.05em;"+
			"}"+

			"#content a, #sidebar a, .content_full_width a, p a strong {"+
			"color: #7D92B9;"+
			"}"+

		    "#main .blog_wrap h1, #main .single_blog_wrap h1, #main .comment-title, .four_o_four, .callout-wrap span, .video-sub h2, .two-d-sub h2, .three-d-sub h2, .main-holder h1, .main-holder h2, .main-holder h3, .main-holder h4, .main-holder h5, .main-holder h6, .search-title, .home-banner-main h2, #main .portfolio_full_width h3, #main .frame h1, #main .month, .home-bnr-jquery-content h2, .callout2, #main .contact_iphone h4, .comment-author-about {"+
			"font-family: Colaborate, Arial, sans-serif;"+
			 "}"+
			".article_summary {"+        
             "margin-top: 0px;"+
             "margin-right: -0.5em;"+
             "margin-bottom: 0px;"+
             "margin-left: -0.5em;"+
             "padding-left: .5em;"+
             "padding-right: .5em;"+
             "display: inline-block;"+
             "width: 100%;"+
             "}";

	/** @Param article article to print. If null, we print all articles. 
	 * Articles that the user may not see are skipped. */
	
	public ArticlePdfGenerator(Article article, ArticleRepository aRepository,
	        boolean doTheUserWantACoverPage,boolean doTheUserWantAToc,boolean doTheUserWantOnlySummary,boolean doTheUserwantSubArticles,boolean doTheUserWantUnpublishedArticles,boolean enableDebug){
	    articleRepository = aRepository;
	    
	    ////// 1. Builds the list of articles to print
	    List<Article> unFilteredArticles = new ArrayList<Article>();
        if (article == null){ // if article is null that mean we re trying to generate the pdf from the whole article list page.
            // Collect list of all articles.
            ArticleTreePdfVisitor atv = new ArticleTreePdfVisitor();
            ArticleTreeWalker atw = new ArticleTreeWalker(atv, articleRepository);
            try {
                atw.walk();
            } catch (IOException e) {
               throw new RuntimeException(e);
            }
            unFilteredArticles.addAll(atv.getListResult());
            Date today = new Date();
            title = "Tout les articles au "+DateUtil.formatddMMyyyy(today);
        } else {
            unFilteredArticles.add(article);
            if(doTheUserwantSubArticles){
                unFilteredArticles.addAll(article.getChildren());
             }
            title = article.getTitle();
        }

        ////// 2. We filter out the articles that the user may not see.
        articles = new ArrayList<Article>();
        for (Article art : unFilteredArticles){
            if(art.isPublished() || 
                    (SecurityContext.isUserHasPrivilege(Privilege.VIEW_UNPUBLISHED_ARTICLE) && doTheUserWantUnpublishedArticles) ){
                articles.add(art);                
            }
        }
        
        init(doTheUserWantACoverPage,doTheUserWantAToc,doTheUserWantOnlySummary,doTheUserWantUnpublishedArticles,enableDebug);
   }
        
        
	
	
    private void init(boolean doTheUserWantACoverPage,boolean doTheUserWantAToc,boolean doTheUserWantOnlySummary,boolean doTheUserWantUnpublishedArticles,boolean enableDebug){
	    
	    this.pd4ml = new PD4ML();
        this.enableDebug = enableDebug;
        this.doTheUserWantACoverPage = doTheUserWantACoverPage;
        this.doTheUserWantAToc = doTheUserWantAToc;
        this.doTheUserWantOnlySummary = doTheUserWantOnlySummary;
        this.doTheUserWantUnpublishedArticles = doTheUserWantUnpublishedArticles;
        
        try {
            url = new URL(COVER_PAGE_IMG);
            img = ImageIO.read(url);
        } catch (Exception e) {
           throw new RuntimeException(e);
        }
        if (enableDebug) {
            pd4ml.enableDebugInfo();
        }

        /** PDF document setting */
        pd4ml.addStyle(CSS,true);
        try {
            pd4ml.useTTF( "java:fonts", true );  // Looks for fonts.jar in classpath (WEB-INF/lib)
        } catch (FileNotFoundException e) {
            throw new RuntimeException("Problem while trying PD4ML to get font file", e);
        }  
     
        pd4ml.setDefaultTTFs("Times New Roman", "Arial", "Courier New");  
        pd4ml.enableImgSplit(false);
        pd4ml.setPageInsets(new Insets(25,50,25,60));
	}

    private String createContent(Article article){

        String content ="";
        content += "<div id=#article ><H1>"+article.getTitle()+"</H1></br></br>"+article.getLastVersionRenderdSummary();

        if(!doTheUserWantOnlySummary){
            content += "<H1>Article - "+article.getTitle()+"</H1></br></br>"+article.getLastVersionRenderedContent();
        }
        content += "</div><pd4ml:page.break>"; 
        return content;
    }

	public ByteArrayOutputStream generatePdf() {
	    String content = "";

	    for(Article article : articles){
                content += createContent(article);
        }
  
        // first we add the begining of the html document start with <html> to <body>
		String head ="<!DOCTYPE html><head><title>Enseignement2.be - "+title+"</title></head><body>";

		// next we add the cover html 
		if(doTheUserWantACoverPage){
		    head += createCoverHtml();
		}

		//next we add the table of content
		if(doTheUserWantAToc){
		    head += createToc();
		}
		
        createHeader();

        createFooter();

		//after the cover we add the rest of the html document
		String finalresult  =  head+content+"</body></html>";
		// generate the pdf in binary output stream from html
		 ByteArrayOutputStream  baos = new ByteArrayOutputStream();
		try {
			pd4ml.render(new StringReader( finalresult), baos);
			System.out.println(finalresult);
		} catch (InvalidParameterException | IOException e) {
			throw new RuntimeException(e);
		}
		
		return baos;
	}




	/** creation of the header */
	public void createHeader(){
	    
		// ${title} will be recognized/replaced by PD4ML.
		String headerHtmlTemplate = "";/*"<table class='header' border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\">"
				+ "<tr>"
				+ "<td style='width:100%' ><span class =\"titre\">Enseignement2.be - "+article.getTitle()+"</span></td>"
				+"<td>"+ getHtmlResizedImg(img,false,url)+"</td>"
				+ "</tr>" + "</table>";*/
		
		PD4PageMark header = new PD4PageMark();
		header.setAreaHeight(45);
		
		// For counter: we start counting the pages at 1. (first page is 1)
		header.setInitialPageNumber(1);

		if (doTheUserWantACoverPage){ //If the user wants a cover page
			header.setPagesToSkip(1);  // No header on the cover page.
		}

		header.setHtmlTemplate(headerHtmlTemplate);
		pd4ml.setPageHeader(header);
	}

	/** creation of the footer */
	public void createFooter(){
		// ${page} and ${total} will be recognized/replaced by PD4ML.
	    
	    String footerHtmlTemplate = "<table  border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" height ='30px'>"
        + "<tr>"
        + "<td >"+ getHtmlResizedImg(img,false,url)+"</td>"
        +"<td style='width:90%;padding-left:5px;text-align:center'><span class =\"titre\">"+title+"</span></td>"
        +"<td style='width:10%;text-align:right' class='valignBottom'>${page}<span class='grey'> /${total}</span></td>"
        + "</tr>" + "</table>";
	
		PD4PageMark foot = new PD4PageMark();
		
		if(doTheUserWantACoverPage){ //If the user want a cover page
			foot.setPagesToSkip(1); //Skip the header at the first page
		}
		foot.setInitialPageNumber(1);

		foot.setHtmlTemplate(footerHtmlTemplate);
		foot.setAreaHeight(30); //Adjust the height
		pd4ml.setPageFooter(foot); //Add footer
	}

	
	
	/** Creation of the cover page */
	public String createCoverHtml(){
		
		String result = new String("");
	
		// we resize the image before add to <div>
		result +=
				"<div align='center' class='logo'>" +
				getHtmlResizedImg(img,false,url)+"</div><br/><br/>"+ 
				"</div><br/><br/>" +

                "<div align='center' style='font-size:24px;color: #AA0000;font-weight: bold;'>"+
                createArticleTitle()+"</div><br/><br/>" +
                
                //we add a second image with 650x430 dimension
                "<div  width='620' height='400' align='center'>"+
                "<table align='center'><tr><td>" +
                getHtmlResizedImg(img,true,url)+

                "</td></tr></table></div>"+
                "<br/><br/><br/>";

		result+="<pd4ml:page.break>";
		return result;
	}

	/** Creation of the table of content */
	public String createToc(){
		String result="<br/><br/><table><tr><td class='valignMiddle'><pd4ml:toc></td></tr></table><pd4ml:page.break>";
		return result;
	}




	/** title style */

	public String createArticleTitle(){
//		int size = getTitleSize(sectionTxt.getSection());
		String title = null;
//		int level = Math.min(size+1, 4);
		title = "<br/><pd4ml:page.break ifSpaceBelowLessThan=\"120\"><h1"/*+level*/+">"+articles.get(0).getTitle()+"</h1"/*+level*/+">";
		return title;
	}


	//get the html tag for resized imgs, coverpage logo size or heaer logo size
	//buffered image is used to compute the dimension picture
	//URL is used to get the url path image.
	public String getHtmlResizedImg(BufferedImage image,boolean bigPictureCoverPage,URL urlImg){
		String align="";
		String imgHtmlTag = "";
		float maxH;
		float maxW;
		// if we want a big picture on cover page ,we change the height and the width of the image which will be use
		// as numeric value in the tag img ( 430px x 650px )
		if(bigPictureCoverPage){
			maxH = 400.0f;
			maxW = 620.0f;
			align = "align='middle'";
			
			// or we want a little image with 40x40 dimension.
		}else{
			maxH = 70.0f;
			maxW = 70.0f;
			align = "align='right'";
		}
		if((float)image.getWidth()/maxW > (float)image.getHeight()/maxH){
			imgHtmlTag +="<img src ='"+urlImg.toString()+"' width='"+maxW+"' "+align+">";
		}else{
			imgHtmlTag +="<img src ='"+urlImg.toString()+"' height='"+maxH+"' "+align+">";
		}
		return imgHtmlTag;
	}


}


