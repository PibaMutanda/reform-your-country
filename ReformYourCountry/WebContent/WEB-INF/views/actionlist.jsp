<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
	<%@ taglib uri='/WEB-INF/tags/ryc.tld' prefix='ryc'%>
	<%@ taglib tagdir="/WEB-INF/tags/ryctag/" prefix="ryctag" %>
<html>
<head>
<script type="text/javascript" src="js/int/votelistaction.js"></script>
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
		<c:forEach items="${actionItems}" var="actionItem">
			<tr>
			 
				<td>${actionItem.action.title}</td>
				<td>${actionItem.action.shortDescription}</td>		
				<td><div id="voteContainer" >
				    <c:set var="vote" value="${actionItem.voteAction}" scope="request"/>
				    <c:set var="resultNumbers" value="${actionItem.resultNumbers}" scope="request" />
				    <c:set var="id" value="${actionItem.action.id}" scope="request" />
		            <%@include file="voteactionwidget.jsp"%>
		           
	                </div>
	                
	                </td>
				<td><a href="/action/${actionItem.action.url}">Détails</a></td>
			</tr>
			

		</c:forEach>
	</table>
	
</body>
</html>