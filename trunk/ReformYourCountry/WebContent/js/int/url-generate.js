$(document).ready(function() {
	$("#generate").click(function(event){
		event.preventDefault();
		var titleValue = $("#title").val();
		$("#url").val(computeUrlFragmentFromName(titleValue));
	});
});
function computeUrlFragmentFromName(nameParam) {
	 // nameParam is something like "Java & OO - Fundamentals". The urlFragment is set to "Java_OO_Fundamentals"  
	nameParam.trim();
	nameParam = nameParam.replace("é", "e");
	nameParam = nameParam.replace("è", "e");
	nameParam = nameParam.replace("ê", "e");
	nameParam = nameParam.replace("ë", "e");
	nameParam = nameParam.replace("ï", "i");
	nameParam = nameParam.replace("î", "i");
	nameParam = nameParam.replace("à", "a");
	nameParam = nameParam.replace("ç", "c");
	nameParam = nameParam.replace("ù", "u");
    nameParam = nameParam.replace(/[^A-Za-z0-9]/g, "_"); // remove all non alphanumeric values (blanks, spaces,...).
	nameParam = nameParam.replace(/___/g, "_"); //Sometimes, there is a '&' in the title. The name is compute like : Java___OO_etc. Whith this method, the name will be Java_OO_etc.
    return nameParam;
}