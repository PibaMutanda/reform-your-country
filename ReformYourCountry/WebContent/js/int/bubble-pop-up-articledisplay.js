
$(document).ready(function(e) {  // do stuff when DOM is ready			
	
    
    // Initialization code on the book refs (the [quote] elements referring books).
    $('label[class^="bookref"],span[class^="bookref"]').each(function(){

    	//for info see http://www.maxvergelli.com/jquery-bubble-popup/documentation/#engine   	
    	$(this).CreateBubblePopup({ innerHtml: 'Loading...'});
    	
    });
    	
    	
//    // Initialization code on the divs (grouped at the end of the page) containing the book info (image, titles,...) which will be injected in the tootlips
//    $(".book").each(function() {
//    	var oldStyleContent = $(this).attr("style");
//    	$(this).attr("style", oldStyleContent + " ; display:none;");  // Make the these divs at the end of the page invisible (their content will be moved by the jQuery bubble JS code, inside other divs when the mouse makes them appear).
//    });
//    
	
    // Show the tooltip bubble with a book inside when the mouse hovers a book title or reference text.
    $('label[class^="bookref"],span[class^="bookref"]').hover(function(e){  // All the labels and having a class starting with "bookref"
	   
 
       // 1. We extract the book abbreviation. For example, in the element <label class="bookref-Emile otherClass">, we need to extract "Emile".
	   var classatrib = $(this).attr("class");  // Now contains "bookref-Emile otherClass"
       console.log(classatrib);
	   // var size  = classatrib.lenght;
	   var abrev = classatrib.substring(8);  // Extracts "Emile" // TODO: change that to take from 8 to the next space or to lenght.
	   console.log($("#book-"+abrev).text());
	   
	   // 2. We look for the book div (at the end of the html). For example, we look for <div id="book-Emile">
       var divWithBook = $("#book-"+abrev).html();

       // 3. We create the popup on this with the book inside
	   $(this).ShowBubblePopup({ 
	    	              width:400,
	 				      innerHtmlStyle: {  // give css property to the inner div of the popup	    	   
				    	                       'opacity':0.9
				    	                   },
				    	   tail: {align:'center', hidden: false},
				    	   selectable :true,				    	
				    	   innerHtml: divWithBook }, true); 	  
				return false;
       },function(){});
    
    
});



/*$(document).ready(function(e) {  // do stuff when DOM is ready			
	
	 //for info see http://www.maxvergelli.com/jquery-bubble-popup/documentation/#engine   	
	 $(".bibref-after-block").CreateBubblePopup({ innerHtml: 'Loading...'});
	   
    $(".booktitle").hover(function(e){
        var div = $(this).parent().children(".bookdialog").html();
	    console.log(div);
	 				
	    $(this).ShowBubblePopup({ 
	 				      innerHtmlStyle: {  // give css property to the inner div of the popup	    	   
				    	                       'text-align':'left',
				    	                       'font-family': 'Times new roman',
				    	                       'font-size': '18px'
				    	                        },
				    	   tail: {align:'right', hidden: false},
				    	   selectable :true,				    	
				    	   innerHtml: div }, true); 	  
				return false;
        },function(){});	  
	   	
});*/

/*
A pop up example with ajax: delete when login popup finished
	 
		// Dialog
		/*	var x = $(".body-template").width() + $(".menu-template").width()+15;
				$('.dialog').dialog({
					autoOpen: false,
					width: 600, 
					closeOnEscape: true,
					show: "slide", 
					hide:"slide",
					position : [x,e.pageY]
				
					
				});
   $(".bibref-after-block").hover(function(e){
	    	
	    	    var abrev = $(this).parent().children("input").attr("value");
	    	  
	    	  
	    	    console.log(abrev);
	    	    var request = $.ajax({
	    	    	  url: "ajax/popbook",
	    	    	  type: "POST",
	    	    	  data: {abrev : abrev},
	    	    	  dataType: "html"
	    	    	});

	    	    	request.done(function(msg) {
	    	    	console.log(msg);
	    	    	$('.dialog').empty();
	    
	    	    	
	  	    	    $('.dialog').html(msg);
	  	        	$('.dialog').dialog('open');
	  	        	
	  	        	x = $(".body-template").width() + $(".menu-template").width()+15;
	  				$('.dialog').dialog('option','position',[x,e.pageY]);
	  				
	  		
	  				
	    	    	});

	    	    	request.fail(function(jqXHR, textStatus) {
	    	    	  alert( "Request failed: " + textStatus );
	    	    	  
	    	    	});
	    	    
	    
				return false;
			},function(){
				
				setTimeout(function(){
					
					$('.dialog').dialog('close');
				},5000);
					
			
				
				
			});

*/