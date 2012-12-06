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
					$("#CkEditFormAbort").attr("onclick","return hideAllCkEditorContainer();");
				},
				"#item"+editedItemDBId
	);
}

// Display editor to create a goodexample
function createStart(idParent) {
	if (showMessageIfNotLogged($('#createDivFakeEditor'))) {
		return;
	}
	hideAllCkEditorContainer();
	$('#createDivFakeEditor').hide();
	sendValues("/ajax/goodexample/edit",
				{ idParent : idParent },
				function(data){
					$("#ckEditForm").html("");
					$('#createDivRealEditor').html(data);
					$('#createDivRealEditor').show();
					activateCkEditorAndHelpDiv('');
					$("#CkEditFormSubmit").attr("onclick","return editSubmit()");
					$("#CkEditFormAbort").attr("onclick","return hideAllCkEditorContainer();");
				}
	);	
}

function deleteItem(objectButtonDelete,editedItemDBId){
	var answer = confirm('Etes vous sur de vouloir supprimer ce bon example ?');
	if (answer) {
		sendValues("/ajax/goodexample/delete", { id : editedItemDBId }, function() {$("#item"+editedItemDBId).remove();}, "item"+editedItemDBId, objectButtonDelete);
	}
}

function hideAllCkEditorContainer(){
	var idItem = $('#ckEditForm > input[name="idItem"]').val();	
	if(typeof idItem === "undefined" || idItem == ""){
		$('#createDivFakeEditor').show();
		$('#createDivRealEditor').empty().hide();
	}else{
		sendValuesAndReplaceItem("/ajax/goodexample/refresh",{id : idItem},"item"+idItem, null);
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
