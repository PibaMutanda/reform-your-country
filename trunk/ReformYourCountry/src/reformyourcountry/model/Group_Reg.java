package reformyourcountry.model;

import java.text.SimpleDateFormat;
import java.util.Date;



public class Group_Reg {
	
	//private Sous_Group sous_group;
	private Group group;
	private User user;
	private Date date;
	
	public Group_Reg(){
		
	}

	public Group_Reg(Group group, User user, Date date) {
		
		//this.sous_group = sous_group;
		this.user = user;
		this.date = date;
		this.group = group;
	}

	/*public Sous_Group getSous_Group() {
		return sous_group;
	}

	public void setSous_Group(Sous_Group sous_group) {
		this.sous_group = sous_group;
	}*/
	
	

	public User getUser() {
		return user;
	}
	
	public void setUser(User user) {
		this.user = user;
	}

	public Group getGroup() {
		return group;
	}

	public void setGroup(Group group) {
		this.group = group;
	}

	

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}
	
	public String toString(){
		SimpleDateFormat dateformatYYYYMMDD = new SimpleDateFormat("yyyy/MM/dd");
		String nowYYYYMMDD = new String( dateformatYYYYMMDD.format( date) );
		return nowYYYYMMDD;
	}
	
	

}
