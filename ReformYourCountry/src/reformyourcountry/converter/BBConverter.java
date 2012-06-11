package reformyourcountry.converter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.ParseException;
import java.util.List;

import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import reformyourcountry.parser.*;
/**
 * @author FIEUX Cédric
 * this class purpose is to verify the BBCode and there attributes and then return Html Code (with errors commented)
 */
public class BBConverter {
	boolean errorFound = false;
	String html;
	
	//////////////////////////////////////////////////////////////////////////////////
	/////////////////////////////////// PUBLIC ///////////////////////////////////////
    //////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * this method return the html code from the bbcode you pass as parameter
	 * @param BBCode
	 * @return
	 */
    public String transformBBCodeToHtmlCode(String BBCode){
    	html="";
		 BBDomParser dp = new BBDomParser();
		 dp.setEscapeAsText(true);
		 dp.addIgnoredTag("[...]");
		 BBTag root = null;
		 root = dp.parse(BBCode);
		 transformDomToHtml(root);
//		 if (errorFound) {
//			 html = "<span class=\"error\">"+html+"</span>";
//		 }
		 return html;
	 }

	//////////////////////////////////////////////////////////////////////////////////
	/////////////////////////////////// Private //////////////////////////////////////
    //////////////////////////////////////////////////////////////////////////////////
    
	private void transformDomToHtml(BBTag root) {
		for (BBTag tag : root.getChildrenList()) {

			switch(tag.getType()) {
			case Error:
				addErrorMessage(tag);
				break;
			case Text:
				String addText = tag.getContent();
				html += addText;
				break;
			case Tag:
				processTag(tag);
				break;
			}
		}
	}
	 
	private void processTag(BBTag tag) {
		switch(tag.getName()) {
		case "escape":
			String addText = getInnerTextContent(tag);
			html+= addText;
			break;
		case "quote" :
			processQuote(tag);
			break;
		case "action" :
			processAction(tag);
			break;
		case "link":
			processLink(tag);
			break;
		}
	}
	
	

	private void processLink(BBTag tag) {
		String article = tag.getAttributeValue("article");
		String content="";
		content = getInnerTextContent(tag);
		if (article == null){
			String out = tag.getAttributeValue("out");
			String label = tag.getAttributeValue("label");
			html+= "<a href=\""+out+"\">"+content+"</a>";
		}else
		{
			html+= "<a href=\"/Article/"+article+"\">"+content+"</a>";
		}
		
	}

	private String getInnerTextContent(BBTag tag) {
		String content = "";
		for (BBTag child: tag.getChildrenList())
		{
			switch (child.getType()){
			case Text: 
				content+= child.getContent();
				break;
			case Error:
				addErrorMessage(child);
				return "";
			case Tag: 
				addErrorMessage("this tag cannot contains other tags", child);
				return "";
				
			}
		}
		return content;
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
		} catch (Exception e) {
			addErrorMessage("id should be numeric", tag);
			return;
		}
		
		///// Get the action from the DB.
		// TODO: Action action = actionDao.find(id);
		// if (action == null) {
		//     addErrorMessage("invalid id "+id+" (corresponding action not found)", tag);
		//     return;
		// }
		
