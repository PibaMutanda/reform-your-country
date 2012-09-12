$(document).ready(function() {
			   // do stuff when DOM is ready
			
	       // initialise jquery ui dialog box
			$('#logindialog').dialog({
				title :   "Identification",
				autoOpen: false,
				width: 300, 
				buttons: {
					"Se connecter": function() {
										
						var username  = $(this).children().children('input[name="identifier"]').attr('value');
						var password =  $(this).children().children('input[name="password"]').attr('value');
						var checkbox = $(this).children().children('input[name="keepLoggedIn"]').is(':checked');
						
						console.log(username);
						console.log(password);
						console.log(checkbox);
						
					// execute the ajax request
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
							console.log(data);
							location.reload();
							$('#logindialog').dialog("close");
						});
						
						// if fail
						request.fail(function(msg){
							console.log(msg.responseText);
							$('#logindialog').children('label[id="errorMsg"]').text(msg.responseText);
						});
						
						
					},
					"Anuler": function() {
						$('#logindialog').dialog("close");
					}
				},
				
				show:"clip",
				hide:"clip"
				
			});
			
	    	
	   
	
			   
	    $("#login").click(function(){
	    	         
	    	        $('#logindialog').children('label[id="errorMsg"]').empty(); // reset the error message
	  	        	$('#logindialog').dialog('open');
	  		
				
				return false;
			});
	    
	    
	    
	    $("#logout").click(function(){
	         
	   
		
			var request = $.ajax({
  	    	  url: "ajax/logout",
  	    	  type: "POST",
  	    	
  	    	  dataType: "html"
  	    	});
			// if success
			request.done(function(data) {
				console.log(data);
				location.reload();
				
			});
	    	
	    	
		
		return false;
	});
	
	 });