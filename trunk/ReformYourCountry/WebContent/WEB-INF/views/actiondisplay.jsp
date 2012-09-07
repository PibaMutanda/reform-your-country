<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<html>
<head>
<title>ActionPage</title>
</head>
<body>

<h1>${action.title}</h1>

	<form action="actionedit" modelAttribute="action" method="get">
		<input type="hidden" value="${action.id}" name="id" />
		<input type="submit" value="Modifier action" />
	</form>

	Contenu : ${action.content}<br/>
	Description brève : ${action.shortDescription}<br/>
	Description étendue : ${action.longDescription}<br/>
	URL : ${action.url}<br/>

</body>
</html>