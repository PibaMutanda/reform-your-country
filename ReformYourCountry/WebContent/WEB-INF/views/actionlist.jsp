<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
	<%@ taglib uri='/WEB-INF/tags/ryc.tld' prefix='ryc'%>
	<%@ taglib tagdir="/WEB-INF/tags/ryctag/" prefix="ryctag" %>
<html>
<head>
<meta name="description" lang="fr" content=""/>
<meta name="robots" content="index, follow"/>	
<meta name="googlebot" content="noarchive" />
</head>
<body>	
<ryctag:pageheadertitle title="Liste des actions"/>
	
	<ryc:conditionDisplay privilege="EDIT_ACTION">
		<form action="/action/create" method="post" >
			<input type="hidden" name="id" value="">
			<input type="submit"  value="Créer action" />
		</form>
	</ryc:conditionDisplay>

	<table>
		<c:forEach items="${actions}" var="action">
			<tr>
				<td>${action.title}</td>			
				<td><a href="/action/${action.url}">Détails</a></td>
			</tr>
			

		</c:forEach>
	</table>
	
</body>
</html>