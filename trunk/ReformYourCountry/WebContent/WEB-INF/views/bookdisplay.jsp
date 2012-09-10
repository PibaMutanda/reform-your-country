<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c'%>
<%@ taglib uri='/WEB-INF/tags/ryc.tld' prefix='ryc'%>
<%@ taglib tagdir="/WEB-INF/tags/ryctag/" prefix="ryctag"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>${book.title}</title>
</head>
<body>
	<ryc:bookInfo book="${book}" />
	<br>

	<c:choose>
		<c:when test="${!empty errorMsg}">
			<div class="error">Error:${errorMsg}</div>
		</c:when>
	</c:choose>
	
	<ryc:conditionDisplay privilege="EDIT_BOOK">
	<ryctag:submit entity="${book}" value="Editer" action="bookedit"/>	
		<form method="post" action="bookimageadd" enctype="multipart/form-data">
			<input type="file" name="file" />
			<input type="hidden" name="id" value="${book.id}" /><input type="submit" value="Uploader une image" />
		</form>
		<form method="post" action="bookimagedelete">
			<input type="hidden" name="id" value="${book.id}" />  <br><input type="submit" value="Supprimer une image" />
		</form>
	</ryc:conditionDisplay>
</body>
</html>