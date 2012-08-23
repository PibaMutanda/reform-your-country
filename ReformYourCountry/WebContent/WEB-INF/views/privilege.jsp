<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c' %>
    <%@ taglib uri='/WEB-INF/tags/ryc.tld' prefix='ryc'%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Privilege</title>
</head>
<body>
<ryc:assertPrivilege privilege="MANAGE_USERS" />
<h1>Liste des Privileges pour ${user.userName }</h1><br>
<h2>Roles :</h2><br>

	<form action="roleeditsubmit" method="post">
		<input type="hidden" name="id" value="${user.id}">
		<input type="radio" name="role" value="ADMIN" <c:if test="${user.role == 'ADMIN'}">checked="checked"</c:if>>Administrateur	<br>
		<input type="radio" name="role" value="MODERATOR" <c:if test="${user.role == 'MODERATOR'}">checked="checked"</c:if>>Modérateur<br>
		<input type="radio" name="role" value="USER" <c:if test="${user.role == 'USER'}">checked="checked"</c:if>>Utilisateur<br>
		<input type="submit" value="Changer rôle"><br>
	</form>
	
	<form action="privilegeeditsubmit" method="post">
		<input type="hidden" name ="id" value="${user.id}">
		<table border="1">
		<c:forEach items="${privilegetriplets}" var="triplets">
		<tr>
			<td><c:if test="${user.role.isLowerOrEquivalent(triplets.getRole()) && user.role != triplets.role}">
				<input type="checkbox" name="${triplets.privilege.name()}" value="${triplets.privilege.name}"<c:if test="${triplets.permitted}">checked="checked"</c:if>>
			</c:if></td>
			<td><c:if test="${user.role.isHigerOrEquivalent(triplets.getRole())}">Dérivé du rôle</c:if></td>
			<td>${triplets.privilege.name}<br></td>
		</tr>
		</c:forEach>
		</table>
		<input type="submit" value="Enregistrer">
	</form>

</body>
</html>