<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c'%>
<%@ taglib uri='/WEB-INF/tags/ryc.tld' prefix='ryc'%>
<%@ taglib tagdir="/WEB-INF/tags/ryctag/" prefix="ryctag"%>
<html>
<head>
<link rel="canonical" href="http://enseignement2.be/book/${book.url}"/>
<meta name="robots" content="index, follow"/>
</head>
<body>
<ryctag:pageheadertitle title="${book.title}"/>
	<ryc:bookInfo book="${book}" />
	<ryc:conditionDisplay privilege="EDIT_BOOK">
	<ryctag:submit entity="${book}" value="Editer" action="book/edit"/>	
		<form method="post" action="book/imageadd" enctype="multipart/form-data">
			<input type="file" name="file" />
			<input type="hidden" name="id" value="${book.id}" /><input type="submit" value="Uploader une image" />
		</form>
		<form method="post" action="book/imagedelete">
			<input type="hidden" name="id" value="${book.id}" />  <br><input type="submit" value="Supprimer une image" />
		</form>
		<form method="post" action="book/remove">
			<input type="hidden" name="id" value="${book.id}" />  <br><input type="submit" value="Supprimer un livre" />
		</form>
	</ryc:conditionDisplay>
	<br><a href="book">Retour à la liste de livres</a>
</body>
</html>