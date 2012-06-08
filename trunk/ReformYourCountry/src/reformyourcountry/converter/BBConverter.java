package reformyourcountry.converter;


import java.util.ArrayList;

import reformyourcountry.parser.*;

public class BBConverter {
	 public static boolean putTagToEndOfList(BBTag parent, String tagName){
		for(BBTag tag:parent.getChildrenList()){
			if (tag.getName().toLowerCase().equals(tagName.toLowerCase())){
				if (parent.getChildrenList().indexOf(tag)!=parent.getChildrenList().size()-1){
					parent.remove(tag);
					parent.add(tag);
					return true;
				}
			}
			if (tag.getName().toLowerCase().equals("quote")){
				if  (putTagToEndOfList(tag,tagName)){
					return true;
				}
			}
		}
		return false;
	 }
	
	 public static Boolean IsTagValid(BBTag tag){
		 ArrayList<String> validAttributeList = new ArrayList<String>();
		 validAttributeList.add("inline");
		 validAttributeList.add("bib");
		 validAttributeList.add("out");
		 validAttributeList.add("addbib");
		 validAttributeList.add("article");
		 validAttributeList.add("label");
		 validAttributeList.add("id");
		 
		 for (BBTag t : tag.getChildrenList()) {
			    IsTagValid((BBTag)t);
			    // out.append(' ');
		 }
		 switch(tag.getType()){
		 	
		 	case Error:
		 		return false;
		 	case Text:
		 		if (tag.getParent().getType() == BBTagType.Tag ||tag.getParent().getType() == BBTagType.Root ||tag.getParent().getType() == BBTagType.Error){
		 			if (!tag.getParent().getName().toLowerCase().equals("escape") && !tag.getName().toLowerCase().equals("codeastext") ){
		 				if (tag.getContent().contains("[")
		 					||tag.getContent().contains("]")
	 						||tag.getContent().contains("<")
	 						||tag.getContent().contains(">")){
				 			tag.setErrorText("The text contains some code part. If you want that, you need to put it in [escape] tag");
		 					return false;
		 				}
		 				return true;
		 			}else{
		 				return true;
		 			}
		 		}else{
		 			tag.setErrorText("There is an unknown problem with the text");
		 			return false;
		 		}
		 	case Tag:
		 		DefaultBBTag tg = (DefaultBBTag)tag;
		 		for (BBAttribute a : tg.getAttributes().values()) {
					if (a.getName() != null) {
					    // if it's a "bib" we have to send the value as a
					    // signature at the end of the BBTag.
					    if (a.getName().toLowerCase().contains("error")) {
					    	tag.setErrorText("the attribute"+ a.getValue()+"throws some exception:"+a.getName());
					    	return false;
					    }
					    if (!validAttributeList.contains(a.getName())){
					    	tag.setErrorText("the attribute"+a.getName()+"is not recognized");
					    	return false;
					    }
					}
		 		}
		 		String name =tag.getName().toLowerCase();
		 		switch (name){
		 			case "quote":
		 				return true;
		 			case "unquote":
		 				DefaultBBTag parentTag = (DefaultBBTag)tag.getParent();
		 				boolean valid = false;
		 				while (parentTag != null){
		 					if (parentTag.getName().toLowerCase().equals("quote")){
		 						valid= true;
		 						break;
		 					}
		 				}
		 				if(!valid){
		 					tag.setErrorText("Unquoted tag must be contained in [quote] tag");
		 					return false;
		 				}
		 				return true;
		 			case "bib":
		 				for(BBAttribute attr :tag.attributes() ){
		 					if (attr.getName().equals("out")){
				 				return true;
		 					}
		 				}
	 					tag.setErrorText("bib tag need an article an out attribute");
 						return false;
		 			case "link":
		 				for(BBAttribute attr :tag.attributes() ){
		 					if (attr.getName().equals("article")^attr.getName().equals("out")){
				 				return true;
		 					}
		 				}
	 					tag.setErrorText("bib tag need an article an out attribute");
 						return false;
		 			case "actionpoint":
		 				for(BBAttribute attr :tag.attributes() ){
		 					if (attr.getName().equals("id")){
				 				return true;
		 					}
		 				}
	 					tag.setErrorText("bib tag need an article an out attribute");
 						return false;
		 			case "escape":
		 				for (BBTag subTag:tag.getChildrenList()){
		 					if (subTag.getType() != BBTagType.Text){
			 					tag.setErrorText("escape tag can't have tags inside");
			 					return false;
		 					}
		 				}
		 				return true;
		 			case "untranslated":
		 				BBTag parent = tag.getParent();
		 				if(parent !=null){
		 					BBTag untranslatedTag = null;
		 					int i =0;
		 					for (BBTag subTag:parent.getChildrenList()){
		 						if (subTag.getName().toLowerCase().equals("untranslated")){
		 							i++;
		 							untranslatedTag= subTag;
		 						}
		 					}
		 					if (i >1){
		 						parent.setErrorText("Tag can't have more than 1 [untranslated] tag");
		 						return false;
		 					}
		 				}else{
		 					tag.setErrorText("Tag [untranslated] must be contained in a [quote] tag");
	 						return false;
		 				}
		 				return true;
		 			default:
		 				tag.setErrorText("The tag is not recognized");
		 				return false;
		 					
		 		}
		 	case Root:
		 		break;
		 }
		 return false;
	 }
	
