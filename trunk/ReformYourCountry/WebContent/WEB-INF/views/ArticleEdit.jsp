<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
     <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
     <%@taglib uri="http://www.springframework.org/tags/form"  prefix="form"%>
<head>
	<script src="/ReformYourCountry/js/jquery-1.8.0.min.js"></script>
	<script src="/ReformYourCountry/js/jquery-ui-1.8.23.custom.min.js"></script>
<script type="text/javascript">
	$(function() {
		$("#datepicker").datepicker({
			dateFormat : "yy-mm-dd" //2012-08-22 
		});
	});
</script>
</head>

<body>
    
	<h1>Edit an article</h1>
	Edition


<script type="text/javascript">
function FitToContent(id)
{
   var text = id && id.style ? id : document.getElementById(id);

   if ( !text )
      return;

   var adjustedHeight = text.clientHeight;

   adjustedHeight = Math.max(text.scrollHeight, adjustedHeight);
   
   if ( adjustedHeight > text.clientHeight){
   console.log("je suis dans le if autre");
   text.style.height = adjustedHeight + "px";}
   
   if ( adjustedHeight < text.clientHeight){
	   console.log("je suis dans le if autre");
	   text.style.height = adjustedHeight + "px";} 
}
</script>

	<form:form modelAttribute="article" action="articleeditsubmit">
		<input type="submit" value="Sauver"/>
		Title:<form:input path="title" /> Release date:<input type="text" name="releaseDateStr" id="datepicker" value="${article.releaseDate}"/>¨<br/>
 		<!-- do not erase class , cols and rows attribute of the textarea , these values are used by textarea-expander.js -->
		<form:textarea path="content" onkeyup="FitToContent(this);" style="width:100%" />
		<form:hidden path="id" /><br/>
	</form:form>
		
</body>
</html>