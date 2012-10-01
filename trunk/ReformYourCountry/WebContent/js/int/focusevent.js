$(document).ready(function() {
	//see articleEdit.jsp
	
	$("#content,#title,#datepicker,#publicView1").focus(function(){
		$(this).css("background-color","#F5F7F7");
	});

	$("#content,#title,#datepicker,#publicView1").focusout(function(){
		$(this).css("background-color","#f4f4f2");
	});
	
	
	
});