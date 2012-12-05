//Will Send the value of the new Argument form to the controller 
function argumentEditSubmit(){
	var isNew = $('#ckEditForm > input[name="idItem"]').val();
	var ispos =$('#ckEditForm > input[name="ispos"]').val();
	if(typeof isNew ==="undefined" || isNew==""){
		CKeditorEditSubmit("colArg"+ispos+">.listArgument",hideAllCkEditorContainer);
	}else{
		CKeditorEditSubmit('item'+$('#ckEditForm > input[name="idItem"]').val(),hideAllCkEditorContainer);
	}
	return false;
}

function hideAllCkEditorContainer(){
	var ispos =$('#ckEditForm > input[name="ispos"]').val();
	var isNew = $('#ckEditForm > input[name="idItem"]').val();	
	hideHelp("help"+isNew);
	if(typeof isNew ==="undefined" || isNew==""){
		$('#argumentAddDivFakeEditor'+ispos).show();
		$('#argumentAddDivRealEditor'+ispos).empty().hide();

	}else{
		sendSimpleValue(null,isNew,'item'+isNew,'item'+isNew,"/ajax/argument/refresh",""); //Refresh the div with the arg values no changes
	}
}
//Display editor to edit an existing argument.
function ItemEditStart(item, newid){
	if (showMessageIfNotLogged(item)) {
		return;
	}
	hideAllCkEditorContainer();
	$.post("/ajax/argument/edit",{argumentId:newid},
				function(data){
			$("#ckEditForm").html("");
			$("#item"+newid).html(data);
			activateCkEditorAndHelpDiv(newid);
			$("#CkEditFormAbort").attr("onclick","return hideAllCkEditorContainer();");
			$("#CkEditFormSubmit").attr("onclick","return argumentEditSubmit();");//return false when method succes otherwise form is submitted
	});
}

// Display editor to create an argument
function argumentCreateStart(isPos, idAction) {
	if (showMessageIfNotLogged($('#argumentAddDivFakeEditor'+isPos))) {
		return;
	}
	$('#argumentAddDivFakeEditor'+isPos).hide();
	hideAllCkEditorContainer();
	$.post("/ajax/argument/edit",{isPos:isPos,idAction:idAction},
			function(data){
				$("#ckEditForm").html("");
				$('#argumentAddDivRealEditor'+isPos).html(data);
				$('#argumentAddDivRealEditor'+isPos).show();
				activateCkEditorAndHelpDiv('');
				$("#CkEditFormSubmit").attr("onclick","return argumentEditSubmit()");
				return;
	});

}

function deleteItem(item,idArgument){
	var answer = confirm('Etes vous sur de vouloir supprimer cet argument?');
	if (answer)
	{
		sendSimpleValue(item,idArgument,"item"+idArgument,"item"+idArgument,"/ajax/argument/argdelete","");
    	$("#item"+idArgument).empty.hide();
	}
}
///////////////////////////////// VOTES 


function voteOnItem(item,idArg,value){
	vote(item,"/ajax/argument/vote",value,idArg,"item"+idArg);
}

function  unVoteItem(id){
	 unVote("/ajax/argument/unvote",id,"item"+id);
}

///////////////////////////////// COMMENTS

function sendEditComment(item,itemId){
	sendSimpleValue(item,$('#idComm'+itemId).val(),"item"+itemId,"commentArea"+itemId,"/ajax/argument/commentedit",$("#comm"+itemId).attr("value"));
}
function sendNewComment(item,divId,idArg){
	sendSimpleValue(item,idArg,divId,"commentArea"+idArg,"/ajax/argument/commentadd",$('#comm'+idArg).val());
}
function deleteComment(item,idComment,idDiv){
	var answer = confirm('Etes vous sur de vouloir supprimer ce commentaire?');
	if (answer)	{
		sendSimpleValue(item,idComment,idDiv,"commentArea"+idComment,"/ajax/argument/commentdelete","");
	}
}

function commentHide(item,idArg,idComment){
	sendSimpleValue(item,idComment,"item"+idArg,'commentArea'+idComment,"/ajax/argument/commenthide","");
}



