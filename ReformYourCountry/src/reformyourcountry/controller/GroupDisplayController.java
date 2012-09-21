package reformyourcountry.controller;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import reformyourcountry.model.Group;
import reformyourcountry.model.GroupReg;
import reformyourcountry.repository.GroupRegRepository;
import reformyourcountry.repository.GroupRepository;
import reformyourcountry.security.Privilege;
import reformyourcountry.security.SecurityContext;
import reformyourcountry.util.FileUtil;
import reformyourcountry.util.FileUtil.InvalidImageFileException;
import reformyourcountry.util.ImageUtil;

@Controller
public class GroupDisplayController extends BaseController<Group> {

    @Autowired  GroupRepository groupRepository;
    @Autowired  GroupRegRepository groupRegRepository;
    
    @RequestMapping("/group")
    public ModelAndView groupDisplay(@RequestParam("id") long id) {
        Group group = getRequiredEntity(id);
        List<GroupReg>groupRegList = groupRegRepository.findAllByGroup(group);
        ModelAndView mv = new ModelAndView("groupdisplay");
        mv.addObject("groupRegList", groupRegList);
        mv.addObject("group", group);   
        mv.addObject("random", System.currentTimeMillis());
        return mv;
    }
    
    @RequestMapping("/groupimageadd")
    public ModelAndView bookImageAdd(@RequestParam("id") long groupid,
            @RequestParam("file") MultipartFile multipartFile) throws Exception{    
        
        SecurityContext.assertUserHasPrivilege(Privilege.EDIT_GROUP);

        Group group = groupRepository.find(groupid);

        ModelAndView mv = new ModelAndView("redirect:group", "id", group.getId());

        ///// Save original image, scale it and save the resized image.
        try {
            FileUtil.uploadFile(multipartFile, FileUtil.getGenFolderPath() + FileUtil.GROUP_SUB_FOLDER + FileUtil.GROUP_ORIGINAL_SUB_FOLDER, 
                    FileUtil.assembleImageFileNameWithCorrectExtention(multipartFile, Long.toString(group.getId())));

            BufferedImage resizedImage = ImageUtil.scale(new ByteArrayInputStream(multipartFile.getBytes()),120 * 200, 200, 200);

            ImageUtil.saveImageToFileAsJPEG(resizedImage,  
                    FileUtil.getGenFolderPath() + FileUtil.GROUP_SUB_FOLDER + FileUtil.GROUP_RESIZED_SUB_FOLDER, group.getId() + ".jpg", 0.9f);

            group.setHasImage(true);
            groupRepository.merge(group);


        } catch (InvalidImageFileException e) {  //Tell the user that its image is invalid.
            setMessage(mv, e.getMessageToUser());
        }

        return mv;
    }
    
    @RequestMapping("/groupimagedelete")
    public ModelAndView groupImageDelete(@RequestParam("id") long groupid){
        SecurityContext.assertUserHasPrivilege(Privilege.EDIT_GROUP);

        Group group = groupRepository.find(groupid);

        FileUtil.deleteFilesWithPattern(FileUtil.getGenFolderPath() + FileUtil.GROUP_SUB_FOLDER + FileUtil.GROUP_ORIGINAL_SUB_FOLDER, group.getId()+".*");
        FileUtil.deleteFilesWithPattern(FileUtil.getGenFolderPath() + FileUtil.GROUP_SUB_FOLDER + FileUtil.GROUP_RESIZED_SUB_FOLDER, group.getId()+".*");
        group.setHasImage(false);
        groupRepository.merge(group);

        return new ModelAndView("redirect:group", "id", group.getId());
    }

}