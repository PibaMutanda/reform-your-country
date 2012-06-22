package reformyourcountry.model;

import java.text.SimpleDateFormat;
import java.util.Date;

public class GroupReg {
	
		private Group group;
		private User user;
		private Date creationDate;
		
		public GroupReg(){
			
		}

		public GroupReg(Group group, User user, Date date) {
			
			
			this.user = user;
			this.creationDate = date;
			this.group = group;
		}

		
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
			return creationDate;
		}

		public void setDate(Date date) {
			this.creationDate = date;
		}
		
		@Override
		public String toString(){
			SimpleDateFormat dateformatYYYYMMDD = new SimpleDateFormat("yyyy/MM/dd");
			String nowYYYYMMDD = dateformatYYYYMMDD.format(creationDate);
			return nowYYYYMMDD;
		}
		

}
