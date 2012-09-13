package reformyourcountry.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import reformyourcountry.model.Group;
import reformyourcountry.repository.GroupRepository;

@Controller
public class GroupDisplayController extends BaseController<Group> {

    @Autowired  GroupRepository groupRepository;

    @RequestMapping("/group")
    public ModelAndView groupDisplay(@RequestParam("id") long id) {
        Group group = getRequiredEntity(id);
        return new ModelAndView("groupdisplay", "group", group);
    }
    
  /*  @RequestMapping("/groupimageadd")
    public ModelAndView bookImageAdd(@RequestParam("id") long groupid,
            @RequestParam("file") MultipartFile multipartFile) throws Exception{    
        
        SecurityContext.assertUserHasPrivilege(Privilege.EDIT_GROUP);

        Group group = groupRepository.find(groupid);

        ModelAndView mv = new ModelAndView("groupdisplay");
        mv.addObject("group", group);

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
    }*/
}
