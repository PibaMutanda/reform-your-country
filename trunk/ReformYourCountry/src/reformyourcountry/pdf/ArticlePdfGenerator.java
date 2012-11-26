package reformyourcountry.pdf;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.InvalidParameterException;

import javax.imageio.ImageIO;

import org.zefer.pd4ml.PD4ML;
import org.zefer.pd4ml.PD4PageMark;

import reformyourcountry.model.Article;
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

    private Article article;
	public boolean enableDebug ;
	private ByteArrayOutputStream baos;
	private URL url;
	private  BufferedImage img;

	private final String CSS = "h1,h2,h3,h4 {" +
			"	color: #AA0000; /* #AA0000 = blackbelt_dark_red  */" +
			"	font-weight: bold;" +
			"}"+
			"h1 { "+
			"font-size: 30px;"+
			"}"+
			"h2 { "+
			"font-size: 24px; "+
			"}"+
			"h3 {"+
			"font-size: 18px;"+
			"}"+
			"h4 {"+
			"font-size: 15px;"+
			"}"+
			"#gardeTitle{"+
			"font-size:32;"+
			"}"+
			"body{"+
			"font-family:arial, helvetica, verdana, sans-serif;"+
			"font-size:15px;"+
			"}"+
			"p{"+
			"line-height:17px;"+
			"}"+
			"a {"+
			"text-decoration: none;"+
			"color: #AA0000;"+
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
			".licence a {"+
			"font-size:8px;"+
			"color: #9E9E9E;"+
			"}"+".valignTop{vertical-align:top;"+
			".valignBottom{"+
			"vertical-align: bottom;"+
			"}"+
			".valignMiddle{"+
			"vertical-align:middle;"+
			"}"+
			".small{"+
			"font-size:10px;"+
			"}";
		

	public ArticlePdfGenerator(Article article ,boolean doTheUserWantACoverPage,boolean doTheUserWantAToc,boolean doTheUserWantOnlySummary, boolean enableDebug){
		this.pd4ml = new PD4ML();
		this.article = article;
		this.enableDebug = enableDebug;
		this.doTheUserWantACoverPage = doTheUserWantACoverPage;
		this.doTheUserWantAToc = doTheUserWantAToc;
		this.doTheUserWantOnlySummary = doTheUserWantOnlySummary;
	
		baos = new ByteArrayOutputStream();
	
        try {
            url = new URL(COVER_PAGE_IMG);
            img = ImageIO.read(url);
            
        } catch (Exception e) {
             
           throw new RuntimeException(e);
        }
	}

	public ByteArrayOutputStream generatePDF() {

	    String content = "Résumé</br></br>"+article.getLastVersionRenderdSummary();
        if(!doTheUserWantOnlySummary){
            content += "Article</br></br>"+article.getLastVersionRenderedContent();
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
      
		
        
        // first we add the begining of the html document start with <html> to <body>
	//	String head ="<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\"><html lang=\"fr\" xmlns=\"http://www.w3.org/1999/xhtml\"   ><head><title>Enseignement2.be</title><META http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\"></head><body>";
		String head ="<!DOCTYPE html><head><title>Enseignement2.be - "+article.getTitle()+"</title></head><body>";
		// next we add the cover html 
		if(doTheUserWantACoverPage)
		head += createCoverHtml();
		
		//next we add the table of content
		if(doTheUserWantAToc)
		head += createToc();
		
		createHeader();

		createFooter();


		// enable debugging of pd4ml 		
		if (enableDebug) {
			pd4ml.enableDebugInfo();
		}	
		//after the cover we add the rest of the html document
		String finalresult  =  head+content+"</body></html>";
		// generate the pdf in binary output stream from html
		try {
		    System.out.println(finalresult);
			pd4ml.render(new StringReader( finalresult), baos);


		} catch (InvalidParameterException e) {
			throw new RuntimeException(e);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		
		return baos;
	}





	/** creation of the header */
	public void createHeader(){
	    
	    
		// ${title} will be recognized/replaced by PD4ML.
		String headerHtmlTemplate = "";/*"<table class='header' border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\">"
				+ "<tr>"
				+ "<td style='width:100%' ><span class =\"titre\">${title}</span></td>"
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
	    
	    String footerHtmlTemplate = "<table  border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" height ='45px'>"
        + "<tr>"
        + "<td >"+ getHtmlResizedImg(img,false,url)+"</td>"
        +"<td style='width:90%;padding-left:5px;'><span class =\"titre\">${title}</span></td>"
        +"<td style='width:10%;text-align:right' class='valignBottom'>${page}<span class='grey'> /${total}</span></td>"
        + "</tr>" + "</table>";
	
		PD4PageMark foot = new PD4PageMark();

  
		if(doTheUserWantACoverPage){ //If the user want a cover page
			foot.setPagesToSkip(1); //Skip the header at the first page
		}
		foot.setInitialPageNumber(1);

		foot.setHtmlTemplate(footerHtmlTemplate);
		foot.setAreaHeight(45); //Adjust the height
		pd4ml.setPageFooter(foot); //Add footer
	}

	public ByteArrayOutputStream getOutputStream(){
	    
	    return baos;
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
		title = "<br/><pd4ml:page.break ifSpaceBelowLessThan=\"120\"><h1"/*+level*/+">"+article.getTitle()+"</h1"/*+level*/+">";
		return title;
	}

	/** Check the title importance */

	/*
	public int getTitleSize(Section section){
		int result = 0;
		//See how much parent have a section. With this number we can see the importance of a title and defined its size.
		while(section.getParent() != null){
			section = section.getParent();
			result++;
		}
		return result;
	}*/



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
			maxH = 40.0f;
			maxW = 40.0f;
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


