package reformyourcountry.parser;

import java.util.Collection;
import java.util.List;

public interface BBTag extends Iterable<BBTag> {
    BBTagType getType();

    String getName();

    boolean isEmpty();

    int size();

    BBTag get(int index);

    BBTag remove(int index);

    boolean remove(BBTag o);

    boolean add(BBTag bbTag);

    boolean add(BBAttribute bbTag);

    String getAttributeValue(String attributeName);

    void add(int index, BBTag element);

    BBTag set(int index, BBTag element);

    BBTag getParent();

    void setParent(BBTag parent);

    String getContent();

    boolean remove(BBAttribute o);
    
    Collection<BBAttribute> attributes();
    List<BBTag> getChildrenList();
    void setErrorText(String text);
    String getErrorText();
}