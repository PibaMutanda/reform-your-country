<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/tags/form"  prefix="form"%>

<html>
<head>
<title>List of groups</title>
</head>
<body>
	<h1>Liste des groupes</h1>
	<table>
		<c:forEach items="${groupList}" var="group">
			<tr>
				<td>${group.name}</td>
				<td>: <a href="group?id=${group.id}">Détails</a></td>
			</tr>
		</c:forEach>
	</table>
</body>
</html>