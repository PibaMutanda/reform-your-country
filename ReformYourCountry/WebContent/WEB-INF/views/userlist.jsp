<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
<head>

<title>List of users</title>
</head>
<body>
	${errorMsg}
	<h1>Liste de tous les utilisateurs</h1>
	<form action="userlist" method="GET">
		prénom, nom ou pseudo <input type="text" name="name"/> 
		<input type="submit" value="rechercher" />
	</form>
	<table>
		<c:forEach items="${userList}" var="user">
			<tr>
				<td>${user.userName}</td>
				<td>${user.firstName}</td>
				<td>${user.lastName}</td>
				<td><a href="user/${user.userName}">Afficher les détails</a></td>
			</tr>
		</c:forEach>
	</table>
</body>
</html>