function focused(item) {
	$(item).css('width', '25px');
	$(item).css('height', '25px');
	var inner="";
	if($(item).text()==-2){
		inner= 'Totalement contre';
	}
	if($(item).text()==-1){
		inner= 'Plutôt contre';
	}
	if($(item).text()==0){
		inner= 'Pas d\'avis';
	}
	if($(item).text()==1){
		inner= 'Plutôt pour';
	}
	if($(item).text()==2){
		inner= 'Totalement pour';
	}
	$(item).CreateBubblePopup({ 
		width:100,
        innerHtmlStyle: {  // give css property to the inner div of the popup	    	   
            'opacity':0.9
        },
        tail: {align:'center', hidden: false},
        selectable :false,	
        innerHtml:inner
    }); 	 
}
function unfocused(item) {
	$(item).css('width', '20px');
	$(item).css('height', '20px');
}
function clicked(item) {
	if(idUser.length>0){

		$("#voted").text($(item).text());
		var voteValue =$(item).text();
		var request = $.ajax({
			url: "/ajax/vote",
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
	        	   +'<a class="login" style="cursor:pointer;" href="/login">Connexion</a>'
	       }); 	 

	}
}
