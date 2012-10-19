<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c' %>
    <%@ taglib uri='/WEB-INF/tags/ryc.tld' prefix='ryc'%>
    <%@ taglib tagdir="/WEB-INF/tags/ryctag/" prefix="ryctag" %>
<html>
<head>
<title>Privilege</title>
</head>
<body>

<ryctag:pageheadertitle title="Privileges de ${user.userName }">
	<ryctag:breadcrumbelement label="${user.firstName} ${user.lastName}" link="user/${user.userName}" />
	<ryctag:breadcrumbelement label="Permission" />
</ryctag:pageheadertitle>

<h2>Roles :</h2><br>

	<form action="/user/roleeditsubmit" method="post">
		<input type="hidden" name="id" value="${user.id}">
		<input type="radio" name="role" value="ADMIN" <c:if test="${user.role == 'ADMIN'}">checked="checked"</c:if>>Administrateur	<br>
		<input type="radio" name="role" value="MODERATOR" <c:if test="${user.role == 'MODERATOR'}">checked="checked"</c:if>>Modérateur<br>
		<input type="radio" name="role" value="USER" <c:if test="${user.role == 'USER'}">checked="checked"</c:if>>Utilisateur<br><br/>
		<input type="submit" value="Changer rôle"><br>
	</form>
	<br/>
	<br/>
	<br/>
	<form action="/user/privilegeeditsubmit" method="post">
		<input type="hidden" name ="id" value="${user.id}">
		<table border="1">
		<c:forEach items="${privilegetriplets}" var="triplets">
		<tr>
			<td><c:if test="${user.role.isLowerOrEquivalent(triplets.getRole()) && user.role != triplets.role}">
				<input type="checkbox" name="${triplets.privilege.name()}" value="${triplets.privilege.name}"<c:if test="${triplets.permitted}">checked="checked"</c:if>>
			</c:if></td>
			<td><c:if test="${user.role.isHigherOrEquivalent(triplets.getRole())}">Dérivé du rôle</c:if></td>
			<td>${triplets.privilege.name}<br></td>
		</tr>
		</c:forEach>
		</table><br/>
		<input type="submit" value="Enregistrer"><a href="/user/${user.userName}">  Annuler</a>
	</form>

</body>
</html>