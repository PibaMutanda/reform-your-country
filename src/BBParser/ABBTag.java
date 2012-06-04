package BBParser;

public abstract class ABBTag implements BBTag {
    protected String name;
    protected BBTagType type;
    protected BBTag parent;
    protected String content;

    //Constructor with no parent
    protected ABBTag(BBTagType type, String name, String content) {
        this(null, type, name, content);
    }
    //Constructor
    protected ABBTag(BBTag parent, BBTagType type, String name, String content) {
        this.parent = parent;
        this.type = type;
        this.name = name;
        this.content = content;
    }

    //Get Type
    @Override
    public BBTagType getType() {
        return type;
    }
    //Get name
    @Override
    public String getName() {
        return name;
    }
    //Get parent
    @Override
    public BBTag getParent() {
        return parent;
    }
    //Send parent
    @Override
    public void setParent(BBTag parent) {
        this.parent = parent;
    }
    //Verify the equality between this ABBTag and one object
    @Override
    public boolean equals(Object o) {
    	//If the adress in the stakc is the same it's true
        if (this == o) return true;
        //if it's not an ABBTag, it's false
        if (!(o instanceof ABBTag)) return false;

        ABBTag abbTag = (ABBTag) o;

        return type == abbTag.type && name.equals(abbTag.name);

    }
    //Get HashCode
    @Override
    public int hashCode() {
        int result = name.hashCode();
        result = 31 * result + type.hashCode();
        return result;
    }
    //GetContent
    @Override
    public String getContent() {
        return content;
    }
}