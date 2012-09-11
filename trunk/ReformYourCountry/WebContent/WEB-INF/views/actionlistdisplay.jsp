<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
    <%@taglib uri="http://www.springframework.org/tags/form"  prefix="form"%>

<html>
<head>

<title>List of actions</title>
</head>
<body>
	<h1>Liste des actions</h1>
	
	<table>
		<c:forEach items="${actions}" var="action">
			<tr>
				<td>${action.title}</td>			
				<td><a href="action?id=${action.id}">Afficher les détails</a></td>
			</tr>
		</c:forEach>
	</table>
</body>
</html>