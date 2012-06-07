package packagePdf;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.StringReader;
import java.security.InvalidParameterException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.zefer.pd4ml.PD4ML;
import org.zefer.pd4ml.PD4PageMark;
import blackbelt.web.ContextUtil;
import blackbelt.web.UrlUtil;

public class TestCoverPage {


	private PD4ML pd4ml;
	private final String KBB_LOGO_IMG_NAME="/imgs/logos/KnowledgeBlackBelt-logo-950x338.png";
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
    public TestCoverPage(){
		
        this.pd4ml = new PD4ML();
        
		
	}
    public void generatePdf (FileOutputStream outputStream,
			boolean doTheUserWantACoverPage) {
		
	
	    this.doTheUserWantACoverPage = doTheUserWantACoverPage;
			
		
		/** Html header */
        String finalResult = "<html><head><title>"+"Hello world"+"</title><META http-equiv=Content-Type content=\"text/html; charset=utf-8\"></head><body>";
        
        /** Cover page */
        if (doTheUserWantACoverPage){
            finalResult+=createCoverHtml();
        }
        
       
   
      
        
        /** Html close */
        finalResult+="</body></html>";
		
        try { 
        	/** PDF document setting */
        	pd4ml.addStyle(CSS,true);
        	pd4ml.useTTF("java:fonts",true);  
        	pd4ml.setDefaultTTFs("arial", "helvetica", "Courier New");

        /*	createHeader();
        	createFooter();*/
        	pd4ml.enableImgSplit(false); //Do not split an image
        	        	     	
        	//final rendering
        	pd4ml.render(new StringReader(finalResult), outputStream); //Start creating PDF //TODO faire marcher ce bazar
        } catch (InvalidParameterException e) {
        	throw new RuntimeException(e);
        } catch (IOException e) {
        	throw new RuntimeException(e);
        }
	}
    public String createCoverHtml(){
        String result = new String("");
        String link=new String("http://knowledgeblackbelt.com/image/KnowledgeBlackBelt-Logo-Header.png");
       
        result +=
                // BBF Logo
        		"<div align='center' class='logo'>" +
        		"<img width='357' height='127' alt='fichier existant mais non affichable' src='"+link+"'></div><br/><br/>"+             
                // Titles
                "<div align='center' style='font-size:24px;color: #AA0000;font-weight: bold;'>"+
                ""+"</div><br/><br/>"+
               
  				"<div align='bottom'>" +
  				"</div><br/><br/><br/><br/><br/>";
        
        result+="<table width='100%'><tr><td width='45px'>"
            +"</td><td class='valignMiddle'><span class='small'>Download by :</span><br/><i>"+
            "</i><br/><span class='small'>"+
            "</span></td></tr></table><pd4ml:page.break>";
        return result;
    }
}
