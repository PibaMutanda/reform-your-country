package blackbelt.parser;



import org.apache.commons.lang.StringEscapeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.vaadin.navigator7.PageResource;

import blackbelt.dao.SectionDao;
import blackbelt.model.SectionText;
import blackbelt.parser.BlackBeltTagParser.Element;
import blackbelt.parser.BlackBeltTagParser.MandatoryParameterNotFoundException;
import blackbelt.ui.common.PictureResource;
import blackbelt.ui.common.Video;
import blackbelt.ui.common.VimeoVideo;
import blackbelt.ui.common.YoutubeVideo;
import blackbelt.ui.common.PictureResource.PicturePath;
import blackbelt.ui.coursepage.CoursePagePage.CoursePagePageResource;
import blackbelt.web.UrlUtil;
/**
 * @author John Rizzo
 * Format the text of a section in a acceptable html for xmlGenerator 
 * */
@Configurable(preConstruction=true)
public class BlackBeltTagHandlerCourseOnXml extends BlackBeltTagHandlerCourse {
    
    @Autowired SectionDao sectionUrlService;

    
    public BlackBeltTagHandlerCourseOnXml(SectionText sectionText) {
        super(sectionText);
        openingParagraphTag = "<para>";
        closingParagraphTag = "</para>";
    }


    @Override
    public void onText(String text){
        // Convert " & " into "&amp;" (else it's no valid xml).
        text = text.replaceAll(" & ", "&amp;");
        super.onText(text);
    }
    
    @Override
    protected void insertVideo(Element element, Video video) {
        addResultTextBlock("<video src='"+video.getUrl()+"' />\n", false);
    }
    

    // that method is different from the BlackBeltTagHandlerCourseOnBrowser (CSS impact)
    @Override
    public void onCodeTag(String textCode, boolean inline, boolean escape, String lang, boolean num) {
        if (textCode == null) {
            insertErrorMessage("[code] ... [/code] elements should have text inside.");
            return;
        }
        if (escape) {  // Default is false.
            textCode = StringEscapeUtils.escapeHtml(textCode); // Escape any formatting (probably <b> tags that should be written "<b>" and not trigger bold).
        }

        
        addResultTextBlock("<code type='"+ (inline ? "inline" : "display") +"'>"
                    +textCode+"</code>", true);
    }

    
    // that method is different from the BlackBeltTagHandlerCourseOnBrowser (CSS impact)
    @Override
    public void onQuoteTag(String innerText) {
        if (innerText == null) {
            insertErrorMessage("[quote] ... [/quote] elements should have text inside.");
            return;
        }
        addResultTextBlock("<quote>"+innerText+"</quote>", true);
    }
    
    
    
    // Helper method usable by descendants.
    protected void insertAttachementLink(Element element, String srcUrl) {
        srcUrl = UrlUtil.getAbsoluteUrl(srcUrl);
        srcUrl = UrlUtil.getUrlWithNoBlank(srcUrl);

        String result = "<attachment src='"+srcUrl+"'>";

        String imageValue = element.getOptionalValue("image");
        if(imageValue != null){
            String imageUrl = UrlUtil.getAbsoluteUrl((new PictureResource((sectionText.getSection().getCourse()), imageValue)).getURL());
            imageUrl = UrlUtil.getUrlWithNoBlank(imageUrl);
            result +=" <graphic src='" + imageUrl + "' /> ";
        } else {
            element.innerText = StringEscapeUtils.escapeHtml(element.innerText);
            if(element.innerText == null || element.innerText.isEmpty()){
                insertErrorMessage("Tag name \""+ element.name + "\" must have a body when no image attribute is specified.");
            } else {
                result += element.innerText; 
            }
        }
        result += "</attachment>\n";
        addResultTextBlock(result, false);
                   
    }


}






