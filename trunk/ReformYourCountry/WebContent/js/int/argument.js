//Will Send the value of the new Argument form to the controller 
function argumentEditSubmit(){
	var isNew = $('#ckEditForm > input[name="idItem"]').val();
	var ispos =$('#ckEditForm > input[name="ispos"]').val();
	if(typeof isNew ==="undefined" || isNew==""){
		CKeditorEditSubmit("colArg"+ispos+">.listArgument");
	}else{
		CKeditorEditSubmit('arg'+$('#ckEditForm > input[name="idItem"]').val());
	}
	$('#argumentAddDivFakeEditor'+ispos).show();  // Show the fake textarea to invite the user adding an argument.
	var argumentAddDivRealEditor = $('#argumentAddDivRealEditor'+ispos);
	argumentAddDivRealEditor.empty();
	argumentAddDivRealEditor.hide();  // a small visible part may remain due to padding.

	return false;
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
			$("#CkEditFormSubmit").attr("onclick","argumentEditSubmit()");
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
				$("#CkEditFormSubmit").attr("onclick","argumentEditSubmit()");
	});

}



///////////////////////////////// VOTES 


function voteOnArgument(item,idArg,value){
	sendSimpleValue(item,idArg,"arg"+idArg,"/ajax/argumentvote",value);
}


///////////////////////////////// COMMENTS

function sendNewComment(item, idArg){
	sendSimpleValue(item,idArg,"arg"+idArg,"/ajax/commentAdd",$('#comm'+idArg).val());
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

function maxlength_comment(textarea, itemToCommentId, max, min) {
	$button = $('#sendArgComm'+itemToCommentId);
	$lengthCountMessage = $('#nbrCaract'+itemToCommentId);
	var currentLength = textarea.value.length;
	
	if (currentLength < min) {
		var mini = min-currentLength;
		$lengthCountMessage.html("Vous devez encore entrer " +mini+ " caractères");
		$button.prop('disabled', true);
	} else {
		if (currentLength>max) {
			textarea.value=textarea.value.substr(0,max);
			//redifine currentLength otherwise $lengthCountMessage show "-1 caractère restant"
			currentLength = textarea.value.length;
		}
		var maxi = max-currentLength;
		$lengthCountMessage.html(maxi + " caractères restant");	
		$button.prop('disabled', false);
	}
}


