package reformyourcountry.model;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;


@Entity
public class VoteGoodExample extends BaseEntity{
	
	@ManyToOne
	@JoinColumn(nullable = false)
	private GoodExample goodExample;
	@ManyToOne
    @JoinColumn(nullable = false)
	private User user; // 5/12/12 cannot we replace by createdBy ?
	
    public GoodExample getGoodExample() {
        return goodExample;
    }

    public void setGoodExample(GoodExample goodExample) {
        this.goodExample = goodExample;
    }

	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}

}
