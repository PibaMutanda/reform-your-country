package reformyourcountry.parser;

import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class TextBBTag extends ABBTag {
    public static final Iterator<BBTag> NO_ELEMENTS = new Iterator<BBTag>() {
        @Override
        public boolean hasNext() {
            return false;
        }

        @Override
        public BBTag next() {
            throw new NoSuchElementException();
        }

        @Override
        public void remove() {
        }
    };

    public TextBBTag(String content) {
        super(BBTagType.Text, "", content);
    }

    public TextBBTag(BBTag parent, String content) {
        super(parent, BBTagType.Text, "", content);
    }
    
    public TextBBTag(String name, String content){
    	super(BBTagType.Text,name,content);
    }

    @Override
    public boolean add(BBTag bbTag) {
        return false;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public int size() {
        return 0;
    }

    @Override
    public BBTag get(int index) {
        return null;
    }

    @Override
    public BBTag remove(int index) {
        return null;
    }

    @Override
    public boolean remove(BBTag o) {
        return false;
    }

    @Override
    public void add(int index, BBTag element) {

    }

    @Override
    public BBTag set(int index, BBTag element) {
        return null;
    }

    @Override
    public String getAttributeValue(String attributeName) {
        return null;
    }

    @Override
    public boolean add(BBAttribute bbTag) {
        return false;
    }

    @Override
    public boolean remove(BBAttribute o) {
        return false;
    }

    @Override
    public Collection<BBAttribute> attributes() {
        return Collections.emptySet();
    }

    @Override
    public Iterator<BBTag> iterator() {
        return NO_ELEMENTS;
    }

    @Override
    public String toString() {
        return  content ;
    }
}