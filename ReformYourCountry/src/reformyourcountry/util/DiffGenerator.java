package reformyourcountry.util;

import java.util.LinkedList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringEscapeUtils;

import reformyourcountry.util.DiffMatchPatch.Diff;
import reformyourcountry.util.DiffMatchPatch.Operation;

/**
 * Stateful object that takes 2 texts and generates a decorated version to
 * highlight their differences. It uses a google diff code: diff_match_patch
 * class (renamed by DiffMatchPatch)
 * 
 * Use a new instance for each original/modified text pairs.
 * 
 * @Author Gaetan Timmermans (Technofutur 2010), Jerome Lengelé (Technofutur 2010).
 * 
 * */

public class DiffGenerator {

	private String textOriginal, textModified;
	private DiffMatchPatch googleDiffMatchPatch;
	private LinkedList // Cannot upcast to List because diff_match_patch class
						// takes LinkedList as param.
	<DiffMatchPatch.Diff> diffs;

	private enum TextType {
		ORIGINAL, MODIFIED
	}

	public DiffGenerator(String textOriginal, String textModified) {
		this.textOriginal = textOriginal;
		this.textModified = textModified;

		googleDiffMatchPatch = new DiffMatchPatch();
		diffs = googleDiffMatchPatch.diff_main(textOriginal, textModified);
		googleDiffMatchPatch.diff_cleanupSemantic(diffs);
	}

	public String getTextOriginalHtmlDiff(boolean escapeSourceHtml) {
		return createHtmlDiff(TextType.ORIGINAL, escapeSourceHtml);
	}
	public String getTextModifiedHtmlDiff(boolean escapeSourceHtml) {
		return createHtmlDiff(TextType.MODIFIED,escapeSourceHtml);
	}

	/**
	 * Does 2 things: 
	 * 1. Removes inserted or deleted text (according output param)
	 * 2. Escapes the html in the text if parameter "escapeSourceHtml" is true
	 *    and decorates inserted/deleted text with html.
	 * 
	 * @return text showing diffs with html tags to highlights 
	 * (original html tags are escaped if escapeSourceHtml is true)
	 */
	private String createHtmlDiff(TextType output, boolean escapeSourceHtml) {
		StringBuilder html = new StringBuilder();
		int i = 0;
		for (Diff aDiff : diffs) {
			String text;
			if (escapeSourceHtml){
				//escape html tags
				text = StringEscapeUtils.escapeHtml4(aDiff.text);
			} else {
			    text = aDiff.text;
			}
			
			text = indentationCode(text);
			text = text.replaceAll("(\r\n|\n\r|\r|\n)", "<br/>");
			
			if (aDiff.operation == Operation.DELETE
					&& output != TextType.MODIFIED) {
				html.append("<DEL STYLE=\"background:#FFE6E6;\" TITLE=\"i=")
						.append(i).append("\">").append(text).append("</DEL>");
			} else if ( aDiff.operation == Operation.INSERT
					&& output != TextType.ORIGINAL) {
			    //Here we can use the Tag INS but this one automatically adds an underscore so we use SPAN 
				html.append("<SPAN STYLE=\"background:#E6FFE6;\" TITLE=\"i=")
						.append(i).append("\">").append(text).append("</SPAN>");
			} else if (aDiff.operation == Operation.EQUAL) {
				html.append("<SPAN TITLE=\"i=").append(i).append("\">")
						.append(text).append("</SPAN>");
			}
			if (aDiff.operation != Operation.DELETE) {
				i += aDiff.text.length();
			}
		}
		return html.toString();
	}
	
	/**
	 * We use Regex to find multiples white space and replace each white space by a &nbsp;  
	 * This permit to show the code with a good indentation.
	 * The user see the same layout text in the edit mode and the rendered mode.  
	 * 
	 * @param text
	 * @return
	 */
	private String indentationCode(String text){
	    Matcher m = Pattern.compile(" {2,}")   //" {2,}" means " " (blank) twice or more. 
	            // If only one blank (as between 2 words) we replace nothing. We mostly target blanks in [code] blocks in front of lines (to must be indented).
	       .matcher(text);
	    StringBuffer sb=new StringBuffer();
	    int index=0;
	    while(m.find()){
	        sb.append(text.substring(index, m.start()));
	        sb.append(m.group().replaceAll(" ", "&nbsp;"));
	        index=m.end();
	    }
	    sb.append(text.substring(index, text.length()));
	    
	    return sb.toString();
	}
}

