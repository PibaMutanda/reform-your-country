package reformyourcountry.converter;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import reformyourcountry.model.Article;
import reformyourcountry.model.Book;
import reformyourcountry.parser.BBAttribute;
import reformyourcountry.parser.BBDomParser;
import reformyourcountry.parser.BBTag;
import reformyourcountry.repository.ArticleRepository;
import reformyourcountry.repository.BookRepository;
import reformyourcountry.util.FileUtil;
import reformyourcountry.web.UrlUtil;
/**
 * @author FIEUX Cédric
 * this class purpose is to verify the BBCode and there attributes and then return Html Code (with errors commented)
 * exemple:
 * [quote]Je suis une citation avec [link article=\"the-great-article-inside\"]un hyperlien[/link]
 * 
 * is changed in:
 * <div class="quote-block">Je suis une citation avec <a href="/Article/the-great-article-inside">un hyperlien</a> vers un autre article dedans, 
 * ainsi qu�un <a href="http://lesoir.be/toto">hyperlien</a> vers un site web.</div>
 */
public class BBConverter {
	
	static private Log log = LogFactory.getLog(FileUtil.class);
	
	boolean errorFound = false;
	String html="";
	String untranslatedText ="";  //Will contain the text in the tag untranslated (we need it as global variable to use it between different methods)
	String textBuffer=""; // We accumulate Strings fragments that are within the same line (but from consecutive nodes), to place <p> around all the fragments alltogether.
	
	BookRepository bookRepository;  // No @Autowired because we are not in a Spring bean.
	ArticleRepository articleRepository;
	private Set<Book> booksRefferedInTheText = new HashSet<Book>();  // To collect the books seen in the [quote bib="..."] and [link bib="..."].
		
	
	//////////////////////////////////////////////////////////////////////////////////
	/////////////////////////////////// PUBLIC ///////////////////////////////////////
    //////////////////////////////////////////////////////////////////////////////////
	
