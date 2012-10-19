<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
    <%@taglib uri="http://www.springframework.org/tags/form"  prefix="form"%>
	<%@ taglib uri='/WEB-INF/tags/ryc.tld' prefix='ryc'%>
	 <%@ taglib tagdir="/WEB-INF/tags/ryctag/" prefix="ryctag" %>
<html>
<head>
</head>
<body>

<!-- <h1>Page pour uploader une image d'un utilisateur</h1> -->
<ryctag:pageheadertitle title="Ajouter une image">
	<ryctag:breadcrumbelement label="${user.firstName} ${user.lastName}" link="/user/${user.userName}" />
	<ryctag:breadcrumbelement label="Ajouter une image" />
</ryctag:pageheadertitle>

	<!-- If there is a error message, show it! -->
	<c:choose>
		<c:when test="${!empty errorMsg}">
			<div class="error">Error:${errorMsg}</div>
		</c:when>
	</c:choose>

	(l'image doit faire moins de 1,5Mo)
	<form method="post" action="/user/imageadd" enctype="multipart/form-data">
		<input type="file" name="file" /><br>
		<input type="hidden" name="id" value="${user.id}" />
		<input type="submit" value="Ajouter" />
		<a href="/user/${user.userName}">Annuler</a><br>
	</form>
	
	<form method="post" action="/user/imagedelete">
		<input type="hidden" name="id" value="${user.id}" />
		<input type="submit" value="Supprimer" />
	</form>
</body>
</html>