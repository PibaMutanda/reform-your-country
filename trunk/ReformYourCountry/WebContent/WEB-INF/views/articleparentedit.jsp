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
	<ryctag:breadcrumb>
			<c:forEach items="${parentsPath}" var="subarticle">
			<ryctag:breadcrumbelement label="${subarticle.title}" link="/article/${subarticle.url}" />
		</c:forEach>
		<ryctag:breadcrumbelement label="Edition du parent" />
	</ryctag:breadcrumb>

	<ryctag:pageheadertitle title="${article.title}"/>

	Parent:
	<ryctag:form action="/article/parenteditsubmit" modelAttribute="article">
		<tr>
		<form:hidden path="id"/>
		
			<td><ryc:articlesTree/></td>
			
		</tr>
		<tr><td><input type="submit" value="Sauver" /></td></tr>	
		</ryctag:form>
	
</body>
</html>