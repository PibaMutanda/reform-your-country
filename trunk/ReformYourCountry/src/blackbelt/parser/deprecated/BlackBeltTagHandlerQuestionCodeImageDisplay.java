package blackbelt.parser.deprecated;

import org.apache.commons.lang.StringEscapeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;

import be.loop.jbb.bl.FileService;
import blackbelt.model.exam.V5QuestionVersion;
import blackbelt.parser.deprecated.BlackBeltTagParser.Element;
import blackbelt.parser.deprecated.BlackBeltTagParser.MandatoryParameterNotFoundException;
import blackbelt.service.QuestionImageService;
import blackbelt.ui.common.ComponentFactory;
import blackbelt.ui.common.PictureResource;
import blackbelt.ui.common.PictureResource.PicturePath;
import blackbelt.web.UrlUtil;


/**
 *Per code fragment of a question,  replaces code with image source;
 */
@Configurable(preConstruction = true)
public class BlackBeltTagHandlerQuestionCodeImageDisplay extends BlackBeltTagHandlerStringGenerator {

    @Autowired FileService fileService;
    @Autowired QuestionImageService questionImgS;

    private V5QuestionVersion questionVersion;
    private int image=0;  // Number of the current image (one image per code fragment)

    //Constructor
    public BlackBeltTagHandlerQuestionCodeImageDisplay(V5QuestionVersion question){
        this.questionVersion = question;
    }

    @Override
    public void onCodeTag(String innerText, boolean inline, boolean escape, String lang, boolean num) {

        if(!inline){//an image is displayed
            image++;
            
            //retrieve the path of an image for a given question
            String imagename=questionImgS.getQuestionCodeImageName(questionVersion,image);

            //get picture path
            PictureResource pictureResource = new PictureResource(PicturePath.QUESTION_IMAGE, imagename);

            //innerText = image source
            innerText = ComponentFactory.pictureHtml(pictureResource, "", ComponentFactory.FloatStyle.NONE);
            addResultTextBlock(innerText, false);
        }else{//text code is displayed
            
            if (innerText == null) {
                onError("[code] ... [/code] elements should have text inside.");
                return;
            }
            
            if (escape) {  // Default is false.
                innerText = StringEscapeUtils.escapeHtml(innerText); // Escape any formatting (probably <b> tags that should be writtent "<b>" and not trigger bold).
            }
            addResultTextBlock(innerText,false);
        }
    }


    //we don' need these methods
    @Override
    public void onImageTag(Element element) {        
        try {
            String srcValue = element.getMandatoryValue("src");
            //String imageUrl = (new PictureResource(course, srcValue)).getURL();
            
            String imageUrl = (new PictureResource(questionVersion, srcValue)).getURL();

            addResultTextBlock("<div style='overflow:auto' align='center'>" +  // Copied from Vaadin book layout. overflow:auto -> scrollbar if too wide.
                                    "<img align='middle' src='"+UrlUtil.getUrlWithNoBlank(imageUrl)+"'/>" + // UrlUtil.getUrlWithNoBlank because pd4ml do not display images when the url contain blanks
                               "</div>", false);
            
        } catch (MandatoryParameterNotFoundException e) {
            // Ok, do nothing. Error message already inserted in output by exception thrower.
        }
    }

	@Override
	public void onUnknownElement(Element element) {
		// Prints the name
		addResultTextBlock("[" + element.name + "]", false);
	}
    
    @Override
    public void onVideoTag(Element element) {       
    }
    @Override
    public void onAttachmentTag(Element element) {
    }
    @Override
    public void onQuoteTag(String innerText) {   
    }

}
