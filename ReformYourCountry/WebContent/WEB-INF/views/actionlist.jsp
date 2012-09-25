<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
	<%@ taglib uri='/WEB-INF/tags/ryc.tld' prefix='ryc'%>

<html>
<head>

<title>Liste des actions</title>
</head>
<body>	
	<h1>Liste des actions</h1>
	
	<ryc:conditionDisplay privilege="EDIT_ACTION">
		<form action="actioncreate" method="get" >
			<input type="hidden" name="id" value="">
			<input type="submit"  value="Créer action" />
		</form>
	</ryc:conditionDisplay>

	<table>
		<c:forEach items="${actions}" var="action">
			<tr>
				<td>${action.title}</td>			
				<td><a href="action?id=${action.id}">Détails</a></td>
			</tr>
			

		</c:forEach>
	</table>
	
</body>
</html>