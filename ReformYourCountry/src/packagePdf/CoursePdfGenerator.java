package packagePdf;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.StringReader;
import java.security.InvalidParameterException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.zefer.pd4ml.PD4ML;
import org.zefer.pd4ml.PD4PageMark;

/**
 * @author Gusbin johan
 * @author Julien Van Assche
 * 
 * Generate and format the course to a pdf
 * 
 * */

/** Generates an html String with course sections, and use PD4ML to transform the html into a pdf document */ 
public class CoursePdfGenerator {
    
	
	
	//default cover page image link
	private final String ASIAN_WOMAN_IMG_NAME="/VAADIN/themes/blackbelt/image/bgphoto/asianWomanSword.jpg";
	private final String KBB_LOGO_IMG_NAME="/imgs/logos/KnowledgeBlackBelt-logo-950x338.png";
	
	private PD4ML pd4ml;

	private String logoUrl;
	private BufferedImage logoImage;
	
	private boolean doTheUserWantACoverPage;
	private boolean useCustomLogo;
	
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
		"font-size:12px;"+
	"}"+
	"p{"+
		"line-height:17px;"+
	"}"+
	"a {"+
		"text-decoration: none;"+
		"color: #AA0000;"+
	"}"+

	".titre{"+
		"font-size:26px;"+
		"font-family:helvetica,Times_New_Roman;"+
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
	
	public CoursePdfGenerator(){
		
        this.pd4ml = new PD4ML();
		
	}
	
	
	public void generatePdf(String result,OutputStream fos,BufferedReader incss) {
		
		
		  
		  
		  try { 
	        	/** PDF document setting */
			  
		        String css=	incss.toString();
	        	pd4ml.addStyle(css,true);
	        //	pd4ml.useTTF("java:fonts",true);  
	        //	pd4ml.setDefaultTTFs("arial", "helvetica", "Courier New");
	        	pd4ml.setHtmlWidth(1000);
	        	PD4PageMark header = new PD4PageMark();
	        	
	        	
	        	  header.setAreaHeight( 20 );
	              header.setTitleTemplate( "title: $[title]" );
	              header.setTitleAlignment( PD4PageMark.CENTER_ALIGN );
	              header.setPageNumberAlignment( PD4PageMark.LEFT_ALIGN );
	              header.setPageNumberTemplate( "#$[page]" );
	    		pd4ml.setPageHeader(header);
	    		
	    		
	    		
	       // 	createFooter();
	        	pd4ml.enableImgSplit(false); //Do not split an image
	        	
	        	/*USE THIS FOR DEBUG INFOS
	        	 * pd4ml.enableDebugInfo();
	        	 * prod console issue on : http://www.KnowledgeBlackBelt.com/static/catalina.out
	        	 * */
	        	/** Html header */
	          //  String finalResult = "<html><head><title>"+"Hello world"+"</title><META http-equiv=Content-Type content=\"text/html; charset=utf-8\"></head><body>";
	            
	            /** Html close */
	          //  finalResult+="</body></html>";
	        	//final rendering
	        	pd4ml.render(new StringReader(result),fos); //Start creating PDF //TODO faire marcher ce bazar
	        } catch (InvalidParameterException e) {
	        	throw new RuntimeException(e);
	        } catch (IOException e) {
	        	throw new RuntimeException(e);
	        }
		  
		
		
	}
	
    //2nd constuctor with a user	

	

	@SuppressWarnings("deprecation")
	public void generatePdf (FileOutputStream outputStream,
			boolean doTheUserWantACoverPage, boolean doTheUserWantAToc) {
		
	
	    this.doTheUserWantACoverPage = doTheUserWantACoverPage;
		
		
		
		
		
		/** Html header */
        String finalResult = "<html><head><title>"+"Hello world"+"</title><META http-equiv=Content-Type content=\"text/html; charset=utf-8\"></head><body><pd4ml:toc> <table id=\"toc\" class=\"toc\">"+
    "<tbody><tr>"+
    "<table class=\"ptoc-table\" cellspacing=\"0\">" +
     " <tr>"+
      "<td class=\"ptoc-left-col\"><a class=\"ptoc-link\" href=\"#ptoc_1\">"+
                "<div class=\"ptoc1-style-left\">Chapter 1<pd4ml-dots></div></a></td></pd4ml:toc>"+
        "<td class=\"ptoc-right-col\"><a class=\"ptoc-link\" href=\"#ptoc_1\">"+
                "<div class=\"ptoc1-style-right\">1</div></a></td></tr>"+
        "</tr>"+
       " <tr>"+
        "<td class=\"ptoc-left-col\"><a class=\"ptoc-link\" href=\"#ptoc_2\">"+
                "<div class=\"ptoc2-style-left\">Chapter 1.1<pd4ml-dots></div></a></td>"+
       "<td class=\"ptoc-right-col\"><a class=\"ptoc-link\" href=\"#ptoc_2\">"+
                "<div class=\"ptoc2-style-right\">2</div></a></td></tr>"+
       "</table>";
   
        
        /** Cover page */
        if (doTheUserWantACoverPage){
            finalResult+=createCoverHtml();
        }
        
        /** Table of contents */
        if(doTheUserWantAToc){
            finalResult+=createToc();
        }
        
   
      
        
        /** Html close */
        finalResult+="</body></html>";
		
        try { 
        	/** PDF document setting */
        	pd4ml.addStyle(CSS,true);
        	pd4ml.useTTF("java:fonts",true);  
        	pd4ml.setDefaultTTFs("arial", "helvetica", "Courier New");

        	createHeader();
        	createFooter();
        	pd4ml.enableImgSplit(false); //Do not split an image
        	
        	/*USE THIS FOR DEBUG INFOS
        	 * pd4ml.enableDebugInfo();
        	 * prod console issue on : http://www.KnowledgeBlackBelt.com/static/catalina.out
        	 * */

        	
        	//final rendering
        	pd4ml.render(new StringReader(finalResult), outputStream); //Start creating PDF //TODO faire marcher ce bazar
        } catch (InvalidParameterException e) {
        	throw new RuntimeException(e);
        } catch (IOException e) {
        	throw new RuntimeException(e);
        }
	}
	
	
	
