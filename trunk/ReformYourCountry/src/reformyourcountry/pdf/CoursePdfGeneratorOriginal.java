package pdftest;
package reformyourcountry.pdf;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.StringReader;
import java.security.InvalidParameterException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.vaadin.navigator7.PageResource;
import org.zefer.pd4ml.PD4ML;
import org.zefer.pd4ml.PD4PageMark;

import be.loop.jbb.util.DateUtil;
import be.loop.jbb.util.image.ImageUtil;
import blackbelt.dao.UserDao;
import blackbelt.model.BeltType;
import blackbelt.model.BeltV5;
import blackbelt.model.Language;
import blackbelt.model.Section;
import blackbelt.model.SectionText;
import blackbelt.model.User;
import blackbelt.parser.BlackBeltTagHandlerCourseOnPdf;
import blackbelt.parser.BlackBeltTagParser;
import blackbelt.service.BeltService;
import blackbelt.service.SectionTextService;
import blackbelt.ui.common.ComponentFactory;
import blackbelt.ui.common.PictureResource;
import blackbelt.ui.common.PictureResource.ImageSize;
import blackbelt.ui.common.PictureResource.PicturePath;
import blackbelt.ui.document.DocumentPage;
import blackbelt.web.ContextUtil;
import blackbelt.web.UrlUtil;

/**
 * @author Gusbin johan
 * @author Julien Van Assche
 * 
 * Generate and format the course to a pdf
 * 
 * */
//@Configurable(preConstruction=true)
/** Generates an html String with course sections, and use PD4ML to transform the html into a pdf document */ 
public class CoursePdfGeneratorOriginal {

	@Autowired SectionTextService sectionTextService;
	@Autowired UserDao userDao;
	
	//default cover page image link
	private final String ASIAN_WOMAN_IMG_NAME="/VAADIN/themes/blackbelt/image/bgphoto/asianWomanSword.jpg";
	private final String KBB_LOGO_IMG_NAME="/imgs/logos/KnowledgeBlackBelt-logo-950x338.png";
	private Section startSection;
	
	private PD4ML pd4ml;
	private User user;
	private String logoUrl;
	private BufferedImage logoImage;
	private Language language;
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
	
	public CoursePdfGeneratorOriginal()
	{
		 this.pd4ml = new PD4ML();
	}
	/*public CoursePdfGeneratorOriginal(Section startSection, Language lang){
		this.startSection = startSection;
        this.pd4ml = new PD4ML();
		this.language = lang;
	}
	
    //2nd constuctor with a user	
	public CoursePdfGeneratorOriginal(Section startSection, User user, Language lang){
        this(startSection, lang);
        this.user = user;
    }*/
	

