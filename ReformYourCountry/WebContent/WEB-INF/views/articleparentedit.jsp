﻿<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c' %>
<%@ taglib uri='/WEB-INF/tags/ryc.tld' prefix='ryc'%>
<%@ taglib uri="http://www.springframework.org/tags/form"  prefix="form"%>
<%@ taglib tagdir="/WEB-INF/tags/ryctag/" prefix="ryctag" %>
<html>
<head>
<style type="text/css">
.li{
list-style-type:none; 
}
</style>

</head>
<body>
<ryctag:pageheadertitle title="Choisir le parent de l'article">
<c:forEach items="${parentsPath}" var="subarticle">
 		<c:if test="${article.title != subarticle.title}">
			<ryctag:breadcrumbelement label="${subarticle.title}" link="/article/${subarticle.url}" />
		</c:if>
	</c:forEach>
	<ryctag:breadcrumbelement label="${article.title} - Edition du parent" />
 </ryctag:pageheadertitle>
	<ryctag:form action="/article/parenteditsubmit" modelAttribute="article">
		<form:hidden path="id" />
		${article.title}
		<tr>
			<td>Parent:</td>
			<td><ryc:articlesTree radio="true" /></td>
			<td><input type="submit" value="Sauver" /></td>
		</tr>
	</ryctag:form>
</body>
</html>