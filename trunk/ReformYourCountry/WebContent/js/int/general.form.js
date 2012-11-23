
//Will Send the value of the new Argument form to the controller 
function CKeditorEditSubmit(idEditedValueContainer){
	console.log("CKeditorEditSubmit");
	if (idUser.length=0){  // Not loggedin
		console.error("Bug: User should not see the button (and the form)");
		return false;
	}
	
	///// Form errors check.
	var errorHappened = false;
	resetErrorMessagesInEditor();
	if( getContentFromCkEditor()=="" ) {
		console.log("Vous devez écrire le contenu.");
		addErrorMessageInEditor("Vous devez écrire le contenu.");
		errorHappened = true;
	}
		
	if ( $('#ckEditForm > input[name="title"]').val()=="") {
		console.log("Vous devez écrire le contenu.");
		addErrorMessageInEditor("Vous devez remplir le champs titre.");
		errorHappened = true;
	}
	
	if (errorHappened) {
		return false;
	}
	console.log("Vous devez écrire le contenu.");
	// Verify if it's a new argument or an edited argument.
	var itemId = $('#ckEditForm > input[name="idItem"]').val();

	//// POST to server
	var requestArg = $.post($("#ckEditForm").attr("action"),serializeFormWithCkEditorContent($("#ckEditForm"), "content"))
	                   .done(function(data) {
	                	   destroyCkEditor();
	                	   if (itemId.length == 0) {   	// Creation of a new item
	                		  
	                		   $("#"+idEditedValueContainer).append(data); // We append the jsp detail of the arg it at the bottom of its column. 
	                	   } else {  // Existing argument, we put it inplace
	                		   $("#"+idEditedValueContainer).replaceWith(data);
	                	   }
	                	   // TODO: Add visual effect (highlight 1 second) to the div containing the argument detail just received (data).
	                   });
	console.log("Vous devez écrire le contenu.");
	requestArg.fail(function(jqXHR, textStatus) {
		// TODO show that error in a jQuery pop-up
		addErrorMessageInEditor("Erreur de communication lors de l'envoi de l'argument...");
		return false;
	});
	return false;
}

function addErrorMessageInEditor(msg){	
	addErrorMessage(msg,"ckEditForm");
}
function resetErrorMessagesInEditor(){	
	$("#ckEditForm > #errors").empty();
}

//Finishes the activation of the editor
function activateCkEditorAndHelpDiv() {
	createCkEditor("contentItem");
    var pos = $("#ckEditForm").position();
	$('#help').css({"position": "absolute"
						,"left":pos.left
						,"top": pos.top-$('#help').outerHeight(true)-5 + "px"
						, "z-index": 1});
	$('#help').show("slide", {direction: 'down'},"slow");
}

//Called by the onClick of the X button in the top right corner of the help div.
function hideHelp(){
	$("#help").hide("slide", {direction: 'down'},"slow");
}