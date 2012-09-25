package reformyourcountry.converter;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.util.StringUtils;



/** Inserts <p> tags to form paragraphs
 * Example input: 
 *   Hello
 *   World
 *   
 *   line4<br />
 *   line5
 *   line6
 *   
 *   <ul><li>Item1</li>   
 *   </li>Item2</li></ul> 
 *   
 * Example output:
 *   <p>Hello
 *   World</p>
 *   
 *   <p>line4<br />   -- br was already there in input.
 *   line5<br/>       -- added br
 *   line5</p>
 *  
 *   <ul><li>Item1</li>   -- no <p> here because of open <ul> pending
 *   </li>Item2</li></ul> -- idem, open <ul> pending at previous line (must be closed at both lines)
 *  */
public  class PTagsGenerator {

	final String[] tagsThatCannotIncludeParagraph = new String[]{"li", "ul", "ol", "table", "td", "tr"};  // we don't want <ul> <p>...</p> </ul>, for example.
	Map<String, Integer> tagOpenCountMap = new HashMap<String, Integer>();  // Key = tagname (as "ul") and Value = how many of these are currently open?

	BufferedReader input;  // Scanner skips empty lines (which we don't want)
	StringBuffer output;

	private List<Line> lines = new ArrayList<>();


	public String transformTextBlocksIntoStringWithPTags(String text) {
		createLinesFromInput(text);
		processParagraphs();
		processBr();
		return transformLinesIntoString();
	}

	private void createLinesFromInput(String text) {
		input = new BufferedReader( new StringReader(text) );

		String lineStr = getNextLine();
		while (lineStr != null) {
			Line line = new Line();
			lines.add(line);
			line.content = lineStr;
			lineStr = getNextLine();
		}
	}


	private void processParagraphs() {
		boolean openPHasNotBeenClosed = false;
		boolean thereAreOpenTagsWithOrWithoutCurrentLine = false;

		for (int currentLineIdx = 0; currentLineIdx < lines.size(); currentLineIdx++) {
			Line line = lines.get(currentLineIdx);
			String trimmedLine = line.content.trim();

			thereAreOpenTagsWithOrWithoutCurrentLine = tagOpenCountMap.size() > 0;
			updateOpenTagsCount(trimmedLine);
			thereAreOpenTagsWithOrWithoutCurrentLine = thereAreOpenTagsWithOrWithoutCurrentLine || tagOpenCountMap.size() > 0;

			if (!thereAreOpenTagsWithOrWithoutCurrentLine) { // ok, no pending tag open for <ul>, <li> and co.

				if (openPHasNotBeenClosed) {//if we are in a paragraph

					if ("".equals(trimmedLine)) { // An empty line means I've to close the paragraph.
						lines.get(currentLineIdx-1).closeParagraphAtTheEnd = true;  // It's more aesthetic to close it at the previous (not empty line, line2 instead of line3): <p>Line1<br/>\nLine2</p>\n\n
						openPHasNotBeenClosed = false;
					}
					
				} else {  // Previous paragraph has been closed => we may need a new one
					if (!"".equals(trimmedLine)) { // we put a new p only non blank line otherwise it's only blank between paragraphs
						line.openParagraphAtStart = true;
						openPHasNotBeenClosed = true;
					} 
				}
			} else { // There is a pending <ul> <table> or other.  ==> don't mess that up with br
				line.addBrAtTheEnd = false;
				
				if (openPHasNotBeenClosed) {  // A <p> has been open, and now we have an <ul> or <table> or... tag => that <p> should be closed. 
					lines.get(currentLineIdx-1).closeParagraphAtTheEnd = true;   // At that line, the <ul> or <table> was not open yet (else we would have closed that <p> sooner...) 
					openPHasNotBeenClosed = false;
				}
			}
		}
		
		if (openPHasNotBeenClosed) {//after the loop we need to verify if we must end a paragraph
			Line line = lines.get(lines.size() -1);  // Last line  (there is at least one line in lines, since a paragraph has been open).
			line.closeParagraphAtTheEnd = true;
			openPHasNotBeenClosed = false;
		}
	}

