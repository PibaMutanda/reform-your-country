
// Adds buttons and divs to enable switching between the original and translated versions.
$(document).ready(function() {
	var untranslatedIdCounter =1;
	$(".untranslated").each(function(){
		$(this).hide();// We hide the VO
		$(this).attr("id","untranslated"+untranslatedIdCounter);//Increment the id of the Untranslated tag
		$(this).append("<div class=\"untranslatedoption\"><a class=\"untranslatedButton\" onclick=\"javascript:showTranslated("+untranslatedIdCounter+")\">VF</a></div>");//add the div with the button to change the version
		untranslatedIdCounter++;
	});
	var translatedIdCounter=1;
	$(".translated").each(function(){ // Same than VO but for VF
		$(this).attr("id","translated"+translatedIdCounter);
		$(this).append("<div class=\"translatedoption\"><a class=\"translatedButton\"  onclick=\"javascript:showUntranslated("+translatedIdCounter+")\">VO</a></div>");
		translatedIdCounter++;
	});
});

function showTranslated(id) {//We slide the two div as if they were binded to each other
	$("#untranslated"+id).hide("slide",{direction: "right"},700);//hide to the right in 700ms
	$("#translated"+id).delay(700).show("slide",{direction: "left"},700);//wait the end of the hiding before showing the VF
}
function showUntranslated(id) {
	$("#translated"+id).hide("slide",{direction: "left"},700);
	$("#untranslated"+id).delay(700).show("slide",{direction: "right"},700);
}