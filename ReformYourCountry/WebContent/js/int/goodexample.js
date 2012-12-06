var createEditorOpen = false;
var editEditorOpen = false;

function editSubmit(){
	var idItem = $('#ckEditForm > input[name="idItem"]').val();

	if(typeof idItem === "undefined" || idItem == ""){ //in case of a new item id is not defined
		CKeditorEditSubmit("list",hideAllCkEditorContainer);
	}else{
		CKeditorEditSubmit('item'+idItem,hideAllCkEditorContainer);
	}
	return false;
}

function ItemEditStart(item, editedItemDBId){
	if (showMessageIfNotLogged(item)) {
		return;
	}
	
	hideAllCkEditorContainer();
	sendValues("/ajax/goodexample/edit", 
				{ idItem : editedItemDBId }, 
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
function createStart(idParent) {
	if (showMessageIfNotLogged($('#createDivFakeEditor'))) {
		return;
	}
	$('#createDivFakeEditor').hide();
	hideAllCkEditorContainer();
	sendValues("/ajax/goodexample/edit",
				{ idParent : idParent },
				function(data){
					$("#ckEditForm").html("");
					$('#createDivRealEditor').html(data);
					$('#createDivRealEditor').show();
					activateCkEditorAndHelpDiv('');
					$("#CkEditFormSubmit").attr("onclick","return editSubmit()");
				}
	);	
}

function deleteItem(objectButtonDelete,editedItemDBId){
	var answer = confirm('Etes vous sur de vouloir supprimer ce bon example ?');
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

function submitComment(objectButton,commentedItemDbID){
	submitCommentEdit("/ajax/goodexample/commenteditsubmit",objectButton,commentedItemDbID);
}

function deleteComment(objectButton,idDbComment,commentedItemDbID){
	if (confirm('Etes vous sur de vouloir supprimer ce commentaire?'))	{
		sendValuesAndReplaceItem("/ajax/goodexample/commentdelete",{id : idDbComment},"item"+commentedItemDbID, objectButton);
	}
}

function commentHide(item,commentedItemDbID,idDbComment){
	sendSimpleValue(item,idDbComment,"item"+commentedItemDbID,'commentArea'+idDbComment,"/ajax/goodexample/commenthide","");
}
