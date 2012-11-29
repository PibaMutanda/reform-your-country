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
	
	<ryc:conditionDisplay privilege="MANAGE_ACTION">
		<form action="/action/create" method="post" >
			<input type="hidden" name="id" value="">
			<input type="submit"  value="Créer action" />
		</form>
	</ryc:conditionDisplay>

	<div class="actionList">
		<c:forEach items="${actionItems}" var="actionItem">
			<div class="actionRow">
			 
				<div class="actionTitle"><a href="/action/${actionItem.action.url}">${actionItem.action.title}</a></div>
						
				<div id="voteContainer" style="float:right;">
				    <c:set var="vote" value="${actionItem.voteAction}" scope="request"/>
				    <c:set var="resultNumbers" value="${actionItem.resultNumbers}" scope="request" />
				    <c:set var="id" value="${actionItem.action.id}" scope="request" />
		            <%@include file="voteactionwidget.jsp"%>
		           
	            </div>
	            <div class="actionShortDescription">${actionItem.action.shortDescription}</div>    
				
			</div>
			

		</c:forEach>
	</div>
	
</body>
</html>