$(document).ready(function() {

	//disable the save button
	$(".save").each(function(){ // .each() used because .attr() only takes the first matched element (so if there are more than one save button, only the first will be disabled).
		$(this).attr('disabled', 'disabled');
	});
	

	$(".autosaveable").keydown(function(event){
		//it save when we type CTRL+S
		if(event.ctrlKey  && event.keyCode == 83) { // touche s = 83
			event.preventDefault();
			performSave();
		}
	});

	$(".autosaveable").keyup(function(event) {
		$(".save").removeAttr('disabled');  // Make the save button enabled (the text effectively changed since the last save).
		contentHasNotBeenSaved = true;

		if(event.ctrlKey == false  &&  event.keyCode != 83) {// get the save timer only when there is new content and when CTRL+S wasn't pressed
			setTimeout(function(){performSave();}, 120000);  // We'll save in 120 seconds from now.
		}
	});

	
	$("input.save").onclick(function(event) {  // The save buttons
		contentHasNotBeenSaved = false;
	});
	

	// Ask to save when exiting without saving
    window.onbeforeunload = function(evt){
        if (contentHasNotBeenSaved) {
        	return "Vous n'avez pas sauvé votre texte.";
        } else {
        	return;
        }
        
    }


	// Function called when a change is performed on the article.
	function performSave() {
		////// We effectively start saving from here
		$(".saving").text("sauvegarde en cours....");		
		//get the action value from the form and add ajax/ before because this is a project convention for ajax request
		$.post("ajax"+$(".autosaveable").closest("form").attr("action"), 
				$(".autosaveable").closest("form").serialize())//closest get the closest ancestor or itself . 
																						//For example if the tag with autosaveable  is contain in a form(with id #exmaple1) and this form is contain in an other form(with id #exmaple2) 
																						//only #exmaple1 is selected and submit via .serialize().
		.done(function(){
			$(".save").each(function(){
				$(this).attr('disabled', 'disabled');
				contentHasNotBeenSaved = false;
			});
			$(".saving").text("sauvé à "+getDate());	
		});
	}

	function getDate(){
		var now = new Date();
		var hours = now.getHours();
		var minutes = now.getMinutes();
		var secondes = now.getSeconds();

		if (hours < 10) hours = "0"+hours;
		if (minutes < 10) minutes ="0"+minutes;
		if (secondes < 10) secondes ="0"+secondes;

		return hours+":"+minutes+":"+secondes;
	}

});