<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<script type="text/javascript"
	src="js/ext/jquery-ui-1.8.23.custom.min.js"></script>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
	<script type="text/javascript">
		function focused(item) {
			
			$("#valuetext").text($(item).attr('id'));
			$(item).css('width', '25px');
			$(item).css('height', '25px');
			
		}
		function unfocused(item) {
			if ($("#valuetext").attr('value')=='0'){
				$("#valuetext").text("");
		    }
			$(item).css('width', '20px');
			$(item).css('height', '20px');
		}
		function clicked(item) {
			$("#voted").text($(item).attr('value'));
			
			var voteValue =$(item).attr('value');
    	    
    	    var request = $.ajax({
    	    	  url: "vote",
    	    	  type: "POST",
    	    	  data: "vote="+ voteValue+"&user="+"1"+"&article="+"323",
    	    	  dataType: "html"
    	    	});

    	    	request.done(function(msg) {
    	        $("#valuetext").text($(item).attr('id'));
    	        $("#voted").text("Vous avez voté : "+$("#valuetext").text());
    	    	});

    	    	request.fail(function(jqXHR, textStatus) {
    	    		$("#valuetext").text($(item).attr('id'));
        	        $("#voted").text("Erreur lors du vote : "+textStatus);
    	    	});
		}
	</script>
	<div id="valuetext" value="0"
		style="text-align: center; width: 400px; height: 20px;"></div>
	<div id="voteContent"
		style=" width: 400px; margin-left: 150px;">
		<div id="Pas du tout d'accord" value="1" onmouseout="unfocused(this);"
			onmouseover="focused(this);" onclick="clicked(this);"
			style="display: inline-block; width: 20px; height: 20px; background-color: #ff0000; text-align: center;">
			1</div>
		<div id="Pas d'accord mais..." value="2" onmouseout="unfocused(this);"
			onmouseover="focused(this);" onclick="clicked(this);"
			style="display: inline-block; width: 20px; height: 20px; background-color: #ff8800; text-align: center;">
			2</div>
		<div id="Moyennement d'accord" value="3" onmouseout="unfocused(this);"
			onmouseover="focused(this);" onclick="clicked(this);"
			style="display: inline-block; width: 20px; height: 20px; background-color: #ffff00; text-align: center;">
			3</div>
		<div id="D'accord excepté un petit détail" value="4"
			onmouseout="unfocused(this);" onmouseover="focused(this);"
			onclick="clicked(this);"
			style="display: inline-block; width: 20px; height: 20px; background-color: #88ff00; text-align: center;">
			4</div>
		<div id="C'est exactement mon avis" value="5"
			onmouseout="unfocused(this);" onmouseover="focused(this);"
			onclick="clicked(this);"
			style="display: inline-block; width: 20px; height: 20px; background-color: #00ff00; text-align: center;">
			5</div>

	</div>
	<div id="voted"
		style="text-align: center; width: 400px; height: 20px;">
		vous n'avez pas encore voté</div>
</body>
</html>