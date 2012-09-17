package reformyourcountry.model;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.*;

import org.hibernate.validator.constraints.NotBlank;

@Entity
@Table(name="Groups")
public class Group extends BaseEntity{

    @Column(length= 100, unique = true, nullable=false)
    @NotBlank(message="entrer le nom de votre groupe svp")
	private String name; // a group has a lot of users
	
	@OneToMany(mappedBy ="group")
	private List <VoteAction> voteActions = new ArrayList <VoteAction>();

	@ManyToOne
	@JoinColumn
	private Group parent;

	@OneToMany(mappedBy ="parent")
	private List <Group> children = new ArrayList <Group>();
	
    @Column(unique = true)
	private String url;
	
	private String description;

	@Column(nullable = false, columnDefinition="boolean default '0'")
    boolean hasImage;  // If the group has an image, the image is named group.id + "jpg"
		
	public Group(){
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	/*public List<GroupReg> getGroupRegs() {
		return groupRegs;
	}*/
	
	public List<VoteAction> getVoteActions() {
		return voteActions;
	}
	public List<Group> getChildren(){
		return children;
	}
	
	public void setParent(Group parent){
		this.parent = parent;
	}
  
    public Group getParent(){
        
        return parent;
    }
	public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    
    public boolean isHasImage() {
        return hasImage;
    }

    public void setHasImage(boolean hasImage) {
        this.hasImage = hasImage;
    }

    public void addVoteAction(VoteAction voteAction){
        voteActions.add(voteAction);
        
    }
    
    public void addChild(Group group){
        
        children.add(group);
    }

    @Override
	public String toString(){
		return name;
	}

}
