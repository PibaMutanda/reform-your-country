$(document).ready(function() {
	//$(".untranslated").hide();
	var i =1;
	$(".untranslated").each(function(){
		$(this).hide();
		$(this).attr("id","untranslated"+i);
		$(this).append("<div class=\"untranslatedoption\"><a class=\"untranslatedButton\" onclick=\"javascript:showTranslated("+i+")\">VF</a></div>");
		i++;
	});
	i=1;
	$(".translated").each(function(){
		$(this).attr("id","translated"+i);
		$(this).append("<div class=\"translatedoption\"><a class=\"translatedButton\"  onclick=\"javascript:showUntranslated("+i+")\">VO</a></div>");
		i++;
	});
});

function showTranslated(id) {
	$("#untranslated"+id).hide("slide",{direction: "right"},700);
	$("#translated"+id).delay(700).show("slide",{direction: "left"},700);
}
function showUntranslated(id) {
	$("#translated"+id).hide("slide",{direction: "left"},700);
	$("#untranslated"+id).delay(700).show("slide",{direction: "right"},700);
}