function clicked(item){
	
	if(idUser.length>0) {  // A user is logged in
	
		var voteValue =$(item).attr('id');
		var idAction=$(item).parent().children('input[name="id"]').attr('value');
		console.log(idAction+" "+voteValue);
		
		var request = $.ajax({
			url: "ajax/voteactionlist",
			type: "POST",
			data: {idAction:idAction,vote:voteValue},
			dataType: "html"
		});

		request.done(function(data) {
			
			
			$("#voteContainer").html(data);
			
		});

	
		
	}
	
	
	
}