function sendNewArgument(item,content,action,title,ispos){
	if(title!="" && content!=""){
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
	}else{
		$("#errorArg").text("Erreur lors de l'envoi d'un argument : Veuillez remplir tout les champs !");
	}
}
function voteOnArgument(item,idArg,value){
	if (idUser.length>0){
		var requestArg = $.ajax({
			url: "/ajax/argumentvote",
			type: "POST",
			data: "idArg="+ idArg+"&value="+ value,
			dataType: "html"
		}).done(function(data) {
			//Send the new data to the div containing the lists
			$("#arg"+idArg).html(data);
		});
	
		requestArg.fail(function(jqXHR, textStatus) {
			$("#errorArg").text("Erreur lors de l'envoi d'un argument : "+textStatus);
		});
	}else{
		$(item).CreateBubblePopup({ 
	           innerHtmlStyle: {  // give css property to the inner div of the popup	    	   
	               'opacity':0.9
	           },
	           tail: {align:'center', hidden: false},
	           selectable :true,				    	
	           innerHtml: 'Pour voter veuillez vous logger : '
	        	   +'<a class="login" style="cursor:pointer;" href="/login">Connexion</a>'
	       }); 	 
	}
}
function sendNewComment(item, comment, arg){
	if(comment!=""){
		var requestArg = $.ajax({
			url: "/ajax/commentAdd",
			type: "POST",
			data: "comment="+ comment+"&arg="+arg,
			dataType: "html"
		}).done(function(data){
			//Send the new data to the div containing the lists
			$("#comment"+arg).html(data);
		});

		requestArg.fail(function(jqXHR, textStatus) {
			$("#errorArg").text("Erreur lors de l'envoi d'un commentaire : "+textStatus);
		});
		}else{
			$("#errorArg").text("Erreur lors de l'envoi d'un commentaire : Veuillez remplir tout les champs !");
		}
	}