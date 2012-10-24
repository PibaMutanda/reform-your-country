package reformyourcountry.util;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class TextUtils {

	public static String escapeTextForJs(String text) {
		return text.replaceAll("\"", "\'").replaceAll("'", "\\\\'").replaceAll(
				"\n", "").replaceAll("\r", "");
	}

	public static String enableHtmlCode(String text){
        if (text == null) {
            return null;
        }

        char[] s = text.toCharArray();
        int length = s.length;
        StringBuffer ret = new StringBuffer(length);

        for (int i = 0; i < length; i++) {
            if (s[i] == '&') {
                if ( ((i + 3) < length) &&
                     (s[i + 1] == 'l') &&
                     (s[i + 2] == 't') &&
                     (s[i + 3] == ';')) { // &lt; = <
                    ret.append('<');
                    i += 3;
                } else if ( ((i + 3) < length) &&
                            (s[i+1] == 'g') &&
                            (s[i+2] == 't') &&
                            (s[i+3] == ';') ) { // &gt; = >
                    ret.append('>');
                    i += 3;
                } else if ( ((i + 4) < length) &&
                            (s[i+1] == 'a') &&
                            (s[i+2] == 'm') &&
                            (s[i+3] == 'p') &&
                            (s[i+4] == ';') ) { // &amp; = &
                    ret.append('&');
                    i += 4;
                } else if ( ((i + 5) < length) &&
                            (s[i+1] == 'q') &&
                            (s[i+2] == 'u') &&
                            (s[i+3] == 'o') &&
                            (s[i+4] == 't') &&
                            (s[i+5] == ';') ) { // &quot; = "
                    ret.append('"');
                    i += 5;
                } else {
                    ret.append('&');
                }
            } else {
                ret.append(s[i]);
            }
        }
        return ret.toString();
	}
	
    /**
     * It is the new version of compare texts (It uses blackbelt.diff.DiffGenerator)
     * 
     * @param original
     * @param updated
     * @return a Map<String, String> The first String (key = "original") contains the difference of the
     *         original text and the second String (key = "updated" ) that of the update.
     */
    public static Map<String, String> getGeneratedDiffs(String original, String updated) {
        DiffGenerator diffGenerator = new DiffGenerator(original, updated);
        Map<String, String> diffs = new HashMap<String, String>();
        diffs.put("original", diffGenerator.getTextOriginalHtmlDiff(true));
        diffs.put("updated", diffGenerator.getTextModifiedHtmlDiff(true));

        return diffs;
    }

}
