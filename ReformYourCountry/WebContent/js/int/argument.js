//Will Send the value of the new Argument form to the controller 
function sendNewArg(item,ispos){
	if( $("#contentArg"+ispos).val()!="" && $("#titleArg"+ispos).val()!=""){
		
		var requestArg = $.post("/ajax/argumentAdd",$("#form"+ispos).serialize()).done(function(data) {
						//If Ok, update the list
						$(data).insertBefore("#help"+ispos);
	                	//$("#argContainer").html(data);
						hideArea(ispos);
            		});

		requestArg.fail(function(jqXHR, textStatus) {
			//If error show it in the error div
			$("#errorArg").text("Erreur lors de l'envoi d'un argument : "+textStatus);
		});
	}else{
		$("#errorArg").text("Erreur lors de l'envoi d'un argument : Veuillez remplir tout les champs !");
	}
}
//Send the value of the form
function sendArgAfterEdit(item,idArg){
	if( $("#contentEdit"+idArg).val()!="" && $("#titleEdit"+idArg).val()!=""){
		var requestArg = $.post("/ajax/argumentedit",$("#editForm"+idArg).serialize()).done(function(data) {

						destroyEditor('contentEdit'+idArg);
	                	$("#arg"+idArg).replaceWith(data);
            		});
		
		requestArg.fail(function(jqXHR, textStatus) {
			$("#errorArg").text("Erreur lors de l'envoi d'un commentaire : "+textStatus);
		});
		$('#helptrue').hide();
	}
}
//Change the Argument in a form
function editArg(item,newid){
	var title = $("#argTitle"+newid).text();
	var content = $("#argContent"+newid).html();
	if (idUser.length>0){
		$("#arg"+newid).html(
				"<form id=\"editForm"+newid+"\" class=\"argumentNegForm\" action=\"\" method=\"post\" >"
				//+"<div style='border:3px inset; background-color: #e2e2e2;'>"
				+" Titre :<input type=\"hidden\" name=\"idArg\" id=\"idArg\" value=\""+newid+"\" />"
				+"<textarea id=\"titleEdit"+newid+"\" name=\"title\" rows=\"1\" cols=\"15\">"+ $.trim(title)+"</textarea><br/>"
				+"<textarea id=\"contentEdit"+newid+"\" name=\"content\" rows=\"5\" cols=\"15\" >"+ $.trim(content)+"</textarea><br/>"
				//+"<div  onclick=\"sendArgAfterEdit(this,"+newid+");\">Sauver</div></div></form>"	
				+"<div  onclick=\"sendArgAfterEdit(this,"+newid+");\">Sauver</div></form>"	
		);

		$('#contentEdit'+newid).ckeditor(function() { /* callback code */ }, { 
	        customConfig : '/js/ext/ckeditor_config.js',
	        toolbar : 'goodExample'});

	    var pos = $('#arg'+newid).position();
		$('#helptrue').css({"position": "absolute"
							,"left":pos.left
							,"top": pos.top-$('#helpfalse').outerHeight(true)-5 + "px"
							, "z-index": 1});
		$('#helptrue').show("slide", {direction: 'down'},"slow");
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
			$("#arg"+idArg).replaceWith(data);
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
	$(item).CreateBubblePopup({ 
		innerHtmlStyle: {  // give css property to the inner div of the popup	    	   
			'opacity':0.9
		},
		tail: {align:'center', hidden: false},
		selectable :true,				    	
		innerHtml: ' '
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
	$('#help'+id).css({"position": "absolute","left":pos.left,"top": pos.top-$('#help'+id).outerHeight(true)-5 + "px", "z-index": 1});
	$('#help'+id).show("slide", {direction: 'down'},"slow");
	$('#hideArgArea'+id).hide();
	$('#showArgArea'+id).show();
	$('#contentArg'+id).ckeditor(function() { /* callback code */ }, { 
        customConfig : '/js/ext/ckeditor_config.js',
        toolbar : 'goodExample'
    });
	$('#showArgArea'+id).parent().css('background-color','#e2e2e2');
}
function hideArea(id){
	$('#help'+id).hide();
	$('#showArgArea'+id).parent().css('background-color','#ffffff');
	$('#showArgArea'+id).hide();
	destroyEditor('contentArg'+id);
	$('#hideArgArea'+id).show();
}
function destroyEditor(name){
	 var editor = CKEDITOR.instances[name];
	 
	    if (editor) { editor.destroy(true);
	    delete CKEDITOR.instances[name];
	    }
}
