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
	<form action="userbynamedisplay" method="GET">
		<input type="text" name="username"/>
		<input type="submit" value="rechercher"/>
	</form>
	<c:forEach items="${userlist}" var="user">
	Username: ${user.userName} <br/>
	Firstname: ${user.firstName}<br/>
	Lastname : ${user.lastName}<br/><br/>
		
	</c:forEach>
</body>
</html>