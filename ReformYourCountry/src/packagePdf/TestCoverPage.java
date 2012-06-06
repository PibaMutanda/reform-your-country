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

public class TestCoverPage {

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
    public TestCoverPage(){
		
        this.pd4ml = new PD4ML();
        
		
	}
    public void generatePdf (FileOutputStream outputStream,
			boolean doTheUserWantACoverPage, boolean doTheUserWantAToc) {
		
	
	    this.doTheUserWantACoverPage = doTheUserWantACoverPage;
		
		
		
		
		
		/** Html header */
        String finalResult = "<html><head><title>"+"Hello world"+"</title><META http-equiv=Content-Type content=\"text/html; charset=utf-8\"></head><body>";
        
        /** Cover page */
        if (doTheUserWantACoverPage){
            finalResult+=createCoverHtml();
        }
        
        /** Table of contents */
     /*   if(doTheUserWantAToc){
            finalResult+=createToc();
        }*/
        
   
      
        
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
    public String createCoverHtml(){
        String result = new String("");
        String link="S:\\BlackBeltFactory-logo-500x317.jpg";
        File filetest = new File("S:\\BlackBeltFactory-logo-500x317.jpg");
        if(filetest.exists()==true)
        {
        	System.out.println("trouvé");
        }
        result +=
                // BBF Logo
                              
                // Titles
                "<div align='center' style='font-size:24px;color: #AA0000;font-weight: bold;'>"+
               "Bonjour le monde"+"<img align='bottom' alt='fichier existant mais non affichable' src='"+link+"'></div><br/><br/>" +

  				"<div align='bottom'>" +
  				//"<img align='Bottom' src='"+link+"'>"+
  				"</div><br/><br/>" +
                // The logo is in a table to not deforme the logo
              /*  "<div  width='650' height='430' align='center'>"+
                "<table align='center'><tr><td>" +
               // getHtmlResizedImg(logoImage, true)+
                "</td></tr></table></div>"+*/
                "<br/><br/><br/>";
        
        result+="<table width='100%'><tr><td width='45px'>"
            +"</td><td class='valignMiddle'><span class='small'>Download by :</span><br/><i>"+
            "</i><br/><span class='small'>"+
            "</span></td></tr></table><pd4ml:page.break>";
        return result;
    }
}
