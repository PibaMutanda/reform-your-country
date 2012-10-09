<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form"  prefix="form"%>
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
        <tr>
            <ryctag:input path="title" label="Titre" id="title" required="required" />
            <ryctag:input path="shortName" label="Raccourci" />
            <tr class="tooltip" data-tooltip="identifiant pour l'article dans les URLs">
				<td><label for="url">Fragment d'URL</label></td>
				<td><form:input path="url" required="required" id="url" type="input" cssStyle="width:100%;" /></td>
				<td><input type="submit" value="Générer une url" id="generate" /></td>
			</tr>
            <ryctag:date path="publishDate" label="Date de publication" />
            <ryctag:checkbox path="publicView" label="Public ?" />
            <form:hidden path="id" />
            <tr>
            	<td colspan="2" align="center" style="text-align: center;">
            		<input type="submit" value="<c:choose><c:when test="${article.id != null}">Sauver</c:when><c:otherwise>Créer</c:otherwise></c:choose>" />
            		<span id="saving" style="font-family: tahoma; font-size: 9px;"></span>
            	</td>
            </tr>
    </ryctag:form>
</body>
</html>