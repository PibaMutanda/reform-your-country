<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c' %>
<%@ taglib uri='/WEB-INF/tags/ryc.tld' prefix='ryc'%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>UserPage</title>
</head>
<body>
<h1>${user.userName }</h1>
	<ryc:conditionDisplay privilege="MANAGE_USERS">
		<form action="privilegeedit" method="get">
			<input type="hidden" name="id" value="${user.id}">
			 <input type="submit" value="Editer privilèges">
		</form>
	</ryc:conditionDisplay>
	
	<form action="useredit" method="get">
		<input type="hidden" value="${user.id}" name="id" /><br /> <input
			type="submit" value="Modifier le Profil" />
	</form>

Nom : ${user.lastName}<br>
Prénom : ${user.firstName}<br>
Pseudo : ${user.userName}<br>
Date de naissance : ${user.birthDate}<br>
Genre : ${user.gender}<br>
Date d'enregistrement : ${user.registrationDate}<br>
Rôle : ${user.role}<br>
Dernier accès : ${user.lastAccess}<br><br> 
mail : ${user.mail}<br>
Dernière IP d'accès : ${user.lastLoginIp}<br>
Status du compte : ${user.accountStatus}<br>
Raison blocage compte : ${user.lockReason}<br>

</body>
</html>