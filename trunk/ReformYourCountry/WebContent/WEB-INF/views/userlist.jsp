<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
    <%@taglib uri="http://www.springframework.org/tags/form"  prefix="form"%>

<html>
<head>

<title>List of users</title>
</head>
<body>
	${errorMsg}
	<h1>Liste de tous les users</h1>
	<form action="userlist" method="GET">
		prénom, nom ou pseudo <input type="text" name="name" /> <input
			type="submit" value="rechercher" />
	</form>
	<table>
		<c:forEach items="${userList}" var="user">
			<tr>
				<td>${user.userName}</td>
				<td>${user.firstName}</td>
				<td>${user.lastName}</td>
				<td><a href="user?id=${user.id}">Affichers les détails</a></td>
			</tr>
		</c:forEach>
	</table>
</body>
</html>