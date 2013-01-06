<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/tags/form"  prefix="form"%>
<%@ taglib uri='/WEB-INF/tags/ryc.tld' prefix='ryc'%>
<%@ taglib tagdir="/WEB-INF/tags/ryctag/" prefix="ryctag"%>

<html>
<head>
<title>Liste des groupes</title>
</head>
<body>
	<ryctag:pageheadertitle title="Liste des groupes"/>
	
	<ryc:conditionDisplay privilege="MANAGE_GROUP">
		<a href="/groupcreate?id=" style="font-size:12px">créer</a><br/>
	</ryc:conditionDisplay>
	
	<table>
		<c:forEach items="${groupList}" var="group">
			<tr>
				<td><a href="/group?id=${group.id}">${group.name}</a></td>
		
			</tr>
		</c:forEach>
	</table>
</body>
</html>