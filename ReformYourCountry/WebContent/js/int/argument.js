function sendNewArg(item,ispos){
	if( $("#contentArg"+ispos).val()!="" && $("#titleArg"+ispos).val()!=""){
		
		var requestArg = $.post("/ajax/argumentAdd",$("#form"+ispos).serialize()).done(function(data) {
	                	$("#argContainer").html(data);
            		});
	        			//Send the new data to the div containing the lists
	        			
//		var requestArg = $.ajax({
//			url: "/ajax/argumentAdd",
//			type: "POST",
//			data: "content="+ $("#comment"+ispos).val()+"&action="+$("#action"+ispos).val()+"&title="+$("#title"+ispos).val()+"&ispos="+ispos,
//			dataType: "html"
//		}).done(function(data) {
//			//Send the new data to the div containing the lists
//			$("#argContainer").html(data);
//		});

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
				+"<form class=\"argumentNegForm\" action=\"\" method=\"post\" >"
				+"<div style='border:3px inset; background-color: #e2e2e2;'>"
				+" Titre :<input type=\"hidden\" id=\"idArg\" value=\""+newid+"\" />"
				+"<textarea id=\"titleEdit"+newid+"\" rows=\"1\" cols=\"15\">"+newtitle+"</textarea><br/>"
				+"<textarea id=\"contentEdit"+newid+"\"rows=\"5\" cols=\"15\" >"+newcontent+"</textarea><br/>"
				+"<div  onclick=\"sendArgAfterEdit(this,"+newid+");\">Sauver</div></div></form>"	
		);

		$('#contentEdit').ckeditor();
		//$('#showArgArea'+id).parent().css('background-color','#e2e2e2');
	}else{

		notLoggedMessage(item);	 
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
function hideHelp(item){
	$(item).hide("slow");
}
function showArea(id){
    var pos = $('#hideArgArea'+id).position();
	$('#help'+id).css({"position": "absolute","top": pos.top-$('#help'+id).outerHeight(true)-5 + "px", "z-index": 1,});

	$('#help'+id).show("slide", {direction: 'down'},"slow");
	$('#hideArgArea'+id).hide();
	$('#showArgArea'+id).show();
	$('#contentArg'+id).ckeditor();
	$('#showArgArea'+id).parent().css('background-color','#e2e2e2');
}

