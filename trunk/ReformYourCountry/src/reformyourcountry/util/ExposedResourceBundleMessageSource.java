package reformyourcountry.util;

import java.util.Locale;
import java.util.ResourceBundle;
import java.util.Set;

import org.springframework.context.support.ResourceBundleMessageSource;

/** Spring does not expose the keys (the names) of properties from .properties files. 
 * We need to create a class to expose them.
 * http://stackoverflow.com/a/6982619
 *  */
public class ExposedResourceBundleMessageSource extends
        ResourceBundleMessageSource {
    public Set<String> getKeys(String basename, Locale locale) {
        ResourceBundle bundle = getResourceBundle(basename, locale);
        return bundle.keySet();
    }
}