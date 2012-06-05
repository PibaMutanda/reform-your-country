package blackbelt.parser;

import org.apache.commons.lang.StringEscapeUtils;

import blackbelt.model.Course;
import blackbelt.model.SectionText;
import blackbelt.parser.BlackBeltTagParser.Element;
import blackbelt.parser.BlackBeltTagParser.MandatoryParameterNotFoundException;
import blackbelt.ui.common.PictureResource;
import blackbelt.ui.common.Video;
import blackbelt.ui.common.VimeoVideo;
import blackbelt.ui.common.YoutubeVideo;
import blackbelt.web.UrlUtil;

public abstract class BlackBeltTagHandlerCourse extends BlackBeltTagHandlerStringGenerator {

    SectionText sectionText;
       
    public BlackBeltTagHandlerCourse(SectionText sectionText) {
        super();
        this.sectionText =sectionText;
    }
    
    
    public BlackBeltTagHandlerCourse() {}
    
    
    
    @Override
    public void onImageTag(Element element) {
        try {
            String srcValue = element.getMandatoryValue("src");
            //String imageUrl = (new PictureResource(course, srcValue)).getURL();
            
            String imageUrl = (new PictureResource(sectionText.getSection().getCourse(), srcValue)).getURL();

            addResultTextBlock("<div style='overflow:auto' align='center'>" +  // Copied from Vaadin book layout. overflow:auto -> scrollbar if too wide.
                                    "<img align='middle' src='"+UrlUtil.getUrlWithNoBlank(imageUrl)+"'/>" + // UrlUtil.getUrlWithNoBlank because pd4ml do not display images when the url contain blanks
                               "</div>", false);
            
        } catch (MandatoryParameterNotFoundException e) {
            // Ok, do nothing. Error message already inserted in output by exception thrower.
        }
    }
    
    
    @Override
    public void onAttachmentTag(Element element) {
        
        String srcValue; 
        try {
            srcValue = element.getMandatoryValue("src");
        } catch (MandatoryParameterNotFoundException e) {
            // Ok, do nothing. Error message already inserted in output by exception thrower.
            return;
        }
        
        String srcUrl = (new PictureResource((sectionText.getSection().getCourse()), srcValue)).getURL();
        insertAttachementLink(element, srcUrl);
    }

    // Helper method usable by descendants.
    protected void insertAttachementLink(Element element, String srcUrl) {
        String imageValue = element.getOptionalValue("image");

        if(imageValue != null){
            String imageUrl = (new PictureResource((sectionText.getSection().getCourse()), imageValue)).getURL();
            addResultTextBlock("<div style='overflow:auto' align='center'>" +  // Copied from Vaadin book layout. overflow:auto -> scrollbar if too wide.
                    "<a href='" + srcUrl + "'><img style='border: none;' align='middle' src='"+UrlUtil.getUrlWithNoBlank(imageUrl)+"'/>" +
                    "<br/><span style='font-size: 65%; font-color: #999999;' align='center'>Click to download</span></a>" + 
                    "</div>",false);
        } else {
            element.innerText = StringEscapeUtils.escapeHtml(element.innerText);
            if(element.innerText == null || element.innerText.isEmpty()){
                insertErrorMessage("Tag name \""+ element.name + "\" must have a body when no image attribute is specified.");
            } else {
                addResultTextBlock("<a href='" + srcUrl + "'>" + element.innerText + "</a>", true); 
            }
        }
                   
    }

    
    @Override
    public void onVideoTag(Element element) {
        try {
            int width = element.getOptionalIntValue("width", 500);
            int height = element.getOptionalIntValue("height", width*9/16);
            String videoId = element.getMandatoryValue("id");
            
            // Video object creation (to get the html).
            Video video;
            String typeValue = element.getMandatoryValue("type").toLowerCase();
            
            if ("youtube".equals(typeValue)) {
                video = new YoutubeVideo(videoId, width, height, false);

            } else if ("vimeo".equals(typeValue)) {
                try {
                    Long videoIdL = Long.parseLong(videoId);
                    video = new VimeoVideo(videoIdL, width, height);
                } catch(NumberFormatException e) {
                    insertErrorMessage("Paramter id is supposed to be a number for Vimeo videos. Current value is ["+videoId+"]");
                    return;
                }

            } else {
                insertErrorMessage("[video] element with unsupported type value '"+typeValue+"'. Should be 'youtube' or 'vimeo'");
                return;
            }
            
            insertVideo(element, video);
            
        } catch (MandatoryParameterNotFoundException e) {
            // Ok, do nothing. Error message already inserted in output by exception thrower.
        }
    }

    
    protected abstract void insertVideo(Element element, Video video);
    
}
