function sendNewArg(item,content,action,title,ispos){
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
function sendArgAfterEdit(item,idArg,title,content){
	if(content.length>0 && title.length>0){
		var requestArg = $.ajax({
			url: "/ajax/argumentedit",
			type: "POST",
			data: "content="+ content+"&idArg="+idArg+"&title="+title,
			dataType: "html"
		}).done(function(data){
			//Send the new data to the div containing the lists	
			$("#arg"+idArg).html(data);
		});

		requestArg.fail(function(jqXHR, textStatus) {
			$("#errorArg").text("Erreur lors de l'envoi d'un commentaire : "+textStatus);
		});
	}
}
function editArg(item,newid,newtitle,newcontent){
	if (idUser.length>0){
		$("#arg"+newid).html(
				"<form class=\"argumentNegForm\" action=\"\" method=\"post\" >"
				+"<input type=\"hidden\" id=\"idArg\" value=\""+newid+"\" />"
				+"<textarea id=\"titleEdit\" rows=\"1\" cols=\"15\">"+newtitle+"</textarea><br/>"
				+"<textarea id=\"contentEdit\"rows=\"5\" cols=\"15\" >"+newcontent+"</textarea><br/>"
				+"<input type=\"button\"   value=\"Sauver\" onclick=\"sendArgAfterEdit(this,"+newid+",titleEdit.value,contentEdit.value);\"></form>"	
		);
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

		notLoggedMessage(item);	 
	}
}
function sendNewComment(item, arg){
	var comment=$('#comm'+arg).val();
	console.log(comment);
	if (idUser.length>0){
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
			$("#errorArg").text("Veuillez écrire un texte avant d'envoyer votre commentaire.");
		}
	}else{

		notLoggedMessage(item);	 
	}
}


function showText(item, addcom){
	if (idUser.length>0){
	$("#addcom"+addcom).hide();
	$("#addcom"+addcom).click(function(){
		$("#addcom"+addcom).hide();
		$("#commentArea"+addcom).show();
	});	
	}else{
		notLoggedMessage(item);
		} 
	
}

function notLoggedMessage(item){
	alert("T'es pas loggé coco!");
	$(item).CreateBubblePopup({ 
		innerHtmlStyle: {  // give css property to the inner div of the popup	    	   
			'opacity':0.9
		},
		tail: {align:'center', hidden: false},
		selectable :true,				    	
		innerHtml: 'Pour commenter veuillez vous logger: '
			+'<a class="login" style="cursor:pointer;" href="/login">Connexion</a>'
});
}
function maxlength_textarea(item,id, max, min)
{
	var txtarea = document.getElementById('comm'+id);
	var button = document.getElementById('sendArgComm'+id);
	var crreste = document.getElementById('nbrCaract'+id);
	var length = txtarea.value.length;
	if(length < 10){
		var len = txtarea.value.length;
		var mini = min-len;
		crreste.innerHTML="Vous devez encore entrer " +mini+ " caractères";
		button.setAttribute("disabled", "disabled");
	}
	else{
		maximum = max-length;
		var len = txtarea.value.length;
		if(len>max)
		{
			txtarea.value=txtarea.value.substr(0,max);
		}
		len = txtarea.value.length;
		var maxi = max-len;
		crreste.innerHTML = maxi+ " caractères restant";	
		button.removeAttribute("disabled");
	}
}

$(function(){
	$('#hideArgArea').click(function(){
		$('#hideArgArea').hide();
		$('#showArgArea').show();
	});
});
