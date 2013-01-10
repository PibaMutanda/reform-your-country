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
	    <div class="page-menu-links">
	       <a href="/action/create">Créer une action</a>
	    </div>
	</ryc:conditionDisplay>

	<div class="actionList">
		<c:forEach items="${actionItems}" var="actionItem">
			<div class="actionRow">
			 
				<ryctag:separator/>
				
				<div class="actionTitle"><a href="/action/${actionItem.action.url}">${actionItem.action.title}</a></div>
						
				<div style="float:right;">
				    <c:set var="vote" value="${actionItem.voteAction}" scope="request"/>
				    <c:set var="id" value="${actionItem.action.id}" scope="request" />
		            <%@include file="voteactionwidget.jsp"%>
		           
	            </div>
	            <div class="actionShortDescription">${actionItem.action.shortDescription}</div>    
				
			</div>
			

		</c:forEach>
	</div>
	
</body>
</html>