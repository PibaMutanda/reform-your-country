<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib tagdir="/WEB-INF/tags/ryctag/" prefix="ryctag" %>
<html>
<head>

</head>
<body>

	<h1>
		<c:choose>
			<c:when test="${book.id != null}">Editer un livre</c:when>
			<c:otherwise>Créer un livre</c:otherwise>
		</c:choose>
	</h1>

	<ryctag:form action="bookeditsubmit" modelAttribute="book">
		<ryctag:input path="abrev" label="Abréviation du livre:"/>
		<ryctag:input path="title" label="Titre du livre:"/>
		<ryctag:input path="description" label="Description du livre:"/>
		<ryctag:input path="author" label="Auteur(s) du livre:"/>
		<ryctag:input path="pubYear" label="Année de publication:"/>
		<ryctag:input path="externalUrl" label="Lien de référence:"/>
		<tr>
			<td>Top book?</td>
			<td><form:radiobutton path="top" value="true" />Top
				<form:radiobutton path="top" value="false" />Other</td>
		<tr/>
		<form:hidden path="id" value="${book.id}"/>
		<tr><td><input type="submit" value="<c:choose><c:when test="${book.id != null}">Sauver</c:when><c:otherwise>Créer</c:otherwise></c:choose>" /></td></tr>
	</ryctag:form>
</body>
</html>

