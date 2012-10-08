$(document).ready(function() {
	//$(".untranslated").hide();
	var i =1;
	$(".untranslated").each(function(){
		$(this).hide();
		$(this).attr("id","untranslated"+i);
		$(this).append("<a class=\"untranslatedButton\" onclick=\"javascript:showTranslated("+i+")\">VF</a>");
		i++;
	});
	i=1;
	$(".translated").each(function(){
		$(this).attr("id","translated"+i);
		$(this).append("<a class=\"untranslatedButton\"  onclick=\"javascript:showUntranslated("+i+")\">VO</a>");
		i++;
	});
});

function showTranslated(id) {
	$("#untranslated"+id).hide("slide",{direction: "left"},1000);
	$("#translated"+id).delay(1000).show("slide",{direction: "left"},1000);
}
function showUntranslated(id) {
	$("#translated"+id).hide("slide",null,1000);
	$("#untranslated"+id).delay(1000).show("slide",null,1000);
}