	 public static String BBTagToHtml(BBTag tg) {
		 StringBuilder out = new StringBuilder();
		 // Foreach BBtag contained in the innertext of this bbtag, transform
		 // these BBtag in html
		 for (BBTag t : tg.getChildrenList()) {
			 out.append(BBTagToHtml((BBTag)t));
		 }
		 switch(tg.getType()){
		 	case Error:
		 		 return "<span class=\"parse-error\">"+tg.getErrorText()+": "+tg.getContent()+"</span>";
		 	case Text:
				 return tg.getContent();
		 	case Tag:
				 DefaultBBTag tag = (DefaultBBTag) tg;
				 
				 String notRecognizedAttribute = "";
				 String bibSignature = "";
				 
				 // If it's not the Level 0 tag
				 if (!(tag.getName().equals(""))) {

					 // if it's a Bbcode tag, we had the attributes in the html tag
					 if (tag.getAttributes().size() > 0) {
						 // BBAttribute defAttribute = attributes.get(null);
						 // if (defAttribute != null) {
						 // out.append('=');
						 // out.append(defAttribute.getValue());
						 // }

						 // We build differently the special attributes
						 for (BBAttribute a : tag.getAttributes().values()) {
							 if (a.getName() != null) {
								 // if it's a "bib" we have to send the value as a
								 // signature at the end of the BBTag.
								 if (a.getName().toLowerCase().contains("error")) {
									 out.append("<span class=\"parse-error\">the attribute "
											 + a.getValue()
											 + " throws some exception: "
											 + a.getName() + "</span>");
								 } else if (a.getName().toLowerCase().equals("bib")) {
									 bibSignature = "<a href=\"/Bibliography#"
											 + a.getValue() + "\">[" + a.getValue()
											 + "]</a>" + bibSignature;
								 } else if (a.getName().toLowerCase()
										 .equals("addbib")) {
									 bibSignature = " " + a.getValue()
											 + bibSignature;
								 } else if (a.getName().toLowerCase().equals("out")) {
									 if (tag.getName().equals("bib"))
										 bibSignature = "(<a href=\"" + a.getValue()
										 + "\">";
									 else if (tag.getName().equals("link"))
										 bibSignature = "<a href=\"" + a.getValue()
										 + "\">";
								 } else if (a.getName().toLowerCase()
										 .equals("article")
										 || a.getName().toLowerCase().equals("id")
										 || a.getName().toLowerCase()
										 .equals("label")
										 || a.getName().toLowerCase()
										 .equals("inline")) {

								 } else {
									 notRecognizedAttribute = "<span class=\"parse-error\">the attribute "
											 + a.getName()
											 + " with value=\""
											 + a.getValue()
											 + "\" is not recognized</span>";
								 }

							 }
						 }

					 }
					 if (!bibSignature.equals("")) {
						 String endTag = GetHtmlClosingTag(tag);
						 if (endTag.contains("span")) {
							 bibSignature = "<span class=\"bibref\">" + bibSignature
									 + endTag;
						 } else if (endTag.contains("div")) {
							 bibSignature = "<div class=\"bibref-after-block\">"
									 + bibSignature + endTag;
						 } else if (tag.getName().equals("bib")) {
							 return GetHtmlOpenTag(tag) + bibSignature + out
									 + GetHtmlClosingTag(tag) + notRecognizedAttribute;
						 } else if (tag.getName().equals("link")) {
							 return bibSignature + out + "</a>"
									 + notRecognizedAttribute;
						 }
					 }
					 out = new StringBuilder(GetHtmlOpenTag(tag) + out
							 + GetHtmlClosingTag(tag) + bibSignature
							 + notRecognizedAttribute);

		 }
		 
		 }
		 return out.toString();
	 }


