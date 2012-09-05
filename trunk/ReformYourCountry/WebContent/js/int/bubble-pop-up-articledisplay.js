$(document).ready(function(e) {  // do stuff when DOM is ready			
	
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
	   	
});

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