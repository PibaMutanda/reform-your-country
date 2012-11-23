var createEditorOpen = false;
var editEditorOpen = false;

function editSubmit(idEditedValueContainer, isNewExample){
	console.log("editSubmit");
	if(isNewExample){
		createFinalize();
	}
	CKeditorEditSubmit(idEditedValueContainer);
	return false;
}

// Display editor to create a goodexample
function createStart(idParent) {
	
	if (showMessageIfNotLogged($('#argumentAddDivFakeEditor'))) {
		return;
	}
	
	if (createEditorOpen) {
		createFinalize();
	}
	
	if (editEditorOpen) {
		//TODO finalize edit editor
	}
	
	createEditorOpen = true;
	
	$("#argumentAddDivFakeEditor").hide();
	$.post("/ajax/goodexample/edit",
			{id:idParent},
			function(data){
				$('#argumentAddDivRealEditor').html(data).show();
				activateCkEditorAndHelpDiv();
				$("#ckEditForm > input[type=submit]").attr("onclick",'editSubmit("list", true);');
	});
	
}

//hide editor to create a goodexample
function createFinalize() {
	$('#argumentAddDivFakeEditor').show();  // Show the fake textarea to invite the user adding an argument.
	$('#argumentAddDivRealEditor').empty().hide();  // a small visible part may remain due to padding.
	createEditorOpen = false;
}

//Display editor to edit an existing GoodExample.
function editStart(item, itemId){
	if (showMessageIfNotLogged(item)) {
		return;
	}
	
	$.post("/ajax/goodexample/edit",
			{id:itemId},
			function(data){
				$("#"+itemId).html(data);
				activateCkEditorAndHelpDiv();
				$("#CkEditFormSubmit").attr("onclick","editSubmit(" + itemId + ", false);");
	});
}