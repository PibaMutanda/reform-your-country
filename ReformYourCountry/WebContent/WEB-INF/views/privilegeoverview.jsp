<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c'%>
<%@ taglib uri='/WEB-INF/tags/ryc.tld' prefix='ryc'%>
<%@ taglib tagdir="/WEB-INF/tags/ryctag/" prefix="ryctag"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Rôles et privilèges</title>
</head>
<body>
	<ryctag:pageheadertitle title="Liste des utilisateurs ayant un rôle ou un privilège spécial"></ryctag:pageheadertitle>
	<table border="1">
		<tr>
			<th>Pseudo</th>
			<th>Role</th>
			<th>Privileges</th>
			<th>Editer privileges</th>
		</tr>
		<c:forEach items="${infoList}" var="info">
			<tr>
				<td><a href= "/user/${info.user.userName}">${info.user.userName}</a></td>
				<td>${info.user.role}</td>
				<td>${info.privileges}</td>
				<td><a href= "/user/privilegeedit?id=${info.user.id}">Editer</a></td>
			</tr>
		</c:forEach>
	</table>
</body>
</html>