<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@taglib uri="http://www.springframework.org/tags/form"  prefix="form"%>
<%@ taglib tagdir="/WEB-INF/tags/ryctag/" prefix="ryctag" %>

<html>
<head>

</head>
<body>

<ryctag:pageheadertitle title="Dernières modifications"/>
 
  <div style="background:url(images/_global/separator3.gif) 0 0 repeat-x;"> 
  <table>
  	
  	<thead>
 		 <tr>
  			<th>Titre</th>
   			<th>création/dernière modification</th>
    		<th>crée/modifié par</th>
  		 </tr>
  	</thead>
    <tbody>
     <c:forEach items="${listlastevent}" var="event">
		 <tr>
		    <td><a href="${event.url}">${event.name}</a></td>
		    <td style="width:172px"><fmt:formatDate pattern="yyyy-MM-dd hh:mm:ss" value="${event.date}"/></td>
		    <td style="width:150px"><ryctag:user user="${event.user}"></ryctag:user></td>
		 </tr>
     </c:forEach> 
    </tbody>
  </table>
 </div>
	

</body>
</html>