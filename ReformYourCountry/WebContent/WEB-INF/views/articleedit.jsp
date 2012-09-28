<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri='/WEB-INF/tags/ryc.tld' prefix='ryc'%>
<%@ taglib tagdir="/WEB-INF/tags/ryctag/" prefix="ryctag"%>

<head>
<title><c:choose>
		<c:when test="${article.id != null}">Editer un article</c:when>
		<c:otherwise>Créer un article</c:otherwise>
	</c:choose></title>
</head>
<body>
	<h1><c:choose>
			<c:when test="${article.id != null}">Editer un article</c:when>
			<c:otherwise>Créer un article</c:otherwise>
		</c:choose></h1>
	<ryctag:form modelAttribute="article" action="articleeditsubmit">
		<tr>
			<td><input id="save" type="submit" value="Sauver" /></td>
		</tr>
		<ryctag:input path="title" label="Titre" required="required" />
		<ryctag:input path="shortName" label="raccourci" required="required" />
		<ryctag:input path="url" label="Nom de la page de l'article" id="url" />
		<td><input type="submit" value="Générer une url" id="generate" /></td>
		<ryctag:date path="publishDate" label="Date de publication"/>
		<ryctag:checkbox path="publicView" label="Public ?"  />
		<form:hidden path="id" />
	</ryctag:form>
</body>
</html>