<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
    <%@ taglib uri="http://www.springframework.org/tags/form"  prefix="form"%>
    <%@ taglib tagdir="/WEB-INF/tags/ryctag/" prefix="ryctag" %>
    
    

<html>
<head>

	<title>Changer de mot de passe</title>
</head>
<body>
<!-- <h1>Formulaire de changement de password</h1> -->
<ryctag:pageheadertitle title="changement de mot de passe">
	<ryctag:breadcrumbelement label="${user.firstName} ${user.lastName}" link="user/${user.userName}" />
	<ryctag:breadcrumbelement label="changement de mot de passe" />
</ryctag:pageheadertitle>

	<form:form modelAttribute="user" action="user/changepasswordsubmit">
	<table>
		<tr><td>Password actuel: </td><td><input type="password" name="oldPassword" /></td><td>${errorNoOld}</td></tr>
		<tr><td>Nouveau password: </td><td><input type="password" name="newPassword" /></td><td>${errorEmpty}${errorDiff}</td></tr>
		<tr><td>Confirmer le nouveau password: </td><td><input type="password" name="confirmPassword" /></td><td>${errorEmpty}${errorDiff}</td></tr>
		<form:hidden path="id" />
		<tr><td><input type="submit" value="changer"/></td><td><a href="user/${user.userName}">Annuler</a></td></tr>
	</table>
	</form:form>
</body>
</html>