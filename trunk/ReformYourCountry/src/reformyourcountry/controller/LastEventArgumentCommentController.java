package reformyourcountry.controller;
import java.util.ArrayList;
import java.util.Collections;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;



import reformyourcountry.model.Argument;
import reformyourcountry.model.Comment;
import reformyourcountry.model.GoodExample;
import reformyourcountry.model.User;
import reformyourcountry.repository.ArgumentRepository;
import reformyourcountry.repository.CommentRepository;
import reformyourcountry.repository.GoodExampleRepository;


@Controller
@RequestMapping("/lasteventargumentcomment")
public class LastEventArgumentCommentController {

	
	@Autowired ArgumentRepository argumentRepository;
	@Autowired CommentRepository commentRepository;
	@Autowired GoodExampleRepository goodExampleRepository;
	
	@RequestMapping("")
	public ModelAndView lastEventArgumentCommentDisplay(){
		
		return new ModelAndView("lasteventargumentcomment");
	}
	
	@RequestMapping("/listupdatedevent")
	public ModelAndView listUpdatedEvent(){
		
		List<Argument> listArgument = argumentRepository.findArgumentByDate();
		List<Comment> listComment = commentRepository.findCommentByDate();
		List<GoodExample> listGoodExample = goodExampleRepository.findGoodExampleByUpdateDate();
		
		List<LastEvent> listLastEvent = new ArrayList<LastEvent>();
				
		 
		 for(Argument argument: listArgument){
			
			 Date dateargument;
			 if(argument.getUpdatedOn()!=null){
				 dateargument=argument.getUpdatedOn();
			 }else{
				 dateargument=argument.getCreatedOn();
			 }
			 listLastEvent.add(new LastEvent(argument.getTitle(),"/action/"+argument.getAction().getUrl(),dateargument,argument.getUpdatedOrCreatedBy()));
		 }
		 
		 for(Comment comment: listComment){
			
			 Date datecomment;
			 if(comment.getUpdatedOn()!=null){
				 datecomment=comment.getUpdatedOn();
			 }else{
				 datecomment=comment.getCreatedOn();
			 }
			 listLastEvent.add(new LastEvent("Comment:"+comment.getContent(),"/action/"+comment.getArgument().getAction().getUrl(),datecomment,comment.getUpdatedOrCreatedBy()));
		 }
		 
		 for(GoodExample goodExample: listGoodExample){
			 Date dategoodexample;
			 if(goodExample.getUpdatedOn()!=null){
				 dategoodexample=goodExample.getUpdatedOn();
			 }else{
				 dategoodexample=goodExample.getCreatedOn();
			 }
			 listLastEvent.add(new LastEvent(goodExample.getTitle(),"",dategoodexample,goodExample.getUpdatedOrCreatedBy()));
		 }
			
		 Collections.sort(listLastEvent);
			
		 ModelAndView mv = new ModelAndView("lasteventargumentcomment");
		 mv.addObject("listlastevent",listLastEvent);
		 return mv;			
					
		}
		

	public static class LastEvent implements Comparable<LastEvent>{
		
		private String name;
		private String url;
		private Date date;
		private User user;
		
		public LastEvent(String name,String url,Date date, User user){
			this.name=name;
			this.url=url;
			this.date=date;
			this.user=user;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getUrl(){
			return url;
		}
		public void setUrl(String url){
			this.url=url;
		}
		public Date getDate() {
			return date;
		}

		public void setDate(Date date) {
			this.date = date;
		}
		public User getUser() {
			return user;
		}

		public void setUser(User user) {
			this.user = user;
		}
		
		@Override
		public int compareTo(LastEvent lastEvent) {
			return this.date.compareTo(lastEvent.date);
		}

		
		
	}
}
