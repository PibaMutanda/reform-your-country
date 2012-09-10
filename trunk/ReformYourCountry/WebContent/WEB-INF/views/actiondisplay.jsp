<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri='/WEB-INF/tags/ryc.tld' prefix='ryc'%>
<html>
<head>
<title>ActionPage</title>
</head>
<body>
	<h1>${action.title}</h1>
	<ryc:conditionDisplay privilege="EDIT_ACTION">
		<form action="actionedit" method="get">
			<input type="hidden" value="${action.id}" name="id" />
			<input type="submit" value="Modifier action" />
		</form>
	</ryc:conditionDisplay>
	<strong>Contenu:</strong>${action.content}
	<br />
	<strong>Description brève:</strong> ${action.shortDescription}
	<br />
	<strong>Description étendue:</strong>${action.longDescription}
	<br />
	<strong>URL:</strong>
	<a href="${action.url}">${action.url}</a>
	<br />
</body>
</html>