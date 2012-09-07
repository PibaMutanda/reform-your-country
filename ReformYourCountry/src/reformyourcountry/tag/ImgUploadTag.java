package reformyourcountry.tag;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.SimpleTagSupport;

import reformyourcountry.model.BaseEntity;

public class ImgUploadTag extends SimpleTagSupport{
    
    private BaseEntity entity;
    
    
    
    public BaseEntity getEntity() {
        return entity;
    }



    public void setEntity(BaseEntity entity) {
        this.entity = entity;
    }



    @Override
    public void doTag() throws JspException, IOException {
        
  JspWriter out = this.getJspContext().getOut();
        
     ((PageContext) this.getJspContext()).setAttribute("entity",entity);
  
  
   try {
            
          out.write("<div>(Attention l'image doit faire moins de 1.5Mo)");
      
           out.write("<form method=\"post\" action=\"imageuploadsubmit\" enctype=\"multipart/form-data\">");
           out.write("<input type=\"file\" name=\"file\" />");
           out.write("<input type=\"submit\" />");
                   
                    
             out.write("</form>");

             out.write("</div>");

            
        
     } catch (IOException e) {
        
        throw new RuntimeException(e);
     }
        
        
        
    }

}
