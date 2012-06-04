package BBParser;

import java.util.*;

/**
 * @author xBlackCat
 */
public class DefaultBBTag extends ABBTag {
    protected List<BBTag> children = new ArrayList<BBTag>();
    protected Map<String, BBAttribute> attributes = new HashMap<String, BBAttribute>();

    protected DefaultBBTag(String name, BBTagType type, String content) {
        this(null, name, type, content);
    }

    protected DefaultBBTag(BBTag parent, String name, BBTagType type, String content) {
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

//    @Override
//    public String getContent() {
//        StringBuilder content = new StringBuilder();
//        content.append('[');
//        content.append(name);
//        if (attributes.size() > 0) {
//            BBAttribute defAttribute = attributes.get(null);
//            if (defAttribute != null) {
//                content.append('=');
//                content.append(defAttribute.getValue());
//            }
//
//            for (BBAttribute a : attributes.values()) {
//                if (a.getName() != null) {
//                    content.append(' ');
//                    content.append(a.getName());
//                    content.append("=\"");
//                    content.append(a.getValue());
//                    content.append('"');
//                }
//            }
//        }
//
//        content.append(']');
//
//        for (BBTag t : children) {
//            content.append(t.getContent());
//        }
//
//        content.append("[/");
//        content.append(name);
//        content.append(']');
//        return content.toString();
//    }

//    @Override
//    public String toString() {
//        StringBuilder out = new StringBuilder();
//        out.append('[');
//        out.append(name);
//        if (attributes.size() > 0) {
//            BBAttribute defAttribute = attributes.get(null);
//            if (defAttribute != null) {
//                out.append('=');
//                out.append(defAttribute.getValue());
//            }
//
//            for (BBAttribute a : attributes.values()) {
//                if (a.getName() != null) {
//                    out.append(' ');
//                    out.append(a.getName());
//                    out.append("=\"");
//                    out.append(a.getValue());
//                    out.append('"');
//                }
//            }
//        }
//
//        out.append("] { ");
//        for (BBTag t : children) {
//            out.append(t.toString());
//            out.append(' ');
//        }
//        out.append('}');
//
//        return out.toString();
//    }
    
    //Modified by cedric F
    //Transform the bbcode in html.
    @Override
    public String toString() {

    	String attributeAsString ="";
    	String bibSignature="";
        StringBuilder out = new StringBuilder();
        //Foreach BBtag contained in the innertext of this bbtag, transform these BBtag in html
        for (BBTag t : children) {
            out.append(t.toString());
            out.append(' ');
        }
        //If it's not the Level 0 tag
        if (!(this.getName().equals(""))){
        	//If it's a text tag just add the text
        	if (this.getType()==BBTagType.Text){
        		out.append(this.name);
        	}else 
        	if (this.getType()== BBTagType.Error){
        		out.append("<span style='color:red;'>"+this.getName()+"</span>");
        	}
        	else {
        	//if it's a Bbcode tag, we had the attributes in the html tag
              if (attributes.size() > 0) {
//            	  BBAttribute defAttribute = attributes.get(null);
//            	  if (defAttribute != null) {
//            		  out.append('=');
//            		  out.append(defAttribute.getValue());
//            	  }
            	  
            	  //We build differently the special attributes
            	  for (BBAttribute a : attributes.values()) {
            		  if (a.getName() != null) {
            			  //if it's a "bib" we have to send the value as a signature at the end of the BBTag.
            			  if(a.getName().toLowerCase().contains("error")){
            				  out.append("<span style='color:red;'>the attribute "+a.getValue()+" throws some exception: "+a.getName()+"</span>");
            			  }
            			  else if(a.getName().toLowerCase().equals("bib")){
            				  bibSignature= "<span class=\"bibref\"><a href=\"/Bibliography/"+a.getValue()+"\">["+a.getValue()+ "]</a></span>";
            			  }
            			  else{
            				  attributeAsString=" "+ a.getName() +"=\""+a.getValue()+"\"";
            			  }
            			 
            		  }
            	  }
            	  
              }
              out = new StringBuilder(GetHtmlOpenTag()+out+bibSignature+GetHtmlClosingTag() );
        	}
        }

        return out.toString();
    }
    private String GetHtmlOpenTag()
    {
    	switch(this.name.toLowerCase()){
    		case "quote":
    			String specialValue ="";
    			System.out.println(this.attributes.get("inline").getValue().toLowerCase());
    			if ((this.attributes.get("inline")!=null) && (this.attributes.get("inline").getValue().toLowerCase().contains("true")))
    			{
    				specialValue = "quote-inline";
    			}else
    			{
    				specialValue = "quote-block";
    			}
    			return "<div class=\""+specialValue+"\">";
    	}
		return "";
    }
    private String GetHtmlClosingTag()
    {
    	switch(this.name.toLowerCase()){
    		case "quote":
    			return "</div>";
    	}
		return "";
    }
    private void updateParent(BBTag bbTag) {
        if (bbTag == null) {
            throw new NullPointerException("Can not add null");
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