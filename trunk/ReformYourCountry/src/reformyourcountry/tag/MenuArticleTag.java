package reformyourcountry.tag;

import java.io.IOException;

import java.util.List;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.SimpleTagSupport;

import reformyourcountry.model.Article;
import reformyourcountry.repository.ArticleRepository;
import reformyourcountry.web.ContextUtil;


public class MenuArticleTag extends SimpleTagSupport{
    
   
    ArticleRepository articleRepository;
    
    
    private String cssClass;
    
    
   public String getCssClass() {
        return cssClass;
    }


    public void setCssClass(String cssClass) {
        this.cssClass = cssClass;
    }

  

@Override
public void doTag() throws JspException, IOException {
    JspWriter out = this.getJspContext().getOut();
    articleRepository =  (ArticleRepository) ContextUtil.getSpringBean("articleRepository");
    List<Article> articles = articleRepository.findAllWithoutParent();

    for(Article article : articles){
            
            out.write("<ul><li><div class=\""+cssClass+"\"><a href =\"Display?id="+article.getId().toString()+"\">"+article.getTitle().trim()+"</a></div></li></ul>");

        if(article.getChildren() != null && !article.getChildren().isEmpty())

            displayChildren(article,out);

    }

}


private void displayChildren(Article element,JspWriter out) throws JspException, IOException{


    for(int i = 0;i< element.getChildren().size();i++){
        Article article = element.getChildren().get(i);
       
        if(i == 0){
            out.write("<ul>");         

        }

        out.write("<li>");      
        out.write("<div class=\""+cssClass+"\"><a href =\"Display?id="+article.getId().toString()+"\">"+article.getTitle().trim()+"</a></div>");   
        if(!article.getChildren().isEmpty())
            displayChildren(article,out); 
        out.write("</li>");       
        if(i == element.getChildren().size()-1){
            out.write("</ul>");          
        }

    }
}




}

        
    


