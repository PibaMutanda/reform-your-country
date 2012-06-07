package blackbelt.parser.deprecated;

import org.apache.commons.lang.StringEscapeUtils;

import blackbelt.model.Course;
import blackbelt.model.SectionText;
import blackbelt.parser.deprecated.BlackBeltTagParser.Element;
import blackbelt.parser.deprecated.BlackBeltTagParser.MandatoryParameterNotFoundException;
import blackbelt.ui.common.Video;
import blackbelt.ui.common.VimeoVideo;
import blackbelt.ui.common.YoutubeVideo;
import blackbelt.ui.common.PictureResource;

/**
 * @author John Rizzo
 * @author Julien Van Assche
 * @author Jerome Lengele
 * @author Pierre Mistrot
 * Format the text of a section, with the goal to display it on a browser (vs PDF)
 * There are course specific stuff as knowing in which directory to find the images
 *  */
public class BlackBeltTagHandlerCourseOnBrowser extends BlackBeltTagHandlerCourse {
	
	Course course;  // Not null <=> sectionText null (rare case when we display the course summary (which is not in a SectionText). 
	SectionText sectionText;
	
	
	public BlackBeltTagHandlerCourseOnBrowser(SectionText sectionText){
	    super(sectionText);
		this.sectionText = sectionText;
	}
	
	public BlackBeltTagHandlerCourseOnBrowser(Course course){
        super();
        this.course = course;
    }

	@Override
    protected void insertVideo(Element element, Video video) {
	    addResultTextBlock("<div style='overflow:auto' align='center'>" + video.getHtml() + "</div>", true);
	}



	@Override
	public void onCodeTag(String textCode, boolean inline, boolean escape,
			String lang, boolean num) {
		if (textCode == null) {
			onError("[code] ... [/code] elements should have text inside.");
			return;
		}
		
		if (escape) {  // Default is false.
			textCode = StringEscapeUtils.escapeHtml(textCode); // Escape any formatting (probably <b> tags that should be written "<b>" and not trigger bold).
		}

		if (inline) {  // Default is false.
			addResultTextBlock("<code>"+textCode+"</code>", true);
			
		} else { // Here we do not want inline (usual case) 
			// <pre> creates carriage returns in the browser
			addResultTextBlock("<pre class='contentProgramListing contentProgramListingSource' xml:space='preserve'>"   // Copied from Vaadin book layout.
					+textCode+"</pre>", false);
		}
	}

	@Override
	public void onQuoteTag(String innerText) {
		if (innerText == null) {
			onError("[quote] ... [/quote] elements should have text inside.");
			return;
		}
		
		addResultTextBlock("<div class='contentQuote'>"+innerText+"</div>", false);
	}
}
