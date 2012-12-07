
//Will Send the value of the new Argument form to the controller 
function CKeditorEditSubmit(idEditedValueContainer,succesfn){
	console.log("CKeditorEditSubmit");
	if (idUser.length=0){  // Not logged in
		console.error("Bug: User should not see the button (and the form)");
		return false;
	}
	
	///// Form errors check.
	var errorHappened = false;
	resetErrorMessagesInEditor();
	if( getContentFromCkEditor() == "" ) {
		console.log("Vous devez écrire le contenu.");
		addErrorMessageInEditor("Vous devez écrire le contenu.");
		errorHappened = true;
	}
		
	if ( $('#ckEditForm > input[name="title"]').val()=="") {
		console.log("Vous devez écrire le titre.");
		addErrorMessageInEditor("Vous devez remplir le champs titre.");
		errorHappened = true;
	}
	
	if (errorHappened) {
		return false;
	}
	console.log("Paramètres ok.");
	// Verify if it's a new argument or an edited argument.
	var itemId = $('#ckEditForm > input[name="idItem"]').val();

	//// POST to server
	$.post($("#ckEditForm").attr("action"),
			serializeFormWithCkEditorContent($("#ckEditForm"), "content"),
			function(data) {
				console.log("it's a succes");
				if (itemId.length == 0) {   	// Creation of a new item
					$("#"+idEditedValueContainer).append(data);    // here idEditedValueContainer is the container (the argument column for example) of the new item.
					$("#"+idEditedValueContainer).children().last().effect("highlight", {}, 3000);                           // We append the jsp detail of the arg it at the bottom of its column.
				
				} else {  // Existing argument, we put it inplace
					$("#"+idEditedValueContainer).replaceWith(data);  // Here idEditedValueContainer is the edited item
					$("#"+idEditedValueContainer).effect("highlight", {}, 3000);  
				}
				hideHelp('help');
				succesfn();
				console.log(idEditedValueContainer+"a été modifié");
				// TODO: Add visual effect (highlight 1 second) to the div containing the argument detail just received (data).
				return false;
			}).error(function(jqXHR, textStatus) {
				var exceptionVO = jQuery.parseJSON(jqXHR.responseText);
				console.error(exceptionVO.method + " in " + exceptionVO.clazz + " throw " + exceptionVO.message);
			
				addErrorMessageInEditor(exceptionVO.message);
				return false;
		});
	return false;
}

function addErrorMessageInEditor(msg){	
	addErrorMessage(msg,"ckEditForm > #errors");
}
function resetErrorMessagesInEditor(){	
	$("#ckEditForm > #errors").empty();
}

//Finishes the activation of the editor
function activateCkEditorAndHelpDiv(itemId) {
	createCkEditor("contentItem");
	showHelp('ckEditForm','help'+itemId);
}

//ReferenceId must be the Div just below the helpDiv (From where the help is appearing)
function showHelp(referenceId,helpId){

    var pos = $("#"+referenceId).position();
	$('#'+helpId).css({"position": "absolute"
		,"left":pos.left
		,"top": pos.top-$('#'+helpId).outerHeight(true)-5 + "px"
		, "z-index": 1});
	$('#'+helpId).show("slide", {direction: 'down'},"slow");
}
//Called by the onClick of the X button in the top right corner of the help div.
function hideHelp(idItem){
	$("#"+idItem).hide("slide", {direction: 'down'},"slow");
}