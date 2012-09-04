package reformyourcountry.test;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;

import be.loop.jbb.bl.UserService;
import blackbelt.integration.SocialSiteDelegate;
import blackbelt.model.SocialIntegration;
import blackbelt.service.SocialNetworkService;
import blackbelt.ui.common.MultiChoiceModalWindow;
import blackbelt.ui.common.NotificationUtil;

import com.vaadin.terminal.ExternalResource;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;

@SuppressWarnings("serial")
@Configurable(preConstruction = true)
public class RetrieveSocialImageWindow extends MultiChoiceModalWindow {

	private Logger logger = Logger.getLogger(RetrieveSocialImageWindow.class);
	
	private @Autowired SocialNetworkService socialNetworkService;
	private @Autowired UserService userService;
	
	
	public RetrieveSocialImageWindow(List<SocialIntegration> creds) {
		super("Choose your profie image", "");
		
		this.setWidth("550px");
		
		int size = creds.size();
		
		//TODO Add text saying it give you points to have an image attached to your profile
		
		if(size == 1) {
			this.addContent(new Label("Click on the button below if you want this image to become your KnowledgeBlackBelt profile picture ..."));
		} else if(size > 1){
			this.addContent(new Label("Click on the button corresponding to the image you want us to use as your KnowledgeBlackBelt profile picture ..."));			
		} else {
			throw new RuntimeException("Size of the list cannot be 0");
		}
		
		HorizontalLayout images = new HorizontalLayout();
		
		for (final SocialIntegration oAuthCredential : creds) {
			String siteName = oAuthCredential.getSite().name().toLowerCase() ;
			final String url = socialNetworkService.getProfilePictureUrl(oAuthCredential);
			if(StringUtils.isEmpty(url)){
				continue;
			}
			ExternalResource resource = new ExternalResource(url);
			Label imageLabel = new Label("<span style='float:left; margin-right:10px;'><center><img src='"+ resource.getURL() + "'/><br/>Image from " + siteName + "</center></span>", Label.CONTENT_XHTML);						
			images.addComponent(imageLabel);						
			Button button = this.addButton("Import image from " + siteName, new ClickListener() {
				@Override
				public void buttonClick(ClickEvent event) {
					try {
						userService.saveUserImages(oAuthCredential.getUser(), new URL(url).openStream());
					} catch (Exception e) {
						logger.error("Not possible to retrieve remote image", e);
						NotificationUtil.showNotification("There was an error retrieveing the image you selected.", "Please try again later");
					}
				}
			}, ClickBehavior.CLOSE_AND_REFRESH);
			// button.setIcon(new PictureResource(PicturePath.IMAGE, oAuthCredential.getSite().getPictureName()));
		}
		
		this.addContent(images);
		this.addButton("Cancel");
	}
	
}
