$(document).ready(function() {
	$("#generate").click(function(event){
		event.preventDefault();
		var titleValue = $("#title").val();
		$("#url").val(computeUrlFragmentFromName(titleValue));
	});
});
function computeUrlFragmentFromName(nameParam) {
	 // nameParam is something like "Java & OO - Fundamentals". The urlFragment is set to "Java_OO_Fundamentals"  
	nameParam = nameParam.replace(/[^A-Za-z\u00E0-\u00FC0-9]/g, "-"); // remove all non alphanumeric values (blanks, spaces,...).
	nameParam = nameParam.replace(/---/g, "-"); //Sometimes, there is a '&' in the title. The name is compute like : Java___OO_etc. Whith this method, the name will be Java_OO_etc.
    return nameParam;
}