<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri='/WEB-INF/tags/ryc.tld' prefix='ryc'%>
<%@ taglib tagdir="/WEB-INF/tags/ryctag/" prefix="ryctag"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
<meta name="description" lang="fr" content="${action.shortDescription}"/>
<meta name="robots" content="index, follow"/>
<script type="text/javascript" src="js/int/voteaction.js"></script>
<script type="text/javascript" src="js/ext/d3.v2.min.js"></script>
<script src="js/ext/jquery-bubble-popup-v3.min.js" type="text/javascript"></script>
<link href="css/ext/jquery-bubble-popup-v3.css" rel="stylesheet" type="text/css" />
</head>
<body>

<script type="text/javascript">
   var idUser = "${current.user.id}";
</script>
    <ryctag:pageheadertitle title="${action.title}"/>

    <%-- Action text --%>
	<div style=" width: 100%;">
			<div style="font-size:1.3em;">
				${action.content}
			</div>
			<div>
				<div style=" ">
					
					<div style="width:100%">
						<a href="/action" style="font-size:0.8em;">retour à la liste des actions</a>
							<ryc:conditionDisplay privilege="EDIT_ACTION">
								- <a href="/action/edit?id=${action.id}" style="font-size:0.8em;">éditer</a>
							</ryc:conditionDisplay>
						
 						<ryctag:form action="/action/edit" modelAttribute="action" method="get" width="50px;"> 
 							<input type="hidden" value="${action.id}" name="id" id="idAction" /> 
						</ryctag:form> 
					</div>
					
				</div>
			</div>
	</div>
		
   <%-- Chart --%>
	<div id="voteGraph" style=" width: 500px; margin-left: 150px;background:url(/images/_global/separator3.gif) 0 0 repeat-x ;padding-top:10px;">
	</div>
	<div id="voteContainer" > <%-- Will be re-filled through Ajax too--%>
	    <%@include file="voteaction.jsp"%>
	</div>
	
	<%-- Arguments --%>
	<div id="argContainer"><%-- Will be re-filled through Ajax--%>
		<%@include file="argument.jsp" %>
	</div>
<%-- <%@include file="argumentcomment.jsp"%> --%>
</body>
</html> 