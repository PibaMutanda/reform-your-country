package reformyourcountry.model;



import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.hibernate.validator.constraints.NotBlank;




@Entity
public class Video extends BaseEntity {
    
	
	public enum VideoType {
	    YOUTUBE("YouTube"), VIMEO("Vimeo");
	    
	    private VideoType(String aName) {
	        this.name = aName;
	    }
	    private String name;

	    public String getName() {
	        return name;
	    }
	    
	    public static VideoType getValueFromName(String name) {
	        for(VideoType videoType : VideoType.values()) {
	            if (videoType.getName().equals(name)) {
	                return videoType;
	            }
	        }
	        throw new IllegalArgumentException(name + " is not the name of a VideoType.");
	    }
	}

    
    @ManyToOne
    @JoinColumn
    private Article article;
    
    @NotBlank(message="Entrer identifiant de la vidéo")
    private String idOnHost;   // Youtube or Vimeo id.
    
    @Column(nullable=true)
    private String caption;  // Text displayed below the video.
    
	@Enumerated(EnumType.STRING)
	private VideoType videoType;

    
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


	public String getCaption() {
		return caption;
	}


	public void setCaption(String caption) {
		this.caption = caption;
	}

	public VideoType getVideoType() {
		return videoType;
	}

	public void setVideoType(VideoType videoType) {
		this.videoType = videoType;
	}

    
}
