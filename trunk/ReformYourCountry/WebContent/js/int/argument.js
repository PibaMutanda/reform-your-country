//Will Send the value of the new Argument form to the controller 
function argumentEditSubmit(ispos){
	if (idUser.length=0){  // Not loggedin
		console.error("Bug: User should not see the button (and the form)");
		return false;
	}
	
	///// Form errors check.
	var errorHappened = false;
	resetErrorMessagesInEditor();
	if( getContentFromCkEditor()=="" ) {
		addErrorMessageInEditor("Vous devez écrire le contenu.");
		errorHappened = true;
	}
		
	if ( $("#titleArg").val()=="") {
		addErrorMessageInEditor("Vous devez remplir le champs titre.");
		errorHappened = true;
	}
	
	if (errorHappened) {
		return false;
	}

	// Verify if it's a new argument or an edited argument.
	var argumentIdFromForm = $('#argEditorForm > input[name="argumentId"]').val();

	//// POST to server
	var requestArg = $.post("/ajax/argumenteditsubmit",	serializeFormWithCkEditorContent("argEditorForm", "content"))
	                   .done(function(data) {
					destroyCkEditor();
		            if (argumentIdFromForm.length == 0) {   	// Creation of a new argument
						$('#argumentAddDivFakeEditor'+ispos).show();  // Show the fake textarea to invite the user adding an argument.
		            	var argumentAddDivRealEditor = $('#argumentAddDivRealEditor'+ispos);
		            	argumentAddDivRealEditor.empty();
		            	argumentAddDivRealEditor.hide();  // a small visible part may remain due to padding.
						$("#colArg"+ispos+">.listArgument").append(data); // We append the dsp detail of the arg it at the bottom of its column. 
		            } else {  // Existing argument, we put it inplace
	                	$("#arg"+argumentIdFromForm).replaceWith(data);
		            }
		            // TODO: Add visual effect (highlight 1 second) to the div containing the argument detail just received (data).
        		});

	requestArg.fail(function(jqXHR, textStatus) {
		// TODO show that error in a jQuery pop-up
		addErrorMessageInEditor("Erreur de communication lors de l'envoi de l'argument...");
		return false;
	});
	return false;
}

function addErrorMessageInEditor(msg){	
	$("#argEditorForm > #errors").prepend("<p style='color:red;'>"+msg+"</p>");
}
function resetErrorMessagesInEditor(){	
	$("#argEditorForm > #errors").empty();
}


//Display editor to edit an existing argument.
function argumentEditStart(item, newid){
	if (showMessageIfNotLogged(item)) {
		return;
	}
	
	$.post("/ajax/argumentedit",{argumentId:newid},
				function(data){
			$("#arg"+newid).html(data);
			activateCkEditorAndHelpDiv();
	});
}

// Display editor to create an argument
function argumentCreateStart(isPos, idAction) {
	if (showMessageIfNotLogged($('#argumentAddDivFakeEditor'+isPos))) {
		return;
	}
	
	$('#argumentAddDivFakeEditor'+isPos).hide();
	$.post("/ajax/argumentedit",{isPos:isPos,idAction:idAction},
				function(data){
			$('#argumentAddDivRealEditor'+isPos).html(data);
			$('#argumentAddDivRealEditor'+isPos).show();
			activateCkEditorAndHelpDiv();
	});

}

// Finishes the activation of the editor
function activateCkEditorAndHelpDiv() {
	createCkEditor("contentArg");
    var pos = $("#argEditorForm").position();
	$('#help').css({"position": "absolute"
						,"left":pos.left
						,"top": pos.top-$('#help').outerHeight(true)-5 + "px"
						, "z-index": 1});
	$('#help').show("slide", {direction: 'down'},"slow");
}

// Called by the onClick of the X button in the top right corner of the help div.
function hideHelp(item){
	$(item).hide("slide", {direction: 'down'},"slow");
}









///////////////////////////////// VOTES 


function voteOnArgument(item,idArg,value){
	if (showMessageIfNotLogged($('#argumentAddDivFakeEditor'+isPos))) {
		return;
	}
	
	var requestArg = $.ajax({
		url: "/ajax/argumentvote",
		type: "POST",
		data: "idArg="+ idArg+"&value="+ value,
		dataType: "html"
	}).done(function(data) {
		//Send the new data to the div containing the lists
		$("#arg"+idArg).replaceWith(data);
	});

	requestArg.fail(function(jqXHR, textStatus) {
		alert("Erreur de communication lors d'un vote"+textStatus);
	});
}


