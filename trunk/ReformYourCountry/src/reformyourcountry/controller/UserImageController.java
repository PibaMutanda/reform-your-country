package reformyourcountry.controller;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionRepository;
import org.springframework.social.connect.UsersConnectionRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import reformyourcountry.model.User;
import reformyourcountry.model.User.AccountConnectedType;
import reformyourcountry.repository.UserRepository;
import reformyourcountry.security.Privilege;
import reformyourcountry.security.SecurityContext;
import reformyourcountry.service.UserService;
import reformyourcountry.util.FileUtil;
import reformyourcountry.util.FileUtil.InvalidImageFileException;
import reformyourcountry.util.ImageUtil;

@Controller
@RequestMapping("/user")
public class UserImageController extends BaseController<User> {

	@Autowired UserRepository userRepository;
	@Autowired UsersConnectionRepository usersConnectionRepository;
	 @Autowired UserService userService;
	
	@RequestMapping("/image")
	public ModelAndView userImage(@RequestParam("id") long userid){
		User user = getRequiredEntity(userid);
		ModelAndView mv= new ModelAndView("userimage", "user", user);
		
		return mv;
	}

	@RequestMapping("/imageadd")
	public ModelAndView userImageAdd(@RequestParam("id") long userid,
			@RequestParam("file") MultipartFile multipartFile) throws Exception{    
		User user = getRequiredEntity(userid);

		ModelAndView mv = new ModelAndView("userdisplay");
		mv.addObject("user",user);
		mv.addObject("canEdit", canEdit(user));
		mv.addObject("random", System.currentTimeMillis());

		///// Save original image, scale it and save the resized image.
		try {
			FileUtil.uploadFile(multipartFile, FileUtil.getGenFolderPath() + FileUtil.USER_SUB_FOLDER + FileUtil.USER_ORIGINAL_SUB_FOLDER, 
					FileUtil.assembleImageFileNameWithCorrectExtention(multipartFile, Long.toString(user.getId())));

			BufferedImage resizedImage = ImageUtil.scale(new ByteArrayInputStream(multipartFile.getBytes()),120 * 200, 200, 200);
						
			ImageUtil.saveImageToFileAsJPEG(resizedImage,  
					FileUtil.getGenFolderPath() + FileUtil.USER_SUB_FOLDER + FileUtil.USER_RESIZED_SUB_FOLDER +  FileUtil.USER_RESIZED_LARGE_SUB_FOLDER, user.getId() + ".jpg", 0.9f);
			
			BufferedImage resizedSmallImage = ImageUtil.scale(new ByteArrayInputStream(multipartFile.getBytes()),40 * 40, 60, 60);
			
			ImageUtil.saveImageToFileAsJPEG(resizedSmallImage,  
					FileUtil.getGenFolderPath() + FileUtil.USER_SUB_FOLDER + FileUtil.USER_RESIZED_SUB_FOLDER + FileUtil.USER_RESIZED_SMALL_SUB_FOLDER, user.getId() + ".jpg", 0.9f);

			user.setPicture(true);
			
			userRepository.merge(user);
		} catch (InvalidImageFileException e) {  //Tell the user that its image is invalid.
			setMessage(mv, e.getMessageToUser());
		}

		

		return mv;
	}

	 @RequestMapping("/imagedelete")
	 public ModelAndView userImageDelete(@RequestParam("id") long userid){
		 User user = getRequiredEntity(userid);

		 FileUtil.deleteFilesWithPattern(FileUtil.getGenFolderPath() + FileUtil.USER_SUB_FOLDER + FileUtil.USER_ORIGINAL_SUB_FOLDER, user.getId()+".*");
		 FileUtil.deleteFilesWithPattern(FileUtil.getGenFolderPath() + FileUtil.USER_SUB_FOLDER + FileUtil.USER_RESIZED_SUB_FOLDER + FileUtil.USER_RESIZED_LARGE_SUB_FOLDER, user.getId()+".*");
		 FileUtil.deleteFilesWithPattern(FileUtil.getGenFolderPath() + FileUtil.USER_SUB_FOLDER + FileUtil.USER_RESIZED_SUB_FOLDER +  FileUtil.USER_RESIZED_SMALL_SUB_FOLDER, user.getId()+".*");
		 
		 user.setPicture(false);
		 
		 userRepository.merge(user);

		 return new ModelAndView("redirect:"+user.getUserName());
		 
	 }

	 private boolean canEdit(User user) {
		 return user.equals(SecurityContext.getUser()) || SecurityContext.isUserHasPrivilege(Privilege.MANAGE_USERS);
	 }

	 
	
}
