$(document).ready(function() {

	//disable the save button
	$("#save").attr('disabled', 'disabled');

	var timer = null;  // Current timer waiting to save the content in a few seconds.

	$("#content,#title,#datepicker,#publicView1").keydown(function(event){
		if(event.ctrlKey  && event.keyCode == 83) { // touche s = 83
			console.log(event.wich + " "+ event.keyCode);	
			event.preventDefault();
			performChange(0);  // 0 = immediately			
		}
	});

	$("#content,#title,#datepicker,#publicView1").keyup(function(event) {
		performChange(5000);  // We'll save in 120 seconds from now.
	});

	$("#datepicker,#publicView1").focusout(function(){
		performChange(0);  // When the field loses focus, we save immediately.
	});

	
	// Function called when a changed is performed on the article.
	// ms = number of miliseconds to wait before saving.
	function performChange(ms) {
		$("#save").removeAttr('disabled');  // Make the save button enabled (the text effectively changed since the last save).
	
        if (timer == null) { // No timer is currently on-going => we need to create one.
		
   		  timer =  // We add an element to the array.  
				setTimeout(function(){
				////// We effectively start saving from here
				$("#saving").text("savegarde en cours....");		
				
				/*	console.log($("#title").attr("value"));
  		            console.log($("#datepicker").attr("value"));
  		            console.log($("#publicView1").is(":checked"));
  		             console.log($("#content").val());
  		             console.log($("#id").attr("value"));*/

				var title = $("#title").attr("value");
				var date = $("#datepicker").attr("value");
				var publicview = $("#publicView1").is(":checked") ;
				var publicviewForSpring;

				if($("#publicView1").is(":checked")){
					publicviewForSpring = "on";

				} else {
					publicviewForSpring = "off";
					
				}
				var content = $("#content").val();
				var id = $("#id").attr("value");

				// Ask the server to save
				var request = $.ajax({
					url: "ajax/articleeditsubmit",
					type: "POST",
					data: { title : title,
						publishDate : date,
						publicView:publicview,
						_publicView : publicviewForSpring,
						content : content,
						id : id},
						dataType: "html"
				});

				request.done(function(){
					$("#save").attr('disabled', 'disabled');
					$("#saving").text("sauvé à "+getDate());	
					timer = null;
				});


			},ms);

		}

	}


	function getDate(){
		var now = new Date();
		var hours = now.getHours();
		var minutes = now.getMinutes();
		var ms = now.getMilliseconds();

		if (hours < 10) hours = "0"+hours;
		if (minutes < 10) minutes ="0"+minutes;

		return hours+":"+minutes+":"+ms;
	}


});