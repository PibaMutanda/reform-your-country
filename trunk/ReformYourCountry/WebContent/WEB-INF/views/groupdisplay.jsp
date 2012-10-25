<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c'%>
<%@ taglib uri='/WEB-INF/tags/ryc.tld' prefix='ryc'%>
<%@ taglib tagdir="/WEB-INF/tags/ryctag/" prefix="ryctag"%>
<html>
<head>
<title>${group.name}</title>
</head>
<body>

<h1>Nom du groupe : ${group.name}</h1>	
Description : ${group.description} <br/>
Url : ${group.url} <br/>

<c:if test="${group.hasImage==true}">
   <img src="/gen/group/resized/${group.id}.jpg?random=${random}"  <%-- Random, to force the reload of the image in case it changes (but its name does not change) --%>
        alt="${group.name}" class="imggroup">
</c:if>
 
<ryc:conditionDisplay privilege="EDIT_GROUP">
		<ryctag:submit entity="${group}" value="Editer" action="/groupedit" />
		
		<%-- IMAGE --%>
		<form method="post" action="/groupimageadd" enctype="multipart/form-data">
			<input type="file" name="file" /> 
			
			<input type="hidden" name="id" value="${group.id}" />
			<input type="submit" value="Uploader une image" />
		</form>
		<form method="get" action="/groupimagedelete">
			<input type="hidden" name="id" value="${group.id}" /> <br>
			<input type="submit" value="Supprimer une image" />
		</form>
		<form method="get" action="/groupremove">
			<input type="hidden" name="id" value="${group.id}" /> <br>
			<input type="submit" value="Supprimer un group" />
		</form>
</ryc:conditionDisplay>
	    
La liste des membres<br /> 
<table>
     <c:forEach items="${groupRegList}" var="gReg">
        <tr><td>${gReg.user.firstName}</td></tr>
     </c:forEach> 
</table>
	    
	    
<br><a href="/grouplist">Liste des groupes</a>
</body>
</html>