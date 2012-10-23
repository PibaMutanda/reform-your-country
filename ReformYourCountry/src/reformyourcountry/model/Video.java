package reformyourcountry.model;



import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;




@Entity
public class Video extends BaseEntity {
    
    
    @ManyToOne
    @JoinColumn
    private Article article;
    
    private String idOnHost;   // Youtube id.
    
    
    public Article getArticle() {
        return article;
    }


    public void setArticle(Article article) {
        this.article = article;
    }


    public String getIdOnHost() {
        return idOnHost;
    }


    public void setIdOnHost(String idYoutube) {
        this.idOnHost = idYoutube;
    }

}