///////////////////////////////// COMMENTS

function sendNewComment(item, idArg){
	if (showMessageIfNotLogged(item)) {
		return;
	}
	
	var comment=$('#comm'+idArg).val();
	if(comment!=""){
		var requestArg = $.post("/ajax/commentAdd",
								{comment : comment,
								 arg : idArg},
								 function(data){
			//Send the new data to the div containing the argument
			$("#arg"+idArg).html(data);
			return false;
		});

		requestArg.fail(function(jqXHR, textStatus) {
			$("#errorArg").text("Erreur lors de l'envoi d'un commentaire : "+textStatus);
			return false;
		});
	}else{
		$("#errorArg").text("Veuillez écrire un texte avant d'envoyer votre commentaire.");
		return false;
	}
}


function commentEditStart(item, addcom){
	if (showMessageIfNotLogged(item)) {
		return;
	}
	
	$("#addcom"+addcom).hide();
	$("#addcom"+addcom).click(function(){
		$("#addcom"+addcom).hide();
		$("#commentArea"+addcom).show();
	});	
}

function maxlength_textarea(textarea,id, max, min) {
	$button = $('#sendArgComm'+id);
	$lengthCountMessage = $('#nbrCaract'+id);
	var currentLength = textarea.value.length;
	
	if (currentLength < min) {
		var mini = min-currentLength;
		$lengthCountMessage.html("Vous devez encore entrer " +mini+ " caractères");
		$button.prop('disabled', true);
	} else {
		if(currentLength>max)
		{
			textarea.value=textarea.value.substr(0,max);
			//redifine currentLength otherwise $lengthCountMessage show "-1 caractère restant"
			currentLength = textarea.value.length;
		}
		var maxi = max-currentLength;
		$lengthCountMessage.html(maxi + " caractères restant");	
		$button.prop('disabled', false);
	}
}

///////////////////////////////////////////// GENERAL


function showMessageIfNotLogged(item){
	if (idUser.length>0) {  // User is logged in (variable defined by the JSP)
		return false;
	}
	
	$(item).CreateBubblePopup({ 
		innerHtmlStyle: {  // give css property to the inner div of the popup	    	   
			'opacity':0.9
		},
		tail: {align:'center', hidden: false},
		selectable :true,				    	
		innerHtml: '<div> Vous devez <a class="login" style="cursor:pointer;" href="/login">vous connecter</a> avant d\'effectuer cette action.</div>'
    });
	return true;
	 
	
}




//////////////////////////////////////////////// CK EDITOR

var ckEditorUniqueInstance = null;  // We only want one CkEditor instance per page, and it's here.

function destroyCkEditor() {
	ckEditorUniqueInstance.destroy(true);
	ckEditorUniqueInstance = null;
//	// Double destroy to be sure (had problems while testing)...
//	var editor = CKEDITOR.instances[name];
//	if (editor) {
//		editor.destroy(true);
//		delete CKEDITOR.instances[name];
//	}
}

// Will replace the text area by the ck editor.
function createCkEditor(textAreaId) {
	if (ckEditorUniqueInstance != null) {
		console.error("Bug: the ckEditorUniqueInstance should be null at this stage.");
	}
	ckEditorUniqueInstance = CKEDITOR.replace( textAreaId, { 
        customConfig : '/js/ext/ckeditor_config.js',
        toolbar : 'goodExample'
            });
}


function getContentFromCkEditor() {
	if (ckEditorUniqueInstance != null) {
		console.error("Bug: the ckEditorUniqueInstance should be null at this stage.");
	}
	return ckEditorUniqueInstance.getData();
}

// http://stackoverflow.com/questions/5075778/how-do-i-modify-serialized-form-data-in-jquery
function serializeFormWithCkEditorContent(formId, textAreaName) {
	var values, index;

	// Get the parameters as an array
	values = $("#"+formId).serializeArray();
	content_val = ckEditorUniqueInstance.getData();
//	console.log("content_val = " + content_val);
	
	// Find and replace `content` if there
	for (index = 0; index < values.length; ++index) {
	    if (values[index].name == textAreaName) {
	        values[index].value = content_val;
	        break;
	    }
	}

	// Add it if it wasn't there
	if (index >= values.length) {
	    values.push({
	        name: textAreaName,
	        value: content_val
	    });
	}

	// Convert to URL-encoded string
	return jQuery.param(values);
}