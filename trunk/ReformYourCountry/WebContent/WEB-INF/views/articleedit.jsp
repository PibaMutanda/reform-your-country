<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri='/WEB-INF/tags/ryc.tld' prefix='ryc'%>
<%@ taglib tagdir="/WEB-INF/tags/ryctag/" prefix="ryctag"%>

<head>
    <script type="text/javascript" src="js/int/url-generate.js"></script>
</head>
<body>
	<c:choose>
		<c:when test="${article.id != null}"><ryctag:pageheadertitle title="${article.title}"/></c:when>
		<c:otherwise><ryctag:pageheadertitle title="Créer un article"/></c:otherwise>
	</c:choose>
	<ryctag:form modelAttribute="article" action="article/editsubmit">
		<tr><td><input id="save" type="submit" value="Sauver"/><span id ="saving" style="font-family:tahoma;font-size:9px;"></span></td>
        <ryctag:input path="title" label="Titre" id="title" required="required"/>
        <ryctag:input path="shortName" label="raccourci" />
        <ryctag:input path="url" label="Nom de la page de l'article" id="url" />
        <td><input type="submit" value="Générer une url" id="generate" /></td>
        <ryctag:date path="publishDate" label="Date de publication" />
        <ryctag:checkbox path="publicView" label="Public ?" />
        <form:hidden path="id" />
    </ryctag:form>
</body>
</html>