package reformyourcountry.parser;

/**
 * @author xBlackCat Date: 14.06.11
 */
public class DefaultBBAttribute implements BBAttribute {
    private final String name;
    private String value;
    private DefaultBBTag parent;
    public DefaultBBAttribute(String name,DefaultBBTag parent) {
        this.name = name;
        this.parent = parent;
        if (this.name == null || this.name=="")
        {
        	
        }
    }

    public String getName() {
        return name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DefaultBBAttribute)) return false;

        DefaultBBAttribute that = (DefaultBBAttribute) o;

        return name != null ? name.equals(that.name) : that.name == null;

    }

    @Override
    public int hashCode() {
        return name != null ? name.hashCode() : 0;
    }
    
    //Exception if name is null, name must exist
    public class AttributeNameException extends Exception
    {
    	public AttributeNameException(DefaultBBAttribute attribute){
    		attribute.parent.content = "!!! Some attribute(s) have no name !!! "+attribute.parent.content;
    		parent.attributes.remove(attribute.name);
    	}
    }
}