	/** creation of the header */
	public void createHeader(){
		PD4PageMark head = new PD4PageMark();
		if(doTheUserWantACoverPage){ //If the user want a cover page
			head.setPagesToSkip(1); //Skip the header at the first page
		}
		if(this.useCustomLogo){ 
			head.setHtmlTemplate(getHtmlResizedImg(logoImage,false)); //Add the logo of the user company
		}
		head.setAreaHeight(45); //Adjust the height
		pd4ml.setPageHeader(head); //Add header
	}
	
	/** creation of the footer */
	public void createFooter(){
		//footerPages
		PD4PageMark foot = new PD4PageMark();
		if(doTheUserWantACoverPage){ //If the user want a cover page
			foot.setPagesToSkip(1); //Skip the header at the first page
		}
		foot.setInitialPageNumber(1);
		
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
		String footerText =  "See the on-line version to get videos, translations, downloads... "
            +" <br />If you don't have access, please "+
            "<u>contact us</u>ContactUs"+"."
            +"<br />(c) "+sdf.format(new Date())+" KnowledgeBlackBelt." +
            		"<span style='font-size:5px;'>No part of this book may be reproduced i any form or by any electronic or mechanical means,<br/>including information storage or retrieval devices or systems, without prior written permission from KnowledgeBlackBelt, except that brief passages may be quoted for review</span>";
		
		
		
		foot.setHtmlTemplate("<table width='100%'><tr><td></td><td class='licence'>"+footerText+
		        "</td><td align='right' class='valignBottom'>${page}<span class='grey'> /${total}</span></td></tr></table>");
		foot.setAreaHeight(45); //Adjust the height
		pd4ml.setPageFooter(foot); //Add footer
	}
			
	/** Creation of the cover page */
	public String createCoverHtml(){
        String result = new String("");
        String linklogo=new String("http://knowledgeblackbelt.com/image/KnowledgeBlackBelt-Logo-Header.png");
        String linkbottom = new String("http://3.bp.blogspot.com/-QqRJffxgYOQ/T5AXnhlDAZI/AAAAAAAAAzk/lUbL8noFzgs/s400/black+belt.jpg");
        result +=
                // BBF Logo
                "<div align='center' class='logo'>" +
                "<img width='357' height='127' alt='fichier existant mais non affichable' src='"+linklogo+"'></div><br/><br/>"+ 
                "</div><br/><br/>" +
                
                // Titles
                "<div align='center' style='font-size:24px;color: #AA0000;font-weight: bold;'>"+
               "Bienvenue sur le site"+"</div><br/><br/>" +

                // The logo is in a table to not deforme the logo
                "<div  width='650' height='430' align='center'>"+
                "<table align='center'><tr><td>" +
                "<img src='"+linkbottom+"'>"+
               // getHtmlResizedImg(logoImage, true)+
                "</td></tr></table></div>"+
                "<br/><br/><br/>";
        
        result+="<table width='100%'><tr><td width='45px'>"
            +"</td><td class='valignMiddle'><span class='small'>Download by :</span><br/><i>"+
            "</i><br/><span class='small'>"+
            "</span></td></tr></table><pd4ml:page.break>";
        return result;
    }
	
	/** Creation of the table of content */
	public String createToc(){
		String result="<br/><br/><table><tr><td class='valignMiddle'><pd4ml:toc></td></tr></table><pd4ml:page.break>";
		return result;
	}
	
	
	
	
  
	
	
	//get the html tag for resized imgs, coverpage logo size or heaer logo size
	public String getHtmlResizedImg(BufferedImage image,boolean bigPictureCoverPage){
		String align="";
		String imgHtmlTag = "";
		float maxH;
		float maxW;
		if(bigPictureCoverPage){
			maxH = 430.0f;
			maxW = 650.0f;
			align = "align='middle'";
		}else{
			maxH = 40.0f;
			maxW = 40.0f;
			align = "align='right'";
		}
		if((float)image.getWidth()/maxW > (float)image.getHeight()/maxH){
			imgHtmlTag +="<img src ='"+this.logoUrl+"' width='"+maxW+"' "+align+">";
		}else{
			imgHtmlTag +="<img src ='"+this.logoUrl+"' height='"+maxH+"' "+align+">";
		}
		return imgHtmlTag;
	}
	
}


