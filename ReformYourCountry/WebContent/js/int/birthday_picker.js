$(document).ready(function(e) {
	
	    /////// Script to constraint the date select boxes (month, day) tobe logical (i.e., in april, there are only 30 days).
	
		$('#birthMonth,#birthYear').change(function () {
			var year = $('#birthYear').val();
			var month = $('#birthMonth').val();
			if ((year != 0) &&  (month!=0)) {
				var lastday = 32 - new Date(year, month - 1, 32).getDate();
				var selected_day = $('#birthDay').val();

				// Change selected day if it is greater than the number of days in current month
				if (selected_day > lastday) {
					$('#birthDay  > option[value=' + selected_day + ']').attr('selected', false);
					$('#birthDay  > option[value=' + lastday + ']').attr('selected', true);
				}

				// Remove possibly offending days
				for (var i = lastday + 1; i < 32; i++) {
					$('#birthDay  > option[value=' + i + ']').remove();  
				}

				// Add possibly missing days
				for (var i = 29; i < lastday + 1; i++) {
					if (!$('#birthDay  > option[value=' + i + ']').length) {
						$('#birthDay').append($("<option></option>").attr("value",i).text(i));
					} 
				}
			}
		});
		
		var year = $('#birthYear').val();
		var month = $('#birthMonth').val();
		if ((year != 0) &&  (month!=0)) {
			var lastday = 32 - new Date(year, month - 1, 32).getDate();
			var selected_day = $('#birthDay').val();

			// Change selected day if it is greater than the number of days in current month
			if (selected_day > lastday) {
				$('#birthDay  > option[value=' + selected_day + ']').attr('selected', false);
				$('#birthDay  > option[value=' + lastday + ']').attr('selected', true);
			}

			// Remove possibly offending days
			for (var i = lastday + 1; i < 32; i++) {
				$('#birthDay  > option[value=' + i + ']').remove();  
			}

			// Add possibly missing days
			for (var i = 29; i < lastday + 1; i++) {
				if (!$('#birthDay  > option[value=' + i + ']').length) {
					$('#birthDay').append($("<option></option>").attr("value",i).text(i));
				} 
			}
		}
}


);
