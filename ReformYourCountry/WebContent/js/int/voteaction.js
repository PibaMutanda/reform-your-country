function focused(item) {
	$(item).css('width', '25px');
	$(item).css('height', '25px');
}
function unfocused(item) {
	$(item).css('width', '20px');
	$(item).css('height', '20px');
}
function clicked(item) {

	if(idUser.length>0){

		$("#voted").text($(item).attr('value'));
		var voteValue =$(item).attr('value');
		var request = $.ajax({
			url: "ajax/vote",
			type: "POST",
			data: "vote="+ voteValue+"&action="+$("#id").attr('value')+"&idVote="+idVote,
			dataType: "html"
		});

		request.done(function(data) {
			$("#voteContainer").html(data);
		});

		request.fail(function(jqXHR, textStatus) {
			$("#voted").text("Erreur lors du vote : "+textStatus);
		});
	}else{
		
	     
		$(item).CreateBubblePopup({ 
	           innerHtmlStyle: {  // give css property to the inner div of the popup	    	   
	               'opacity':0.9
	           },
	           tail: {align:'center', hidden: false},
	           selectable :true,				    	
	           innerHtml: 'Pour voter veuillez vous logger : '
	        	   +'<script src="js/int/login.js"></script>'
	        	   +'<a class="login" style="cursor:pointer;">Connexion</a>'
	       }); 	 

	}
}
