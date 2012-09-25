$(function(){  // on document ready
	///// Position the "?" help handle, and the div for the help text.
	var bodyTemplate = $('.body-template');
	var rightEdge = bodyTemplate.offset().left + bodyTemplate.width() + 20;
	$('#helphandle').css('left',  rightEdge);  // clickable "?"
	
	var leftPosOfHelpText = rightEdge-$('#helptext').width();
	$('#helptext').css('left',  leftPosOfHelpText);  // large div with the help text.
	
    /// Computes the height of the help text to go to the bottom border of the browser.
	var helpTextTop = $('#helptext').offset().top - $(window).scrollTop();  // Top of the help div, relative to the window.
	$('#helptext').css('height', $(window).height() - helpTextTop);
	
	////// Hide the help text and show on click.
	$('#helptext').hide();
	$("#click", "body").click(function(){
	   $('#helptext').load("helptxt/edithelp.html");  // takes the content from the server.
    	$("#helptext").slideToggle("slow");//replace functions "slideUp" and "slideDown" in a single function "slideToggle"
    });	
});