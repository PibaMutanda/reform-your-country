package reformyourcountry.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import reformyourcountry.exception.InvalidUrlException;
import reformyourcountry.model.Group;
import reformyourcountry.model.GroupReg;
import reformyourcountry.model.User;
import reformyourcountry.repository.GroupRegRepository;
import reformyourcountry.repository.GroupRepository;
import reformyourcountry.repository.UserRepository;

@Service
@Transactional
public class GroupService {

	@Autowired GroupRegRepository groupRegRepository;
	@Autowired GroupRepository groupRepository;
	@Autowired UserRepository userRepository;
	@Autowired BadgeService badgeService;

	public void changeGroupReg(User user,Long[] groupIds){

		/////// 1. Add new groupRegs
       
		for (Long groupId : groupIds) {  // For all groups selected by the user

			// Search: Is there already a GroupReg for the current group?
			boolean found=false;
			for (GroupReg groupeg : user.getGroupRegs()) {
				if(groupeg.getGroup().getId() == groupId) {
					found =true;    
					break;
				}
			}

			if (!found) {   // We need to create a groupReg for this group
				GroupReg gReg = new GroupReg();
				gReg.setUser(user);
				Group group = groupRepository.find(groupId);
				if (group == null) {
					throw new InvalidUrlException("Group introuvable depuis l'id '"+groupId+".");
				}
				gReg.setGroup(group);
				groupRegRepository.persist(gReg);
				user.getGroupRegs().add(gReg);
			}    
		}



		/////// 2. REMOVE UNSELECTED GROUPREGS
		/*
		 * POUR TOUS LES GROUPREGS DU USER USER.GETGROUPREGS
		 *   RECHERCHER SI LE GROUPE DU GROUPEREG EN COURS EST DANS L'ARRAY GROUPIDS
		 *   SI PAS TROUVÉ (C'EST QU'ON VEUT LE RETIRER): RETIRER CE GROUPREG DE USER.GETGROUPREGS, ET PUIS FAIRE EM.REMOVE(GROUPREG)
		 */

		for(int idx = 0; idx < user.getGroupRegs().size(); idx++) { // Not a foreach because we'll modify the collection in the loop
			GroupReg groupReg = user.getGroupRegs().get(idx);
			// We look if greg is in the groupIds array (else it has to be removed)
			boolean found=false;  
			for(long grpid: groupIds){
				if (groupReg.getGroup().getId().equals(grpid)) {
					found=true;
					break;
				}
			}

			if (!found) {  // Not found in the ids from the web page => not selected by the user => to be removed from the DB.
				user.getGroupRegs().remove(groupReg);
				groupRegRepository.remove(groupReg);
			}
		}
		
		///////////// 3. Grand badges
		badgeService.grantBadgeForGroups(user);
		
	}
}
