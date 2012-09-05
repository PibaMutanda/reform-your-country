<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
    <%@taglib uri="http://www.springframework.org/tags/form"  prefix="form"%>
    <%@ taglib tagdir="/WEB-INF/tags/ryctag/" prefix="ryctag" %>
    

<html>
<head>

	<title>Changer de password</title>
</head>
<body>
<h1>Formulaire de changement de password</h1>
	<form:form modelAttribute="user" action="userchangepasswordsubmit">
	<table>
		<tr><td>Password actuel: </td><td><input type="password" name="oldPassword" /></td><td>${errorNoOld}</td></tr>
		<tr><td>Nouveau password: </td><td><input type="password" name="newPassword" /></td><td>${errorEmpty}${errorDiff}</td></tr>
		<tr><td>Confirmer le nouveau password: </td><td><input type="password" name="confirmPassword" /></td><td>${errorEmpty}${errorDiff}</td></tr>
		<form:hidden path="id" />
		<tr><td><input type="submit" value="changer"/></td><td><a href="user?username=${user.userName}">Annuler</a></td></tr>
	</table>
	</form:form>
</body>
</html>