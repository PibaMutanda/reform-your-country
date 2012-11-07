function sendNewComment(item,content,action,title,ispos){
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
		$("#errorArg").text("Erreur lors de l'envoi d'un argument : "+textStatus);
	});
}
function voteOnArgument(item,idArg,value){
	var requestArg = $.ajax({
		url: "/ajax/argumentVote",
		type: "POST",
		data: "idArg="+ idArg+"value="+ value,
		dataType: "html"
	}).done(function(data) {
		//Send the new data to the div containing the lists
		$("#argContainer").html(data);
	});

	requestArg.fail(function(jqXHR, textStatus) {
		$("#argContainer").text("Erreur lors de l'envoi d'una rgument : "+textStatus);
	});
}