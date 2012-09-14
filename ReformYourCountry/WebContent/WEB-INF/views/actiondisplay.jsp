<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri='/WEB-INF/tags/ryc.tld' prefix='ryc'%>
<html>
<head>
<title>ActionPage</title>
</head>
<body>

	<h1>${action.title}</h1>
	<div>
		<div style="display: inline-block; width: 400px;">


			<ryc:conditionDisplay privilege="EDIT_ACTION">
				<form action="actionedit" modelAttribute="action" method="get">
					<input type="hidden" value="${action.id}" name="id" id="id" />
					 <input	type="submit" value="Modifier action" />
				</form>
			</ryc:conditionDisplay>
	<form action="actionlist" method="get">
			<input type="submit" value="Liste des actions" />
	</form>
			Contenu : ${action.content}<br /> Description brève :
			${action.shortDescription}<br /> Description étendue :
			${action.longDescription}<br /> URL : <a href="${action.url}">${action.url}</a><br />
		</div>
	</div>
	
	<div id="voteContainer"> <%-- Will be re-filled through Ajax --%>
	  <%@include file="voteaction.jsp"%>
	</div>
</body>
</html>