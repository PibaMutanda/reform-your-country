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
function sendSimpleValue(button,idItem,idEditedValueContainer,url,value){
	if (showMessageIfNotLogged(button)) {
		return;
	}
	var requestArg = $.post(url,
			{value : value,
			id : idItem},
		function(data){
			//Send the new data to the div containing the argument
			$("#"+idEditedValueContainer).html(data);
			return false;
		});

	requestArg.fail(function(jqXHR, textStatus) {
		addErrorMessageInEditor("Erreur de communication lors d'un vote"+textStatus);
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
			});
	requestArg.fail(function(jqXHR, textStatus) {
		addErrorMessageInEditor("Erreur de communication lors d'un vote"+textStatus);
	});
}
function vote(item,url,value,idParent,idItemToReplace){
	sendSimpleValue(item,idParent,idItemToReplace,url,value);
}



//COMMENT
function commentEditStart(item, idParent,idComment,content){
	if (showMessageIfNotLogged(item)) {
		return;
	}
	$("#comm"+idParent).attr("value",content);
	showHelp("addcom"+idParent,"help"+idParent);
	$("#idComm"+idParent).attr("value",idComment);
	$("#addcom"+idParent).hide();
	$("#sendEditComm"+idParent).show();
	$("#sendArgComm"+idParent).hide();
	$("#commentArea"+idParent).show();
	$("#addcom"+idParent).click(function(){
		$("#addcom"+idParent).hide();
		$("#commentArea"+idParent).show();
	});	
}

function commentAddStart(item, idParent){
	if (showMessageIfNotLogged(item)) {
		return;
	}
	$("#comm"+idParent).attr("value","");
	showHelp("addcom"+idParent,"help"+idParent);
	$("#idComm"+idParent).attr("value","");
	$("#addcom"+idParent).hide();
	$("#sendEditComm"+idParent).hide();
	$("#sendArgComm"+idParent).show();
	$("#addcom"+idParent).click(function(){
		$("#addcom"+idParent).hide();
		$("#commentArea"+idParent).show();
	});	
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
