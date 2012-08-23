<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>

	<h1>Edit a book</h1>

	<form:form modelAttribute="book" method="post" action="bookeditsubmit">

		<table>
			<tr>
				<td>Titre du livre:</td>
				<td><form:input path="title" value="${book.title}" /></td>
			</tr>
			<tr>
				<td>Description du livre:</td>
				<td><form:textarea path="description"
						value="${book.description}" /></td>
			</tr>
			<tr>
				<td>Auteur(s) du livre:</td>
				<td><form:input path="author" value="${book.author}" /></td>
			</tr>
			<tr>
				<td>Année de publication:</td>
				<td><form:input path="pubYear" value="${book.pubYear}" /></td>
			</tr>
			<tr>
				<td>Lien de référence:</td>
				<td><form:input path="externalUrl" value="${book.externalUrl}" /></td>
			</tr>
		</table>
		<input type ="hidden" name ="id" value="${book.id}"/>
		<input type="submit" value="Sauver" />


	</form:form>
</body>
</html>