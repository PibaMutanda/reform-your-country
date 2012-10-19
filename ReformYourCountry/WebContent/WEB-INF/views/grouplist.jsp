<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/tags/form"  prefix="form"%>
<%@ taglib uri='/WEB-INF/tags/ryc.tld' prefix='ryc'%>

<html>
<head>
<title>Liste des groupes</title>
</head>
<body>
	<h1>Liste des groupes</h1>
	
	<ryc:conditionDisplay privilege="EDIT_GROUP">
		<form action="/groupcreate" method="get" >
			<input type="hidden" name="id" value="">
			<input type="submit"  value="Créer un nouveau groupe" />
		</form>
	</ryc:conditionDisplay>
	<table>
		<c:forEach items="${groupList}" var="group">
			<tr>
				<td>${group.name}</td>
				<td>: <a href="/group?id=${group.id}">Détails</a></td>
			</tr>
		</c:forEach>
	</table>
</body>
</html>