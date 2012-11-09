<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
<title>List All User Badges</title>
</head>
<body>
<table>
<c:forEach items="${badges}" var="badge">	
<tr>
    <td>${badge.name}</td> 
	<td>${badge.description}</td>
	<td>${badge.badgeTypeLevel}</td>
</tr>
</c:forEach>
</table>
</body>
</html>