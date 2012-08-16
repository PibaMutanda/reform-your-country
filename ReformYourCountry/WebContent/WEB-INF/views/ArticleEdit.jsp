<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
     <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
     <%@taglib uri="http://www.springframework.org/tags/form"  prefix="form"%>
     
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
   
   if ( adjustedHeight > text.clientHeight ){
   console.log("je suis dans le if autre");
   text.style.height = adjustedHeight + "px";}
    
}
</script>

	<form:form modelAttribute="article" action="articleeditsubmit">
 		<form:input path="title" /> ¨<br/>
		<form:textarea path="content"  onkeyup="FitToContent(this);" style="width:100%" />
		<form:hidden path="id" /><br/>
		<input type="submit" value="Editer"/>
	</form:form>
</body>
</html>