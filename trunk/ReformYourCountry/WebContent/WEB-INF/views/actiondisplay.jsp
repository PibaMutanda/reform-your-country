<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri='/WEB-INF/tags/ryc.tld' prefix='ryc'%>
<%@ taglib tagdir="/WEB-INF/tags/ryctag/" prefix="ryctag"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
<meta name="description" lang="fr" content="${action.shortDescription}"/>
<meta name="robots" content="index, follow"/>
</head>
<body>
<ryctag:pageheadertitle title="${action.title}"/>
	<div>
		<div style="display: inline-block; width: 400px;">
			<ryc:conditionDisplay privilege="EDIT_ACTION">
				<ryctag:form action="/action/edit" modelAttribute="action" method="get">
					<input type="hidden" value="${action.id}" name="id" id="id" />
					 <input	type="submit" value="Modifier action" />
				</ryctag:form>
			</ryc:conditionDisplay>
	<form action="/action" method="post">
			<input type="submit" value="Liste des actions" />
	</form>
			Contenu : ${action.content}<br /> Description brève :
			${action.shortDescription}<br /> Description étendue :
			${action.longDescription}<br />
		</div>
	</div>
	<div id="voteContainer"> <%-- Will be re-filled through Ajax too --%>
	  <%@include file="voteaction.jsp"%>
	</div>
	
	<div id="argContainer"> <%-- Will be re-filled through Ajax --%>
		<%@include file="argument.jsp" %>
	</div>
</body>
</html> 