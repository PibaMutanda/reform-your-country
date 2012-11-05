package reformyourcountry.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.validation.constraints.Pattern;

import org.hibernate.annotations.Type;
import org.hibernate.validator.constraints.NotBlank;

@Entity
public class GoodExample extends BaseEntity {

	@NotBlank
	@Column(length = 100, unique = true, nullable = false)// need nullable= false for schemaupdate
	private String title;
	
	@Lob
    @Type(type="org.hibernate.type.StringClobType")
	private String description;
	
	@Column(unique = true, nullable = false)
	@NotBlank(message="entrer une Url")
	@Pattern(message="l'Url ne peut contenir des caractères spéciaux", regexp="[A-Za-z0-9_-]{2,256}")
	private String url;
	
	@ManyToMany(mappedBy = "goodExamples")
	private List<Article> articles = new ArrayList <Article>();

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

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public List<Article> getArticles() {
		return articles;
	}
	
	
}
