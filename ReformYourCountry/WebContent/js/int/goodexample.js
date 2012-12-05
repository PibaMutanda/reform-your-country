var createEditorOpen = false;
var editEditorOpen = false;

function editSubmit(idEditedValueContainer, isNewExample){
	var idItem = $('#ckEditForm > input[name="idItem"]').val();

	if(typeof idItem === "undefined" || idItem == ""){ //in case of a new item id is not defined
		CKeditorEditSubmit("colArg"+suffixDivId+">.listArgument",hideAllCkEditorContainer);
	}else{
		CKeditorEditSubmit('item'+$('#ckEditForm > input[name="idItem"]').val(),hideAllCkEditorContainer);
	}
	return false;
}

function editStart(item, editedItemDBId){
	if (showMessageIfNotLogged(item)) {
		return;
	}
	
	hideAllCkEditorContainer();
	sendValues("/ajax/goodexample/edit", 
				{ argumentId : editedItemDBId }, 
				function(data){
					$("#ckEditForm").empty();
					$("#item"+editedItemDBId).html(data);
					activateCkEditorAndHelpDiv(editedItemDBId);
					$("#CkEditFormSubmit").attr("onclick","return editSubmit();");//return false when method success to avoid form submition
				},
				"#item"+editedItemDBId
	);
}

// Display editor to create a goodexample
function createStart(ParamToSend,suffixDivId) {
	if (showMessageIfNotLogged($('#argumentAddDivFakeEditor'+suffixDivId))) {
		return;
	}
	$('#createDivFakeEditor'+suffixDivId).hide();
	hideAllCkEditorContainer();
	sendValues("/ajax/argument/edit",
				ParamToSend,
				function(data){
					$("#ckEditForm").html("");
					$('#createDivRealEditor'+suffixDivId).html(data);
					$('#createDivRealEditor'+suffixDivId).show();
					activateCkEditorAndHelpDiv('');
					$("#CkEditFormSubmit").attr("onclick","return editSubmit()");
				}
	);	
}

function deleteItem(objectButtonDelete,editedItemDBId){
	var answer = confirm('Etes vous sur de vouloir supprimer cet argument?');
	if (answer) {
		sendValues("/ajax/goodexample/delete", { id : editedItemDBId }, "item"+editedItemDBId, function() {$("#item"+editedItemDBId).remove();}, objectButtonDelete);
	}
}

function hideAllCkEditorContainer(){
	var idItem = $('#ckEditForm > input[name="idItem"]').val();	
	if(typeof idItem === "undefined" || idItem == ""){
		$('#argumentAddDivFakeEditor').show();
		$('#argumentAddDivRealEditor').empty().hide();
	}else{
		sendSimpleValue(null,idItem,'item'+idItem,"/ajax/goodexample/refresh",""); //Refresh the div with the item value
	}
}

///////////////////////////////// VOTES 


function voteOnItem(voteButton,idGoodExample){
	vote(voteButton,"/ajax/goodexample/vote",1,idGoodExample,"item"+idGoodExample);
}

function unVoteItem(idGoodExample){
	 unVote("/ajax/goodexample/unvote",idGoodExample,"item"+idGoodExample);
}

///////////////////////////////// COMMENTS

function sendEditComment(item,itemId){
	sendSimpleValue(item,$('#idComm'+itemId).val(),"item"+itemId,"commentArea"+itemId,"/ajax/argument/commentedit",$("#comm"+itemId).attr("value"));
}
function sendNewComment(item,divId,idArg){
	sendSimpleValue(item,idArg,divId,"commentArea"+idArg,"/ajax/argument/commentadd",$('#comm'+idArg).val());
}
function deleteComment(item,DbidComment,commentedItemDbId){
	var answer = confirm('Etes vous sur de vouloir supprimer ce commentaire?');
	if (answer)	{
		sendSimpleValue(item,DbidComment,"item" + commentedItemDbId,"commentArea" + DbidComment,"/ajax/argument/commentdelete","");
	}
}
function commentHide(item,idArg,DbidComment){
	sendSimpleValue(item,DbidComment,"item"+idArg,'commentArea'+DbidComment,"/ajax/argument/commenthide","");
}