		html += "<div class=\"action-title\">" + "Coca gratuit" + "</div><div class=\"action-body\">"+"Il faut que le coca-cola soit gratuit chez TechnofuturTic"+"</div>"; 
	}

	private void processQuote(BBTag tag) {
		// bib  either [quote bib="book-ref"]; or either [quote] ... [bib out="http"]hyperlink text[/bib] [/quote]
		// either bib refers a Book or a link to another site (outlink)
		
		/// 0. We look for the type of tag (span or div)
		String bibRefString ="";
		String inline = tag.getAttributeValue("inline");
		if (inline == null || inline.equals("false")){
			html +="<div class=\"quote-block\">";
		}
		else{
			html +="<span class=\"quote-inline\">";
		}
		
		/// 1. We look for a book reference (in attribute)
		//Book book = null; 
		String book = null;
		String bibValueFromAttrib = tag.getAttributeValue("bib");  // Books can only be referred throub attribute (not nested tag)
		if (bibValueFromAttrib != null) {
			// TODO
			//book = bookDao.findByName(bibValueFromAttrib);
			book = bibValueFromAttrib;
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
			URL u;
			try {
				u = new URL(outUrl);
				HttpURLConnection huc;
				huc = ( HttpURLConnection )  u.openConnection();
				huc.setRequestMethod("GET"); 
				huc.connect () ; 
				int code = huc.getResponseCode (  ) ;
			}catch (IOException e){
				addErrorMessage("Invalid url in",bibTag);
			}
			bibRefString =addBook(outUrl,getInnerTextContent(bibTag),inline,null);
		}
		
		if (book != null && outUrl != null) {
			addErrorMessage("Cannot have both bib attribute and bib nested tag simultaneously", tag);
		}
		
		if (book != null) {
			bibRefString =addBook(bibValueFromAttrib,null,inline,tag.getAttributeValue("addbib"));
		}
		
		if (outUrl != null) {
			// TODO: add html for out link;
		}

		String untranslatedHtml="";
		int untranslatedCounter=0;
		for(BBTag child:tag.getChildrenList()){
			switch(child.getType()) {
			case Error :
				addErrorMessage(child);
				break;
			case Text :
				String addText = child.getContent();
				html += addText;
				break;
			case Tag :
				switch(child.getName()){
				///////////// Unquote
				case "unquote": 
					processUnquote(child,inline);
					break;
				case "untranslated":
					/////////// Untranslate
					untranslatedHtml = processUntranslated(child);
					untranslatedCounter++;
					break;
				case "link":
					processLink(child);
					break;
				case "escape":
					String addTxt = getInnerTextContent(child);
					html+= addTxt;
					break;
				}
				break;
			}
			if (untranslatedCounter>1){
				addErrorMessage("There can be only one untranslated tag in [quote]", child);
				break;
			}
		}
		html+=untranslatedHtml;
		if (inline == null || inline.equals("false")){
			html +="</div>";
		}
		else{
			html +="</span>";
		}
		html+=bibRefString;
	}
	
	

	private String addBook(String bibRef,String content, String inline,String addBib) {
		String result="";
		String startTag,endTag;
		if (addBib== null){
			addBib="";
		}else
		{
			addBib =" "+addBib;
		}
		if (inline == null || inline.equals("false")){
			startTag="<div class=\"bibref-after-block\">";
			endTag="</div>";
		}
		else{
			startTag="<span class=\"bibref\">";
			endTag="</span>";
		}
		if (content == null || content.equals("")){
			result += startTag+"<a href=\"/Bibliography#"+bibRef+"\">["+bibRef+"]</a>"+addBib+endTag;
		}else
		{
			result += startTag+"(<a href=\""+bibRef+"\">"+content+"</a>)"+addBib+endTag;
		}
		return result;
	}

	private String processUntranslated(BBTag tag) {
		String result="";
		result = "<div class=\"quote-untranslated\">"+ getInnerTextContent(tag)+"</div>";
		return result;
	}

	private void processUnquote(BBTag tag, String inline) {
		String htmlTxt="";
		if (inline == null || inline.equals("false")){
			htmlTxt +="</div><div class=\"unquote\">";
		}
		else{
			htmlTxt +="</span><span class=\"unquote\">";
		}
		htmlTxt +=getInnerTextContent(tag);
		if (inline == null || inline.equals("false")){
			htmlTxt +="</div><div class=\"quote-block\">";
		}
		else{
			htmlTxt +="</span><span class=\"quote-inline\">";
		}
		html+= htmlTxt;
	}

	private BBTag getChildTagWithName(BBTag tag, String name) {
		 
		for(BBTag child:tag.getChildrenList()){
			String childName = child.getName().toLowerCase();
			if (childName.equals(name.toLowerCase())){
				return child;
			}
		}
		return null;// si rien trouvé, sinon 1er rencontré
	}

	private void addErrorMessage(String message, BBTag tag) {
		errorFound = true;
		html += "<span class=\"error\">"+ message + " (for tag "+tag.getContent()+")</span>";    
	}

	private void addErrorMessage(BBTag tag) {
		addErrorMessage(tag.getErrorText(), tag);  
	}	
	
	
	
	
	
	

	//////////////////////////////////////////////////////////////////////////////////
	/////////////////////////////////// Old code ///////////////////////////////////////
    //////////////////////////////////////////////////////////////////////////////////
	
	
	
	
	
	
	
	
	
	
