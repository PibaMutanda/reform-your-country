<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
     <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
     <%@taglib uri="http://www.springframework.org/tags/form"  prefix="form"%>
     
     <body>
    
	<h1>Edit an article</h1>
	Edition

<script src = "js/jquery-1.3.2.min.js"></script>
<script src = "js/jquery.textarea-expander.js"></script>
<script type="text/javascript">

</script>

	<form:form modelAttribute="article" action="articleeditsubmit">
		<input type="submit" value="Sauver"/>
 		<form:input path="title" /> ¨<br/>
 		<!-- do not erase class , cols and rows attribute of the textarea , these values are used by textarea-expander.js -->
		<form:textarea path="content" class="expand" cols="60" rows="3" style="width:100%" />
		<form:hidden path="id" /><br/>
	</form:form>
</body>
</html>