<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c' %>
    <%@ taglib uri='/WEB-INF/tags/security.tld' prefix='ryc'%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Privilege</title>
</head>
<body>
<ryc:security privilege="MANAGE_USERS">
<h1>Liste des Privileges pour ${user.userName }</h1><br>
<h2>Roles :</h2><br>

	<form action="editprivilege">
		<input type="radio" name="role" value="ADMIN" <c:if test="${role == 'ADMIN'}">checked="checked"</c:if>>Administrateur	<br>
		<input type="radio" name="role" value="MODERATOR" <c:if test="${role == 'MODERATOR'}">checked="checked"</c:if>>Modérateur<br>
		<input type="radio" name="role" value="USER" <c:if test="${role == 'USER'}">checked="checked"</c:if>>Utilisateur<br>
		<input type="submit" value="Sélectionner rôle"><br>
	</form>
	<form action="saveprivilege" method="post">		
		<input type="hidden" name ="id" value="${user.id}">
		<table border="1">
		<c:forEach items="${privilegetriplets}" var="triplets">
		<tr>
			<td><c:if test="${role != triplets.getRole()}">
				<input type="checkbox" name="${triplets.getPrivilege().name()}" value="${triplets.getPrivilege().name}"<c:if test="${triplets.isPermitted()}">checked="checked"</c:if>>
			</c:if></td>
			<td><c:if test="${role == triplets.getRole()}">Dérivé du rôle</c:if></td>
			<td>${triplets.getPrivilege().name}<br></td>
		</tr>
		</c:forEach>
		</table>
		<input type="submit" value="Enregistrer">
	</form>
</ryc:security>
</body>
</html>