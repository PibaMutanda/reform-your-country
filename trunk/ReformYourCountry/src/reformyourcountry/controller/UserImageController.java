package reformyourcountry.controller;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import reformyourcountry.model.Book;
import reformyourcountry.model.User;
import reformyourcountry.repository.UserRepository;
import reformyourcountry.security.Privilege;
import reformyourcountry.security.SecurityContext;
import reformyourcountry.util.FileUtil;
import reformyourcountry.util.FileUtil.InvalidImageFileException;
import reformyourcountry.util.ImageUtil;

@Controller
public class UserImageController extends BaseController<User> {

	@Autowired UserRepository userRepository;
	
	@RequestMapping("/userimage")
	public ModelAndView userImage(@RequestParam("id") long userid){
		
		User user= userRepository.find(userid);
		ModelAndView mv= new ModelAndView("userimage");
		mv.addObject("user",user);
		
		return mv;
	}
	
	 @RequestMapping("/userimageadd")
	    public ModelAndView userImageAdd(@RequestParam("id") long userid,
	            @RequestParam("file") MultipartFile multipartFile) throws Exception{    
	       

	        User user = userRepository.find(userid);
	        
	        ModelAndView mv = new ModelAndView("userdisplay");
	        mv.addObject("user",user);
	        mv.addObject("canEdit", canEdit(user));
	       
	        ///// Save original image, scale it and save the resized image.
	        try {
	        	FileUtil.uploadFile(multipartFile, FileUtil.getGenFolderPath() + FileUtil.USER_SUB_FOLDER + FileUtil.USER_ORIGINAL_SUB_FOLDER, 
	        	        FileUtil.assembleImageFileNameWithCorrectExtention(multipartFile, Long.toString(user.getId())));

	        	BufferedImage resizedImage = ImageUtil.scale(new ByteArrayInputStream(multipartFile.getBytes()),120 * 200, 200, 200);
	        	
	        	ImageUtil.saveImageToFileAsJPEG(resizedImage,  
	        			FileUtil.getGenFolderPath() + FileUtil.USER_SUB_FOLDER + FileUtil.USER_RESIZED_SUB_FOLDER, user.getId() + ".jpg", 0.9f);

	        } catch (InvalidImageFileException e) {  //Tell the user that its image is invalid.
	            setMessage(mv, e.getMessageToUser());
	        }
	      
	        user.setPicture(true);
	        userRepository.merge(user);

	        return mv;
	    }
	 
	 @RequestMapping("/userimagedelete")
	    public ModelAndView userImageDelete(@RequestParam("id") long userid){
	        

		 	User user = userRepository.find(userid);

	        FileUtil.deleteFilesWithPattern(FileUtil.getGenFolderPath() + FileUtil.USER_SUB_FOLDER + FileUtil.USER_ORIGINAL_SUB_FOLDER, user.getId()+".*");
	        FileUtil.deleteFilesWithPattern(FileUtil.getGenFolderPath() + FileUtil.USER_SUB_FOLDER + FileUtil.USER_RESIZED_SUB_FOLDER, user.getId()+".*");
	        user.setPicture(false);
	        userRepository.merge(user);

	        ModelAndView mv = new ModelAndView("redirect:user");
	        mv.addObject("username", user.getUserName());
	        return mv;
	    }
	
	 private boolean canEdit(User user) {
			return user.equals(SecurityContext.getUser()) || SecurityContext.isUserHasPrivilege(Privilege.MANAGE_USERS);
		}
	
}
