package pdftest;

import java.awt.Dimension;
import java.awt.Insets;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
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

	
	public CoursePdfGenerator(){
        this.pd4ml = new PD4ML();
	}
	
	public void generatePDF(String inputHTMLFileName, FileOutputStream fos, Dimension format, String fontsDir, String headerBody) {

		pd4ml.setHtmlWidth(950);  // ???????????????????????????????????????????????????????????
	
		if ( fontsDir != null && fontsDir.length() > 0 ) {
			try {
			    pd4ml.useTTF( fontsDir, true );
			} catch(FileNotFoundException e) {
				throw new RuntimeException(e);
			}
		}
		if ( headerBody != null && headerBody.length() > 0 ) {
			PD4PageMark header = new PD4PageMark();
			header.setAreaHeight( -1 );
			header.setHtmlTemplate( headerBody ); 
			pd4ml.setPageHeader( header );
		}
		
		
		
		PD4PageMark header = new PD4PageMark();
		
		header.setInitialPageNumber(1);
		header.setAreaHeight(20);
		header.setTitleTemplate("title: $[title]");
		header.setTitleAlignment(PD4PageMark.CENTER_ALIGN);
		header.setPageNumberAlignment(PD4PageMark.LEFT_ALIGN);
		header.setPageNumberTemplate("#$[page]");
		
		pd4ml.setPageHeader(header);

		
		
		
		PD4PageMark foot = new PD4PageMark();
		foot.setPagesToSkip(1);
		foot.setInitialPageNumber(1);

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
		String footerText = "WIKIPEDIA"

				+ "<br />(c) "
				+ sdf.format(new Date())
				+ "<span style='font-size:20px;'>Ceci est un exemple de footer</span>";

		foot.setHtmlTemplate("<table width='100%'><tr><td></td><td class='licence'>"
				+ footerText
				+ "</td><td align='right' class='valignBottom'>${page}<span class='grey'> /${total}</span></td></tr></table>");
		foot.setAreaHeight(45); // Adjust the height
		pd4ml.setPageFooter(foot); // Add footer
	    		
	    		
		
		pd4ml.enableDebugInfo();
		pd4ml.render(new StringReader(inputHTMLFileName), fos);
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
              
                "<div align='center' class='logo'>" +
                "<img width='357' height='127' alt='fichier existant mais non affichable' src='"+linklogo+"'></div><br/><br/>"+ 
                "</div><br/><br/>" +
                
             
                "<div align='center' style='font-size:24px;color: #AA0000;font-weight: bold;'>"+
               "Bienvenue sur le site"+"</div><br/><br/>" +

          
                "<div  width='650' height='430' align='center'>"+
                "<table align='center'><tr><td>" +
                "<img src='"+linkbottom+"'>"+
          
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


