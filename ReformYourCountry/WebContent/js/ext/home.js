///// Used for the 3D slider (function name referred in the xml file)
function changeMe(element) {
	var tabs = new Array();
	var tabs1 = document.getElementsByTagName('a');
	for(var j=0, z=0; j<tabs1.length; j++) {
		if(tabs1[j].className == 'tabmenu') {
			tabs[z] = tabs1[j];
			z++;
		}
	}
	var el = element.href.split('#')[1];
	for(var i=0; i<tabs.length; i++) {
		for(var a=0; a<tabs.length; a++) {
			var hr = tabs[a].href.split('#')[1];
			if(hr != el) {
				var el1 = document.getElementById(hr);
				el1.style.display = 'none';
				tabs[a].parentNode.className = '';
			}
		}
	}
	document.getElementById(el).style.display = 'block';
	element.parentNode.className = 'active';
	return false;
}


////// Sets variables for the 3D slider
//add your FlashVars
var vars = { xml_location : 'CU3ER/full-width-dark.xml', width:'465', height:'311' };
// add Flash embedding parameters, etc. wmode, bgcolor...
var params = { bgcolor : '#ffffff' };
params.allowScriptAccess = "always";
// Flash object attributes id and name
var attributes = { id:'CU3ER', name:'CU3ER' };
// dynamic embed of Flash, set the location of expressInstall if needed
swfobject.embedSWF('CU3ER/CU3ER.swf', "CU3ER", 465, 311, "10.0.0", 
"js/expressinstall.swf", vars, params, attributes );
// initialize CU3ER class containing Javascript controls and events for CU3ER
var CU3ER_object = new CU3ER("CU3ER");
