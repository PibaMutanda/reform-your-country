package reformyourcountry.repository;

import java.util.List;

import org.springframework.stereotype.Repository;

import reformyourcountry.model.Group;
import reformyourcountry.model.GroupReg;

@Repository
@SuppressWarnings("unchecked")
public class GroupRegRepository extends BaseRepository<GroupReg> {
	
	public List<GroupReg>findAllByGroup(Group group) {
	   	 return	 em.createQuery("select gr" +
	   	 		" from GroupReg gr where gr.group = :group")
	   			    .setParameter("group", group).getResultList();
	   }

}
