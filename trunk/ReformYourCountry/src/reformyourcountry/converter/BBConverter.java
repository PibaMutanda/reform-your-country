package reformyourcountry.converter;
import java.io.IOException;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URL;

import reformyourcountry.parser.BBDomParser;
import reformyourcountry.parser.BBTag;
import reformyourcountry.utils.FileUtils;
/**
 * @author FIEUX Cédric
 * this class purpose is to verify the BBCode and there attributes and then return Html Code (with errors commented)
 * exemple:
 * [quote]Je suis une citation avec [link article=\"the-great-article-inside\"]un hyperlien[/link]
 * is changed in:
 * <div class="quote-block">Je suis une citation avec <a href="/Article/the-great-article-inside">un hyperlien</a> vers un autre article dedans, 
 * ainsi qu�un <a href="http://lesoir.be/toto">hyperlien</a> vers un site web.</div>
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
	 * @return htmlCode
	 */
    public String transformBBCodeToHtmlCode(String bbCode){
    	html="";
		 BBDomParser dp = new BBDomParser();
		 dp.setEscapeAsText(true);
		 dp.addIgnoredTag("[...]");
		 BBTag root = dp.parse(bbCode);
		 transformDomToHtml(root);
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
		
		case "image":
		    processImage(tag);
            break;    
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
		default :
			addErrorMessage("Unrecognized tag",tag);
			
		}
	}
	
	private void processImage(BBTag tag) {
        html+="<img src=\""+FileUtils.getArticlePicsFolderPath()+tag.getAttributeValue("name")+"\"/>";
        
    }

    private void processLink(BBTag tag) {
		String article = tag.getAttributeValue("article");
        String abrev = tag.getAttributeValue("abrev");
		String content="";
		content = getInnerTextContent(tag);
		if (article == null){
		    if (abrev !=null){
		        html+= "<p onmouseover=\"showBookPopUp("+abrev+")\">"+content+"</p>";
		    }else{
		        String out = tag.getAttributeValue("out");
		        String label = tag.getAttributeValue("label");
		        html+= "<a href=\""+out+"\">"+content+"</a>";
			}
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
		String inline = tag.getAttributeValue("inline");
		if (inline == null || inline.equals("false")){
			html +="<div class=\"quote-block\">";
		} else {
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
		String bibRefString ="";
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
					processUnquote(child);
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
				case "bib":
					break;
				default:
					addErrorMessage("You cannot put this sort of tag in a [quote] tag", child);
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
		} else {
			addBib =" "+addBib;
		}
		
		if (inline == null || inline.equals("false")){
			startTag="<div class=\"bibref-after-block\">";
			endTag="</div>";
		} else {
			startTag="<span class=\"bibref\">";
			endTag="</span>";
		}
		
		if (content == null || content.equals("")) {
			result += startTag+"<a href=\"/Bibliography#"+bibRef+"\">["+bibRef+"]</a>"+addBib+endTag;
		} else {
			result += startTag+"(<a href=\""+bibRef+"\">"+content+"</a>)"+addBib+endTag;
		}
		return result;
	}

	private String processUntranslated(BBTag tag) {
		String result="";
		result = "<div class=\"quote-untranslated\">"+ getInnerTextContent(tag)+"</div>";
		return result;
	}

	private void processUnquote(BBTag tag) {
		String htmlTxt="";
		htmlTxt +="<span class=\"unquote\">"+getInnerTextContent(tag)+"</span>";
		
		html+= htmlTxt;
	}

	private BBTag getChildTagWithName(BBTag tag, String name) {
		 
		for(BBTag child:tag.getChildrenList()){
			String childName = child.getName().toLowerCase();
			if (childName.equals(name.toLowerCase())){
				return child;
			}
		}
		return null;
	}

	private void addErrorMessage(String message, BBTag tag) {
		errorFound = true;
		html += "<span class=\"error\">"+ message + " (for tag "+tag.getContent()+")</span>";    
	}

	private void addErrorMessage(BBTag tag) {
		addErrorMessage(tag.getErrorText(), tag);  
	}	
	
	

}
