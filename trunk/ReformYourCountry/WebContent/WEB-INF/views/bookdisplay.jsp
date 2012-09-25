<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c'%>
<%@ taglib uri='/WEB-INF/tags/ryc.tld' prefix='ryc'%>
<%@ taglib tagdir="/WEB-INF/tags/ryctag/" prefix="ryctag"%>
<html>
<head>
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
		<form method="post" action="removebook">
			<input type="hidden" name="id" value="${book.id}" />  <br><input type="submit" value="Supprimer un livre" />
		</form>
	</ryc:conditionDisplay>
	<br><a href="booklist">Retour à la liste de livres</a>
</body>
</html>