
$(document).ready(function(e) {  // do stuff when DOM is ready			
	
    
    // Initialization code on the book refs (the [quote] elements referring books).
    $('label[class^="bookref"],span[class^="bookref"],a[class^="bookref"]').each(function(){

        ///// 1. We extract the book abbreviation. For example, in the element <label class="bookref-Emile otherClass">, we need to extract "Emile".
 	   var classatrib = $(this).attr("class");  // Now contains "bookref-Emile otherClass"
       //console.log(classatrib);
        
       // Extracts "Emile" // TODO: change that to take from 8 to the next space or to lenght.
       var blankpos = classatrib.indexOf(" ");
       if (blankpos > 0) {
     	   end = blankpos;
       } else {
     	   end = classatrib.lenght;
       }
 	   var abrev = classatrib.substring(8, end); 
 	   //console.log(abrev);
 	   //console.log($("#book-"+abrev).text());
 	   
 	   ///// 2. We look for the book div (at the end of the html). For example, we look for <div id="book-Emile">
       var divWithBook = $("#book-"+abrev);

       ////// 3. We create the popup on this with the book inside
 	   $(this).CreateBubblePopup({ 
           width:400,
           innerHtmlStyle: {  // give css property to the inner div of the popup	    	   
               'opacity':0.9
           },
           tail: {align:'center', hidden: false},
           selectable :true,				    	
           innerHtml: divWithBook.html()
       }); 	  
 		
    });
    
    // Show the tooltip bubble with the out references (no books but a single line of text).
    $("span.quote").each(function(e) {

    	if ($(this).attr("class").indexOf("bookref") > -1) { // has a class starting with "bookref" 
    		return;  // We don't process spans needing a book tooltip. It's done in the another hover function.
    	}
    	
    	// We are the span in <span ...>I'm a quote</span><div>John Doe</div>. And we look for the next div (the one with John Doe).
    	var divOutLink = $(this).parent().next("div").children(); //the element just next to this.
    	//console.log("div = " + divOutLink.html());
    	//console.log("this = " + $(this).html());
    	
        ////// 3. We create the popup on this with the book inside
  	   $(this).CreateBubblePopup({ 
           innerHtmlStyle: {  // give css property to the inner div of the popup	    	   
               'opacity':0.9
           },
           tail: {align:'center', hidden: false},
           selectable :true,				    	
           innerHtml: divOutLink.html()
       });
    });
    
});


