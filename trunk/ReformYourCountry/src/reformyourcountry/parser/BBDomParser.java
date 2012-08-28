package reformyourcountry.parser;

import java.util.ArrayList;
import java.util.Deque;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;


/**
 * 
 * This will parse trough a text for BBCode tags and sort them into a DOM tree.
 * 
 * 
 * 
 * example :
 * [quote]a text and [link]a link[/link] and [code]some code[/code][/quote]
 * 
 * output:
 *   root: BBTag
 *      |- [quote]: BBTag
 *      |	|-a text and: TextBBTag
 *		|	|-[link]: BBTag
 *		|	|	|-a link: TextBBTag
 *		|	|-and: TextBBTag
 *		|	|-[code]: BBTag
 *		|	|	|-some code: TextBBTag
 * 
 * @author xBlackCat & Thomas Van Roy
 */
public class BBDomParser {

	List<String> ignoredTags = new ArrayList<String>();
	boolean escapeAsText = false;

	////////////////////////////////////////////// PUBLIC API ////////////////////////////////////////// 
	////////////////////////////////////////////// PUBLIC API ////////////////////////////////////////// 
	////////////////////////////////////////////// PUBLIC API ////////////////////////////////////////// 
	////////////////////////////////////////////// PUBLIC API ////////////////////////////////////////// 
	
	public BBDomParser(){}
	public BBDomParser(List<String> ignoredList){
		this.ignoredTags=ignoredList;
	}

	public void setEscapeAsText(boolean escapeAsText){
		this.escapeAsText=escapeAsText;
	}
	/**
	 * The main method; parse a String for BBTags, converts them into a DOM Tree. 
	 * @param input 
	 * @return The root of the DOM tree.
	 * @throws BBParserException
	 */
	public BBTag parse(String input) throws BBParserException {
		Iterator<Part> i = new SplitIterator(input);

		BBTag root = new DefaultBBTag("", BBTagType.Root, "");  // The root tag is always a DefaultBBTag.

		BBTag currentTag = root;

		Deque<BBTag> tagStack = new LinkedList<BBTag>();

		while (i.hasNext()) {
			String part = i.next().getContent();

			if ((escapeAsText && currentTag.getName().equals("escape")&&!part.equals("[/escape]")) ) {  // the tag is [escape] and we need to skip it (not parse what's inside)
				if (ignoredTags.contains(part)){
					currentTag.add(new TextBBTag("codeAsText", part));
				} else if (!part.equals("[/escape]")){
					currentTag.add(new TextBBTag(part));// if part isn't a tag, it's a text.
				}

			} else {  // Normal case

				if (isTag(part) && !ignoredTags.contains(part)) {  // It's a tag (not a raw text or ignored tag)
					String tagName = getTagName(part);

					if (part.charAt(1) != '/') { // Opening tag
						BBTag tag = parseOpenTag(part);
						currentTag.add(tag);
						char testEnd=tag.getContent().charAt(tag.getContent().length()-2);
						if (testEnd!='/'){
							tagStack.addFirst(currentTag);
							currentTag = tag;
						}

					} else { // The tag is closing tag.

						// Look for open tag in stack. If match is not found - treat the tag as plain text.

						// Simple check - is closing current tag?
						if (tagName != null
								&& tagName.equals(currentTag.getName())) {
							currentTag = tagStack.pollFirst();

							// Checks successful - ignore text content.
							continue;
						}

						// Verifying Worse way - one of tags is not closed or open.
						boolean hasOpenTag = false;
						for (BBTag t : tagStack) {
							if (tagName.equals(t.getName())) {
								hasOpenTag = true;
								break;
							}
						}
						if (!hasOpenTag) {// The tag has not been open: treat as plain text
							currentTag.add(new DefaultBBTag(part.toString(),BBTagType.Error,"This closing tag has no opening tag"));
							return root;
						}

						BBTag lastTagParent = currentTag;
						// Normal case
						// We put the tag in the correct tag (children inside parents)
						while (lastTagParent != null && lastTagParent != root && !lastTagParent.getName().equals(tagName)) {
							if (lastTagParent!=null&&!lastTagParent.getName().equals(tagName)){
								DefaultBBTag ErrorTag= new DefaultBBTag("/"+tagName,BBTagType.Tag,part);
								ErrorTag.setErrorText("This closing tag has no opening tag.");
								lastTagParent.add(ErrorTag);
							}
							lastTagParent = lastTagParent.getParent();
						} 
						if (lastTagParent == null){
							DefaultBBTag ErrorTag= new DefaultBBTag("/"+tagName,BBTagType.Tag,part);
							ErrorTag.setErrorText("This closing tag has no opening tag.");
							root.add(ErrorTag);
							return root;
						}


						// Open tag was found - close it
						currentTag = tagStack.pollFirst();
					}

				} else {  // It's not a tag (raw text or ignored tag considered as raw text) 
					if(ignoredTags.contains(part)){
						currentTag.add(new TextBBTag("codeAsText", part));
					} else{
						currentTag.add(new TextBBTag(part));// if part isn't a tag, it's a text.
					}
				}

			}
			//			if(((SplitIterator) i).getError()){
			//				currentTag.add(new DefaultBBTag("Error", BBTagType.Error, "An error occured; some text have not been parsed"));
			//			}
		}

		return root;
	}