	 private static String GetHtmlOpenTag(DefaultBBTag tag) {
		 String specialValue = "";
		 switch (tag.getName().toLowerCase()) {
		 case "quote":
			 specialValue = "";
			 if ((tag.getAttributes().get("inline") != null)
					 && (tag.getAttributes().get("inline").getValue().toLowerCase()
							 .contains("true"))) {
				 return "<span class=\"quote-inline\">";
			 } else {
				 return "<div class=\"quote-block\">";
			 }
		 case "link":
			 specialValue = "";
			 if (tag.getAttributes().get("article") != null) {
				 specialValue = "/Article/"
						 + tag.getAttributes().get("article").getValue();
			 } else if (tag.getAttributes().get("out") != null) {
				 specialValue = tag.getAttributes().get("out").getValue();
			 }
			 return "<a href=\"" + specialValue + "\">";
		 case "unquote":
			 return "<span class=\"unquote\">";
		 case "untranslated":
			 return "<div class=\"quote-untranslated\">";
		 case "bib":
			 DefaultBBTag parentTag = (DefaultBBTag) tag.getParent();
			 if (tag != null) {
				 if ((parentTag.getAttributes().get("inline") != null)
						 && (parentTag.getAttributes().get("inline").getValue()
								 .toLowerCase().contains("true"))) {
					 return "</span><span class=\"bibref\">";
				 } else {
					 return "</div><div class=\"bibref-after-block\">";
				 }
			 }
		 case "actionpoint":
			 return "";
		 case "escape":
			 return "";
		 default:
			 return "<span class=\"parse-error\">"+tag.getErrorText();
		 }
	 }

	 private static String GetHtmlClosingTag(DefaultBBTag tag) {
		 switch (tag.getName().toLowerCase()) {
		 case "quote":
			 if ((tag.getAttributes().get("inline") != null)
					 && (tag.getAttributes().get("inline").getValue().toLowerCase()
							 .contains("true"))) {
				 return "</span>";
			 } else {
				 return "</div>";
			 }
		 case "link":
			 return "</a>";
		 case "unquote":
			 return "</span>";
		 case "untranslated":
			 return "</div>";
		 case "actionpoint":
			 if (tag.getAttributes().get("id").getValue() != null)
				 return GetActionPoint(tag.getAttributes().get("id").getValue());
			 else
				 return "ActionPoint error";
		 case "bib":
			 return "</a>)";
		 case "escape":
			 return "";
		 default:
			 return "</span>";
		 }
	 }

	 private static String GetActionPoint(String value) {
		 String result = "<div class=\"actionpoint-title\">";
		 if (value.equals("34"))
			 result += "Coca gratuit</div><div class=\"actionpoint-body\">Il faut que le coca-cola soit gratuit chez TechnoFuturTIC</div>";
		 return result;
	 }

}
