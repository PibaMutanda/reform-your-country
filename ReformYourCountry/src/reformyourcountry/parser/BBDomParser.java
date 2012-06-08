package reformyourcountry.parser;

import java.io.Reader;
import java.util.ArrayList;
import java.util.Deque;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;


/**
 * @author xBlackCat
 */
public class BBDomParser {
    public static boolean isTag(Part p) {
        return isTag(p.getContent());
    }

    public static boolean isTag(String content) {
        return content != null &&
                content.length() >= 3 &&
                content.charAt(0) == '[' &&
                content.charAt(content.length() - 1) == ']';

    }

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
    						if (part.charAt(pos + 1) == ']'){
    							buf.append(part.charAt(pos));
    							pos++;
    						}
    						int i = pos;
    						char testChar = part.charAt(i);
    						while (i < part.length()&& Character.isSpaceChar(testChar)){
    							i = i + 1;
    							testChar = part.charAt(i);
    						}
    					
                            // Attribute name ended - no value
                            state = ReadAttributeState.Null;
                            addAttribute(tag, buf.toString(), "");
                            buf.setLength(0);
                            pos++;
                        } else {
                            buf.append(c);
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
                            addAttribute(tag, name, "");
                        }
                        break;
                    case Value:
                        if (/*Character.isSpaceChar(c) ||*/ openQuote != null && openQuote == c) {
                            // Attribute value ended.
                            state = ReadAttributeState.Null;
                            addAttribute(tag, name, buf.toString());
                            buf.setLength(0);
                        } else {
                            buf.append(c);
                        }
                        break;
                    default:
                        if (Character.isLetter(c)) {
                            state = ReadAttributeState.Name;
                            buf.setLength(0);
                            buf.append(c);
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
                    addAttribute(tag, name, buf.toString());
                    break;
                case Quote:
                    addAttribute(tag, name, "");
                    break;
            }
        }

        return tag;
    }

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
    /**
	 * Add an Attribute to the Tag. Attribute is additional informations
	 * regarding the tag, for instance "href=" for a [a]
	 * 
	 * @param tag
	 * @param name Name of the attribute.
	 * @param value
	 */
    private static void addAttribute(BBTag tag, String name, String value) {
        BBAttribute a = new DefaultBBAttribute(name, tag);
        a.setValue(value);
        tag.add(a);
    }

    public BBTag parse(Reader input) throws BBParserException{
    	return parse(input,new ArrayList<String>());
    }
    /**
     * The main method; parse a text (inside a Reader) for tags, converts them into a DOM Tree. 
     * The parser can accept a list of tags it must ignore.
     * @param input 
     * @param ignoreTags 
     * @return The root of the DOM tree.
     * @throws BBParserException
     */
    public BBTag parse(Reader input, List<String> illegalTags) throws BBParserException {
    	Iterator<Part> i = new SplitIterator(input);

        BBTag root = new DefaultBBTag("", BBTagType.Root, "");

        BBTag currentTag = root;

        Deque<BBTag> tagStack = new LinkedList<BBTag>();

        while (i.hasNext()) {
            String part = i.next().getContent();

            if (isTag(part) && !illegalTags.contains(part)) {   
                String tagName = getTagName(part);
                if (part.charAt(1) == '/') {
                    // The tag is closing tag.

                    // Look for open tag in stack. If match is not found - treat the tag as plain text.

                    // Simple check - is closing current tag?
                    if (tagName!=null && tagName.equals(currentTag.getName())) {
                        currentTag = tagStack.pollFirst();

                        // Checks successful - ignore text content.
                        continue;
                    }

                    // Worse way - one of tags is not closed or open.
                    boolean hasOpenTag = false;
                    for (BBTag t : tagStack) {
                        if (tagName.equals(t.getName())) {
                            hasOpenTag = true;
                            break;
                        }
                    }

                    if (!hasOpenTag) {
                        // The tag has not been open: treat as plain text
                        currentTag.add(new TextBBTag(part));
                        continue;
                    }

                    do {
                        BBTag lastTagParent = currentTag.getParent();
                        lastTagParent.remove(currentTag);
                        StringBuilder content = new StringBuilder(currentTag.getContent());
                        for (BBTag t : currentTag) {
                            content.append(t.getContent());
                        }
                        lastTagParent.add(new TextBBTag(content.toString()));
                        currentTag = tagStack.pollFirst();
                    } while (currentTag != null && currentTag != root && !currentTag.getName().equals(tagName));

                    if (currentTag == null) {
                        // No more tags in stack: it is can not be at all.
                        throw new BBParserException("Expecting open tag in stack.");
                    }

                    // Open tag was found - close it
                    currentTag = tagStack.pollFirst();
                } else {
                    BBTag tag = parseOpenTag(part);
                    currentTag.add(tag);
                    tagStack.addFirst(currentTag);

                    currentTag = tag;
                }
            } else {
            	if(illegalTags.contains(part)){
            		currentTag.add(new TextBBTag("codeAsText", part));
            	}else{
                currentTag.add(new TextBBTag(part));// if part isn't a tag, it's a text.
            	}
            }
        }

        return root;
    }

    private static enum ReadAttributeState {
        Null,
        Name,
        Quote,
        Value
    }
}
