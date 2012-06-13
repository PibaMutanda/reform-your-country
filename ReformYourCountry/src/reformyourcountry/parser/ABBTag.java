package reformyourcountry.parser;

import java.util.ArrayList;
import java.util.List;

/** 
 * This holds the information of a tag in the tree. 
 * For example, in "blabla [quote inline'tru']abc[/quote]"
 * this would hold the information of the quote tag, with a list of attributes (defined in descendant class), and the text....
 * (Added children list and errorText) 
 * @author xBlackCat and FIEUX Cédric and Thomasn Van roy
 *
 */
public abstract class ABBTag implements BBTag {
    protected String name;
    protected BBTagType type;
    protected BBTag parent;
    protected String content;  // Would be "abc" in the example above
    protected String errorText; //If we find an error in the tag, it's the comment about the error.
    protected List<BBTag> children = new ArrayList<BBTag>();
    
    @Override
    public String getErrorText(){
    	return errorText;
    }
    @Override
    public void setErrorText(String text){
    	errorText = text;
    	this.type = BBTagType.Error;
    }


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
        if (this == o) return true;
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
    

    @Override
    public List<BBTag> getChildrenList(){
    	return this.children;
    }
}