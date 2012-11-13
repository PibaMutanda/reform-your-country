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

		<div style="background:url(/images/_global/separator3.gif) 0 0 repeat-x ;display: inline-block; width: 800px;">
		<div>
			<h5>${action.title}</h5>
			<p>${action.content}</p>
		</div>
		<div >
			<div style="width:500px;float:right;">
				Créé le : ${action.getFormatedCreatedOn()} - Modifié le: ${action.getFormatedUpdatedOn()} 
			</div>
			<div style=" width:200px;">
				
				<div style="float:right;width:110px;">
					<a href="/action">Retour à la liste</a>
				</div>
				<div style="width:50px;">
					<ryctag:form action="/action/edit" modelAttribute="action" method="get" width="50px;">
						<input type="hidden" value="${action.id}" name="id" id="idAction" />
						<ryc:conditionDisplay privilege="EDIT_ACTION">
							<a href="/action/edit?id=${action.id}">Edit</a>
						</ryc:conditionDisplay>
					</ryctag:form>
				</div>
				
		</div>
			</div>
		</div>
		

	
	<div id="voteContainer" style="background:url(/images/_global/separator3.gif) 0 0 repeat-x ;padding-top:10px;"> <%-- Will be re-filled through Ajax too --%>
	  <%@include file="voteaction.jsp"%>
	</div>
	<div id="argContainer"  style="background:url(/images/_global/separator3.gif) 0 0 repeat-x ;padding-top:10px;"> <%-- Will be re-filled through Ajax --%>
		<%@include file="argument.jsp" %>
	</div>
</body>
</html> 