	public BBConverter(BookRepository bRep, ArticleRepository aRep) {
	    bookRepository = bRep;
	    articleRepository = aRep;
	}
	
	
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
				bufferTextForP(addText);
				//html += processText(addText);
				break;
			case Tag:
				processTag(tag);
				break;
			}
		}
		
		if (!textBuffer.isEmpty()) {
		    processTextHtmlAfterHavingClosedPendingP("");  // Flush the remaining text of currentLine.
		}
		
		//Loop through the set of books to generate the bood divs (call a method).
		addToolTipBooks();
	}
	 
	private void processTag(BBTag tag) {
		switch(tag.getName()) {
		
        case "image":
            processImage(tag);
            break;  
        case "escape":
        	processEscape(tag);
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
		case "todo":
			processTodo(tag);
			break;
		default :
			addErrorMessage("Unrecognized tag",tag);
			
		}
	}


	private void processTodo(BBTag tag) {
		supportedAttributes(tag);
		bufferTextForP("<div class='todo'>");   // Div used instead of a span because span does not allow block (divs, block-quotes,...) inside.
			transformDomToHtml(tag);
		bufferTextForP("</div>");
	}


	private void processEscape(BBTag tag) {
		supportedAttributes(tag);
		String addText = getInnerTextContent(tag)
							.replaceAll("\\[", "&#91;")
							.replaceAll("\\]", "&#93;")
							.replaceAll("\\<", "&lt;")
							.replaceAll("\\>", "&gt;");
		bufferTextForP(addText);
	}

	private void processImage(BBTag tag) {
		supportedAttributes(tag, "name", "style");
		
		String name = tag.getAttributeValue("name");
		
		if(name != null) {

			String imgHtml = "<img src=\"gen/article/"+name+"\"";

			if(tag.getAttributeValue("style") != null)
				imgHtml +=" style=\""+tag.getAttributeValue("style")+"\"";

			imgHtml += "/>";
			bufferTextForP(imgHtml);
		}
		else{
			addErrorMessage("You must specifie a name for the image",tag);
		}
	}
	
	/** Verify that the tag contains no other attribute than those passed as parameter. */
	private void supportedAttributes(BBTag tag, String... attrNamesArray) {
		List<String> attrNames = Arrays.asList(attrNamesArray);
		for (BBAttribute attribute : tag.attributes()) {
			if (!attrNames.contains(attribute.getName())) {
				 addErrorMessage("Unsupported attribute named '"+attribute.getName()+"'", tag);
			}
		}
		
	}

	private void processLink(BBTag tag) {
		supportedAttributes(tag, "article", "bib");
		
		String articleShortName = tag.getAttributeValue("article");
		String bookAbbrev = tag.getAttributeValue("bib");
		String content = getInnerTextContent(tag);// will be empty if the tag is closed([link/] or [link][/link]) or empty.
		

		
		
		if(articleShortName!=null && bookAbbrev!=null){
			addErrorMessage("In a [link] tag, you may not specify both a book and an article. Either give a book reference, or an article's.",tag);
		}
		
        if (articleShortName!=null) { // it's a link to an article
    		Article article = articleRepository.findByShortName(articleShortName);
			if (article == null) {
				addErrorMessage(
						"Aucun article trouvé pour le raccourci suivant : '"
								+ articleShortName + "'", tag);
			} else if (!content.isEmpty()) {
				bufferTextForP("<a href=\"article/" + article.getUrl() + "\">"
						+ content + "</a>");
			} else {
				bufferTextForP("<a href=\"article/" + article.getUrl() + "\">"
						+ article.getTitle() + "</a>");
			}
			
		} else if (bookAbbrev!=null) { // it's a link to a book
        	Book book = processBibAttribute(tag);
        	if (book == null) {
        		// An error message has already been added to the text by processBibAttribute()
        		return;
        	}
			String linkedText = StringUtils.isBlank(content) ? book.getTitle() : content;
			
			bufferTextForP("<a class='"+getCssClassName(book)+" booktitle' href='book#"+book.getAbrev()+"'>" + linkedText + "</a>");
		}
	}

	private void processAction(BBTag tag) {
		supportedAttributes(tag, "id");
		
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

	
	/** Buffers the output text, to keep fragments together before putting a <p> element around it. */
	private void bufferTextForP(String text) {
		textBuffer += text;
	}

	/** Used to insert html, and we want no <p> around it. It's typically the case when we want to insert a <div>: any pending open <p> should be closes (</p>) before that */
	private void processTextHtmlAfterHavingClosedPendingP(String htmlText) {
		if (! textBuffer.isEmpty()) {
			// It's time to put a <p> around the fragments in the buffer, and empty the buffer. 
			PTagsGenerator pTagsGenerator = new PTagsGenerator();
			html+=pTagsGenerator.transformTextBlocksIntoStringWithPTags(textBuffer);
			textBuffer = "";
		}
		
		html += htmlText;
	}

	
	/** Retruns false if attribute = "false" or if not defined. */
	private boolean isAttributeTrue(BBTag tag, String attributeName) {
	    String valueStr = tag.getAttributeValue(attributeName);
	    return !(valueStr == null || valueStr.equals("false"));
	}

	/** This is an example of fragment handled by this method
	   [quote bib="emile"]Some quoted text[/quote]
       Ceci est une belle citation: [quote bib="emile" inline="true"]La valeur n'attend pas[/quote], n'est-ce pas?

     * This would be the produced output generated by this method (inside this.html)
        <blockquote> class="quote-block">
          Some quoted text
        </blockquote>
        <div class="bibref-after-block">
           <label class="bookref-emile, quote-inline">Emile ou de l'éducation, Jean-Jacques Rousseau</label>
        </div>
        
        Ceci est une belle citation: <span class="bookref-emile">La valeur n'attend pas</span>, n'est-ce pas?

     * Later, we'll add the content of the tooltip at the end of the generated text (not in this method)  
        <div id="book-emile" class="book">..... <img .../>title, description, ...</div>

	 */
	private void processQuote(BBTag tag) {
		supportedAttributes(tag, "bib", "author", "out", "inline");
		// bib  either [quote bib="book-ref"]I say it! [/quote]
		//   or either [quote author="anonymous" out="http://myblog/article/happy"] I say it! [/quote]
		// either bib refers a Book or out refers a link to another site (outlink)

		/// 1. We look for a book reference in the quote tag attribute: [quote bib="emile"]...
		Book book = processBibAttribute(tag);

		/// 2. We look for an author with eventually an out link.
		String author = tag.getAttributeValue("author");  
		if (book != null && author != null) {
			addErrorMessage("In a [quote] tag, you may not specify both a book and an author. Either give a book reference, or an author (with maybe an out url)", tag);
		}

		String outUrl = tag.getAttributeValue("out");  
		if (outUrl != null) {
			if (!UrlUtil.isUrlValid(outUrl)) {  // FIXME: opening a connection each time is probably a bad performance idea (unless we buffer the generated html).
				addErrorMessage("Invalid url '" + outUrl+"'", tag);
			}
			if (author == null) {
				addErrorMessage("You may not speficy an 'out' attribute in a quote, when you have no author. Please remove the out attribut or add an author attribute.", tag);
			}
		}


		/// 0. We look for the type of tag (span or div)
		if (isAttributeTrue(tag, "inline")){ //////////////////// Inline quote, just a little span.


			bufferTextForP("<span class='"+	(book != null ? getCssClassName(book) : "") +" quote-inline'>");
			// TODO: prevents  [untranslated]  inside quote inline.
			processQuoteBody(tag);             // Add the quoted text.

			String quoteHtml ="</span>";

			if (author != null) { // Normally, here book == null
				// We need to display, in a bubble tooltip, the author (and maybe with an out link to an URL).
				// We create a div with that content.

				// Outside div which will not be taken inside the tooltip.
				quoteHtml +="<div style='display:none;'>";    // We don't display the author within the text (=> display: none).   

				// Inner div, taken within the tooltip.
				quoteHtml +="<div class='authorToolTip'>";  // div and class to style the tooltip through CSS.

				if (outUrl != null) {
					quoteHtml += "<a href='"+outUrl+"' target = '_blank'>"+author+"</a>";
				} else {
					quoteHtml += author;
				}

				quoteHtml +="</div>";
				quoteHtml +="</div>";
			}
			
			bufferTextForP(quoteHtml);

		} else { //////////////////// Non inline quote, with div and so on.


			//// 2. Add the line below, with who (book or author) is the author of the quote.
			String lineBelowQuote = "";  // Will either contain the book title and other information, or the content of a [bib] subtag.
			String cite = "";  // To be placed in the <blockquote cite='...'> tag. Maybe useful for SEO.
			
			// We add the title of the book or the external link below the quoted text block
			if (book != null || author != null) {  // We need a line below the quote.
				lineBelowQuote += "<div class='bibref-after-block'>\n";

				if (book != null) { // book title to be added
					lineBelowQuote += "<span class ='"+getCssClassName(book)+" booktitle'>";
					lineBelowQuote += book.getTitle();
					if (book.getAuthor()!= null && !book.getAuthor().isEmpty()){
						lineBelowQuote += " &ndash; "+book.getAuthor();
					}
					if(book.getPubYear() != null && !book.getPubYear().isEmpty()){
						lineBelowQuote += " &ndash; " + book.getPubYear();
					}
					lineBelowQuote +="</span>\n";
					
					cite = "cite='"+ UrlUtil.getAbsoluteUrl("book#"+book.getAbrev()) +"'";
				}

				if (author != null) {  // author to be added
					lineBelowQuote += "<span class='author'>";
					if (outUrl != null) {
						lineBelowQuote += "<a href='"+outUrl+"' target='_blank'>" + author + "</a>";
						cite = "cite='"+ outUrl +"'";
					} else {
						lineBelowQuote += author;
					}
					lineBelowQuote += "</span>";
				}
				lineBelowQuote +="</div>\n";
			}
			
			//// 1. Add the quoted text.
			processTextHtmlAfterHavingClosedPendingP("<blockquote class=\"quote-block\" "+cite+">\n");
			
			// For text that has a translation, we add div arounds.
			boolean hasUntranslated = containsSubTag(tag, "untranslated");
			if(hasUntranslated){
				processTextHtmlAfterHavingClosedPendingP("<div class=\"translated\"><div class=\"translatedcontent\">");
			}
			
			processQuoteBody(tag);             // Add the quoted text. 
			
			if(hasUntranslated){
				processTextHtmlAfterHavingClosedPendingP("</div></div>");
				processTextHtmlAfterHavingClosedPendingP(untranslatedText);
			}
			processTextHtmlAfterHavingClosedPendingP("</blockquote>\n");

			///// 2. The ref line (book title) below the quote.
			processTextHtmlAfterHavingClosedPendingP(lineBelowQuote);
		}


	}

/**
 * Used to process references to a book in quote and link tags
 * @param tag either [link] or [quote]
 * @return the book associated with the bib reference, or null if there is none
 */
	private Book processBibAttribute(BBTag tag) { 
		Book book = null;
		String bibValueFromAttrib = tag.getAttributeValue("bib");  // Books can only be referred through attribute (not nested tag)
		if (bibValueFromAttrib != null) {
			book = bookRepository.findBookByAbrev(bibValueFromAttrib);
			if (book == null) {
				addErrorMessage("Book not found in DB for abrev = '"+bibValueFromAttrib+"'", tag);
				return book;
			}
			booksRefferedInTheText.add(book);
		}
		return book;
	}


	private boolean containsSubTag(BBTag tag, String searchedName) {
		for(BBTag child : tag.getChildrenList()){
			if(child.getName().equals(searchedName)){
				return true;
			}
		}
		return false;
	}


    private void processQuoteBody(BBTag tag) {
		int untranslatedCounter=0; // To verify that there are 0 or 1 (no more)
		
		for(BBTag child:tag.getChildrenList()){
			switch(child.getType()) {
			case Error :
				addErrorMessage(child);
				break;
			case Text :
				String addText = child.getContent();
				bufferTextForP(addText);
				break;
			case Tag :
				switch(child.getName()){
				///////////// Unquote
				case "unquote": 
					processUnquote(child);
					break;
				case "untranslated":
					/////////// Untranslate TODO add error if tag inside untranslated
					untranslatedText = processUntranslated(child);
					untranslatedCounter++;
					break;
				case "link":
					processLink(child);
					break;
				case "escape":
				
					processEscape(tag);
					
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
    }

	
	/** Returns the class name with the book abbrev in it, so the JavaScript code can find it easily, for example with the book "emile":  $(".bookref-emile")
	 */
	private String getCssClassName(Book book){
	    return "bookref-"+book.getAbrev();
	}

	
	private void addToolTipBooks(){
	    for(Book book :booksRefferedInTheText){
	        html += getBookFragment(book);
	    }
	}
	
	private String processUntranslated(BBTag tag) {
		String result="";
		result = "<div class=\"untranslated\"><div class=\"untranslatedcontent\">"+ getInnerTextContent(tag)+"</div></div>";
		return result;
	}

	private void processUnquote(BBTag tag) {
		supportedAttributes(tag);
		
		bufferTextForP("<span class=\"unquote article_content\">");
		processQuoteBody(tag);
		bufferTextForP("</span>");//we add article_content in the class to change the style of the unquote to the one of article_content
		
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
		processTextHtmlAfterHavingClosedPendingP("<span class=\"error\">"+ message + " (for tag "+tag.getContent()+")</span>");    
	}

	private void addErrorMessage(BBTag tag) {
		addErrorMessage(tag.getErrorText(), tag);  
	}	
	
    public static String getBookFragment(Book book) {
        String block = "";

        block += "<div id ='book-"+book.getAbrev()+"' style='display:none;'>"; // This div will be "removed" by jQuery bubbles (tooltip)
        block += "<div class='book'>";  // Will be in the tooltip too.
        
        if(book.isHasImage())  // TODO: Change the URL
            block += "<img src='gen" + FileUtil.BOOK_SUB_FOLDER + FileUtil.BOOK_RESIZED_SUB_FOLDER +"/"+book.getId()+".jpg' alt='"+book.getTitle()+"' class='imgbook'>";

        if (book.getSubtitle()!=null) {
        	block += "<a href='book#"+book.getAbrev()+"'>";  // bibliography page 
        	block += book.getTitle()+" "+book.getSubtitle()+"</a><br/>";
		}else{
			block += "<a href='book#"+book.getAbrev()+"'>";  // bibliography page 
        	block += book.getTitle()+"</a><br/>";
		}
        
        if(book.getAuthor()!=null && book.getPubYear()!=null){
        	block += "<span class='bookInfo'>"+book.getAuthor()+" - "+ book.getPubYear()+"</span><br/>";
        }
		
        if(book.getDescription()!=null){
        	block += "<p class=\"bookContent\">"+book.getDescription()+"</p>";
        }
        
        
        block +="</div>\n";
        
        block +="</div>\n";

        return block;

    }

}
