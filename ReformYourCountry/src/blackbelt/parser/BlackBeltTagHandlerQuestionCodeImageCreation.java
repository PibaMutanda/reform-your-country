package blackbelt.parser;

import org.apache.commons.lang.StringEscapeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;

import blackbelt.model.exam.V5QuestionVersion;
import blackbelt.parser.BlackBeltTagParser.Element;
import blackbelt.service.QuestionImageService;

/**
 * Per code fragment of a question, create it's image and saves it in /gen/questions
 */
@Configurable(preConstruction = true)
public class BlackBeltTagHandlerQuestionCodeImageCreation implements BlackBeltTagHandler{

    @Autowired QuestionImageService questionImageService;

    private V5QuestionVersion question;
    private int image=0;  // Number of the current image (one image per code fragment)

    //Constructor
    public BlackBeltTagHandlerQuestionCodeImageCreation(V5QuestionVersion question){
        this.question = question;
        //if there are older version of the question delete them
        questionImageService.deleteImages(question);
    }

    

    @Override
    public void onCodeTag(String innerText, boolean inline, boolean escape, String lang, boolean num) {
        image++;

        if (innerText == null) {
            onError("[code] ... [/code] elements should have text inside.");
            return;
        }

        if (escape) {  // Default is false.
            innerText = StringEscapeUtils.escapeHtml(innerText); // Escape any formatting (probably <b> tags that should be writtent "<b>" and not trigger bold).
        }


        if(!inline){
            innerText = questionImageService.addJavaStyle(innerText,num,false); 

            innerText = "<pre style='background-color:#EEEEEE; padding: 7px;overflow: auto;' xml:space='preserve'>"+innerText+"</pre>";// Copied from Vaadin book layout.

            questionImageService.generateAndSaveImage(innerText,question,image);
        }

    }


    //we don't need these methods
    @Override
    public void onImageTag(Element element) {
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
    @Override
    public void onText(String text) {  
    }
    @Override
    public void onError(String errorText) {
    }
    @Override
    public String getOutputString(String s) {
        return null;
    }
    @Override
    public void setParser(BlackBeltTagParser blackBeltTagParser) {
    }



	@Override
	public void onUnknownElement(Element element) {
		// TODO Auto-generated method stub
		
	}

}
