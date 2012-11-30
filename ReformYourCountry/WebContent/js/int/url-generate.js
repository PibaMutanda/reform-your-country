$(document).ready(function() {
	$("#generate").click(function(event){
		event.preventDefault();
		var titleValue = $("input[name='title']").val();
		$("input[name='url']").val(computeUrlFragmentFromName(titleValue));
	});
});
function computeUrlFragmentFromName(nameParam) {
	 // nameParam is something like "Java & OO - Fundamentals". The urlFragment is set to "Java_OO_Fundamentals"  
	nameParam.trim();
	nameParam = nameParam.replace(/é/g, "e");
	nameParam = nameParam.replace(/è/g, "e");
	nameParam = nameParam.replace(/ê/g, "e");
	nameParam = nameParam.replace(/ë/g, "e");
	nameParam = nameParam.replace(/ï/g, "i");
	nameParam = nameParam.replace(/î/g, "i");
	nameParam = nameParam.replace(/à/g, "a");
	nameParam = nameParam.replace(/ç/g, "c");
	nameParam = nameParam.replace(/ù/g, "u");
    nameParam = nameParam.replace(/[^A-Za-z0-9]/g, "_"); // remove all non alphanumeric values (blanks, spaces,...).
	nameParam = nameParam.replace(/___/g, "_"); //Sometimes, there is a '&' in the title. The name is compute like : Java___OO_etc. Whith this method, the name will be Java_OO_etc.
    return nameParam;
}