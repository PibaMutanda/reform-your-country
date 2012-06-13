package reformyourcountry.parser;

/**
 *  Added parent tag of the attribute, if there is an error with the attribute, we change the tag in error tag
 * @author xBlackCat,FIEUX Cédric,Thomas Van Roy
 */
public class DefaultBBAttribute implements BBAttribute {
    private final String name;
    private String value;
    public DefaultBBAttribute(String name) {
        this.name = name;
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
    

}