package reformyourcountry.repository;

import java.util.List;

import org.springframework.stereotype.Repository;

import reformyourcountry.model.Group;
import reformyourcountry.model.GroupReg;
import reformyourcountry.model.User;

@Repository
@SuppressWarnings("unchecked")
public class GroupRegRepository extends BaseRepository<GroupReg> {
	
	public List<GroupReg>findAllByGroup(Group group) {
	   	 return	 em.createQuery("select gr" +
	   	 		" from GroupReg gr where gr.group = :group")
	   			    .setParameter("group", group).getResultList();
	   }
	
	
	public List<GroupReg>findAllByUser(User user){
	    return em.createQuery("select gr" +
	            "from GroupReg gr where gr.user= :user")
	            .setParameter("user", user).getResultList();
	}
	
	public void removeByGroupId(Group group, User user){
	    em.createQuery("delete " +
                "from GroupReg gr where gr.user= :user and gr.group= :group")
                .setParameter("group", group)
                .setParameter("user", user);
	}
	
	
}
