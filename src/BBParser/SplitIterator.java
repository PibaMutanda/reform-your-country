package BBParser;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.util.Iterator;

/**
 * Created by IntelliJ IDEA. User: Alexey Date: 12.06.11 Time: 14:16 To change this template use File | Settings | File
 * Templates.
 */
class SplitIterator implements Iterator<Part> {
    private StringBuilder buf = new StringBuilder();

    private boolean braceOpen = false;
    private char openQuote = 0;

    private final Reader source;

    private Part nextPart;
    
    private boolean error=false;

    public SplitIterator(Reader source) {
        this.source = source;
    }
    
    public boolean getError(){
    	return this.error;
    }


    @Override
    public boolean hasNext() {
        try {
            nextPart = readNextPart();
        } catch (IOException e) {
            return false;
        }

        return nextPart != null;
    }

    @Override
    public Part next() {
        return nextPart;
    }

    @Override
    public void remove() {
    }
    public Part  readNextEscapedPart() throws IOException{
    	Part result = null;
    	int v;
    	String txt ="";
        while ((v = source.read()) != -1) {
        	char c = (char) v;
        	txt += c;
        	 if (txt.contains("[/escape]")){
             	String test = txt.substring(0,txt.indexOf("[/escape]"));
             	return new Part(test);
             }
        }
       
    	return result;
    }
    private Part readNextPart() throws IOException {
        Part result = null;

        int v;
        while ((v = source.read()) != -1) {
            char c = (char) v;

            if (c == '[') {
                if (!braceOpen) {
                    if (buf.length() > 0) {
                        result = new Part(buf.toString());
                        buf.setLength(0);
                    }
                    braceOpen = true;
                }else{
                	error=true;
                    result = new Part(buf.toString());
                    return result;
                }
                buf.append(c);
            } else if ((c == '"' || c == '\'') && braceOpen) {
                if (openQuote == 0) {
                    openQuote = c;
                } else if (openQuote == c) {
                    openQuote = 0;
                }
                buf.append(c);
            } else if (c == ']' && braceOpen) {
                buf.append(c);
                if (buf.length() > 0) {
                    result = new Part(buf.toString());
                    buf.setLength(0);
                }
                braceOpen = false;
            } else if (c == '\n' && !braceOpen) {
                buf.append(c);
                if (buf.length() > 0) {
                    result = new Part(buf.toString());
                    buf.setLength(0);
                }
            } else {
                buf.append(c);
            }

            if (result != null) {
                return result;
            }
        }

        if (buf.length() > 0) {
            result = new Part(buf.toString());
            buf.setLength(0);
        }

        return result;
    }
}