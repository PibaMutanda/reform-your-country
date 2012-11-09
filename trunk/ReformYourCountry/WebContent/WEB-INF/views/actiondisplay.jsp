<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri='/WEB-INF/tags/ryc.tld' prefix='ryc'%>
<%@ taglib tagdir="/WEB-INF/tags/ryctag/" prefix="ryctag"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
<meta name="description" lang="fr" content="${action.shortDescription}"/>
<meta name="robots" content="index, follow"/>
<link rel="stylesheet" type="text/css" href=".css">
</head>
<body>
<%-- <ryctag:pageheadertitle title="${action.title}"/> --%>
	<div>
		<div style="display: inline-block; width: 400px;">
				<ryctag:form action="/action/edit" modelAttribute="action" method="get">
					<input type="hidden" value="${action.id}" name="id" id="idAction" />
					
			<ryc:conditionDisplay privilege="EDIT_ACTION">
					 <input	type="submit" value="Modifier action" />
					 
			</ryc:conditionDisplay>
				</ryctag:form>
	<form action="/action" method="post">
			<input type="submit" value="Liste des actions" />
	</form>
			Description brève :
			${action.shortDescription}<br /> Contenu :
			${action.content}<br />
		</div>
	</div>
	
	
	<div id="voteContainer"> <%-- Will be re-filled through Ajax too --%>
	  <%@include file="voteaction.jsp"%>
	</div>
	<div class="errorMessage" id="errorArg"></div>
	<div id="argContainer"> <%-- Will be re-filled through Ajax --%>
		<%@include file="argument.jsp" %>
	</div>
	
</body>
</html> 