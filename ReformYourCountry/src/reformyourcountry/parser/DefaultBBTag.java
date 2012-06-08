package reformyourcountry.parser;

import java.util.*;


import reformyourcountry.parser.*;

/**
 * @author xBlackCat,FIEUX cédric & Thomas VAN ROY
 */
public class DefaultBBTag extends ABBTag {
    protected Map<String, BBAttribute> attributes = new HashMap<String, BBAttribute>();
    
    protected DefaultBBTag(String name, BBTagType type, String content) {
	this(null, name, type, content);
    }

    protected DefaultBBTag(BBTag parent, String name, BBTagType type,
	    String content) {
	super(parent, type, name, content);
    }

    @Override
    public Iterator<BBTag> iterator() {
	return children.iterator();
    }

    @Override
    public boolean isEmpty() {
	return children.isEmpty();
    }

    @Override
    public int size() {
	return children.size();
    }

    @Override
    public BBTag get(int index) {
	return children.get(index);
    }

    @Override
    public BBTag remove(int index) {
	BBTag bbTag = children.remove(index);
	if (bbTag != null) {
	    removeParent(bbTag);
	}
	return bbTag;
    }

    @Override
    public boolean remove(BBTag o) {
	if (children.remove(o)) {
	    removeParent(o);
	    return true;
	} else {
	    return false;
	}
    }

    @Override
    public boolean add(BBAttribute bbAttribute) {

	return attributes.put(bbAttribute.getName(), bbAttribute) != null;
    }

    @Override
    public String getAttributeValue(String attributeName) {
    	BBAttribute a = attributes.get(attributeName);

    	return a == null ? null : a.getValue();
    }

    @Override
    public boolean remove(BBAttribute o) {
	return attributes.remove(o.getName()) != null;
    }

    @Override
    public Collection<BBAttribute> attributes() {
	return attributes.values();
    }

    @Override
    public boolean add(BBTag bbTag) {
	updateParent(bbTag);
	return children.add(bbTag);
    }

    @Override
    public void add(int index, BBTag bbTag) {
	updateParent(bbTag);
	children.add(index, bbTag);
    }

    @Override
    public BBTag set(int index, BBTag bbTag) {
	updateParent(bbTag);
	return children.set(index, bbTag);
    }
    public Map<String, BBAttribute> getAttributes(){
    	return this.attributes;
    }
    
     @Override
     public String toString() {
     StringBuilder out = new StringBuilder();
     out.append('[');
     out.append(name);
     if (attributes.size() > 0) {
     BBAttribute defAttribute = attributes.get(null);
     if (defAttribute != null) {
     out.append('=');
     out.append(defAttribute.getValue());
     }
    
     for (BBAttribute a : attributes.values()) {
     if (a.getName() != null) {
     out.append(' ');
     out.append(a.getName());
     out.append("=\"");
     out.append(a.getValue());
     out.append('"');
     }
     }
     }
    
     out.append("] { ");
     for (BBTag t : children) {
     out.append(t.toString());
     out.append(' ');
     }
     out.append('}');
    
     return out.toString();
     }

    private void updateParent(BBTag bbTag) {
	if (bbTag == null) {
	    throw new NullPointerException("Can not add null tag");
	}

	if (bbTag.getParent() != null) {
	    bbTag.getParent().remove(bbTag);
	}

	bbTag.setParent(this);
    }

    private void removeParent(BBTag bbTag) {
	if (bbTag == null) {
	    throw new NullPointerException("Can not add null");
	}

	bbTag.setParent(null);
    }
}