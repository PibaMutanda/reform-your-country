package BBParser;

/**
 * @author xBlackCat Date: 14.06.11
 */
public class DefaultBBAttribute implements BBAttribute {
    private final String name;
    private String value;

    public DefaultBBAttribute(String name) {
        this.name = name;
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