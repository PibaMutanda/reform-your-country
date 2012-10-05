$(function(){  // on document ready
	
	$('#helphandle').show();	
	$('#helptext').hide();
	
	///// Position the "?" help handle, and the div for the help text.
	var posLeft = $('.main-holder').position().left+$('.main-area').width();
	
	$('#helphandle').css('left',  posLeft);  // clickable "?"
	$('#helphandle').css('top',  $('.main-holder').offset().top);  // clickable "?"
	
	$('#helptext').css('left',  posLeft-$('#helptext').width());  // large div with the help text.
	
    /// Computes the height of the help text to go to the bottom border of the browser.
	// Top of the help div, relative to the window.
	$('#helptext').css('height', $(window).height() - $('#helptext').offset().top - $(window).scrollTop());
	
	$("#click").click(function(){
	   $('#helptext').load("helptxt/edithelp.html");  // takes the content from the server.
    	$("#helptext").slideToggle("slow");//replace functions "slideUp" and "slideDown" in a single function "slideToggle"
    });	
});