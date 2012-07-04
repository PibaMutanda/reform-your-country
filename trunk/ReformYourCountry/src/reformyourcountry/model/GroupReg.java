package reformyourcountry.model;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
@Entity
public class GroupReg extends BaseEntity{
	    //TODO Change when group is an entity
		//private Group group;
    
        @ManyToOne
		private User user;
        
		private Date creationDate;
		
		private boolean confirmed;
		
		private User owner;
		
		private User confirmedBy;
		
		public GroupReg(){
			
		}

		public GroupReg(Group group, User user, Date date,User owner) {
			
			
			this.user = user;
			this.creationDate = date;
			this.owner = owner;
			//this.group = group;
		}

		
		public User getUser() {
			return user;
		}
		
		public void setUser(User user) {
			this.user = user;
		}

//		public Group getGroup() {
//			return group;
//		}
//
//		public void setGroup(Group group) {
//			this.group = group;
//		}

		

		public boolean isConfirmed() {
            return confirmed;
        }

        public void setConfirmed(boolean confirmed) {
            this.confirmed = confirmed;
        }

        public User getConfirmedBy() {
            return confirmedBy;
        }

        public void setConfirmedBy(User confirmedBy) {
            this.confirmedBy = confirmedBy;
        }

        public Date getCreationDate() {
            return creationDate;
        }

        public User getOwner() {
            return owner;
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
