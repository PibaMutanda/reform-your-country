﻿<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
    <%@ taglib tagdir="/WEB-INF/tags/ryctag/" prefix="ryctag" %>
	<%@ taglib uri='/WEB-INF/tags/ryc.tld' prefix='ryc'%>
<html>
<head>
<!-- you can set variables starting with "p_" in the file named website_content.properties -->
<meta name="description" content="Utilisateurs de ${p_website_name}">
<meta name="Keywords" content="utilisateur, informations" />
<meta name="robots" content="index, follow" />
<meta name="googlebot" content="noarchive"/>
</head>
<body>
<ryctag:pageheadertitle title="Rechercher des utilisateurs"></ryctag:pageheadertitle>
	<ryc:conditionDisplay privilege="MANAGE_USERS">
		 <a href="/privilegeoverview">voir rôles et privilèges des utilisateurs</a>
	</ryc:conditionDisplay>
	<form action="/user" method="GET">
		prénom, nom ou pseudo <input type="text" name="name"/> 
		<input type="submit" value="rechercher" />
	</form>
	<table>
		<c:forEach items="${userList}" var="user">
			<tr>
				<td>${user.userName}</td>
				<td>${user.firstName}</td>
				<td>${user.lastName}</td>
				<td><a href="/user/${user.userName}">Afficher les détails</a></td>
			</tr>
		</c:forEach>
	</table>
</body>
</html>