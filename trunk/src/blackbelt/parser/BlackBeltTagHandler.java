package blackbelt.parser;

import blackbelt.parser.BlackBeltTagParser.Element;

public interface BlackBeltTagHandler {

	void onImageTag(Element element);
	void onVideoTag(Element element);
	void onAttachmentTag(Element element);
	void onCodeTag(String textCode,boolean inline, boolean escape, String lang, boolean num);
	void onQuoteTag(String innerText);
	void onText(String text);
	void onText(String text,boolean newLine);
	void onError(String errorText);
	void onUnknownElement(Element element);
	
	String getOutputString(String s);
	/** The handler may like to have a reference to the parser */
	void setParser(BlackBeltTagParser blackBeltTagParser);
}
