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
		<tr />
		<form:hidden path="id" value="${book.id}"/>
		<input type="submit" value="
				<c:choose>
					<c:when test="${book.id != null}">Sauver</c:when>
					<c:otherwise>Créer</c:otherwise>
				</c:choose>
			" />
	</ryctag:form>

	<%--<form:form modelAttribute="book" method="post" action="bookeditsubmit">
		<table>
			<tr>
				<td>Abréviation du livre:</td>
				<td>
					<form:input path="abrev" /> 
					<form:errors path="abrev" cssClass="error" />
				</td>
			</tr>
			<tr>
				<td>Titre du livre:</td>
				<td><form:input path="title" /></td>
			</tr>
			<tr>
				<td>Description du livre:</td>
				<td><form:textarea path="description"/></td>
			</tr>
			<tr>
				<td>Auteur(s) du livre:</td>
				<td><form:input path="author"/></td>
			</tr>
			<tr>
				<td>Année de publication:</td>
				<td><form:input path="pubYear"/></td>
			</tr>
			<tr>
				<td>Lien de référence:</td>
				<td><form:input path="externalUrl"/></td>
			</tr>
			<tr>
				<td>Top book?</td>
				<td><form:radiobutton path="top" value="true" />Top
				    <form:radiobutton path="top" value="false" />Other</td>
			<tr />
		</table>
		<input type="hidden" name="id" value="${book.id}" />
		<input type="submit" value="
				<c:choose>
					<c:when test="${book.id != null}">Sauver</c:when>
					<c:otherwise>Créer</c:otherwise>
				</c:choose>
			" />
			
	</form:form>--%>

</body>
</html>

