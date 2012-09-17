	$(function() {
		$("#datepicker").datepicker({
			dateFormat : "yy-mm-dd", //2012-08-22 
			onClose: function(){ $("#datepicker").focus();} 
		});
	});
