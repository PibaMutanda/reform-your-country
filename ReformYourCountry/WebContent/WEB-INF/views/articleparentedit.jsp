<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c' %>
<%@ taglib uri='/WEB-INF/tags/ryc.tld' prefix='ryc'%>
<%@ taglib uri="http://www.springframework.org/tags/form"  prefix="form"%>
<%@ taglib tagdir="/WEB-INF/tags/ryctag/" prefix="ryctag" %>
<html>
<head>

<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title><c:choose>
			<c:when test="${article.id != null}">Editer le parent d'un article</c:when>
			<c:otherwise>Créer un article</c:otherwise>
		</c:choose></title>
<script type="text/javascript" src="js/int/url-generate.js"></script>
<style type="text/css">
.li{
list-style-type:none; 
}

</style>

</head>
<body>
	<h1><c:choose>
			<c:when test="${article.id != null}">Editer le parent d'un article</c:when>
			<c:otherwise>Créer un article</c:otherwise>
		</c:choose></h1>
		<ryctag:form action="articleparenteditsubmit" modelAttribute="article" method="post">
			<ryctag:input path="title" label="Titre" id="title"/>
			<ryctag:input path="url" label="Nom de la page de l'article" id="url"/>
			<td><input type="submit" value="Générer une url" id="generate"/></td>
			<form:hidden path="id" value="${article.id}"/>
			<input type="submit" value="<c:choose><c:when test="${article.id !=null}">Sauver</c:when><c:otherwise>Cr�er article</c:otherwise></c:choose>"/>
		<tr>
		<td>Parent: </td>
		<td><ryc:articlesTree radio="true" /></td></tr>
		</ryctag:form>
	<%--<form:form modelAttribute="article" method="post" action="articleparenteditsubmit">
		Title: <input type="text" name="title" value="${article.title}"/>

		<input type="hidden" name="id" value="${article.id}"/>
		
		<input type="submit" value="<c:choose><c:when test="${article.id !=null}">Sauver</c:when>
		<c:otherwise>Cr�er article</c:otherwise></c:choose>"/>
		
		<br/><hr/>
		Parent: <br />
		<ryc:articlesTree radio="true" />
	</form:form> --%>

</body>
</html>