	@SuppressWarnings("deprecation")
	public void generatePdf (ByteArrayOutputStream outputStream,
			boolean doTheUserWantACoverPage, boolean doTheUserWantAToc,
	        PicturePath logoPicturePath, String logoPictureName) {
		
		
	    this.doTheUserWantACoverPage = doTheUserWantACoverPage;
		
		/** definition of logoResource and logoImage. Used in createHeader and createCoverHtml */
		if(logoPicturePath==null||logoPictureName==null){
			//this.logoUrl = BlackBeltUriAnalyzer.getFullUrlNoUi(new PictureResource(PicturePath.EMPTY, ASIAN_WOMAN_IMG_NAME)); 
	        //this.logoUrl = UrlUtil.getAbsoluteUrl(ASIAN_WOMAN_IMG_NAME);  // Something like: "http://KnowledgeBlackBelt.com/VAADIN/..."
			this.useCustomLogo = false;
		} else{
		    //this.logoUrl = BlackBeltUriAnalyzer.getFullUrlNoUi(logoPictureResource);
		    this.logoUrl = UrlUtil.getAbsoluteUrl(logoPicturePath.getPath()+logoPictureName, true);  // something like : "http://KnowledgeBlackBelt.com/gen/..."
			this.useCustomLogo = true;
		}
		//this.logoImage = ImageUtil.readImage(logoUrl);*/
		
		
		/** Html header */
        String finalResult = "<html><head><title>"+this.startSection.getCourse().getName()+"</title><META http-equiv=Content-Type content=\"text/html; charset=utf-8\"></head><body>";
        
        /** Cover page */
        if (doTheUserWantACoverPage){
            finalResult+=createCoverHtml();
        }
        
        /** Table of contents */
        if(doTheUserWantAToc){
            finalResult+=createToc();
        }
        
        /** Main content (all sub sections of startSection), use a recursive tree */
        finalResult += generateHtmlForSection(this.startSection);
        
        /** Html close */
        finalResult+="</body></html>";
		
        try { 
        	/** PDF document setting */
        	pd4ml.addStyle(CSS,true);
        	pd4ml.useTTF("java:fonts",true);  
        	pd4ml.setDefaultTTFs("arial", "helvetica", "Courier New");

        	createHeader();
        	createFooter();
        	pd4ml.enableImgSplit(false); //Do not split an image when cutting pages
        	
        	/*USE THIS FOR DEBUG INFOS
        	 * pd4ml.enableDebugInfo();
        	 * prod console issue on : http://www.KnowledgeBlackBelt.com/static/catalina.out
        	 * */

        	
        	//final rendering
        	pd4ml.render(new StringReader(finalResult), outputStream); //Start creating PDF
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
		String footerText =  "See the on-line version to get videos, translations, downloads... "+UrlUtil.PROD_ABSOLUTE_DOMAIN_NAME
            +" <br />If you don't have access, please "+
            ComponentFactory.pageLinkHtml("<u>contact us</u>", new PageResource(DocumentPage.class, "ContactUs"))+"."
            +"<br />(c) "+sdf.format(new Date())+" KnowledgeBlackBelt." +
            		"<span style='font-size:5px;'>No part of this book may be reproduced i any form or by any electronic or mechanical means,<br/>including information storage or retrieval devices or systems, without prior written permission from KnowledgeBlackBelt, except that brief passages may be quoted for review</span>";
		
		
		
		foot.setHtmlTemplate("<table width='100%'><tr><td><img height='30' width='50' align='left' src='"+
		          UrlUtil.getAbsoluteUrl(KBB_LOGO_IMG_NAME) +"'></td><td class='licence'>"+footerText+
		        "</td><td align='right' class='valignBottom'>${page}<span class='grey'> /${total}</span></td></tr></table>");
		foot.setAreaHeight(45); //Adjust the height
		pd4ml.setPageFooter(foot); //Add footer
	}
			
	/** Creation of the cover page */
	public String createCoverHtml(){
        String result = new String("");
        result +=
                // BBF Logo
                "<div align='center' class='logo'>" +
                "  <img width='357' height='127' src='"+UrlUtil.getAbsoluteUrl(KBB_LOGO_IMG_NAME)+"'>" +
                "</div><br/><br/>" +
                
                // Titles
                "<div align='center' style='font-size:24px;color: #AA0000;font-weight: bold;'>"+
                this.startSection.getCourse().getTypedName()+"</div><br/><br/>" +

                // The logo is in a table to not deforme the logo
                "<div  width='650' height='430' align='center'>"+
                "<table align='center'><tr><td>" +
                getHtmlResizedImg(logoImage, true)+
                "</td></tr></table></div>"+
                "<br/><br/><br/>";
        
        result+="<table width='100%'><tr><td width='45px'><img width='45px' height='35' align='left' src='"+
            this.getBeltPictureUrl()
            +"'></td><td class='valignMiddle'><span class='small'>Download by :</span><br/><i>"+
            this.user.getFullName()+"</i><br/><span class='small'>"+DateUtil.formatyyyyMMdd(new Date())+
            "</span></td></tr></table><pd4ml:page.break>";
        return result;
    }
	
	/** Creation of the table of content */
	public String createToc(){
		String result="<br/><br/><table><tr><td class='valignMiddle'><pd4ml:toc></td></tr></table><pd4ml:page.break>";
		return result;
	}
	
	/** Recursive generation of html for given section and all sub-sections */
	public String generateHtmlForSection(Section section){
		//make sectionText
        SectionText sectionText = sectionTextService.getLastVersionOrEN(section, language);

		// Title
		String result = createSectionTitle(sectionText);
        //String finalResult =sectionText.getTitle();
		
		// Body
		BlackBeltTagHandlerCourseOnPdf bbthpdf= new BlackBeltTagHandlerCourseOnPdf(sectionText);  //-- Donner le SectionText en param.
		BlackBeltTagParser bbtp = new BlackBeltTagParser(bbthpdf, sectionText.getText());
		result += bbtp.parse();
		
		// Sub-sections
        for(Section subSection : section.getChildren()){
			result += this.generateHtmlForSection(subSection);
		}
		return result;
	}
	
	
	
	/** title style */
	public String createSectionTitle(SectionText sectionTxt){
		int size = getTitleSize(sectionTxt.getSection());
		String title = null;
		int level = Math.min(size+1, 4);
		title = "<br/><pd4ml:page.break ifSpaceBelowLessThan=\"120\"><h"+level+">"+sectionTxt.getNumberedTitle()+"</h"+level+">";
		return title;
	}
	
	/** Check the title importance */
	public int getTitleSize(Section section){
		int result = 0;
		//See how much parent have a section. With this number we can see the importance of a title and defined its size.
		while(section.getParent() != null){
			section = section.getParent();
			result++;
		}
		return result;
	}
	
	
    public String getBeltPictureUrl(){
    	user = userDao.get(user.getId());
    	BeltV5 javaBelt = ContextUtil.getSpringBean(BeltService.class).getJavaBelt(user);
    	if(javaBelt != null){
    		return new PictureResource(PicturePath.BELT, PictureResource.getBeltV5PictureName(javaBelt.getBeltType(), ImageSize.LARGE), true).getURL();
    	} else {
    		return new PictureResource(PicturePath.BELT, PictureResource.getBeltV5PictureName(BeltType.WHITE, ImageSize.LARGE), true).getURL();    		
    	}
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
		if((float)image.getWidth()/maxW>(float)image.getHeight()/maxH){
			imgHtmlTag +="<img src ='"+this.logoUrl+"' width='"+maxW+"' "+align+">";
		}else{
			imgHtmlTag +="<img src ='"+this.logoUrl+"' height='"+maxH+"' "+align+">";
		}
		return imgHtmlTag;
	}
	
}
