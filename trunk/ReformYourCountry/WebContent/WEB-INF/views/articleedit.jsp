<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
     <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
     <%@taglib uri="http://www.springframework.org/tags/form"  prefix="form"%>
<head>
	<script src="/ReformYourCountry/js/jquery-1.8.0.min.js"></script>
	<script src="/ReformYourCountry/js/jquery-ui-1.8.23.custom.min.js"></script>
    <script src="<c:url value="js/jquery.textarea-expander.js" />"></script>
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

	<form:form modelAttribute="article" action="articleeditcancel">
	<input type="submit" value="Annuler"/>
	<form:hidden path="id" />
	</form:form>
	
	<form:form modelAttribute="article" action="articleeditsubmit">
		<input type="submit" value="Sauver"/>
		Title:<form:input type="text" path="title" />
		Publish date:<input type="text" name="publishDateStr" id="datepicker" value="${article.publishDate}"/><br/>
 		<!-- do not erase class , cols and rows attribute of the textarea , these values are used by textarea-expander.js -->
		<form:textarea path="content" class="expand" cols="60" rows="3" style="width:100%" />
		<form:hidden path="id" /><br/>
	</form:form>
</body>
</html>