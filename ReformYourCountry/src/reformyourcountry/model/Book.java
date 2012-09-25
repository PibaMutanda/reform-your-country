package reformyourcountry.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Lob;

import org.hibernate.validator.constraints.NotBlank;

@Entity
public class Book extends BaseEntity {
	
    @NotBlank(message = "Vous devez introduire une abréviation")
    @Column(nullable = false, unique = true)
    String abrev;
    
    String title;
    
    @Column(unique = true, nullable=false)
	private String url; //Used to create a more readable URL; derived from the title (ie: if the title is "Le Web 2.0", url will be "le-Web-2-0")
    
    @Lob
    String description;
    
    String author; // could a list of many authors.
    
    String pubYear; //publication year, typically 4 digits. Maybe a range.
    
    boolean top; //true if that book is a favorite book to be displayed at the top of the bibliography.
    
    @Column(nullable = false, columnDefinition="boolean default '0'")
    boolean hasImage;  // If the book has an image, the image is named book.id + "jpg"
    


    String externalUrl; //link to the publisher or another web site about that book.
    
    public Book() { }
    
    public Book(String abrev, String title, String url, String description, String author,
            String pubYear, boolean top, String externalUrl) {
        super();
        this.abrev = abrev;
        this.title = title;
        this.url = url;
        this.description = description;
        this.author = author;
        this.pubYear = pubYear;
        this.top = top;
        this.externalUrl = externalUrl;
    }
    
    public String getAbrev() {
        return abrev;
    }
    public void setAbrev(String abrev) {
        this.abrev = abrev;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public String getAuthor() {
        return author;
    }
    public void setAuthor(String author) {
        this.author = author;
    }
    public String getPubYear() {
        return pubYear;
    }
    public void setPubYear(String pubYear) {
        this.pubYear = pubYear;
    }
    public boolean isTop() {
        return top;
    }
    public void setTop(boolean top) {
        this.top = top;
    }
    public String getExternalUrl() {
        return externalUrl;
    }
    public void setExternalUrl(String externalUrl) {
        this.externalUrl = externalUrl;
    }
   
    public boolean isHasImage() {
        return hasImage;
    }

    public void setHasImage(boolean hasImage) {
        this.hasImage = hasImage;
    }


    @Override
    public String toString() {
        return "Book [abrev=" + abrev + ", title=" + title + ", description="
                + description + ", author=" + author + ", pubYear=" + pubYear
                + ", top=" + top + ", externalUrl=" + externalUrl + "]";
    }
}