	/** Adds <br/> */
	private void processBr() {
		boolean openPHasNotBeenClosed = false;
		
		for (int currentLineIdx = 0; currentLineIdx < lines.size(); currentLineIdx++) {
			Line line = lines.get(currentLineIdx);
			
			if (line.addBrAtTheEnd == null) { // Not yet decided
				String trimmedLine = line.content.trim();

				if (line.closeParagraphAtTheEnd == true) {
					openPHasNotBeenClosed = false;
				} else if (line.openParagraphAtStart == true) {
					openPHasNotBeenClosed = true;
				}

				if (openPHasNotBeenClosed) {  // We are in a paragraph => the next line is most likely not empty
					if (trimmedLine.contains("<br>") || trimmedLine.contains("<br/>") || trimmedLine.contains("<br />")) {
						// The current line already contains a <br/> => we don't add one
					} else {
						line.addBrAtTheEnd = true;
					}
				}
			}
			line.addBrAtTheEnd = line.addBrAtTheEnd != null ? line.addBrAtTheEnd : false;  // if null => false 
		}
	}
	

	private String transformLinesIntoString() {
		output = new StringBuffer();

		for (int currentLineIdx = 0; currentLineIdx < lines.size(); currentLineIdx++) {
			Line line = lines.get(currentLineIdx);
			String trimmedLine = line.content.trim();

			if (line.openParagraphAtStart) {
				output.append("<p>");
			}
			output.append(trimmedLine);
			if (line.closeParagraphAtTheEnd) {
				output.append("</p>");
			}
			if(line.addBrAtTheEnd){//if we are in an ending line case
				output.append("<br/>");
			}
			output.append("\n");//to have more spaced code
		}

		return output.toString();
	}


	class Line {
		private boolean openParagraphAtStart;
		private boolean closeParagraphAtTheEnd;
		private Boolean addBrAtTheEnd;  // null == I don't know yet.

		private String content;
	}


	//		public String transformTextBlocksIntoStringWithPTagsOld(String text) {
	//			input = new BufferedReader( new StringReader(text) );
	//			StringBuffer output = new StringBuffer(text.length() + (text.length()/4));
	//			
	//			boolean openPHasNotBeenClosed = false;  // true if a <br/> makes the paragraph span multiple lines. 
	//			String line = getNextLine();
	//			while (line != null) {
	//				String trimmedLine = line.trim();
	//				if (currentLineShouldBePTagged) {
	//					if ("".equals(trimmedLine)) { // Empty line. We remove them. If the user really wants extra lines, he must insert <br/>
	//						// do nothing (don't output a <p></p>)
	//						
	//					} else {  // Non empty line (normal case)
	//					    boolean thereAreOpenTagsWithOrWithoutCurrentLine = tagOpenCountMap.size() > 0;
	//	                    updateOpenTagsCount(trimmedLine);
	//	                    thereAreOpenTagsWithOrWithoutCurrentLine = thereAreOpenTagsWithOrWithoutCurrentLine || tagOpenCountMap.size() > 0;
	//					    if (thereAreOpenTagsWithOrWithoutCurrentLine) { // Some pending <ul>, <li> or other anti-paragraph element open
	//                            /// We don't want a "<ul>" to be transformed into a "<p><ul></p>"
	//                            output.append(line + "\n");
	//					    } else { // ok, no pending tag open for <ul>, <li> and co.
	//
	//					        if (trimmedLine.indexOf("<br>") >=0 || trimmedLine.indexOf("<br/>") >=0) { // Contains (probably ends with) "<br>"
	//					            // Means that the user does not want us to add a paragraph around, but simply wants a line break inside another paragraph.
	//					            // Example: "<p>Hello<br/> \n   World</p>
	//					            if (! openPHasNotBeenClosed) { // It's a new paragraph (usual case)
	//					                output.append(openingParagraphTag);
	//					                openPHasNotBeenClosed = true;  // Should be closed later.
	//					            }
	//					            output.append(line + "\n");
	//					        } else { // Usual case
	//					            if (! openPHasNotBeenClosed) { // It's a new paragraph (usual case)
	//					                output.append(openingParagraphTag);
	//					            }
	//					            output.append(line + closingParagraphTag+"\n");
	//					            openPHasNotBeenClosed = false;  
	//					        }
	//					    }
	//					}
	//				} else { // Special line with no <p>. example: "<img .../>", or "<pre>abcd".
	//					output.append(line + "\n");
	//				}
	//				line = getNextLine();
	//			}
	//
	//			return output.toString();
	//		}


