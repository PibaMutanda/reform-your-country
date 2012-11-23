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
			activateCkEditorAndHelpDiv(newid);
			$("#CkEditFormSubmit").attr("onclick","return argumentEditSubmit();");//return false when method succes otherwise form is submitted
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
				activateCkEditorAndHelpDiv('');
				$("#CkEditFormSubmit").attr("onclick","argumentEditSubmit()");
	});

}



///////////////////////////////// VOTES 


function voteOnArgument(item,idArg,value){
	vote(item,"/ajax/argumentvote",value,idArg,"arg"+idArg);
}

function  unVoteArg(id){
	 unVote("/ajax/unvoteargument",id,"arg"+id);
}

///////////////////////////////// COMMENTS

function sendNewComment(item,divId,idArg){
	sendSimpleValue(item,idArg,divId,"/ajax/argcommentadd",$('#comm'+idArg).val());
}




