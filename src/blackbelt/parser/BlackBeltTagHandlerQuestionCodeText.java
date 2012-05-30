package blackbelt.parser;



import org.apache.commons.lang.StringEscapeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;

import blackbelt.model.exam.V5QuestionVersion;
import blackbelt.parser.BlackBeltTagParser.Element;
import blackbelt.parser.BlackBeltTagParser.MandatoryParameterNotFoundException;
import blackbelt.service.QuestionImageService;
import blackbelt.ui.common.PictureResource;
import blackbelt.web.UrlUtil;

/** Displays question with code as text. */
@Configurable(preConstruction = true)
public class BlackBeltTagHandlerQuestionCodeText extends BlackBeltTagHandlerStringGenerator{

    @Autowired QuestionImageService questionImgageService;
    
	private V5QuestionVersion questionVersion;

    public BlackBeltTagHandlerQuestionCodeText(V5QuestionVersion question){
        this.questionVersion = question;
    }
    
    @Override
    public void onCodeTag(String textCode, boolean inline, boolean escape,
            String lang, boolean num) {
        if (textCode == null) {
            onError("[code] ... [/code] elements should have text inside.");
            return;
        }
        
        if (escape) {  // Default is false.
            textCode = StringEscapeUtils.escapeHtml(textCode); // Escape any formatting (probably <b> tags that should be writtent "<b>" and not trigger bold).
        }

        textCode = questionImgageService.addJavaStyle(textCode, num, true);
       
        if (inline) {  // Default is false.
            addResultTextBlock("<code>"+textCode+"</code>", true);
            
        } else { // Here we do not want inline (usual case) 
            // <pre> creates carriage returns in the browser
            addResultTextBlock("<pre class='contentProgramListing' xml:space='preserve'>"   // Copied from Vaadin book layout.
                    +textCode+"</pre>", false);
        }
        
    }

    //we don't need these methods
    @Override
    public void onQuoteTag(String innerText) {
    }
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

}
