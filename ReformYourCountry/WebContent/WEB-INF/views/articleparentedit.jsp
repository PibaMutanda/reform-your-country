﻿<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c' %>
<%@ taglib uri='/WEB-INF/tags/ryc.tld' prefix='ryc'%>
<%@ taglib uri="http://www.springframework.org/tags/form"  prefix="form"%>
<%@ taglib tagdir="/WEB-INF/tags/ryctag/" prefix="ryctag" %>
<html>
<head>
<title>Editer le parent d'un article</title>
<script type="text/javascript" src="js/int/url-generate.js"></script>
<style type="text/css">
.li{
list-style-type:none; 
}
</style>

</head>
<body>
	<h1>Choisir le parent de l'article</h1>
	<ryctag:form action="articleparenteditsubmit" modelAttribute="article" method="post">
		<form:hidden path="id" />
		<ryctag:input path="title" readonly="readonly"/>
		<tr>
			<td>Parent:</td>
			<td><ryc:articlesTree radio="true" /></td>
			<td><input type="submit" value="Sauver" /></td>
		</tr>
	</ryctag:form>
</body>
</html>