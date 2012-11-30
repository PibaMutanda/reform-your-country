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

function addErrorMessage(msg,idItem){	
	$("#"+idItem).prepend("<p style='color:red;'>"+msg+"</p>");
}


//VOTE

function unVote(url,idItem,idItemToReplace){
	var requestArg = $.post(url,
			{id : idItem},function(data){
				$("#"+idItemToReplace).replaceWith(data);
				$("#"+idItemToReplace).effect("highlight", {}, 3000);
			});
	requestArg.fail(function(jqXHR, textStatus) {
		addErrorMessageInEditor("Erreur de communication lors d'un vote"+textStatus);
	});
}
function vote(item,url,value,idParent,idItemToReplace){
	sendSimpleValue(item,idParent,idItemToReplace,idParent,url,value);
}



//COMMENT
function commentEditStart(item, idParent,idComment,content){
	if (showMessageIfNotLogged(item)) {
		return;
	}
	$("#comm"+idParent).attr("value",content);  // Put content in text area
	showHelp("addcom"+idParent,"help"+idParent);
	$("#idComm"+idParent).attr("value",idComment);  // Put id in a hidden field
	$("#addcom"+idParent).hide();   // Hide the button to add a comment
	$("#sendEditComm"+idParent).show(); // show the "modify" submit button
	$("#sendArgComm"+idParent).hide();  // hides the "create" submit button
	$("#commentArea"+idParent).show();  // show the text area

}

function commentAddStart(item, idParent){
	if (showMessageIfNotLogged(item)) {
		return;
	}
	$("#comm"+idParent).attr("value","");//Initialize content textarea
	showHelp("addcom"+idParent,"help"+idParent);
	$("#idComm"+idParent).attr("value","");//Initialize id hidden field
	$("#addcom"+idParent).hide(); // Hide the button to add a comment
	$("#sendEditComm"+idParent).hide();// hide the "modify" submit button
	$("#sendArgComm"+idParent).show();// show the "create" submit button
	$("#commentArea"+idParent).show();	// show the text area
}


function maxlength_comment(textarea, itemToCommentId, max, min) {
	$button = $('#sendArgComm'+itemToCommentId);
	$buttonEdit=$('#sendEditComm'+itemToCommentId);
	$lengthCountMessage = $('#nbrCaract'+itemToCommentId);
	var currentLength = textarea.value.length;
	
	if (currentLength < min) {
		var mini = min-currentLength;
		$lengthCountMessage.html("Vous devez encore entrer " +mini+ " caractères");
		$button.prop('disabled', true);
		$buttonEdit.prop('disabled', true);
	} else {
		if (currentLength>max) {
			textarea.value=textarea.value.substr(0,max);
			//redifine currentLength otherwise $lengthCountMessage show "-1 caractère restant"
			currentLength = textarea.value.length;
		}
		var maxi = max-currentLength;
		$lengthCountMessage.html(maxi + " caractères restant");	
		$button.prop('disabled', false);
		$buttonEdit.prop('disabled', false);
	}
}

function cancelComment(idItem){
	$("#addcom"+idItem).show();
	$("#commentArea"+idItem).hide();
	
}
