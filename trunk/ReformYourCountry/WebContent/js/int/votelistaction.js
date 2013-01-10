function clicked(item) {  // A user is voting.
	 if (showMessageIfNotLogged(item)) {
		 return;
	 };
	 
	 // A user is logged in
		var voteValue =$(item).attr('id');
		var idAction=$(item).parent().children('input[name="id"]').attr('value');
//		console.log(idAction+" "+voteValue);
		
		var request = $.ajax({
			url: "ajax/voteactionlist",
			type: "POST",
			data: {idAction:idAction, vote:voteValue},
			dataType: "html"
		});

		request.done(function(data) {
			// Reload the widget
			$(item).parent().parent().html(data);
		});
	
}