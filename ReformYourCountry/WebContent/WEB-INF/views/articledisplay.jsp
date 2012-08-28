<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c' %>
<%@ taglib uri='/WEB-INF/tags/ryc.tld' prefix='ryc'%>
<html>
<head>

<style type="text/css">

.ui-widget-header {
border: none;
background: none;
color: none;

}
.ui-widget {
font-family: Verdana,Arial,sans-serif/*{ffDefault}*/;
font-size: 0.9em/*{fsDefault}*/;
}

.ui-dialog-title {
float: left;
margin: .1em 2px .1em 0;
height:0px;
}

.book{
font-style:italic

}

.bookdialog{
display:none;

}
img
{
float:right;
}

</style>
<script type="text/javascript" src="js/jquery-1.8.0.min.js"></script>
<script type="text/javascript" src="js/jquery-ui-1.8.23.custom.min.js"></script>
<script type="text/javascript">
		
		$(document).ready(function(e) {
			   // do stuff when DOM is ready
	
	
			// Dialog
		var x = $(".body-template").width() + $(".menu-template").width()+15;
			$('.dialog').dialog({
				autoOpen: false,
				width: 600, 
				closeOnEscape: true,
				show: "slide", 
				hide:"slide",
				position : [x,e.pageY]
			
				
			});
			
	    	
	   
	
			   
	    $(".bibref-after-block").hover(function(e){
	    	
	    	  //  var abrev = $(this).parent().children("input").attr("value");
	    	  
	    	   var div = $(this).children(".bookdialog").html();
	    	    console.log(div);
	    	 /*   var request = $.ajax({
	    	    	  url: "ajax/popbook",
	    	    	  type: "POST",
	    	    	  data: {abrev : abrev},
	    	    	  dataType: "html"
	    	    	});

	    	    	request.done(function(msg) {*/
	    	    	
	    	    	$('.dialog').empty();
	    
	    	    	
	  	    	    $('.dialog').html(div);
	  	        	$('.dialog').dialog('open');
	  	        	
	  	        	x = $(".body-template").width() + $(".menu-template").width()+15;
	  				$('.dialog').dialog('option','position',[x,e.pageY]);
	  				
	  		
	  				
	    	    	//});

	    	    	/*request.fail(function(jqXHR, textStatus) {
	    	    	  alert( "Request failed: " + textStatus );
	    	    	  
	    	    	});*/
	    	    
	    
				return false;
			},function(){
				
				setTimeout(function(){
					
					$('.dialog').dialog('close');
				},5000);
					
			
				
				
			});	  
	
	 });
		
/*

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
</script>

<title>${article.title}</title>
</head>

<body>
	<!-- BREADCRUMB -->
	<h2>
		<c:forEach items="${parentsPath}" var="article">
			<a href="article?id=${article.id}">${article.title}></a>
		</c:forEach>
	</h2>

	<h2>${article.title}</h2>
	release date: ${article.releaseDate}<br/>

	<ryc:conditionDisplay privilege="EDIT_ARTICLE">
		<form action=articleedit method="GET">
			<input type="hidden" name="id" value="${article.id}"/> 
			<input	type="submit" value="Editer" />
		</form>
		<form action="articleparentedit" method="GET">
		<input type="hidden" name="id" value="${article.id}" /> <input type="submit"
			value="Editer parent" />
		</form>
	</ryc:conditionDisplay>

	
    <!-- COUNT DOWN -->
	<hr/>
	<c:if test="${!article.published}">
		<script type="text/javascript">
			$(function () {
					var publishDay = new Date();
					publishDay = new Date(${publishYear}, ${publishMonth}, ${publishDay});
					function reload() { 
						window.location.reload(); 
					} 
					$('#defaultCountdown').countdown({until: publishDay, onExpiry:reload, format: 'dHMS',layout: ' {dn} {dl} , {hn} {hl} , {mn} {ml} and {sn} {sl} until the article is published   <<<<<<<<<< DESIGNER, PLEASE IMPROVE (discret si droit de voir le texte, en grand sinon)'});
			});
		</script>
		<div id="defaultCountdown"></div>
	</c:if>
	
	<hr/>
	
	<!-- ARTICLE CONTENT -->
	<c:if test="${showContent}">
		${articleContent}
	</c:if>
	

</body>
</html>   

