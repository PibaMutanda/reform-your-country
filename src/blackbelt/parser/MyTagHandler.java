package blackbelt.parser;

import blackbelt.parser.BlackBeltTagParser.Element;

public class MyTagHandler extends BlackBeltTagHandlerStringGenerator{

	@Override
	public void onImageTag(Element element) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onVideoTag(Element element) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onAttachmentTag(Element element) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onCodeTag(String textCode, boolean inline, boolean escape,
			String lang, boolean num) {
		addResultTextBlock("<i>"+ textCode + "</i>", true);
		
	}

	@Override
	public void onQuoteTag(String innerText) {
		addResultTextBlock("<quote>"+ innerText + "</quote>", true);

	}


}
