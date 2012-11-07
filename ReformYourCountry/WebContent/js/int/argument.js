function sendNewComment(item,content,action,title,ispos){
	alert(title);
	var requestArg = $.ajax({
		url: "/ajax/argumentAdd",
		type: "POST",
		data: "content="+ content+"&action="+action+"&title="+title+"&ispos="+ispos,
		dataType: "html"
	}).done(function(data) {
		//Send the new data to the div containing the lists
		$("#argContainer").html(data);
	});

	requestArg.fail(function(jqXHR, textStatus) {
		$("#argContainer").text("Erreur lors de l'envoi d'una rgument : "+textStatus);
	});
}