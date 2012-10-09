$(function(){  
		var helpMark = $('#helphandle'); //the "?"
		var helpText = $('#helphandle  div');
		var refrencePositionnedBlock = $('#content');

		helpMark.show();//showed only when javascript enable
		
        ///// Position the "?" help handle, and the div for the help text.
        var leftPosition = refrencePositionnedBlock.offset().left + refrencePositionnedBlock.width() + 20;
       
        helpMark.css("left", leftPosition);  // clickable "?"
        helpMark.css("top", refrencePositionnedBlock.offset().top);
       
        ///// position the help text
        helpText.css('left',  (leftPosition - helpText.width() - 40));  // -40 because of css padding
        helpText.css('height', ($(window).height() - helpText.offset().top - $(window).scrollTop() - 60) );//-60 don't know why. Top of the help div, relative to the window.
        
        
        
        helpText.hide();//hide after add css otherweise javascript cannot compute height and width of the text div
        
        helpMark.click(function(){
        	helpText.load("helptxt/edithelp.html");  // takes the content from the server.
        	helpText.slideToggle("slow");//replace functions "slideUp" and "slideDown" in a single function "slideToggle"
        });
});

