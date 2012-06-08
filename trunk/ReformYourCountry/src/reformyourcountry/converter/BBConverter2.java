package reformyourcountry.converter;

import java.io.StringReader;
import java.util.List;

import reformyourcountry.parser.BBAttribute;
import reformyourcountry.parser.BBDomParser;
import reformyourcountry.parser.BBTag;

/** Statefull class to convert a BBCode String into an html String for this application.
 * Includes schema verification (hardcoded).
 * 
 */
public class BBConverter2 {

	boolean errorFound = false;
	String html;
	
    public String transformBBCodeToHtmlCode(String BBCode){
		 BBDomParser dp = new BBDomParser();
//TODO		 dp.addIgnoredTag("[...]");
		 BBTag root = null;
		 root = dp.parse(new StringReader(BBCode));
		 transformDomToHtml(root);
		 if (errorFound) {
			 
		 }
		 return html;
	 }

	private void transformDomToHtml(BBTag root) {
		for (BBTag tag : root.getChildrenList()) {
			switch(tag.getType()) {
			case Error:
				addErrorMessage(tag);
				break;
			case Text:
				html += tag.getContent();
				break;
			case Tag:
				processTag(tag);
				break;
			}
		}
	}
	 
	private void processTag(BBTag tag) {
		switch(tag.getName()) {
		case "quote" :
			processQuote(tag);
			break;
		case "action" :
			processAction(tag);
			break;
		}
	}
	
	

	private void processAction(BBTag tag) {
		///// Get the id attribute
		String idStr = tag.getAttributeValue("id");
		if (idStr == null) {
			addErrorMessage("Missing id", tag);
			return;
		}
		// numeric?
		long id;
		try {
			id = Long.parseLong(idStr);
		} catch () {
			addErrorMessage("id should be numeric", tag);
			return;
		}
		
		///// Get the action from the DB.
		// TODO: Action action = actionDao.find(id);
		// if (action == null) {
		//     addErrorMessage("invalid id "+id+" (corresponding action not found)", tag);
		//     return;
		// }
		
		html += "<......>" + /* action.qqchose() */ + "</....>"; 
	}

	private void processQuote(BBTag tag) {
		// bib  either [quote bib="book-ref"]; or either [quote] ... [bib out="http"]hyperlink text[/bib] [/quote]
		// either bib refers a Book or a link to another site (outlink)
		
		/// 1. We look for a book reference (in attribute)
		Book book = null; 
		String bibValueFromAttrib = tag.getAttributeValue("bib");  // Books can only be referred throub attribute (not nested tag)
		if (bibValueFromAttrib != null) {
			// TODO
//			book = bookDao.findByName(bibValueFromAttrib);
		}

		/// 2. We look for an out link (in nested tag)
		String outUrl = null;  // http://...
		String outText = null; // text to be hyperlinked
		BBTag bibTag = getChildTagWithName(tag, "bib");
		if (bibTag != null) {
			outUrl = bibTag.getAttributeValue("out");
			if (outUrl == null) {
				addErrorMessage("Missing mandatory out attribute with url", tag);
				return;
			}
			// TODO: verify that URL is valid (has a good shape)
			outText = bibTag.getContent();
		}
		
		if (book != null && outUrl != null) {
			addErrorMessage("Cannot have both bib attribute and bib nested tag simultaneously", tag);
		}
		
		if (book != null) {
			//TODO: add html for book
		}
		
		if (outUrl != null) {
			// TODO: add html for out link;
		}


		/////////// Unquote
		
		
		
		
		
		/////////// Untranslate
		

		
	}
	
	

	private BBTag getChildTagWithName(BBTag tag, String string) {
		pour chaque enfant, chercher tag.name == string
		return null si rien trouvé, sinon 1er rencontré
	}

	private void addErrorMessage(String message, BBTag tag) {
		errorFound = true;
		html += message + " for tag ["+tag.getName()+"] ";     // TODO: Arranger avec un span et du rouge.
	}

	private void addErrorMessage(BBTag tag) {
		addErrorMessage(tag.getErrorText(), tag);  
	}
}