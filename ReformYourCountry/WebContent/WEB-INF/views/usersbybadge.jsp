<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri='/WEB-INF/tags/ryc.tld' prefix='ryc'%>
<%@ taglib tagdir="/WEB-INF/tags/ryctag/" prefix="ryctag"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
<head>
<title>Insert title here</title>
<link rel="stylesheet" href="css/int/content.css"  type="text/css" />
</head>
<body>
  
	<ryctag:badge badgeType="${badgeType}"/> ${badgeType.description} - 
	    <c:choose>
	     <c:when test="${badges.size()==0 || badges.size()==1}">${badges.size()}: utilisateur a gagné ce badge.<br/> 
	    </c:when>
	   		 <c:otherwise> ${badges.size()}: utilisateurs ont gagné ce badge. <br/>
	   		 </c:otherwise>
	   </c:choose>
	    
	<table style='width:100%'>    
	<% int i = 0;%>   
   		<c:forEach items="${badges}" var="badge">
    	    <c:set var="u" value="<%=i%>"/>
    	    <c:if test="${(u mod 4) == 0}"><tr></c:if>
   		    <td><ryctag:user user="${badge.user}"></ryctag:user></td>
       	    <c:if test="${(u mod 4) == 3}"></tr></c:if>
   		    <%  i=i+1; %>
		</c:forEach>
    </table>
    

      
</body>
</html>