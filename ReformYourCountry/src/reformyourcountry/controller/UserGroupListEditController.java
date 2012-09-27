package reformyourcountry.controller;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import reformyourcountry.exception.InvalidUrlException;
import reformyourcountry.model.Group;
import reformyourcountry.model.GroupReg;
import reformyourcountry.model.User;
import reformyourcountry.repository.GroupRegRepository;
import reformyourcountry.repository.GroupRepository;
import reformyourcountry.repository.UserRepository;

@Controller
public class UserGroupListEditController extends BaseController<User> {


    @Autowired  GroupRegRepository groupRegRepository;
    @Autowired  GroupRepository groupRepository;
    @Autowired UserRepository userRepository;

    @RequestMapping("/usergrouplistsubmit")
    public ModelAndView userGroupListEditSubmit(@RequestParam("id")long userid, 
            @RequestParam(value="groupIds", required=false)Long[] groupIds)  // required = false because there is no such param if all groups have been unchecked.
    {


        User user=getRequiredEntity(userid);
        
        if (groupIds == null) {  // All groups have been unchecked
            groupIds = new Long[0];  // Empty array, better than null variable for our algorithms.
        }
        
        ModelAndView mv=new ModelAndView("redirect:user","username",user.getUserName());

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
                em.remove(groupReg);
            }
        }
        return mv;
    }
}
