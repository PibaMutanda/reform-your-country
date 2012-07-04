package reformyourcountry.model;

public class VoteArgument {
	
	private Long id;
	private  int value = 0;  // -2 = against; +2 = pro.
	private Argument argument;
	private User user;  
	private boolean pro;
	
	public VoteArgument() {
		
	}

	public VoteArgument(long id, int value, Argument argument, User user) {
		
		this.id = id;
		this.value = value;
		this.argument = argument;
		this.user = user;
		
	}

	
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}

	public Argument getArgument() {
		return argument;
	}

	public void setArgument(Argument argument) {
		this.argument = argument;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

    public boolean isPro() {
        return pro;
    }

    public void setPro(boolean pro) {
        this.pro = pro;
    }
	
	
	/*
	@Override
	public String toString () {
		
		if (user == null) { 
			throw new NullPointerException("Bug: an argument should either have a user. This argument has none. id = "+ id);			
		}
		
		return "vote value : " + value;
		
	}
	*/

}
