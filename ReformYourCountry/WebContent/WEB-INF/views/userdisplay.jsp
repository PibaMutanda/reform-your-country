<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c' %>
<%@ taglib uri='/WEB-INF/tags/ryc.tld' prefix='ryc'%>
<%@ taglib tagdir="/WEB-INF/tags/ryctag/" prefix="ryctag" %>
<%@taglib uri="http://www.springframework.org/tags/form"  prefix="form"%>
<html>
<head>
<title>UserPage</title>
</head>
<body>
<h1>${user.userName }</h1>
	<ryc:conditionDisplay privilege="MANAGE_USERS">
		<form action="privilegeedit" method="get">
			<input type="hidden" value="${user.id}" name="id" />
			<input	type="submit" value="Editer privilèges" />
		</form>
	</ryc:conditionDisplay>

	<c:if test="${canEdit}">
		<form action="useredit" modelAttribute="user" method="get">
			<input type="hidden" value="${user.id}" name="id" />
			<input type="submit" value="Modifier le Profil" />
		</form>

		<form action="userchangepassword" modelAttribute="user">
			<input type="hidden" value="${user.id}" name="id" />
			<input type="submit" value="Modifier le mot de passe" />
		</form>


		<a href= "userimage?id=${user.id}">Upload image</a><br/><br/>
	</c:if>

	<c:if test="${user.picture}">
		<img alt="${user.userName}"
			src="/ReformYourCountry/gen/user/resized/large/${user.id}.jpg"
			style="float: left" />
	</c:if>

	Nom : ${user.lastName}
	<br /> Prénom : ${user.firstName}
	<br /> Pseudo : ${user.userName}
	<br> Date de naissance : ${user.birthDate}
	<br /> Genre : ${user.gender}
	<br /> Date d'enregistrement : ${user.registrationDate}
	<br /> Rôle : ${user.role}
	<br /> Dernier accès : ${user.lastAccess}
	<br />
	<br />

	<c:if test="${canEdit}">
mail : ${user.mail}<br />
Dernière IP d'accès : ${user.lastLoginIp}<br />
Status du compte : ${user.accountStatus}<br />
Raison blocage compte : ${user.lockReason}<br />
	</c:if>


</body>
</html>