<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib tagdir="/WEB-INF/tags/ryctag/" prefix="ryctag" %>
<html>
<head>
<script type="text/javascript" src="js/int/url-generate.js"></script>
</head>
<body>

<c:choose>
	<c:when test="${book.id != null}">
		 <ryctag:pageheadertitle title="Editer un livre">
			<ryctag:breadcrumbelement label="${book.title}" />
		 </ryctag:pageheadertitle>
	</c:when>
	<c:otherwise> 
		<ryctag:pageheadertitle title="Créer un livre"/>
	</c:otherwise>
</c:choose>


	<ryctag:form action="book/editsubmit" modelAttribute="book" width="800px">
		<ryctag:input path="abrev" label="Abréviation du livre:"/>
		<ryctag:input path="title" label="Titre du livre:" id="title"/>
		<tr class="tooltip" data-tooltip="identifiant pour l'article dans les URLs">
			<td><label for="url">fragment d'URL</label></td>
			<td><form:input path="url" required="true" id="url" type="input" cssStyle="width:100%;" /></td>
			<td><input type="submit" value="Générer une url" id="generate" /></td>
		</tr>
		
		<ryctag:textarea path="description" label="Description du livre:"/>
		<ryctag:input path="author" label="Auteur(s) du livre:"/>
		<ryctag:input path="pubYear" label="Année de publication:"/>
		<ryctag:input path="externalUrl" label="Lien de référence:"/>
		<tr>
			<td>Top book?</td>
			<td><form:radiobutton path="top" value="true" />Top
				<form:radiobutton path="top" value="false" />Other</td>
		<tr/>
		<form:hidden path="id" value="${book.id}"/>
		<tr><td colspan="2" align="center" style="text-align: center;"><input type="submit" value="<c:choose><c:when test="${book.id != null}">Sauver</c:when><c:otherwise>Créer</c:otherwise></c:choose>" /></td></tr>
	</ryctag:form>
</body>
</html>

