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
	
		<ryctag:form action="privilegeedit" modelAttribute="user">
			<form:hidden path="id" value="${user.id}"/>
			<input	type="submit" value="Editer privilèges" />
		</ryctag:form>
	</ryc:conditionDisplay>
	<ryctag:form action="useredit" modelAttribute="user">
		<form:hidden path="id" value="${user.id}"/>
		<input	type="submit" value="Modifier le Profil" />
	</ryctag:form>
	<form action="userchangepassword" method="get">
		<input type="hidden" value="${user.id}" name="id" /><br /> <input
			type="submit" value="Modifier le password" />
	</form>

Nom : ${user.lastName}<br/>
Prénom : ${user.firstName}<br/>
Pseudo : ${user.userName}<br>
Date de naissance : ${user.birthDate}<br/>
Genre : ${user.gender}<br/>
Date d'enregistrement : ${user.registrationDate}<br/>
Rôle : ${user.role}<br/>
Dernier accès : ${user.lastAccess}<br/><br/> 

<ryc:conditionDisplay privilege="MANAGE_USERS">

mail : ${user.mail}<br/>
Dernière IP d'accès : ${user.lastLoginIp}<br/>
Status du compte : ${user.accountStatus}<br/>
Raison blocage compte : ${user.lockReason}<br/>

</ryc:conditionDisplay>


</body>
</html>