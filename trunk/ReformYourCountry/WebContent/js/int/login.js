/*
 * Script who generate a jquerydialog popup for the user login
 * Actually not used because we need a dedicated page with more login option and some redirect
 */

/*
$(document).ready(function() {			   // do stuff when DOM is ready
			
	
			
	function sendConnectRequest(){


		var username  = $("#logindialog").children().children('input[name="identifier"]').attr('value');
		var password =  $("#logindialog").children().children('input[name="password"]').attr('value');
		var checkbox = $("#logindialog").children().children('input[name="keepLoggedIn"]').is(':checked');


		// execute the ajax request.
		var request = $.ajax({
			url: "ajax/loginsubmit",
			type: "POST",
			data: { identifier : username,
				password : password,
				keepLoggedIn : checkbox},
				dataType: "html"
		});

		// if success
		request.done(function(data) {

			location.reload();
			$('#logindialog').dialog("close");
		});

		// if fail
		request.fail(function(msg){

			$('#logindialog').children('label[id="errorMsg"]').text(msg.responseText);
		});


	}  

	$(".login").click(function(){  // To open the dialog box when clicking on the upper right corner login link.
		// 1. initialise jquery ui dialog box. It is empty and does not open.
		console.log("login");
		$('#logindialog').dialog({
			title :   "Identification",
			autoOpen: false,
			width: 320, 
			buttons: {
				"Se connecter": function() {  // Called on button click;
					sendConnectRequest();
				},
				"Anuler": function() {
					$('#logindialog').dialog("close");
				}
			},
			show:"clip",
			hide:"clip"
		});
		
		// 2. ills the dialog with content from the server (LoginController) whent the user clicks the link
		$('#logindialog').load("ajax/login", function(){
			$('#logindialog').children('label[id="errorMsg"]').empty(); // reset the error message
			$('#logindialog').dialog('open');
		});
		
		// 3. KeyBoard shorcut : enter send the connection request
		$("#logindialog").keydown(function(e){
			if(e.which == 13) {
				sendConnectRequest();
			}
		});

		return false;
	});



	// TODO add secured path to avoid malformed url error due to missing parameter for spring controller
	//maybe better solution ??


	$("#logout").click(function(){
		var request = $.ajax({
			url: "ajax/logout",
			type: "POST", 	
			dataType: "text"
		});
		// if success
		request.done(function(data) {
			location.reload();  // Maybe will make the server throw an UnauthorizedException if anonymous users cannot see that page. 
		});

		return false;
	});


});*/