	/** update the counters of open tags. */
	private void updateOpenTagsCount(String line) {
		for (String tagName : tagsThatCannotIncludeParagraph) {
			Integer openCount = tagOpenCountMap.get(tagName);
			if (openCount == null) {
				openCount = 0;
			}
			int delta = countOpenMinusCloseInLine(tagName, line);
			openCount = new Integer(openCount+delta);
			if (openCount <= 0) { // Never < 0 if html is well formed (wich may not be the case if the author made a mistake)
				tagOpenCountMap.remove(tagName);
			} else {
				tagOpenCountMap.put(tagName, openCount);
			}
		}
	}

	/** with line = "<ul> aaa  <ul> zzzz   </ul> rrr" with tagname = "ul", it will return +1 */
	private int countOpenMinusCloseInLine(String tagName, String line) {
		return StringUtils.countOccurrencesOf(line, "<"+tagName) - StringUtils.countOccurrencesOf(line, "</"+tagName) 
				- StringUtils.countOccurrencesOf(line, tagName+"/>") - StringUtils.countOccurrencesOf(line, tagName+" />");  // Not really robust, because there could be attributes between the tag name and the closing bracket.
	}




	// If we have 5 TextBlocks: 
	//   "This image shows\n",   true
	//   "<img .... />",         false
	//   "\nthe ",               true
	//   "<code>Button</code>",  true (because inline code)
	//   " class.\nBye."         true
	// then we have the following lines
	//   This image shows        true
	//   <img .../>              true
	//   the <code>Button</code> class. true
	//   Bye                     true
	private String getNextLine() {
		try {
			return input.readLine();
		} catch (IOException e) {
			throw new RuntimeException("IO Exception while reading a String ???", e);
		}

		//			if (currentTextBlockIndex >= textBlocks.size()) {
		//				return null; // Last line reached
		//			}
		//				
		//			String result = "";
		//			TextBlock textBlock = textBlocks.get(currentTextBlockIndex);
		//			
		//			if (splitsOfCurrentBlock != null &&  // Could be the case for the 1st call.
		//					currentSplitIndex < splitsOfCurrentBlock.length-1) {  // It's not the last line of its block 
		//				result = splitsOfCurrentBlock[currentSplitIndex];
		//				currentSplitIndex++;
		//				currentLineShouldBePTagged = textBlock.insertParagraphs;
		//				
		//			} else {  // We are the last line of the block.
		//				if (splitsOfCurrentBlock == null) { // First call to getNextLine 
		//					currentTextBlockIndex = -1;  // a ++ follows below...
		//				} else { // Normal case
		//					result = splitsOfCurrentBlock[currentSplitIndex];
		//					currentLineShouldBePTagged = textBlock.insertParagraphs;
		//				}
		//				//// Next block with a \n
		//				// We iterate until we find a block containing are \n
		//				while (true) {  // We have return inside statements as stop conditions.
		//					// Next block from the list
		//					splitsOfCurrentBlock = null;
		//					currentSplitIndex = 0;
		//					currentTextBlockIndex++;
		//					
		//					if (currentTextBlockIndex < textBlocks.size()) { // One more block to handle
		//						textBlock = textBlocks.get(currentTextBlockIndex);
		//						currentLineShouldBePTagged = currentLineShouldBePTagged && textBlock.insertParagraphs;  // Should be tagged with <p> only if all parts of the line (result) should be tagged.
		//						splitsOfCurrentBlock = textBlock.text.split("\n");
		//						if (textBlock.text.endsWith("\n")) { // There is a new line at the end of the block (not detected by the split above).
		//							// We are going to add one empty element "" at the end of splitsOfCurrentBlock.
		//							splitsOfCurrentBlock = Arrays.copyOf(splitsOfCurrentBlock, splitsOfCurrentBlock.length+1);
		//							splitsOfCurrentBlock[splitsOfCurrentBlock.length-1] = ""; // additional element (will be added at the beginning of the next line).
		//						}
		//
		//						if (splitsOfCurrentBlock.length == 1) {  // There is no "/n" in this block.
		//							// We add the split and we continue for more.
		//							result += splitsOfCurrentBlock[0];
		//						} else if (splitsOfCurrentBlock.length > 1) { // There is a \n in this block. We'll stop iterating for blocks here.
		//							// We add the first split and we stop.
		//							result += splitsOfCurrentBlock[0];
		//							currentSplitIndex++;
		//							return result;
		//						} else throw new RuntimeException("This should not happen... String is ["+textBlock.text+"]");
		//					} else {  // We have reached the end, the last block.
		//						return result;
		//					}
		//				}
		//			}
		//			
		//			return result;
	}
}



