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

//send value with an id only for logged users
//AND REPLACE CONTENT OF THE DIV WITH ID = idEditedValueContainer, By the content returned by server
function sendSimpleValue(button,idItem,idEditedValueContainer,IdErrorMessageContainer,url,value){
	if (showMessageIfNotLogged(button)) {
		return;
	}
	$.post(url,
			{value : value,
			id : idItem},
			function(data){
				//Send the new data to the div containing the argument
				$("#"+idEditedValueContainer).html(data);
				$("#"+idEditedValueContainer).effect("highlight", {}, 1500);
				return false;
			}
	).error(function(jqXHR, textStatus) {
		var exceptionVO = jQuery.parseJSON(jqXHR.responseText);
		console.error(exceptionVO.method + " in " + exceptionVO.clazz + " throw " + exceptionVO.message);
		addErrorMessage(exceptionVO.message,idEditedValueContainer);
	});
}

function sendValuesAndReplaceItem (url,values,idItemToReplace, ObjectBubbleAttached){
	sendValues(url, 
			values, 
			function(data){
				//Send the new data to the div containing the argument
				$("#"+idItemToReplace).html(data);
				console.log("i replace");
				$("#"+idItemToReplace).effect("highlight", {}, 1500);
			}, 
			idItemToReplace, 
			ObjectBubbleAttached);
}

function sendValues(url, values, succesFunction, idErrorContainer, ObjectBubbleAttached){
	if (showMessageIfNotLogged(ObjectBubbleAttached)) {
		return;
	}
	$.post(url,values
	).success(function(data){
		console.log("it's a success");
		if (typeof succesFunction === 'function') {
			console.log("i execute the succes function");
			succesFunction(data);
		}	
	}).error(function(jqXHR, textStatus) {
		var exceptionVO = jQuery.parseJSON(jqXHR.responseText);
		console.error(exceptionVO.method + " in " + exceptionVO.clazz + " throw " + exceptionVO.message);
		addErrorMessage(exceptionVO.message,idErrorContainer);
	});
}
/**
 * send one value as post
 * @param value : the value to send with value as parameter name
 */
function sendValue(url, value, idErrorContainer, succesFunction){
	sendValues(url, {value : value}, idErrorContainer, succesFunction);
}

function addErrorMessage(msg,idItem){	
	$("#"+idItem).prepend("<p style='color:red;'>"+msg+"</p>");
}


//VOTE

function unVote(url,idItem,idItemToReplace){
	$.post(url,{id : idItem}
	).success(
		function(data){
			$("#"+idItemToReplace).replaceWith(data);
			$("#"+idItemToReplace).effect("highlight", {}, 3000);
		}
	).error(
		function(jqXHR, textStatus) {
			var exceptionVO = jQuery.parseJSON(jqXHR.responseText);
			addErrorMessage(isNaN(exceptionVO.message) ? exceptionVO.message : "",idEditedValueContainer);
		}
	);
}
function vote(item,url,value,idParent,idItemToReplace){
	sendSimpleValue(item,idParent,idItemToReplace,idParent,url,value);
}



//COMMENT
function commentEditStart(item, itemToCommentDbId,idComment,content){
	if (showMessageIfNotLogged(item)) {
		return;
	}
	showHelp("addcomForItem"+itemToCommentDbId,"help"+itemToCommentDbId);
	
	$button = $('#commentAreaForItem'+itemToCommentDbId+'> input[type="button"]');
	$textarea = $("#comm"+itemToCommentDbId);
	$idCommentHiddenField = $('#commentAreaForItem'+itemToCommentDbId+'> input[name="idEditedComment"]');
	
	////Initialize content textarea and id hidden field
	if(typeof content !== "undefined" && typeof idComment !== "undefined") { //this is a comment edit
		$textarea.val(content);
		$idCommentHiddenField.val(idComment);
		$button.val("Modifier");
	} else { // empty the field in case of create
		$textarea.val("");
		$idCommentHiddenField.val("");
		$button.val("Commenter");
	}
	
	$("#addcomForItem"+itemToCommentDbId).hide(); //hide the box who invite user to add a new comment
	$("#commentAreaForItem"+itemToCommentDbId).show();  //show the area where user can edit or add a comment
}

function maxlength_comment(textarea, itemToCommentId, max, min) {
	$button = $('#commentAreaForItem'+itemToCommentId+'> input[type="button"]');
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

function submitCommentEdit(url,objectButton,commentedItemDbID){
	dbIdComment = $('#commentAreaForItem'+commentedItemDbID+'> input[name="idEditedComment"]').val();
	contentComment = $('#comm'+commentedItemDbID).val();
	
	values = {content : contentComment};

	if(dbIdComment > 0){//in case of an edit
		values.idComment =  dbIdComment;
	} else {//this is a new comment
		values.idCommentedItem =  commentedItemDbID;
	}
	
	sendValuesAndReplaceItem(url,values,"item"+commentedItemDbID, objectButton);
}

function cancelComment(idItem){
	$("#addcomForItem"+idItem).show();
	$("#commentAreaForItem"+idItem).hide();
	
}
