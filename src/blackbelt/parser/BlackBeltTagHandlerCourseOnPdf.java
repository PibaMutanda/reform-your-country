package blackbelt.parser;



import org.apache.commons.lang.StringEscapeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.vaadin.navigator7.PageResource;

import blackbelt.dao.SectionDao;
import blackbelt.model.SectionText;
import blackbelt.parser.BlackBeltTagParser.Element;
import blackbelt.parser.BlackBeltTagParser.MandatoryParameterNotFoundException;
import blackbelt.ui.application.BlackBeltUriAnalyzer;
import blackbelt.ui.common.PictureResource;
import blackbelt.ui.common.PictureResource.PicturePath;
import blackbelt.ui.common.Video;
import blackbelt.ui.coursepage.CoursePagePage.CoursePagePageResource;
import blackbelt.web.UrlUtil;
/**
 * @author Gusbin johan
 * @author Pierre Mistrot
 * Format the text of a section in a acceptable html for pdfGenerator 
 * */
@Configurable(preConstruction=true)
public class BlackBeltTagHandlerCourseOnPdf extends BlackBeltTagHandlerCourse{
    @Autowired SectionDao sectionUrlService;

    private final String VIDEO_LOGO = "logovideo.jpg";

    //protected Logger logger;
    
    
    
    public BlackBeltTagHandlerCourseOnPdf(SectionText sectionText) {
        super(sectionText);
    }

    
    @Override
    public void onImageTag(Element element) {
        try {
            String srcValue = element.getMandatoryValue("src");
            //String imageUrl = (new PictureResource(course, srcValue)).getURL();
            
            String imageUrl = (new PictureResource(sectionText.getSection().getCourse(), srcValue, true )).getURL();

            addResultTextBlock("<div style='overflow:auto' align='center'>" +  // Copied from Vaadin book layout. overflow:auto -> scrollbar if too wide.
                                    "<img align='middle' src='"+UrlUtil.getUrlWithNoBlank(imageUrl)+"'/>" + // UrlUtil.getUrlWithNoBlank because pd4ml do not display images when the url contain blanks
                               "</div>", false);
            
        } catch (MandatoryParameterNotFoundException e) {
            // Ok, do nothing. Error message already inserted in output by exception thrower.
        }
    }
    



    // that method is different from the BlackBeltTagHandlerCourseOnBrowser (CSS impact)
    @Override
    public void onCodeTag(String textCode, boolean inline, boolean escape,String lang, boolean num) {
        if (textCode == null) {
            insertErrorMessage("[code] ... [/code] elements should have text inside.");
            return;
        }
        if (escape) {  // Default is false.
            textCode = StringEscapeUtils.escapeHtml(textCode); // Escape any formatting (probably <b> tags that should be written "<b>" and not trigger bold).
        }

        if (inline) {  // Default is false.
            addResultTextBlock("<code>"+textCode+"</code>",true);

        } else { // Here we do not want inline (usual case) 
            // <pre> creates carriage returns in the browser
           // ---- extract method + comment table
            addResultTextBlock(getTableTagHtml(true, textCode), false);
        }
    }

    
    // that method is different from the BlackBeltTagHandlerCourseOnBrowser (CSS impact)
    @Override
    public void onQuoteTag(String innerText) {
        if (innerText == null) {
            insertErrorMessage("[quote] ... [/quote] elements should have text inside.");
            return;
        }
        //addResultTextBlock("<div class='contentQuote'>"+element.innerText+"</div>");
        addResultTextBlock(getTableTagHtml(false, innerText), false);
    }
    
    
    /** isCodeTag = true when [code] and false when [quote] */
    public String getTableTagHtml(boolean isCodeTag, String textCode){
        String imageUrlStart = UrlUtil.getAbsoluteUrl("/VAADIN/themes/blackbelt/image/");
        String startStr = "<pd4ml:page.break ifSpaceBelowLessThan=\"80\"><table width='100%'><tr><td class='valignTop' width='20px'>" +
                "<img src='"+imageUrlStart;
        if (isCodeTag){
            return startStr + "src-icon.jpg'></td><td><pre class='contentProgramListing' xml:space='preserve'>"
            +textCode+"</pre></td></tr></table>";
        } else {
            return startStr + "quote-icon.jpg'></td><td><div class='contentQuote'>"
            +textCode+"</div></td></tr></table>";           
        }
    }
    
    /** The link is different from the ancestor. In the pdf we give the link to the coursePage, not directly to the attachement. */
    @Override
    public void onAttachmentTag(Element element) {
        // Link to the sectionText, not the the attachement.
        String srcUrl = UrlUtil.getAbsoluteUrl(new CoursePagePageResource(sectionText));

        // We don't call super, but we call the same helper method (with common code)
        insertAttachementLink(element, srcUrl);
    }



    @Override
    protected void insertVideo(Element element, Video video) {
        if (sectionText == null) {
            this.insertErrorMessage("The [video] tag cannot be used outside of a course's section.");
        }
        
        PageResource coursePageResource = new CoursePagePageResource(sectionText);
        String coursePageFullUrl = UrlUtil.getAbsoluteUrl(coursePageResource);  // No relative URLs in PDFs please.
        String imgHtml = "<img src='"+ new PictureResource(PicturePath.PDF, VIDEO_LOGO).getURL() + "' width='80' height='80' border='0' />";
        
        
        String clickableImgHtml = "<a href=\"" + coursePageFullUrl + "\">" + imgHtml + "</a>";
                
        addResultTextBlock("<div style='overflow:auto' align='center'>" +
                clickableImgHtml + "<br/><br/>" +
                "Watch this video on <br/><a href='"+  coursePageFullUrl +"'>"+coursePageFullUrl+"</a></div> ", false);
    }
    
    @Override
    public void onText(String text){
        // Remove the body tag.
        text = text.replaceAll("</?body.*>", "");
        super.onText(text);
    }
}