    /**
     * This method will parse tags themselves, identifying their name and eventual attributes and attribute values.
     * It will then create the BBTag object with those informations.
     * 
     * @param part
     * @return
     */
	protected BBTag parseOpenTag(String part) {
		String tagName = getTagName(part);
		if (tagName == null /*|| !Character.isLetter(part.charAt(1))*/) {
			return null;
		}

		BBTag tag = new DefaultBBTag(tagName, BBTagType.Tag, part);

		ReadAttributeState state = ReadAttributeState.Null;

		String name = null;
		Character openQuote = null;

		StringBuilder buf = new StringBuilder();

		int pos = tagName.length() + 1;
		char c;
		if (pos != part.length() - 1) {
			while ((c = part.charAt(pos)) != ']') {
				pos++;
				switch (state) {
				case Name:
					if (c == '=') {
						// Attribute name ended.
						state = ReadAttributeState.Quote;
						if (buf.length() > 0) {
							name = buf.toString();
							buf.setLength(0);
						} else {
							name = null;
						}
					} else if (Character.isSpaceChar(c)|| part.charAt(pos + 1) == ']') {
						buf.append(c);
						if (part.charAt(pos + 1) == ']') {
							buf.append(part.charAt(pos));
							pos++;
						}
						int i = pos;
						char testChar = part.charAt(i);
						while (i < part.length()
								&& Character.isSpaceChar(testChar)) {
							i = i + 1;
							testChar = part.charAt(i);
						}
						// Attribute name ended - no value
						state = ReadAttributeState.Null;
						addAttribute(tag, buf.toString(), "");
						buf.setLength(0);
						pos++;
					} else {
						buf.append(Character.toLowerCase(c));
					}
					break;
				case Quote:
					if (c == '"' || c == '\'') {
						openQuote = c;
						state = ReadAttributeState.Value;
					} else if (!Character.isSpaceChar(c)) {
						openQuote = null;
						buf.setLength(0);
						buf.append(c);
						state = ReadAttributeState.Value;
					} else {
						state = ReadAttributeState.Null;
						if(!tag.attributes().contains(buf.toString())){
							addAttribute(tag, name, "");
						}
					}
					break;
				case Value:
					if (/*Character.isSpaceChar(c) ||  */openQuote != null && openQuote == c) {
						// Verify that the tag doesn't already have the same attribute.
						if (tag.getAttributeValue(name)==null) {
							// Attribute value ended.
							state = ReadAttributeState.Null;
							addAttribute(tag, name, buf.toString());
							buf.setLength(0);
						}else{
							tag.setErrorText("Tag contains the same attribute more than once");
						}
					} else {
						buf.append(c);
					}
					break;
				default:
					if (Character.isLetter(c)) {
						state = ReadAttributeState.Name;
						buf.setLength(0);
						buf.append(Character.toLowerCase(c));
					} else if (c == '=') {
						// Default attribute
						state = ReadAttributeState.Quote;
						buf.setLength(0);
						name = null;
					}
					break;
				}
			}

			// Make last step according state
			switch (state) {
			case Name:
				addAttribute(tag, buf.toString(), "");
				break;
			case Value:
				if (tag.getAttributeValue(name)==null) {
					addAttribute(tag, name, buf.toString());
				}
				break;
			case Quote:
				addAttribute(tag, name, "");
				break;
			}
		}

		return tag;
	}
	/**
	 * Add a tag to the list of ignorable tags.
	 * @param tag in the [...] form.
	 */
	public void addIgnoredTag(String tag){
		ignoredTags.add(tag);
	}


	



	/////////////////////////////////////// INTERNALS ///////////////////////////////////////////////
	/////////////////////////////////////// INTERNALS ///////////////////////////////////////////////
	/////////////////////////////////////// INTERNALS ///////////////////////////////////////////////
	/////////////////////////////////////// INTERNALS ///////////////////////////////////////////////
	/////////////////////////////////////// INTERNALS ///////////////////////////////////////////////
	/////////////////////////////////////// INTERNALS ///////////////////////////////////////////////
	/////////////////////////////////////// INTERNALS ///////////////////////////////////////////////
	/////////////////////////////////////// INTERNALS ///////////////////////////////////////////////
	/////////////////////////////////////// INTERNALS ///////////////////////////////////////////////
	
	
	/**
	 * Extracts a tag name from string. If string do not matched '[text]' or '[/text]' - <code>null</code> is returned.
	 *
	 * @param tag
	 * @return tag name or <code>null</code>
	 */
	public static String getTagName(String tag) {
		if (!isTag(tag)) {
			return null;
		}

		String tagName="";

		int size = tag.length();
		boolean closing = tag.charAt(1) == '/';
		int emptyTagSize = closing ? 2 : 1;// curr = 2 if the tag is a closing tag ([/...]) , curr = 1 if the tag is opening ([...]).

		if (emptyTagSize >= size-1) {// check if the tag is empty ([] or [/])
			return null;
		}
		int curr = emptyTagSize;

		char currChar = Character.toLowerCase(tag.charAt(curr++));
		while (curr < size && !Character.isSpaceChar(currChar)) {
			tagName+=currChar;
			currChar = Character.toLowerCase(tag.charAt(curr++));
		}

		if (tagName.length() == 0) {
			return null;
		}

		return tagName;
	}
	
	
	public static boolean isTag(Part p) {
		return isTag(p.getContent());
	}

	public static boolean isTag(String content) {
		return content != null &&
				content.length() >= 3 &&
				content.charAt(0) == '[' &&
				content.charAt(content.length() - 1) == ']';

	}

	/**
	 * Add an Attribute to the Tag. Attribute is additional informations
	 * regarding the tag, for instance "href="http://link"" for a [a]
	 * @param tag the tag from witch the attribute belong
	 * @param name the name of the attribute ie: href
	 * @param value the value of the attribute ie: http://link
	 */
	private static void addAttribute(BBTag tag, String name, String value) {
		BBAttribute a = new DefaultBBAttribute(name);
		a.setValue(value);
		tag.add(a);
	}




	private static enum ReadAttributeState {
		Null,
		Name,
		Quote,
		Value
	}
}