//	 /**
//	  * Transform the BBcode in html
//	  * @param BBCode the text with the BBCode
//	  * @param ignoredTags the BBCode we don't want to translate [...]
//	  * @return the html code 
//	  */
//	 public static String GetValidHtmlCode(String BBCode,List<String> ignoredTags){
//		 BBDomParser dp = new BBDomParser();
//		 BBTag root = null;
//		 root = dp.parse(new StringReader(BBCode),ignoredTags);
//		 IsTagValid(root);
//		 boolean isOk = true;
//		 while (isOk){
//			 isOk = putTagToEndOfList(root, "untranslated");
//		 }
//		 return BBTagToHtml(root);
//	 }
//	 /**
//	  * Transform the BBcode in html
//	  * @param BBCode the text with the BBCode
//	  * @return the html code 
//	  */
//	 public static String GetValidHtmlCode(String BBCode){
//		 BBDomParser dp = new BBDomParser();
//		 BBTag root = null;
//		 root = dp.parse(new StringReader(BBCode));
//		 IsTagValid(root);
//		 boolean isOk = true;
//		 while (isOk){
//			 isOk = putTagToEndOfList(root, "untranslated");
//		 }
//		 return BBTagToHtml(root);
//	 }
//	 
//	 /**
//	  * Put a tag at the end of the child list from his parent.
//	  * @param parent is the parent of BBTagList 
//	  * @param tagName is the string name of the tag you want to put at the end of the list
//	  * @return true if something was changed
//	  */
//	 private static boolean putTagToEndOfList(BBTag parent, String tagName){
//		for(BBTag tag:parent.getChildrenList()){
//			if (tag.getName().toLowerCase().equals(tagName.toLowerCase())){
//				if (parent.getChildrenList().indexOf(tag)!=parent.getChildrenList().size()-1){
//					parent.remove(tag);
//					parent.add(tag);
//					return true;
//				}
//			}
//			if (tag.getName().toLowerCase().equals("quote")){
//				if  (putTagToEndOfList(tag,tagName)){
//					return true;
//				}
//			}
//		}
//		return false;
//	 }
//	
//	 /**
//	  * Verify if all the conditions are filled for each type of BBTag, if not, change it in an error tag
//	  * @param tag is the root of your TagTree
//	  * @return false 
//	  */
//	 private static Boolean IsTagValid(BBTag tag){
//		 ArrayList<String> validAttributeList = new ArrayList<String>();
//		 validAttributeList.add("inline");
//		 validAttributeList.add("bib");
//		 validAttributeList.add("out");
//		 validAttributeList.add("addbib");
//		 validAttributeList.add("article");
//		 validAttributeList.add("label");
//		 validAttributeList.add("id");
//		 
//		 for (BBTag t : tag.getChildrenList()) {
//			    IsTagValid((BBTag)t);
//			    // out.append(' ');
//		 }
//		 switch(tag.getType()){
//		 	
//		 	case Error:
//		 		return false;
//		 	case Text:
//		 		if (tag.getParent().getType() == BBTagType.Tag ||tag.getParent().getType() == BBTagType.Root ||tag.getParent().getType() == BBTagType.Error){
//		 			if (!tag.getParent().getName().toLowerCase().equals("escape") && !tag.getName().toLowerCase().equals("codeastext") ){
//		 				if (tag.getContent().contains("[")
//		 					||tag.getContent().contains("]")
//	 						||tag.getContent().contains("<")
//	 						||tag.getContent().contains(">")){
//				 			tag.setErrorText("The text contains some code part. If you want that, you need to put it in [escape] tag");
//		 					return false;
//		 				}
//		 				return true;
//		 			}else{
//		 				return true;
//		 			}
//		 		}else{
//		 			tag.setErrorText("There is an unknown problem with the text");
//		 			return false;
//		 		}
//		 	case Tag:
//		 		DefaultBBTag tg = (DefaultBBTag)tag;
//		 		for (BBAttribute a : tg.getAttributes().values()) {
//					if (a.getName() != null) {
//					    // if it's a "bib" we have to send the value as a
//					    // signature at the end of the BBTag.
//					    if (a.getName().toLowerCase().contains("error")) {
//					    	tag.setErrorText("the attribute"+ a.getValue()+"throws some exception:"+a.getName());
//					    	return false;
//					    }
//					    if (!validAttributeList.contains(a.getName())){
//					    	tag.setErrorText("the attribute"+a.getName()+"is not recognized");
//					    	return false;
//					    }
//					}
//		 		}
//		 		String name =tag.getName().toLowerCase();
//		 		switch (name){
//		 			case "quote":
//		 				return true;
//		 			case "unquote":
//		 				DefaultBBTag parentTag = (DefaultBBTag)tag.getParent();
//		 				boolean valid = false;
//		 				while (parentTag != null){
//		 					if (parentTag.getName().toLowerCase().equals("quote")){
//		 						valid= true;
//		 						break;
//		 					}
//		 				}
//		 				if(!valid){
//		 					tag.setErrorText("Unquoted tag must be contained in [quote] tag");
//		 					return false;
//		 				}
//		 				return true;
//		 			case "bib":
//		 				for(BBAttribute attr :tag.attributes() ){
//		 					if (attr.getName().equals("out")){
//				 				return true;
//		 					}
//		 				}
//	 					tag.setErrorText("bib tag need an article an out attribute");
// 						return false;
//		 			case "link":
//		 				for(BBAttribute attr :tag.attributes() ){
//		 					if (attr.getName().equals("article")^attr.getName().equals("out")){
//				 				return true;
//		 					}
//		 				}
//	 					tag.setErrorText("bib tag need an article an out attribute");
// 						return false;
//		 			case "actionpoint":
//		 				for(BBAttribute attr :tag.attributes() ){
//		 					if (attr.getName().equals("id")){
//				 				return true;
//		 					}
//		 				}
//	 					tag.setErrorText("bib tag need an article an out attribute");
// 						return false;
//		 			case "escape":
//		 				for (BBTag subTag:tag.getChildrenList()){
//		 					if (subTag.getType() != BBTagType.Text){
//			 					tag.setErrorText("escape tag can't have tags inside");
//			 					return false;
//		 					}
//		 				}
//		 				return true;
//		 			case "untranslated":
//		 				BBTag parent = tag.getParent();
//		 				if(parent !=null){
//		 					int i =0;
//		 					for (BBTag subTag:parent.getChildrenList()){
//		 						if (subTag.getName().toLowerCase().equals("untranslated")){
//		 							i++;
//		 						}
//		 					}
//		 					if (i >1){
//		 						parent.setErrorText("Tag can't have more than 1 [untranslated] tag");
//		 						return false;
//		 					}
//		 				}else{
//		 					tag.setErrorText("Tag [untranslated] must be contained in a [quote] tag");
//	 						return false;
//		 				}
//		 				return true;
//		 			default:
//		 				tag.setErrorText("The tag is not recognized");
//		 				return false;
//		 					
//		 		}
//		 	case Root:
//		 		break;
//		 }
//		 return false;
//	 }
//	
//	 /**
//	  * Return all the text in html
//	  * @param tg is the root of your TagTree 
//	  * @return the html code as a string
//	  */
//	 private static String BBTagToHtml(BBTag tg) {
//		 StringBuilder out = new StringBuilder();
//		 // Foreach BBtag contained in the innertext of this bbtag, transform
//		 // these BBtag in html
//		 for (BBTag t : tg.getChildrenList()) {
//			 out.append(BBTagToHtml((BBTag)t));
//		 }
//		 switch(tg.getType()){
//		 	case Error:
//		 		 return "<span class=\"parse-error\">"+tg.getErrorText()+": "+tg.getContent()+"</span>";
//		 	case Text:
//				 return tg.getContent();
//		 	case Tag:
//				 DefaultBBTag tag = (DefaultBBTag) tg;
//				 
//				 String notRecognizedAttribute = "";
//				 String bibSignature = "";
//				 
//				 // If it's not the Level 0 tag
//				 if (!(tag.getName().equals(""))) {
//
//					 // if it's a Bbcode tag, we had the attributes in the html tag
//					 if (tag.getAttributes().size() > 0) {
//
//						 // We build differently the special attributes
//						 for (BBAttribute a : tag.getAttributes().values()) {
//							 if (a.getName() != null) {
//								 // if it's a "bib" we have to send the value as a
//								 // signature at the end of the BBTag.
//								 if (a.getName().toLowerCase().contains("error")) {
//									 out.append("<span class=\"parse-error\">the attribute "
//											 + a.getValue()
//											 + " throws some exception: "
//											 + a.getName() + "</span>");
//								 } else if (a.getName().toLowerCase().equals("bib")) {
//									 bibSignature = "<a href=\"/Bibliography#"
//											 + a.getValue() + "\">[" + a.getValue()
//											 + "]</a>" + bibSignature;
//								 } else if (a.getName().toLowerCase()
//										 .equals("addbib")) {
//									 bibSignature = " " + a.getValue()
//											 + bibSignature;
//								 } else if (a.getName().toLowerCase().equals("out")) {
//									 if (tag.getName().equals("bib"))
//										 bibSignature = "(<a href=\"" + a.getValue()
//										 + "\">";
//									 else if (tag.getName().equals("link"))
//										 bibSignature = "<a href=\"" + a.getValue()
//										 + "\">";
//								 } else if (a.getName().toLowerCase()
//										 .equals("article")
//										 || a.getName().toLowerCase().equals("id")
//										 || a.getName().toLowerCase()
//										 .equals("label")
//										 || a.getName().toLowerCase()
//										 .equals("inline")) {
//
//								 } else {
//									 notRecognizedAttribute = "<span class=\"parse-error\">the attribute "
//											 + a.getName()
//											 + " with value=\""
//											 + a.getValue()
//											 + "\" is not recognized</span>";
//								 }
//
//							 }
//						 }
//
//					 }
//					 if (!bibSignature.equals("")) {
//						 String endTag = GetHtmlClosingTag(tag);
//						 if (endTag.contains("span")) {
//							 bibSignature = "<span class=\"bibref\">" + bibSignature
//									 + endTag;
//						 } else if (endTag.contains("div")) {
//							 bibSignature = "<div class=\"bibref-after-block\">"
//									 + bibSignature + endTag;
//						 } else if (tag.getName().equals("bib")) {
//							 return GetHtmlOpenTag(tag) + bibSignature + out
//									 + GetHtmlClosingTag(tag) + notRecognizedAttribute;
//						 } else if (tag.getName().equals("link")) {
//							 return bibSignature + out + "</a>"
//									 + notRecognizedAttribute;
//						 }
//					 }
//					 out = new StringBuilder(GetHtmlOpenTag(tag) + out
//							 + GetHtmlClosingTag(tag) + bibSignature
//							 + notRecognizedAttribute);
//
//		 }
//		 
//		 }
//		 return out.toString();
//	 }
//
//	 /**
//	  * return the good html open tag
//	  * @param tag is the tag we want to convert
//	  * @return the html open tag
//	  */
//	 private static String GetHtmlOpenTag(DefaultBBTag tag) {
//		 String specialValue = "";
//		 switch (tag.getName().toLowerCase()) {
//		 case "quote":
//			 specialValue = "";
//			 if ((tag.getAttributes().get("inline") != null)
//					 && (tag.getAttributes().get("inline").getValue().toLowerCase()
//							 .contains("true"))) {
//				 return "<span class=\"quote-inline\">";
//			 } else {
//				 return "<div class=\"quote-block\">";
//			 }
//		 case "link":
//			 specialValue = "";
//			 if (tag.getAttributes().get("article") != null) {
//				 specialValue = "/Article/"
//						 + tag.getAttributes().get("article").getValue();
//			 } else if (tag.getAttributes().get("out") != null) {
//				 specialValue = tag.getAttributes().get("out").getValue();
//			 }
//			 return "<a href=\"" + specialValue + "\">";
//		 case "unquote":
//			 return "<span class=\"unquote\">";
//		 case "untranslated":
//			 return "<div class=\"quote-untranslated\">";
//		 case "bib":
//			 DefaultBBTag parentTag = (DefaultBBTag) tag.getParent();
//			 if (tag != null) {
//				 if ((parentTag.getAttributes().get("inline") != null)
//						 && (parentTag.getAttributes().get("inline").getValue()
//								 .toLowerCase().contains("true"))) {
//					 return "</span><span class=\"bibref\">";
//				 } else {
//					 return "</div><div class=\"bibref-after-block\">";
//				 }
//			 }
//		 case "actionpoint":
//			 return "";
//		 case "escape":
//			 return "";
//		 default:
//			 return "<span class=\"parse-error\">"+tag.getErrorText();
//		 }
//	 }
//	 /**
//	  * return the good html closing tag
//	  * @param tag is the tag we want to convert
//	  * @return the html closing tag
//	  */
//	 private static String GetHtmlClosingTag(DefaultBBTag tag) {
//		 switch (tag.getName().toLowerCase()) {
//		 case "quote":
//			 if ((tag.getAttributes().get("inline") != null)
//					 && (tag.getAttributes().get("inline").getValue().toLowerCase()
//							 .contains("true"))) {
//				 return "</span>";
//			 } else {
//				 return "</div>";
//			 }
//		 case "link":
//			 return "</a>";
//		 case "unquote":
//			 return "</span>";
//		 case "untranslated":
//			 return "</div>";
//		 case "actionpoint":
//			 if (tag.getAttributes().get("id").getValue() != null)
//				 return GetActionPoint(tag.getAttributes().get("id").getValue());
//			 else
//				 return "ActionPoint error";
//		 case "bib":
//			 return "</a>)";
//		 case "escape":
//			 return "";
//		 default:
//			 return "</span>";
//		 }
//	 }
//	 /**
//	  * Convert the BBCode Actionpoint in html
//	  * @param value is the id of the actionpoint
//	  * @return html actionpoint code
//	  */
//	 private static String GetActionPoint(String value) {
//		 String result = "<div class=\"actionpoint-title\">";
//		 if (value.equals("34"))
//			 result += "Coca gratuit</div><div class=\"actionpoint-body\">Il faut que le coca-cola soit gratuit chez TechnoFuturTIC</div>";
//		 return result;
//	 }

}
