$(document).ready(function() {
	//see articleEdit.jsp
	
	$("#sub_nav ul li ").click(function(){
		//$("#sub_nav ul li ").css('background','');
		$(this).attr('style','background:url(../images/secondary-royal-blue/navblueback.png) 0 0 repeat-y');
		alert("test    "+$(this).attr('style'));
	});

	
	
	 	
});