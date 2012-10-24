package reformyourcountry.model;



import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.hibernate.validator.constraints.NotBlank;




@Entity
public class Video extends BaseEntity {
    
    
    @ManyToOne
    @JoinColumn
    private Article article;
    
    @NotBlank(message="Entrer identifiant de la vidéo")
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
