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

function cancelComment(idItem){
	$("#addcom"+idItem).show();
	$("#commentArea"+idItem).hide();
	
}
function unVote(url,idItem){
	var requestArg = $.post(url,
			{id : idItem},function(data){
				return data;
			});
	requestArg.fail(function(jqXHR, textStatus) {
		addErrorMessageInEditor("Erreur de communication lors d'un vote"+textStatus);
	});
}